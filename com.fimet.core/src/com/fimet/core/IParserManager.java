package com.fimet.core;

import java.util.List;

import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.entity.sqlite.Parser;

public interface IParserManager extends IManager, ISocketFieldMapper {
	IParser getParser(String name);
	IParser getParser(Integer idParser);
	IParser getParser(Parser entity);
	List<IParser> getParsers();
	void reload(Integer idParser);
	public Class<?>[] getParserClasses();
	public List<Parser> getEntities();
	public Parser insert(Parser parser);
	public Parser update(Parser parser);
	public Parser delete(Parser parser);
	public void free(List<Integer> groups);
	public Integer getNextIdParser();
	public Integer getPrevIdParser();
	Parser getEntity(Integer id);
}
