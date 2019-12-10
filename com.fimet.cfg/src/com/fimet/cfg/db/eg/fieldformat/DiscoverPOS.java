package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.ASCII_TO_BINARY;
import static com.fimet.commons.converter.Converter.ASCII_TO_HEX;
import static com.fimet.commons.converter.Converter.NONE;
import static com.fimet.commons.numericparser.NumericParser.DEC;
import static com.fimet.commons.numericparser.NumericParser.HEX_DOUBLE;

import com.fimet.cfg.FieldFormatBuilder;
import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.field.impl.mc.MC48VarFieldParser;
import com.fimet.parser.field.impl.mc.MCTagVarFieldParser;
import com.fimet.parser.field.impl.mc.MCTagsVarFieldParser;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class DiscoverPOS {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.DISCOVER_POS.getId();
	public DiscoverPOS(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"001","1",ASCII_TO_BINARY.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",64,null,"Second Bitmap",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),".+",2,19,"Primary account number (PAN)",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"003","3",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",6,null,"Processing code",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"Transaction Type",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (From)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (To)",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"004","4",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",12,null,"Amount, transaction",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"005","5",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",12,null,"Amount, settlement",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"006","6",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",12,null,"Amount, cardholder billing",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"007","7",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Transmission date & time",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, cardholder billing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",8,null,"Conversion rate, settlement",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",8,null,"Conversion rate, cardholder billing",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",6,null,"System trace audit number (STAN)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",6,null,"Local transaction time (hhmmss)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",4,null,"Local transaction date (MMDD)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",4,null,"Expiration date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",4,null,"Settlement date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",4,null,"Currency conversion date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",4,null,"Capture date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",4,null,"Merchant type, or merchant category code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Acquiring institution (country code)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"PAN extended (country code)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Forwarding institution (country code)",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"022","22",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Point of service entry mode",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"PAN/date entry mode",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"PIN entry capability",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Card Sequence Number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Function Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"025","25",NONE.getId(),NONE.getId(),DEC.getId(),"[A-Z0-9]+",2,null,"Message Reason Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"026","26",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,null,"Point of Sale (POS)Personal Identification Number (PIN) Capture Code ",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"027","27",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",1,null,"Authorizing identification response length",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",NONE.getId(),NONE.getId(),DEC.getId(),"(C|D)[0-9]+",9,null,"Transaction Fee Amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"029","29",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, settlement fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"030","30",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, transaction processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"031","31",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, settlement processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"032","32",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]*",2,11,"Acquiring Institution Identification Code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"033","33",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,11,"Forwarding institution identification code",VarFieldParser.class));	
		dao.insertOrUpdate(new FieldFormat(idGroup,"034","34",NONE.getId(),NONE.getId(),DEC.getId(),"[^a-zA-Z ]+",2,28,"Primary account number, extended",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"036","36",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,104,"Track 3 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"037","37",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",12,null,"Retrieval reference number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"038","38",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",6,null,"Approval Code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"039","39",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,null,"Response code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"040","40",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Service restriction code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"041","41",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",8,null,"Card acceptor terminal identification",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"042","42",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",15,null,"Card acceptor identification code",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"043","43",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",40,null,"Card acceptor name/location",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",22,null,"Merchant Name",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"Space",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",13,null,"Merchant's City",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"(?s).+",1,null,"Space",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"(?s).+",3,null,"Merchant's State or Country Code",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"044","44",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,25,"Additional data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"045","45",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,76,"Track 1 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"046","46",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (ISO)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"047","47",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"048","48",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Network Information",MC48VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"049","49",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Currency code, transaction",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"050","50",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Currency code, settlement",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"051","51",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Currency code, cardholder billing",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"052","52",NONE.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",8,null,"Personal identification number data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"053","53",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",16,null,"Security related control information",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"054","54",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,120,"Additional amounts",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"055","55",ASCII_TO_HEX.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Chip Card Related Data",MCTagsVarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","5F2A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 5F2A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"002","71",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 71",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"003","72",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 72",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"004","82",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 82",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","84",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 84",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","91",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 91",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","95",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 95",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","9A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","9C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9C",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"010","9F02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F02",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"011","9F03",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F03",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","9F09",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F09",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","9F10",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F10",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","9F1A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F1A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","9F1E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F1E",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"016","9F26",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F26",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"017","9F27",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F27",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","9F33",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F33",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","9F34",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F34",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"020","9F35",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F35",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"021","9F36",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F36",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","9F37",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F37",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","9F41",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F41",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"024","9F53",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F53",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"025","9F5B",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F5B",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"026","9F6E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"TAG 9F6E",MCTagVarFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"056","56",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Customer Related Data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"057","57",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"058","58",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"059","59",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Transport Data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"060","60",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (national) (e.g. settlement request: batch number, advice transactions: original transaction amount, batch upload: original MTI plus original RRN plus original STAN, etc)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"061","61",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Point of Sale (POS) Data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"062","62",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Geographic Data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"063","63",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Address Verification Service (AVS)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"064","64",NONE.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",16,null,"Message authentication code (MAC)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"065","65",NONE.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",1,null,"Extended bitmap indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"066","66",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",1,null,"Settlement code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"067","67",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,null,"Extended payment code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"068","68",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Receiving institution country code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"069","69",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Settlement institution country code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"070","70",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,null,"Network management information code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"071","71",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",4,null,"Message number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"072","72",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",4,null,"Last message's number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"073","73",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",6,null,"Action date (YYMMDD)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"074","74",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Number of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"075","75",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Credits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"076","76",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Number of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"077","77",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Debits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"078","78",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Transfer number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"079","79",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Transfer, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"080","80",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Number of inquiries",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"081","81",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",10,null,"Number of authorizations",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"082","82",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",12,null,"Credits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"083","83",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",12,null,"Credits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"084","84",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",12,null,"Debits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"085","85",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",12,null,"Debits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"086","86",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",16,null,"Total amount of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"087","87",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",16,null,"Credits, reversal amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"088","88",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",16,null,"Total amount of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"089","89",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",16,null,"Debits, reversal amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"090","90",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",42,null,"Original Data Elements",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"091","91",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",1,null,"File update code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"092","92",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,null,"File security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"093","93",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",5,null,"IssuerResponse indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"094","94",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",7,null,"Service indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"095","95",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",42,null,"Replacement Amounts",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"096","96",NONE.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",64,null,"Message security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"097","97",NONE.getId(),NONE.getId(),DEC.getId(),"(C|D)[0-9]+",16,null,"Net settlement amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"098","98",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",25,null,"Payee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"099","99",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,11,"Settlement institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"100","100",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,11,"Receiving institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"101","101",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,17,"File name",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"102","102",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,28,"Account identification 1",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"103","103",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,28,"Account identification 2",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"104","104",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,100,"Transaction-Specific Data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"105","105",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Transaction-Specific Data 2",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"106","106",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Transactional Data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"107","107",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"108","108",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"109","109",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"110","110",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"111","111",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"112","112",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"113","113",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"114","114",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"115","115",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"116","116",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"117","117",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"118","118",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"119","119",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"120","120",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"121","121",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Secondary PIN Block",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"122","122",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Additional Authentication Data ",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"123","123",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Promotional Field",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"124","124",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Transaction Qualifier",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"125","125",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Personal Use Field",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"126","126",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Card Identifier (CID)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"127","127",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Version Indicator",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"128","128",NONE.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",64,null,"Message authentication code",FixedFieldParser.class));

	}

}
