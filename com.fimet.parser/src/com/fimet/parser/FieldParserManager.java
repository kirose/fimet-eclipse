package com.fimet.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.ParserException;
import com.fimet.core.IFieldFormatManager;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.TrimFixedFieldParser;
import com.fimet.core.ISO8583.parser.TrimVarFieldParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.parser.field.impl.mc.MC48VarFieldParser;
import com.fimet.parser.field.impl.mc.MCTagVarFieldParser;
import com.fimet.parser.field.impl.mc.MCTagsVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTagFixedFieldParser;
import com.fimet.parser.field.impl.nac.NacTagVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTagsVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTokenEZVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTokenVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTokensVarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTag55VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTag56VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTags55VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTags56VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokenEZVarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokenQKVarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokenVarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokensVarFieldParser;
import com.fimet.parser.field.impl.visa.Visa126VarFieldParser;
import com.fimet.parser.field.impl.visa.Visa62VarFieldParser;
import com.fimet.parser.field.impl.visa.Visa63VarFieldParser;
import com.fimet.parser.field.impl.visa.VisaDatasetVarFieldParser;
import com.fimet.parser.field.impl.visa.VisaDatasetsVarFieldParser;
import com.fimet.parser.field.impl.visa.VisaTagVarFieldParser;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class FieldParserManager implements IFieldParserManager {
	private IFieldFormatManager fieldFormatGroupManager = Manager.get(IFieldFormatManager.class); 
	private Map<String, IFieldParser> cache = new HashMap<>();
	
	public FieldParserManager() {
		super();
	}
	/**
	 * 
	 * @param idParser
	 * @param idField
	 * @param reader
	 * @return
	 */
	public byte[] parse(Integer idField, IReader reader, IMessage message) {
		return getFieldParser(message.getParser().getIdGroup(), idField+"").parse(reader, message);
	}
	/**
	 * 
	 * @param msgParser
	 * @param index
	 * @return
	 */
	public byte[] parse(String idField, IReader reader, IMessage message) {
		return getFieldParser(message.getParser().getIdGroup(), idField).parse(reader, message);
	}
	/**
	 * 
	 * @param msgParser
	 * @param index
	 * @return
	 */
	public void format(IMessage message, String idField, IWriter writer) {
		getFieldParser(message.getParser().getIdGroup(), idField).format(writer, message);
	}
	public void format(IMessage message, int idField, IWriter writer) {
		getFieldParser(message.getParser().getIdGroup(), ""+idField).format(writer, message);
	}
	public IFieldParser getFieldParser(Integer idGroup, String idField) {
		return getFieldParser(fieldFormatGroupManager.getGroup(idGroup), idField);
	}
	/**
	 * 
	 * @param idField
	 * @return
	 */
	public IFieldParser getFieldParser(FieldFormatGroup group, String idField) {
		if (cache.containsKey(group.getId() + "." + idField)) {
			return cache.get(group.getId() + "." + idField);
		} else {
			FieldFormat fieldFormat = FieldFormatDAO.getInstance().findByGroupAndIdField(group.getId(),idField);
			if (fieldFormat != null) {
				try {
					IFieldParser fieldParser = null;
					Class<?> classParser = Class.forName(fieldFormat.getClassParser());
					fieldParser = (IFieldParser) classParser.getConstructor(FieldFormat.class).newInstance(fieldFormat);
					cache.put(group.getId() + "." + idField, fieldParser);
					return fieldParser;
				} catch (Exception e) {
					throw new ParserException(group+" cannot found Parser: "+fieldFormat.getClassParser(),e);
				}
			} else {
				if (group.getParent() == null) {
					return null;
					//throw new ParserException(group+" cannot found Field Format: "+idField);
				} else {
					IFieldParser fieldParser = getFieldParser(group.getParent(), idField);
					if (fieldParser != null) {
						cache.put(group.getId() + "." + idField, fieldParser);
					}
					return fieldParser;
				}
			}
		}
	}
	
	public List<IFieldParser> getRootFieldParsers(Integer idGroup){
		return getRootFieldParsers(fieldFormatGroupManager.getGroup(idGroup));
	}
	/**
	 * For extract
	 * @param group
	 * @return
	 */
	public List<IFieldParser> getRootFieldParsers(FieldFormatGroup group){
		loadGroup(group);
		String id = group.getId()+".";
		List<IFieldParser> parsers = new ArrayList<IFieldParser>();
		for (Map.Entry<String, IFieldParser> e : cache.entrySet()) {
			if (e.getKey().startsWith(id) && e.getKey().indexOf('.', id.length()+1) == -1) {
				parsers.add(e.getValue());
				
			}
		}
		Collections.sort(parsers, (IFieldParser f1, IFieldParser f2)->{
			return f1.getIdOrder().compareTo(f2.getIdOrder());
		});
		return parsers;
	}
	private boolean isLoadedGroup(FieldFormatGroup group) {
		String id = group.getId()+".";
		for (Map.Entry<String, IFieldParser> e : cache.entrySet()) {
			if (e.getKey().startsWith(id)) {
				return true;
			}
		}
		return false;
	}
	private void loadGroup(FieldFormatGroup group) {
		if (!isLoadedGroup(group)) {
			List<FieldFormat> formats = FieldFormatDAO.getInstance().findByGroup(group.getId());
			for (FieldFormat format : formats) {
				if (!cache.containsKey(group.getId() + "." + format.getIdField())) {
					try {
						IFieldParser fieldParser = null;
						Class<?> classParser = Class.forName(format.getClassParser());
						fieldParser = (IFieldParser) classParser.getConstructor(FieldFormat.class).newInstance(format);
						cache.put(group.getId() + "." + format.getIdField(), fieldParser);
					} catch (Exception e) {
						throw new ParserException(group+" cannot found Parser: "+format.getClassParser(),e);
					}
				}		
			}
		}
		if (group.getParent() != null) {
			loadGroup(group);
		}
	}
	/**
	 * 
	 * @param idGroup
	 */
	public void deleteCache(Integer idGroup) {
		if (!cache.isEmpty() && idGroup != null) {
			FieldFormatGroup group = fieldFormatGroupManager.getGroup(idGroup);
			deleteCache(group);
		}
	}
	public void deleteCache(FieldFormatGroup group) {
		if (!cache.isEmpty() && group != null) {
			_deleteCache(group);
			List<FieldFormatGroup> subGroups = fieldFormatGroupManager.getSubGroups(group.getId());
			if (subGroups != null && !subGroups.isEmpty()) {
				for (FieldFormatGroup subgroup : subGroups) {
					deleteCache(subgroup);
				}
			}
		}
	}
	private void _deleteCache(FieldFormatGroup group) {
		Map<String, IFieldParser> newCache = new HashMap<>();
		List<String> starts = new ArrayList<String>();
		while (group != null) {
			starts.add(group.getId()+".");
			group = group.getParent();
		}
		boolean startsWith = false;
		for (Map.Entry<String, IFieldParser> e : cache.entrySet()) {
			startsWith = false;
			for (String s : starts) {
				if (e.getKey().startsWith(s)) {
					startsWith = true;
					break;
				}
			}
			if (!startsWith) {
				newCache.put(e.getKey(), e.getValue());
			}
		}
		cache = newCache;
	}
	@Override
	public void free() {}
	@Override
	public void saveState() {}
	public Class<?>[] getParserClasses() {
		return new Class<?>[] {FixedFieldParser.class,VarFieldParser.class,TrimVarFieldParser.class,TrimFixedFieldParser.class,NacTagFixedFieldParser.class,NacTagsVarFieldParser.class,NacTagVarFieldParser.class,NacTokenEZVarFieldParser.class,NacTokensVarFieldParser.class,NacTokenVarFieldParser.class,TpvTag55VarFieldParser.class,TpvTag56VarFieldParser.class,TpvTags55VarFieldParser.class,TpvTags56VarFieldParser.class,TpvTokenEZVarFieldParser.class,TpvTokenQKVarFieldParser.class,TpvTokensVarFieldParser.class,TpvTokenVarFieldParser.class,Visa126VarFieldParser.class,Visa62VarFieldParser.class,Visa63VarFieldParser.class,VisaDatasetsVarFieldParser.class,VisaDatasetVarFieldParser.class,VisaTagVarFieldParser.class,MC48VarFieldParser.class,MCTagsVarFieldParser.class,MCTagVarFieldParser.class};
	}
}
