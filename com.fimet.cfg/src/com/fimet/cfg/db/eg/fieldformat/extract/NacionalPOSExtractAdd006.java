package com.fimet.cfg.db.eg.fieldformat.extract;

import static com.fimet.commons.converter.Converter.NONE;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalPOSExtractAdd006 {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.EXTRACT_ADD006_POS.getId();
	public NacionalPOSExtractAdd006(FieldFormatDAO dao) {
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
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",4,null,"Message Type Identifier",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",6,null,"Processing Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",10,null,"Date And Time Transmission",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"(?s).+",6,null,"System Trace Audit Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"(?s).+",12,null,"Date and Time Local Transaction",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"(?s).+",12,null,"Point Of Service Data Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"(?s).+",4,null,"Card Acceptor Bussiness Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"(?s).+",1,null,"Approval Code Length",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"(?s).+",15,null,"Acquirer Reference Data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",NONE.getId(),null,null,"(?s).+",1,null,"Acquiring Institution Identification Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"(?s).+",12,null,"Retrieval Reference Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"(?s).+",3,null,"Action Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"(?s).+",10,null,"Card Acceptor Identification Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"(?s).+",4,null,"Additional Data ",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"022","22",NONE.getId(),null,null,"(?s).+",25,null,"Original Data Elements",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",NONE.getId(),null,null,"(?s).+",20,null,"Seller ID",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",NONE.getId(),null,null,"(?s).+",94,null,"Filler",FixedFieldParser.class));	
	}
}
