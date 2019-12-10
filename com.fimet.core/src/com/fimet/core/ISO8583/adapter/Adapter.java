package com.fimet.core.ISO8583.adapter;


/**
 * Un Adapter es el encargado de generar la entrada (adaptar) correcta a un parseador  
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public abstract class Adapter implements IAdapter {

	protected static final String REGEXP_LOG = "^[0-9A-Fa-f\n\t\r ]+$";
	protected static final String REGEXP_HEX_STAR = "[0-9A-Fa-f*]+";
	protected static final String REGEXP_HEX = "[0-9A-Fa-f]+";
	
	private Integer id;
	private String name;
	Adapter(int id, String name) {
		this.id = id;
		this.name = name;
		AdapterManager.adapters.add(id,this);
		if (this instanceof IStringAdapter) {
			AdapterManager.adaptersString.add((IStringAdapter)this);
		}
		if (this instanceof IByteArrayAdapter) {
			AdapterManager.adaptersByteArray.add((IByteArrayAdapter)this);
		}
		if (this instanceof IStreamAdapter) {
			AdapterManager.adaptersStream.add((IStreamAdapter)this);
		}
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
	    return name;
	}
}
