package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.NONE;
import static com.fimet.commons.numericparser.NumericParser.DEC;

import com.fimet.cfg.FieldFormatBuilder;
import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.field.impl.nac.NacTokenVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTokensVarFieldParser;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalATM {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.NACIONAL_ATM.getId();
	public NacionalATM(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,19,"Primary account number (PAN)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"126","126",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",NacTokensVarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","01",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 01",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"002","Q1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token Q1",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"003","Q2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token Q2",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"004","Q3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token Q3",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","Q4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token Q4",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","Q6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token Q6",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","04",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 04",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","C0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token C0",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","C4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token C4",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"010","C5",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token C5",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"011","ER",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token ER",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","ES",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token ES",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","ET",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token ET",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","EW",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token EW",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","EX",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token EX",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"016","EY",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token EY",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"017","EZ",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token EZ",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","R1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R1",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","R2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R2",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"020","R3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R3",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"021","R7",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R7",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","R8",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R8",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","C6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token C6",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"024","CE",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token CE",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"025","S3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token S3",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"026","17",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 17",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"027","20",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 20",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"028","QS",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QS",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"029","B0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B0",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"030","B1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B1",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"031","B2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B2",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"032","B3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B3",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"033","B4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B4",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"034","R0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R0",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"035","QF",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QF",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"036","R4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R4",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"037","QR",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QR",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"038","QO",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QO",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"039","QP",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QP",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"040","CZ",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token CZ",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"041","TV",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token TV",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"042","TM",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token TM",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"043","25",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 25",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"044","30",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 30",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"045","QM",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QM",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"046","QN",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QN",NacTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"047","QC",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QC",NacTokenVarFieldParser.class))
		.insertOrUpdate(dao);
	}
}
