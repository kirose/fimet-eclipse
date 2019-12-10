package com.fimet.parser.field.impl.tpv;

import com.fimet.commons.data.reader.IReader;
import com.fimet.commons.data.reader.impl.ByteArrayReader;
import com.fimet.commons.data.writer.IWriter;
import com.fimet.commons.exception.FormatException;
import com.fimet.commons.exception.ParserException;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.Activator;

public class TpvTokensVarFieldParser extends VarFieldParser {
	
	public TpvTokensVarFieldParser(FieldFormat fieldFormat) {
		super(fieldFormat);
	}
	@Override
	protected void parseChilds(byte[] value, IMessage message) {
		if (value != null && !new String(value).startsWith("00")) {// Cierre de lotes no se parsean sus hijos
			if (childs != null) {
				try {
					IReader reader = new ByteArrayReader(value);
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
		String tokenName = reader.ascii(2).toString();
		if (!childs.contains(tokenName)) {
			throw new ParserException("Unknow Token: '"+tokenName+"', tokens declared: "+childs);
		}
		reader.move(-2);
		return getFieldParserManager().getFieldParser(getGroup(),idField+"."+tokenName).parse(reader, message);
	}
	@Override
	protected void formatChilds(IWriter writer, IMessage message) {
		String token;
		for (String idChild : message.getIdChildren(idField)) {
			token = idChild.substring(idChild.lastIndexOf('.')+1);
			if (!childs.contains(token)) {
				throw new FormatException("Unknow Token: '"+token+"', tokens declared: "+childs);
			}
			getFieldParserManager().format(message, idChild, writer);
		}
	}
}
