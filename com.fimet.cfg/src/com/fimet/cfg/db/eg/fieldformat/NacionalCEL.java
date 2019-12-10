package com.fimet.cfg.db.eg.fieldformat;


import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalCEL {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.NACIONAL_CEL.getId();
	public NacionalCEL(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
	}
}
