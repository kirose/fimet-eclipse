package com.fimet.core.entity.sqlite;

public interface IRule {
	final char IAP = 'I';
	final char INTERCHANGE = 'C';
	final char INTERFACE = 'F';
	final char MLI = 'M';
	final char EQUALS = '=';
	final char MATCHES = '~';
	final char CONTAINS = '>';
	public int getIdTypeEnviroment();
	public void setIdTypeEnviroment(int idTypeEnviroment);
	public int getOrder();
	public void setOrder(int order);
	public char getType();
	public void setType(char t);
	public char getOperator();
	public void setOperator(char o);
	public Integer getId();
	public String getRule();
	public void setRule(String r);
	public Integer getIdResult();
	public void setIdResult(Integer idr);

}
