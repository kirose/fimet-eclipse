package com.fimet.core;

import java.util.List;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.entity.sqlite.FieldFormatGroup;

public interface IFieldParserManager extends IManager {
	public byte[] parse(Integer idField, IReader reader, IMessage message);
	public byte[] parse(String idField, IReader reader, IMessage message);
	public void format(IMessage message, int field, IWriter writer);
	public void format(IMessage message, String field, IWriter writer);
	public IFieldParser getFieldParser(Integer idGroup, String idField);
	public IFieldParser getFieldParser(FieldFormatGroup group, String idField);
	public List<IFieldParser> getRootFieldParsers(FieldFormatGroup group);
	public List<IFieldParser> getRootFieldParsers(Integer group);
	public void deleteCache(Integer idGroup);
	public Class<?>[] getParserClasses();
}
