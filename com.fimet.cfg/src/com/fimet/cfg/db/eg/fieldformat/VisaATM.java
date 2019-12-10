package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.HEXEBCDIC_TO_ASCII;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class VisaATM {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.VISA_ATM.getId();
	public VisaATM(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",HEXEBCDIC_TO_ASCII.getId(),null,null,".*",18,null,"Amount, transaction fee",FixedFieldParser.class));
	}

}
