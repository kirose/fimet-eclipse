package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.HEXEBCDIC_TO_ASCII;
import static com.fimet.commons.converter.Converter.NONE;
import static com.fimet.commons.numericparser.NumericParser.HEX;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.TrimVarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class VisaPOS {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.VISA_POS.getId();
	public VisaPOS(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),HEX.getId(),".+",2,19,"Primary account number (PAN)",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",HEXEBCDIC_TO_ASCII.getId(),null,null,"[0-9]+",18,null,"Amount, transaction fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",TrimVarFieldParser.class));
	}

}
