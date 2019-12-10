package com.fimet.cfg.db.eg.fieldformat.extract;

import static com.fimet.commons.converter.Converter.NONE;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalPOSExtractAdd016 {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.EXTRACT_ADD016_POS.getId();
	public NacionalPOSExtractAdd016(FieldFormatDAO dao) {
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
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",3,null,"Point Of Servive Entry Mode",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",3,null,"Electronic Commerce Security Level Indicator and UCAF Collection Indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",26,null,"Point Of Service Data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 1 POS Terminal Attendance",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 2 Reserved for future use",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 3 POS Terminal Location",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 4 POS Card Holder Presence",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 5 POS Card Presence",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 6 POS Card Capture Capabilities",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 7 POS Transaction Status",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 8 POS Transaction Security",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 9 Reserved for future use",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 10 Cardholder-activated Terminal Level",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 11 POS Card Data Terminal Input Capability Indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"022","22",NONE.getId(),null,null,"(?s).+",2,null,"Subfield 12 POS Authorization Life Cycle",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",NONE.getId(),null,null,"(?s).+",3,null,"Subfield 13 POS Country Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",NONE.getId(),null,null,"(?s).+",10,null,"Subfield 14 POS Postal Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"025","25",NONE.getId(),null,null,"(?s).+",7,null,"Electronic Commerce Indicators",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"026","26",NONE.getId(),null,null,"(?s).+",3,null,"Subfield 1 Electronic Commerce Security Level Indicator and UCAF Collection Indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"027","27",NONE.getId(),null,null,"(?s).+",3,null,"Subfield 2 Original Electronic Commerce Security Level Indicator and UCAF Collection Indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",NONE.getId(),null,null,"(?s).+",1,null,"Subfield 3 Reason for UCAF Collection Indicator Downgrade Values",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"029","29",NONE.getId(),null,null,"(?s).+",87,null,"Filler",FixedFieldParser.class));
	}
}
