package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.ASCII_TO_HEX;
import static com.fimet.commons.converter.Converter.HEXEBCDIC_TO_ASCII;
import static com.fimet.commons.converter.Converter.HEX_TO_ASCII;
import static com.fimet.commons.converter.Converter.HEX_TO_BINARY;
import static com.fimet.commons.converter.Converter.NONE;
import static com.fimet.commons.numericparser.NumericParser.DEC;
import static com.fimet.commons.numericparser.NumericParser.DEC_DOUBLE;
import static com.fimet.commons.numericparser.NumericParser.HEX;
import static com.fimet.commons.numericparser.NumericParser.HEX_DOUBLE;

import com.fimet.cfg.FieldFormatBuilder;
import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.TrimFixedFieldParser;
import com.fimet.core.ISO8583.parser.TrimVarFieldParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.field.impl.tpv.TpvTag55VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTag56VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTags55VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTags56VarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokenEZVarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokenQKVarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokenVarFieldParser;
import com.fimet.parser.field.impl.tpv.TpvTokensVarFieldParser;
import com.fimet.parser.field.impl.visa.Visa126VarFieldParser;
import com.fimet.parser.field.impl.visa.Visa62VarFieldParser;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class Tpvs {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.TPVS.getId();
	public Tpvs(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"001","1",HEX_TO_BINARY.getId(),null,null,"[a-fA-F0-9]+",64,null,"Second Bitmap",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),".+",2,19,"Primary account number (PAN)",TrimVarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"[0-9]+",6,null,"Processing code",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"Transaction Type",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (From)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (To)",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, transaction",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, settlement",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, cardholder billinrg",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"[0-9]+",10,null,"Transmission date & time",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"[0-9]+",8,null,"Amount, cardholder billing fee",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"[0-9]+",8,null,"Conversion rate, settlement",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"[0-9]+",8,null,"Conversion rate, cardholder billing",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"[0-9]+",6,null,"System trace audit number (STAN)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"[0-9]+",6,null,"Local transaction time (hhmmss)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"[0-9]+",4,null,"Local transaction date (MMDD)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"[0-9]+",4,null,"Expiration date",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"[0-9]+",4,null,"Settlement date",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"[0-9]+",4,null,"Currency conversion darte",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",NONE.getId(),null,null,"[0-9]+",4,null,"Capture date",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"[0-9]+",4,null,"Merchant type, or mrerchant category code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"[0-9]+",3,null,"Acquiring institution (country code)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"[0-9]+",3,null,"PAN extended (country code)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"[0-9]+",3,null,"Forwarding institution (country corde)",TrimFixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"022","22",NONE.getId(),null,null,"[0-9]+",3,null,"Point of service entry mode",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"PAN/date entry mode",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"PIN entry capability",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",NONE.getId(),null,null,"[0-9]+",3,null,"Application PAN sequence number",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",NONE.getId(),null,null,"[0-9]+",3,null,"Function code (ISO 8583:1993), or netwrork international identifier (NII)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"025","25",NONE.getId(),null,null,"[A-Z0-9]+",2,null,"Point of service condition code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"026","26",NONE.getId(),null,null,"[0-9]+",2,null,"Point of service capture code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"027","27",NONE.getId(),null,null,"[0-9]+",1,null,"Authorizing identification response length",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX.getId(),"(C|D)[0-9]+",9,null,"Amount, transaction fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"029","29",NONE.getId(),null,null,"(C|D)[0-9]+",8,null,"Amount, settlement fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"030","30",NONE.getId(),null,null,"(C|D)[0-9]+",8,null,"Amount, transaction processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"031","31",NONE.getId(),null,null,"(C|D)[0-9]+",8,null,"Amount, settlement processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"032","32",NONE.getId(),HEX_TO_ASCII.getId(),DEC_DOUBLE.getId(),"[0-9]+",4,12,"Acquiring institution identification code",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"033","33",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",2,12,"Forwarding institution identification code",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"034","34",NONE.getId(),NONE.getId(),HEX.getId(),"[^a-zA-Z ]+",2,28,"Primary account number, extended",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"036","36",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",2,104,"Track 3 data",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"037","37",HEX_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",24,null,"Retrieval reference number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"038","38",HEX_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",12,null,"Authorization identification response",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"039","39",HEX_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",4,null,"Response code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"040","40",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Service restriction code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"041","41",HEX_TO_ASCII.getId(),null,null,"(?s).+",16,null,"Card acceptor terminal identification",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"042","42",HEX_TO_ASCII.getId(),null,null,"(?s).+",30,null,"Card acceptor identification code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"043","43",NONE.getId(),null,null,"(?s).+",40,null,"Card acceptor name/location",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"044","44",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",2,24,"Additional response data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"045","45",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",2,76,"Track 1 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"046","46",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",2,999,"Additional data (ISO)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"047","47",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",2,999,"Additional data (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"048","48",HEX_TO_ASCII.getId(),NONE.getId(),DEC_DOUBLE.getId(),"[a-zA-Z0-9 ]+",4,38,"Additional data (private)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"049","49",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, transaction",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"050","50",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",3,null,"Currency code, settlement",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"051","51",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, cardholder billing",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"052","52",NONE.getId(),null,null,"[a-fA-F0-9]+",8,null,"Personal identification number data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"053","53",NONE.getId(),null,null,"[0-9]+",16,null,"Security related control information",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"054","54",HEX_TO_ASCII.getId(),NONE.getId(),DEC_DOUBLE.getId(),"[a-zA-Z0-9 ]*",4,120,"Additional amounts",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"055","55",NONE.getId(),NONE.getId(),DEC_DOUBLE.getId(),"(?s).+",4,999,"Integrated Circuit Card (ICC)-Related Data",TpvTags55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","71",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Issuer Script Template 1",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"002","72",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Issuer Script Template 2",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"003","82",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Application Interchange Profile",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"004","84",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Dedicated File Name",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","91",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Issuer Authentication Data",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","95",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Terminal Verification Results",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","9A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Transaction Data",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","9C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Transaction Type",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","C0",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Secondary PIN Block",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"010","5F2A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Transaction Currency Code",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"011","9F02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Amount, Authorized",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","9F03",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Amount, Other",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","9F09",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Application Version Number",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","9F10",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Issuer Application Data",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","9F1A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Terminal Country Code",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"016","9F1E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Interface Device (IFD) Serial Number",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"017","9F26",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Application Cryptogram",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","9F27",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Cryptogram Information Data",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","9F33",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Terminal Capability Profile",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"020","9F34",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Cardholder Verification Method (CVM) Results",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"021","9F35",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Terminal Type",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","9F36",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Application Transaction Counter",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","9F37",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Unpredictable Number",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"024","9F41",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Transaction Sequence Counter",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"025","9F53",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Transaction Category Code",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"026","9F5B",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Issuer Script Results",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"027","9F6E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Form Factor Indicator",TpvTag55VarFieldParser.class))
			.add(new FieldFormat(idGroup,"028","9F7C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,999,"Customer Exclusive Data (U.S. only)",TpvTag55VarFieldParser.class))
		.insertOrUpdate(dao);
		new FieldFormatBuilder(new FieldFormat(idGroup,"056","56",NONE.getId(),NONE.getId(),DEC_DOUBLE.getId(),"(?s).+",4,255,"Reserved (ISO)",TpvTags56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","00",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 00",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"002","01",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 01",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"003","02",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 02",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"004","03",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 03",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","04",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 04",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","05",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 05",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","06",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 06",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","07",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 07",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","08",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 08",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"010","09",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 09",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"011","10",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 10",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","11",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 11",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","12",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 12",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","13",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 13",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","15",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 15",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"016","16",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 16",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"017","20",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 20",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","21",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 21",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","0A",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 0A",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"020","0B",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 0B",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"021","35",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 35",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","32",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 32",TpvTag56VarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","33",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,255,"Tag 33",TpvTag56VarFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"057","57",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",4,999,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"058","58",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",4,999,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"059","59",HEX_TO_ASCII.getId(),NONE.getId(),DEC_DOUBLE.getId(),"(?s).*",4,256,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"060","60",HEX_TO_ASCII.getId(),NONE.getId(),DEC_DOUBLE.getId(),"(?s).*",4,25,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"061","61",NONE.getId(),NONE.getId(),DEC_DOUBLE.getId(),"(?s).+",4,36,"Reserved (private)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"062","62",NONE.getId(),NONE.getId(),DEC_DOUBLE.getId(),"(?s).+",4,255,"Reserved (private)",Visa62VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"063","63",HEX_TO_ASCII.getId(),NONE.getId(),DEC_DOUBLE.getId(),"(?s).*",4,999,"Reserved (private)",TpvTokensVarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","01",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token 01",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"002","04",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token 04",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"003","17",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token 17",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"004","20",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token 20",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","B2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token B2",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","B3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token B3",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","B4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token B4",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","C0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token C0",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","C4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token C4",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"010","C5",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token C5",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"011","C6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token C6",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","CE",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token CE",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","CZ",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token CZ",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","ER",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token ER",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","ES",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token ES",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"016","ET",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token ET",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"017","EW",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token EW",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","EX",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token EX",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","EY",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token EY",TpvTokenVarFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"020","EZ",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token EZ",TpvTokenEZVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",20,null,"Key Serial Number",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",7,null,"Contador Real de Cifrados ",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"003","3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Contador de Cifrados Fallidos Consecutivos",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"004","4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",1,null,"Bandera de TRACK2",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"005","5",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Longitud del Track2",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"006","6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",1,null,"Bandera de CVV2",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"007","7",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Longitud de CVV2 en claro",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"008","8",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",1,null,"Bandera de TRACK1",FixedFieldParser.class))
				.add(new FieldFormatBuilder(new FieldFormat(idGroup,"009","9",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",24,null,"Datos Sensitivos Cifrados",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"001","track2",ASCII_TO_HEX.getId(),NONE.getId(),DEC.getId(),"(?s).+",19,null,"Track2 Cifrado",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"002","cvv2",ASCII_TO_HEX.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,null,"CVV2 Cifrado",FixedFieldParser.class))
				)
				.add(new FieldFormat(idGroup,"010","10",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",4,null,"4 Ultimos Digitos del PAN",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"011","11",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",8,null,"CRC32 sobre Datos Sensitivos",FixedFieldParser.class))
			)
			.add(new FieldFormat(idGroup,"021","Q1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token Q1",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","Q2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token Q2",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","Q3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token Q3",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"024","Q6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token Q6",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"025","QF",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QF",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"026","QI",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QI",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"027","QJ",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QJ",TpvTokenVarFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"028","QK",ASCII_TO_HEX.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QK",TpvTokenQKVarFieldParser.class))
				.add(new FieldFormatBuilder(new FieldFormat(idGroup,"001","1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",80,null,"Datos complementarios cifrados",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"001","1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",4,null,"4 ultimos digitos pan",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",8,null,"Afiliacion",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"003","3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Presencia ATC",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"004","4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Aleatorio 1",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"005","5",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",4,null,"ATC",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"006","6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",4,null,"4 ultimos digitos tpv",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"007","7",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Aleatorio 2",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"008","8",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",12,null,"Monto",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"009","9",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Aleatorio 3",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"010","10",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Presencia Unpredictable",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"011","11",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",8,null,"Unpredictable",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"012","12",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,null,"Aleatorio 4",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"013","13",HEX_TO_ASCII.getId(),NONE.getId(),DEC.getId(),"(?s).+",24,null,"RRN",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"014","14",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",4,null,"Aleatorio 5",FixedFieldParser.class))
				)
				.add(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",8,null,"CRC32 de los datos complementarios cifrados",FixedFieldParser.class))
			)
			.add(new FieldFormat(idGroup,"029","QO",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QO",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"030","QP",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QP",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"031","QR",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QR",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"032","QS",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token QS",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"033","R0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token R0",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"034","R1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token R1",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"035","R2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token R2",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"036","R3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token R3",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"037","R4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token R4",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"038","R7",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token R7",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"039","R8",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token R8",TpvTokenVarFieldParser.class))
			.add(new FieldFormat(idGroup,"040","S3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,255,"Token S3",TpvTokenVarFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"064","64",NONE.getId(),NONE.getId(),HEX.getId(),"[a-fA-F0-9]+",16,null,"Message authentication code (MAC)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"065","65",NONE.getId(),NONE.getId(),HEX.getId(),"[a-fA-F0-9]+",1,null,"Extended bitmap indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"066","66",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",1,null,"Settlement code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"067","67",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",2,null,"Extended payment code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"068","68",NONE.getId(),null,null,"[0-9]+",3,null,"Receiving institution country code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"069","69",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",3,null,"Settlement institution country code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"070","70",NONE.getId(),null,null,"[0-9]+",3,null,"Network management information code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"071","71",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",4,null,"Message number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"072","72",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",4,null,"Last message's number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"073","73",NONE.getId(),null,null,"[0-9]+",6,null,"Action date (YYMMDD)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"074","74",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Number of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"075","75",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Credits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"076","76",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Number of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"077","77",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Debits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"078","78",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Transfer number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"079","79",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Transfer, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"080","80",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Number of inquiries",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"081","81",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",10,null,"Number of authorizations",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"082","82",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",12,null,"Credits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"083","83",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",12,null,"Credits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"084","84",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",12,null,"Debits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"085","85",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",12,null,"Debits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"086","86",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",16,null,"Total amount of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"087","87",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",16,null,"Credits, reversal amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"088","88",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",16,null,"Total amount of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"089","89",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",16,null,"Debits, reversal amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"090","90",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",42,null,"Original data elements",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"091","91",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",1,null,"File update code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"092","92",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",2,null,"File security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"093","93",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",5,null,"IssuerResponse indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"094","94",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",7,null,"Service indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"095","95",NONE.getId(),NONE.getId(),HEX.getId(),"[a-zA-Z0-9 ]+",42,null,"Replacement amounts",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"096","96",NONE.getId(),NONE.getId(),HEX.getId(),"[a-fA-F0-9]+",64,null,"Message security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"097","97",NONE.getId(),NONE.getId(),HEX.getId(),"(C|D)[0-9]+",16,null,"Net settlement amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"098","98",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",25,null,"Payee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"099","99",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",2,11,"Settlement institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"100","100",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",2,11,"Receiving institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"101","101",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,17,"File name",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"102","102",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,28,"Account identification 1",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"103","103",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,28,"Account identification 2",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"104","104",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,100,"Transaction description",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"105","105",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"106","106",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"107","107",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"108","108",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"109","109",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"110","110",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"111","111",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"112","112",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"113","113",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"114","114",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"115","115",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"116","116",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"117","117",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"118","118",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"119","119",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"120","120",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"121","121",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"122","122",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"123","123",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"124","124",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"125","125",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"126","126",NONE.getId(),NONE.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",Visa126VarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",HEX_TO_ASCII.getId(),null,null,"(?s).+",16,null,"Visa Merchant Identifier",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"(?s).+",34,null,"Cardholder Certificate Serial Number",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"(?s).+",34,null,"Merchant Certificate Serial Number",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",40,null,"Transaction ID (XID)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",40,null,"CAVV Data",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",12,null,"CVV2 Authorization IssuerRequest Data and American Express CID Data",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"012","12",HEX_TO_BINARY.getId(),null,null,"(?s).+",24,null,"Service Indicators",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"013","13",HEX_TO_ASCII.getId(),null,null,"(?s).+",2,null,"POS Environment",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"015","15",HEX_TO_ASCII.getId(),null,null,"(?s).+",2,null,"MasterCard UCAF Collection Indicator",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"016","16",HEX_TO_ASCII.getId(),null,null,"(?s).+",66,null,"MasterCard UCAF Field",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"(?s).+",24,null,"Agent Unique Account Result",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"019","19",HEX_TO_ASCII.getId(),null,null,"(?s).+",2,null,"Dynamic Currency Conversion Indicator",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"127","127",NONE.getId(),ASCII_TO_HEX.getId(),HEX.getId(),"(?s).+",2,255,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"128","128",NONE.getId(),ASCII_TO_HEX.getId(),HEX.getId(),"[a-fA-F0-9]+",64,null,"Message authentication code",FixedFieldParser.class));
	}

}
