package com.fimet.parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fimet.commons.exception.ParserException;
import com.fimet.commons.history.History;
import com.fimet.core.IBindingManager;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.AbstractMessageParser;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.entity.sqlite.IRuleValue;
import com.fimet.core.entity.sqlite.Parser;
import com.fimet.parser.extract.impl.ExtractParser;
import com.fimet.parser.msg.iso.impl.DiscoverParser;
import com.fimet.parser.msg.iso.impl.MasterCardParser;
import com.fimet.parser.msg.iso.impl.NacionalParser;
import com.fimet.parser.msg.iso.impl.TpvParser;
import com.fimet.parser.msg.iso.impl.VisaParser;
import com.fimet.persistence.sqlite.dao.ParserDAO;

public class ParserManager implements IParserManager {
	Map<Integer,IParser> mapIdParsers = new HashMap<>();
	Map<String,Integer> mapNameId = new HashMap<>();
	Map<Integer,IParser> mapHashParsers = new HashMap<>();
	public ParserManager() {
		super();
	}

	@Override
	public IParser getParser(String name) {
		if (mapNameId.containsKey(name)) {
			return mapIdParsers.get(mapNameId.get(name));
		} else {
			Parser entity = findEntity(name);
			IParser parser = newParser(entity);
			mapIdParsers.put(parser.getId(), parser);
			mapNameId.put(parser.getName(),parser.getId());
			mapHashParsers.put(entity.hashCode(), parser);
			return parser;
		}
	}

	@Override
	public IParser getParser(Integer idParser) {
		if (mapIdParsers.containsKey(idParser)) {
			return mapIdParsers.get(idParser);
		} else {
			Parser entity = findEntity(idParser);
			IParser parser = newParser(entity);
			mapIdParsers.put(parser.getId(), parser);
			mapNameId.put(parser.getName(),parser.getId());
			mapHashParsers.put(entity.hashCode(), parser);
			return parser;
		}
	}
	@Override
	public IParser getParser(Parser entity) {
		int hashCode = entity.hashCode();
		if (mapHashParsers.containsKey(hashCode)) {
			return mapHashParsers.get(hashCode);
		} else {
			IParser parser = newParser(entity);
			mapIdParsers.put(parser.getId(), parser);
			mapHashParsers.put(hashCode,parser);
			return parser;			
		}
	}
	private Parser findEntity(Integer idParser) {
		Parser entity = ParserDAO.getInstance().findById(idParser);
		if (entity != null) {
			return entity;
		} else {
			throw new ParserException("Invalid parser id: "+idParser);
		}
	}
	private Parser findEntity(String parser) {
		Parser entity = ParserDAO.getInstance().findByName(parser);
		if (entity != null) {
			return entity;
		} else {
			throw new ParserException("Unknow parser: "+parser);
		}
	}
	@SuppressWarnings("unchecked")
	private IParser newParser(Parser entity) {
		try {
			Class<? extends AbstractMessageParser> parserClass = (Class<? extends AbstractMessageParser>) Class.forName(entity.getParserClass());
			Constructor<? extends AbstractMessageParser> constructor = parserClass.getConstructor(com.fimet.core.entity.sqlite.Parser.class);
			return constructor.newInstance(entity);
		} catch (ClassNotFoundException e) {
			throw new ParserException("Invalid Parser class: " + entity.getParserClass(),e);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ParserException("Invalid Parser class: " + entity.getParserClass(),e);
		} catch (NoSuchMethodException e) {
			throw new ParserException("No found public constructor with com.fimet.core.entity.sqlite.Parser.class as argument in class: " + entity.getParserClass(),e);
		} catch (SecurityException e) {
			throw new ParserException("Invalid Parser class: " + entity.getParserClass(),e);
		} catch (IllegalArgumentException | InvocationTargetException e) {
			throw new ParserException("Invalid Parser class: " + entity.getParserClass(),e);
		}
	}

