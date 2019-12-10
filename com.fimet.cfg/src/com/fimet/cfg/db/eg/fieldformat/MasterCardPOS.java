package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.EBCDIC_TO_ASCII;
import static com.fimet.commons.numericparser.NumericParser.DEC;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class MasterCardPOS {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.MASTERCARD_POS.getId();
	public MasterCardPOS(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),".+",2,19,"Primary account number (PAN)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",VarFieldParser.class));
	}

}
