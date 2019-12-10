package com.fimet.parser;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.converter.IConverter;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.parser.msg.iso.impl.NacionalParser;

public class ParserBuilder {

	private static IParserManager parserManager = Manager.get(IParserManager.class);
	private com.fimet.core.entity.sqlite.Parser entity;

	public ParserBuilder() {
		super();
		entity= new com.fimet.core.entity.sqlite.Parser();
		entity.setName("Dinamyc Build");
		entity.setIdGroup(4);
		entity.setIdConverter(Converter.NONE.getId());
		entity.setParserClass(NacionalParser.class.getName());
		entity.setType(IParser.ISO8583);
	}
	public ParserBuilder id(int idParser) {
		entity.setId(idParser);
		return this;
	}
	public ParserBuilder group(int id) {
		entity.setIdGroup(id);
		return this;
	}
	public ParserBuilder converter(IConverter converter) {
		entity.setIdConverter(converter.getId());
		return this;
	}
	public ParserBuilder clazz(Class<? extends IParser> parserClass) {
		entity.setId(entity.hashCode());
		entity.setName("Dinamyc Build "+parserClass.getSimpleName());
		entity.setParserClass(parserClass.getName());
		return this;
	}
	public ParserBuilder type(Integer type) {
		entity.setType(type);
		return this;
	}
	public IParser create() {
		return parserManager.getParser(entity);
	}
}
