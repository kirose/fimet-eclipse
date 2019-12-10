package com.fimet.cfg.db.eg.fieldformat;


import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class MasterCardATM {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.MASTERCARD_ATM.getId();
	public MasterCardATM(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
	}

}
