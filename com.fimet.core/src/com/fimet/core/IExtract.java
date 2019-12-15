package com.fimet.core;

import java.util.Map;

import com.fimet.core.ISO8583.parser.IMessage;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public interface IExtract {
	
	public int getBaseLength();
	public IMessage getBase();
	public byte[] getBaseAsBytes();
	public String getBaseAsString();
	
	public boolean hasAdditional(int id);
	public int getAdditionalLength(int id);
	public IMessage getAdditional(int id);
	public byte[] getAdditionalAsBytes(int id);
	public String getAdditionalAsString(int id);
	
	public Map<Integer, IMessage> getAdditionals();
	
	public void setAdditionals(byte[][] additionals);
	public int hashCode();
	public byte[] getExtractsAsBytes();
}
