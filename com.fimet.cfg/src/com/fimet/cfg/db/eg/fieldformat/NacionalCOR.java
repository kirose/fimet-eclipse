package com.fimet.cfg.db.eg.fieldformat;


import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalCOR {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.NACIONAL_COR.getId();
	public NacionalCOR(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		
	}
}
