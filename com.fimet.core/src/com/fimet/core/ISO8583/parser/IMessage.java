package com.fimet.core.ISO8583.parser;

import java.util.List;

import com.fimet.core.ISO8583.adapter.IAdapter;

public interface IMessage {
	public IParser getParser();
	public IAdapter getAdapter();
	public void setAdapter(IAdapter adapter);
	public void setField(String idField, byte[] value);
	public String getValue(int idField);
	public String getValue(String idField);
	public void setValue(String idField, String value);
	public byte[] getField(String idField);
	public boolean hasField(String idField);
	public boolean hasChildren(String idField);
	public List<String> getIdChildren(String idField);
	public List<Field> getRootFields();
	public String toJson();
}
