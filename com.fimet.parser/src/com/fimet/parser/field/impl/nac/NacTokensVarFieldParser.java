package com.fimet.parser.field.impl.nac;

import java.util.List;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.exception.ParserException;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.Activator;

public class NacTokensVarFieldParser extends VarFieldParser {
	
	public NacTokensVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	protected void parseChilds(byte[] value, IMessage message) {
		if (childs != null) {
			if (value != null && value.length > 0) {
				try {
					IReader reader = new ByteArrayReader(value);
					reader.move(12);
					if (reader.hasNext()) {
						parseTokens(reader, message);
					}
				} catch (Exception e) {
					if (getFailOnError()) {
						throw e;
					} else {
						Activator.getInstance().warning("Parsing tokens "+idField,e);
					}
				}
			}
		}
	}
	private void parseTokens(IReader reader, IMessage message) {
		while (reader.hasNext()) {
			parseToken(reader, message);
		}		
	}
	private byte[] parseToken(IReader reader, IMessage message) {
		reader.assertChar('!');
		reader.assertChar(' ');
		String tokenName = reader.ascii(2).toString();
		if (!childs.contains(tokenName)) {
			throw new ParserException("Unknow Token: '"+tokenName+"', tokens declared: "+childs);
		}
		reader.move(-4);
		return  getFieldParserManager().getFieldParser(getGroup(),idField+"."+tokenName).parse(reader, message);
	}
	@Override
	protected void formatChilds(IWriter writer, IMessage message) {
		/**
		 * & 0000700266
		 * 		00007:Number of Tokens
		 * 		00266: Length
		 */
		int cursor = writer.length();
		writer.append("& ");
		List<String> children = message.getIdChildren(idField);
		String numTokens = StringUtils.leftPad(""+(children.size()+1),5,'0');
		writer.append(numTokens);
		int cursorLength = writer.length();
		writer.move(5);
		String token;
		for (String idField : children) {
			token = idField.substring(idField.lastIndexOf('.')+1);
			if (!childs.contains(token)) {
				throw new FormatException("Unknow Token: '"+token+"', tokens declared: "+childs);
			}
			getFieldParserManager().format(message, idField, writer);
		}
		String ln = StringUtils.leftPad(""+(writer.length()-cursor), 5, "0"); 
		writer.replace(cursorLength, ln);
	}
}