	@Override
	public List<IParser> getParsers() {
		List<Parser> entities = ParserDAO.getInstance().findAll();
		if (entities != null) {
			List<IParser> ps = new ArrayList<>();
			for (Parser e : entities) {
				if (mapIdParsers.containsKey(e.getId())) {
					ps.add(mapIdParsers.get(e.getId()));
				} else {
					ps.add(getParser(e));
				}
			}
			return ps;
		} else {
			return null;
		}
	}
	private IParser uninstall(Integer idParser) {
		if (mapIdParsers.containsKey(idParser)) {
			IParser pold = mapIdParsers.remove(idParser);
			mapNameId.remove(pold.getName());
			mapHashParsers.remove(pold.hashCode());
			Manager.get(IBindingManager.class).uninstall(pold);
			return pold;
		} else {
			return null;
		}
	}
	private IParser install(Integer idParser) {
		if (!mapIdParsers.containsKey(idParser)) {
			IParser parser = getParser(idParser);
			Manager.get(IBindingManager.class).install(parser);
			return parser;
		} else {
			return getParser(idParser);
		}
	}
	@Override
	public void reload(Integer idParser) {
		uninstall(idParser);
		install(idParser);
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {}
	public Class<?>[] getParserClasses() {
		return new Class<?>[] {NacionalParser.class,VisaParser.class, MasterCardParser.class, TpvParser.class, DiscoverParser.class, ExtractParser.class};
	}
	@Override
	public List<Parser> getEntities() {
		return ParserDAO.getInstance().findAll();
	}
	@Override
	public Parser insert(Parser parser) {
		if (parser.getId() == null)
			parser.setId(getNextIdParser());
		ParserDAO.getInstance().insert(parser);
		return parser;
	}
	@Override
	public Parser update(Parser parser) {
		ParserDAO.getInstance().update(parser);
		reload(parser.getId());
		return parser;
	}
	@Override
	public Parser delete(Parser parser) {
		ParserDAO.getInstance().delete(parser);
		uninstall(parser.getId());
		return parser;
	}
	@Override
	public void free(List<Integer> groups) {
		List<Integer> ids = new ArrayList<>();
		for (Entry<Integer, IParser> e : mapIdParsers.entrySet()) {
			if (groups.contains(e.getValue().getIdGroup())) {
				ids.add(e.getKey());
			}
		}
		List<Integer> hashes = new ArrayList<>();
		for (Entry<Integer, IParser> e : mapHashParsers.entrySet()) {
			if (groups.contains(e.getValue().getIdGroup())) {
				hashes.add(e.getKey());
			}
		}
		IParser parser = null;
		for (Integer id : ids) {
			parser = mapIdParsers.remove(id);
			if (parser != null)
				mapNameId.remove(parser.getName());
		}
		for (Integer hash : hashes) {
			parser = mapHashParsers.remove(hash);
			if (parser != null)
				mapNameId.remove(parser.getName());
			if (!ids.contains(parser.getId())) {
				ids.add(parser.getId());
			}
		}
		IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
		for (Integer idParser : ids) {
			fieldParserManager.deleteCache(idParser);
		}
	}

	@Override
	public Integer getNextIdParser() {
		return ParserDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdParser() {
		return ParserDAO.getInstance().getPrevSequenceId();
	}
	
	@Override
	public Object getBind(Integer id) {
		return id != null ? getParser(id) : null;
	}
	@Override
	public List<IRuleValue> getValues() {
		List<Parser> parsers = ParserDAO.getInstance().findAll();
		if (parsers != null && !parsers.isEmpty()) {
			List<IRuleValue> results = new ArrayList<>(parsers.size());
			for (Parser a : parsers) {
				results.add((IRuleValue)a);
			}
			return results;
		}
		return null;
	}

	@Override
	public Parser getEntity(Integer id) {
		return ParserDAO.getInstance().findById(id);
	}

	@Override
	public void commit(History<Parser> history) {
		for (Parser m : history.getDeletes()) {
			delete(m);
		}
		for (Parser m : history.getUpdates()) {
			update(m);
		}
		for (Parser m : history.getInserts()) {
			insert(m);
		}		
	}
}
