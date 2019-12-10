package com.fimet.cfg.enums;

import java.util.Arrays;
import java.util.List;

import com.fimet.commons.exception.FimetException;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 */
public enum EnviromentType {
	POS34(0,"POS 3.4"),
	ATM34(1,"ATM 3.4"),	
	CEL34(2,"CEL 3.4"),
	COR34(3,"COR 3.4")/*,
	POS45(4,"POS 4.5"),
	ATM45(5,"ATM 4.5"),
	CEL45(6,"CEL 4.5"),
	COR45(7,"COR 4.5")*/;

	public static List<EnviromentType> getEnviromentTypes(){
		return Arrays.asList(values());
	}
	public static EnviromentType get(int id) {
		if (id >= 0 && id < values().length) {
			return values()[id];
		}
		throw new FimetException("Not yet configured enviroment for id "+id);
	}
	private int id;
	private String name;
	
	private EnviromentType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return name;
	}
	public int getId() {
		return id;
	}
}
