package com.fimet.cfg.db.eg.fieldformat.extract;

import static com.fimet.commons.converter.Converter.NONE;

import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalPOSExtractAdd003 {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.EXTRACT_ADD003_POS.getId();
	public NacionalPOSExtractAdd003(FieldFormatDAO dao) {
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
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Crypto Info Data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",2,null,"Crypto Info Data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",1,null,"Flag - TVR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"(?s).+",10,null,"TVR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"(?s).+",1,null,"Flag - ARQC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"(?s).+",16,null,"Application Cryptogram  (ARQC ó TC)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Amount Authorized ",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"(?s).+",12,null,"Amount Authorized",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Amount Other",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",NONE.getId(),null,null,"(?s).+",12,null,"Amount Other",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Application Interchange Profile (AIP)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"(?s).+",4,null,"Application Interchange Profile (AIP)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Application Transaction Counter (ATC)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"(?s).+",4,null,"Application Transaction Counter (ATC)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"022","22",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Terminal Country Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",NONE.getId(),null,null,"(?s).+",3,null,"Terminal Country Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Transaction Currency Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"025","25",NONE.getId(),null,null,"(?s).+",3,null,"Transaction  Currency Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"026","26",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Tran Date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"027","27",NONE.getId(),null,null,"(?s).+",6,null,"Transaction Date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Tran Type",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"029","29",NONE.getId(),null,null,"(?s).+",2,null,"Transaction Type",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"030","30",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Unpredictable Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"031","31",NONE.getId(),null,null,"(?s).+",8,null,"Unpredictable Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"032","32",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Issuer Application Data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"033","33",NONE.getId(),null,null,"(?s).+",2,null,"Issuer Appliaction Data Len",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"034","34",NONE.getId(),null,null,"(?s).+",2,null,"Visa Len",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),null,null,"(?s).+",2,null,"Derivation Key Index ",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"036","36",NONE.getId(),null,null,"(?s).+",2,null,"Cryptogram Version Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"037","37",NONE.getId(),null,null,"(?s).+",8,null,"Card Verification Results",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"038","38",NONE.getId(),null,null,"(?s).+",50,null,"Filler",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"039","39",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Terminal Serial Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"040","40",NONE.getId(),null,null,"(?s).+",8,null,"Terminal serial Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"041","41",NONE.getId(),null,null,"(?s).+",1,null,"Flag - EMV Terminal Capability",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"042","42",NONE.getId(),null,null,"(?s).+",6,null,"EMV Terminal Capability",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"043","43",NONE.getId(),null,null,"(?s).+",1,null,"Flag - EMV Terminal Type",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"044","44",NONE.getId(),null,null,"(?s).+",2,null,"EMV Terminal Type",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"045","45",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Appl Version Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"046","46",NONE.getId(),null,null,"(?s).+",4,null,"Appl Version Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"047","47",NONE.getId(),null,null,"(?s).+",1,null,"Flag - CVM Results",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"048","48",NONE.getId(),null,null,"(?s).+",6,null,"CVM Results",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"049","49",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Dedicated File Name",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"050","50",NONE.getId(),null,null,"(?s).+",2,null,"Dedicated File Name Len",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"051","51",NONE.getId(),null,null,"(?s).+",32,null,"Dedicated File Name",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"052","52",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Appl PAN Seq Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"053","53",NONE.getId(),null,null,"(?s).+",2,null,"Application PAN Seq Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"054","54",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Issuer Authentication Data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"055","55",NONE.getId(),null,null,"(?s).+",2,null,"Issuer Authentication Data LEN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"056","56",NONE.getId(),null,null,"(?s).+",16,null,"ARPC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"057","57",NONE.getId(),null,null,"(?s).+",4,null,"ARPC Resp. Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"058","58",NONE.getId(),null,null,"(?s).+",12,null,"Dependiente del Emisor",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"059","59",NONE.getId(),null,null,"(?s).+",1,null,"Flag - Token B4",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"060","60",NONE.getId(),null,null,"(?s).+",3,null,"PT. SRV. Entry Mode:",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"061","61",NONE.getId(),null,null,"(?s).+",1,null,"Terminal Eentry Capability",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"062","62",NONE.getId(),null,null,"(?s).+",1,null,"Last EMV Status",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"063","63",NONE.getId(),null,null,"(?s).+",1,null,"Data Suspect",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"064","64",NONE.getId(),null,null,"(?s).+",6,null,"Device Info",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"065","65",NONE.getId(),null,null,"(?s).+",4,null,"Reason Online Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"066","66",NONE.getId(),null,null,"(?s).+",1,null,"ISO Response Code Indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"067","67",NONE.getId(),null,null,"(?s).+",1,null,"Flag- Token B5",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"068","68",NONE.getId(),null,null,"(?s).+",1,null,"Send Card Block",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"069","69",NONE.getId(),null,null,"(?s).+",1,null,"Send Put Data",FixedFieldParser.class));

	}
}
