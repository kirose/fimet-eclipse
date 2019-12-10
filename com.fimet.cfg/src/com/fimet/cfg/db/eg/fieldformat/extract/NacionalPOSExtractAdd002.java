package com.fimet.cfg.db.eg.fieldformat.extract;

import static com.fimet.commons.converter.Converter.NONE;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalPOSExtractAdd002 {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.EXTRACT_ADD002_POS.getId();
	public NacionalPOSExtractAdd002(FieldFormatDAO dao) {
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
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",2,null,"REGION",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",7,null,"AFILIACION",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",6,null,"FILLER",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"(?s).+",4,null,"DESTINO DEL ABONO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"(?s).+",5,null,"TIPO DE PAGO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"(?s).+",10,null,"REFERENCIA - # CELULAR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"(?s).+",6,null,"REFERENCIA - FILLER",FixedFieldParser.class));
	}
}
