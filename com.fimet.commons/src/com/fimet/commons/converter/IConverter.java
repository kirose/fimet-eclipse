package com.fimet.commons.converter;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public interface IConverter {
	public int getId();
	public byte[] convert(byte[] bytes);
	public byte[] deconvert(byte[] bytes);
}
