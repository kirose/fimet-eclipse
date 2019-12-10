package com.fimet.cfg.db.eg.fieldformat.extract;

import static com.fimet.commons.converter.Converter.NONE;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalPOSExtractAdd018 {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.EXTRACT_ADD018_POS.getId();
	public NacionalPOSExtractAdd018(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",6,null,"FECHA DE CONCILIACION",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"TIPO DESC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",1,null,"SITE ORIGEN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"(?s).+",2,null,"PERIODO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"(?s).+",7,null,"SEQ",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"(?s).+",3,null,"ID-REGISTRO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"(?s).+",4,null,"LONGITUD-REGISTRO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","8",NONE.getId(),null,null,"(?s).+",96,null,"Token QK cifrado de requerimiento",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","9",NONE.getId(),null,null,"(?s).+",96,null,"Token QJ cifrado de respuesta",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","10",NONE.getId(),null,null,"(?s).+",2,null,"Id Version Criptograma",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","11",NONE.getId(),null,null,"(?s).+",16,null,"Criptograma de EG",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","12",NONE.getId(),null,null,"(?s).+",15,null,"Filler",FixedFieldParser.class));
	}
}
