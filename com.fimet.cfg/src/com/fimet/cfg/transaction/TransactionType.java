package com.fimet.cfg.transaction;


/**
 * The default Parsers
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public enum TransactionType {

	FINANCIERA_0200(0,"Financiera 0200"),
	F0200_COMPRA(1,"Compra"),
	F0200_DEVOLUCION(2,"Devolucion"),	
	F0200_CASHBACK(3,"CashBack"),
	NOTIFICACION(4,"Notificacion"),
	N_POSTPROPINA(5,"PostPropina"),
	N_OFFLINE(6,"Notificacion Offline"),
	N_CHECKOUT(7,"CheckOut"),
	FINANCIERA_0100(8,"Financiera 0100"),
	F0100_CHEKIN(9,"CashIn"),
	F0100_REAUTORIZACION(10,"Reautorizacion")	
	;
	
	private int id;
	private String name;
	TransactionType(int idParser, String parser) {
		this.id = idParser;
	    this.name = parser;
	}
	@Override
	public String toString() {
	    return name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
