package com.fimet.core.ISO8583.parser;

import java.util.Arrays;
import java.util.List;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.converter.IConverter;
import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.data.writer.impl.ByteArrayWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.exception.ParserException;
import com.fimet.core.Activator;
import com.fimet.core.IFieldFormatManager;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.entity.sqlite.FieldFormatGroup;

/**
 * Parser for MessageFields from the message 
 * 
 * @author Marco Antonio
 *
 */
public abstract class AbstractFieldParser implements IFieldParser {

	protected final String idFieldParent;
	protected final String idField;
	protected final String idOrder;
	protected final short[] address;
	protected final String key;
	protected final String name;
	protected final String type;
	protected final IConverter  converterValue;
	protected final List<String> childs;
	protected final FieldFormatGroup group;
	protected final int length;
	private static IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	private static IFieldFormatManager fieldFormatGroupManager = Manager.get(IFieldFormatManager.class);
	
	public AbstractFieldParser(FieldFormat fieldFormat) {
		super();
		if (fieldFormat.getType() == null || "".equals(fieldFormat.getType().trim())) {
			throw new ParserException(this+"  FieldFormat.type is null for field: "+fieldFormat.getIdField() + "-" +fieldFormat.getName());
		}
		if (fieldFormat.getLength() == null) {
			throw new ParserException(this+"  FieldFormat.length is null for field: "+fieldFormat.getIdField() + "-" +fieldFormat.getName());
		}
		this.length = fieldFormat.getLength();
		this.idFieldParent = fieldFormat.getIdFieldParent();
		this.idField = fieldFormat.getIdField();
		this.idOrder = fieldFormat.getIdOrder();
		this.key = fieldFormat.getKey();
		this.type = fieldFormat.getType();
		this.name = fieldFormat.getName();
		this.group = fieldFormatGroupManager.getGroup(fieldFormat.getIdGroup());
		this.childs = fieldFormat.getChilds() != null ? Arrays.asList(fieldFormat.getChilds().split(",")) : null;
		this.converterValue = Converter.get(fieldFormat.getIdConverterValue());
		String[] orders = idOrder.split("\\.");
		this.address = new short[orders.length];
		int i = 0;
		for (String o : orders) {
			address[i++] = Short.parseShort(o); 
		}
		if (fieldFormat.getType()==null) {
			throw new ParserException(this+" type is null");
		}
	}
	/**
	 * Parse an field to Msg
	 * @return Field an field parsed
	 */
	@Override
	public byte[] parse(IReader reader, IMessage message) {
		preParseValue(reader, message);
		byte[] value = parseValue(reader, message);
		return posParseValue(value, message);
	}
	abstract protected byte[] parseValue(IReader reader, IMessage message);
	
	protected void preParseValue(IReader reader, IMessage message) {}
	
	protected byte[] posParseValue(byte[]value, IMessage message) {
		message.setField(idField, value);
		applyRuleType(value);
		parseChilds(value, message);
		return message.getField(idField);
	}
	
	protected void parseChilds(byte[] value, IMessage message) {
		if (childs != null) {
			IReader reader = new ByteArrayReader(value);
			byte[] child;
			String idChild;
			for (String childIndex : childs) {
				try {
					idChild = idField + "." + childIndex;
					child = getFieldParserManager().parse(idChild, reader, message);
					message.setField(idChild, child);
				} catch (Exception e) {
					if (getFailOnError()) {
						throw e;
					} else {
						Activator.getInstance().warning(this+" parsing subfield '"+idField + "." + childIndex+"'",e);
					}
				}
			}
		}		
	}
	/**
	 * Format the field from Msg
	 */
	@Override
	public byte[] format(IWriter writer, IMessage message) {
		byte[] value = preFormatValue(writer, message);
		value = formatValue(writer, message, value);
		posFormatValue(value, message);
		return value;
	}
	
	abstract protected byte[] formatValue(IWriter writer, IMessage message, byte[] value);

	protected byte[] preFormatValue(IWriter writer, IMessage message) {
		byte[] value;
		if (message.hasChildren(idField)) {
			ByteArrayWriter temp = new ByteArrayWriter();
			formatChilds(temp, message);
			value = temp.getBytes();
		} else {
			value = message.getField(idField);
		}
		applyRuleType(value);
		return value;
	}
	protected void posFormatValue(byte[] value, IMessage message) {}
	
	protected void formatChilds(IWriter writer, IMessage message) {
		String idChild;
		for (String child : childs) {
			idChild = idField+"."+child;
			if (!message.hasField(idChild)) {
				throw new FormatException("Expected subfield: "+idChild);
			}
			getFieldParserManager().format(message, idChild, writer);
		}
	}
	protected void applyRuleType(byte[] value) {
		if (childs == null && !new String(value).matches(type)) {
			throw new ParserException(this+" - Expected format '"+type+"' found: '"+new String(value)+"' in field "+idField);
		}
	}
	protected boolean getFailOnError() {
		return false;//PreferenceDAO.getBoolean(PreferenceDAO.FAIL_ON_PARSE_FIELD_ERROR, Parser.DEFAUT_FAIL_ON_ERROR);
	}
	protected IFieldParser getParent(){
		return idFieldParent != null ? fieldParserManager.getFieldParser(group,idFieldParent) : null;
	}
	public static IFieldParserManager getFieldParserManager() {
		return fieldParserManager;
	}
	public FieldFormatGroup getGroup() {
		return group;
	}
	@Override
	public String toString() {
		return "Id \""+ idField+"\"" +(group != null ? " Group \""+group+"\"" : ""); 
	}
	public boolean hasChild(String idChild) {
		return childs != null && childs.contains(idChild);
	}
	public int indexOfChild(String idChild) {
		return childs != null ? childs.indexOf(idChild) : -1;
	}
	public String getIdChild(int index) { 
		return childs.get(index);
	}
	public String getName() {
		return name;
	}
	public String getIdField() {
		return idField;
	}
	public String getIdOrder() {
		return idOrder;
	}
	public String getType() {
		return type;
	}
	public int getLength() {
		return length;
	}
	public short[] getAddress() {
		return address;
	}
	@Override
	public boolean isValidValue(String value) {
		return value != null && value.matches(type); 
	}
}
