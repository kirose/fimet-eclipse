package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.ASCII_TO_BINARY;
import static com.fimet.commons.converter.Converter.ASCII_TO_HEX;
import static com.fimet.commons.converter.Converter.EBCDIC_TO_ASCII;
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

public class MasterCard {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.MASTERCARD.getId();
	public MasterCard(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"001","1",ASCII_TO_BINARY.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",64,null,"Second Bitmap",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),".+",2,19,"Primary account number (PAN)",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"003","3",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",6,null,"Processing code",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"Transaction Type",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (From)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (To)",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"004","4",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",12,null,"Amount, transaction",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"005","5",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",12,null,"Amount, settlement",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"006","6",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",12,null,"Amount, cardholder billing",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"007","7",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Transmission date & time",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, cardholder billing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",8,null,"Conversion rate, settlement",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",8,null,"Conversion rate, cardholder billing",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",6,null,"System trace audit number (STAN)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",6,null,"Local transaction time (hhmmss)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",4,null,"Local transaction date (MMDD)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",4,null,"Expiration date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",4,null,"Settlement date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",4,null,"Currency conversion date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",4,null,"Capture date",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",4,null,"Merchant type, or merchant category code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Acquiring institution (country code)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"PAN extended (country code)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Forwarding institution (country code)",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"022","22",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Point of service entry mode",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"PAN/date entry mode",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"PIN entry capability",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Application PAN sequence number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Function code (ISO 8583:1993), or network international identifier (NII)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"025","25",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[A-Z0-9]+",2,null,"Point of service condition code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"026","26",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",2,null,"Point of service capture code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"027","27",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",1,null,"Authorizing identification response length",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(C|D)[0-9]+",9,null,"Amount, transaction fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"029","29",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, settlement fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"030","30",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, transaction processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"031","31",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",8,null,"Amount, settlement processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"032","32",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]*",2,11,"Acquiring institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"033","33",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",2,11,"Forwarding institution identification code",VarFieldParser.class));	
		dao.insertOrUpdate(new FieldFormat(idGroup,"034","34",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[^a-zA-Z ]+",2,28,"Primary account number, extended",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"036","36",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,104,"Track 3 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"037","37",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",12,null,"Retrieval reference number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"038","38",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",6,null,"Authorization identification response",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"039","39",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,null,"Response code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"040","40",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Service restriction code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"041","41",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",8,null,"Card acceptor terminal identification",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"042","42",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",15,null,"Card acceptor identification code",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"043","43",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",40,null,"Card acceptor name/location",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",22,null,"Card Acceptor Name",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"Space",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",13,null,"Card Acceptor City",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"(?s).+",1,null,"Space",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"(?s).+",3,null,"Card Acceptor State or Country Code",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"044","44",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,25,"Additional response data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"045","45",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,76,"Track 1 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"046","46",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (ISO)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"047","47",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (national)",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"048","48",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Additional data (private)",MC48VarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","tcc",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",1,null,"Transaction Category Code",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","10",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Encrypted PIN Block Key",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"003","11",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Key Exchange Block Data (Single-length Keys)|(Double-length Keys)|(Triple-length Keys)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"004","12",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Routing Indicator",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","13",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Hosted Mobile Phone Top-up IssuerRequest Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","14",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","15",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Authorization Platform Advice Date",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","16",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Processor Pseudo ICA",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","17",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Authentication Indicator",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"010","18",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Service Parameters",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"011","19",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","20",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Cardholder Verification Method",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","21",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","22",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","23",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Payment Initiation Channel",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"016","25",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Cash Program Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"017","26",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Wallet Program Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","27",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","28",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"020","29",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"021","30",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Token Transaction Identifier",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","31",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","32",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Assigned ID",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"024","33",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"PAN Mapping File Information",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"025","34",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Dynamic CVC 3 ATC Information",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"026","35",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Contactless Non-Card Form Factor IssuerRequest/IssuerResponse",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"027","36",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Visa MVV (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"028","37",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Additional Merchant Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"029","38",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Account Category",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"030","39",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Account Data Compromise Information",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"031","40",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Electronic Commerce Merchant/Cardholder Certificate Serial Number (Visa and American Express)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"032","41",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Electronic Commerce Certificate Qualifying Information",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"033","42",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Electronic Commerce Indicators",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"034","43",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Universal Cardholder Authentication Field (UCAF)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"035","44",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Visa 3-D Secure Electronic Commerce Transaction Identifier (XID) (Visa and American Express)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"036","45",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Visa 3-D Secure Electronic Commerce Transaction IssuerResponse Code (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"037","46",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Product ID (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"038","47",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Payment Gateway Indicator",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"039","48",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mobile Program Indicators",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"040","49",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Time Validation Information",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"041","50",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"042","51",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Merchant On-behalf Services",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"043","52",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Transaction Integrity Class",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"044","53",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"045","54",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"046","55",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Merchant Fraud Scoring Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"047","56",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Security Services Additional Data for Issuers",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"048","57",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Security Services Additional Data for Acquirers",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"049","58",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"ATM Additional Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"050","59",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"051","60",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"052","61",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"POS Data, Extended Condition Codes",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"053","62",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"054","63",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Trace ID",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"055","64",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Transit Program",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"056","65",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"057","66",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"058","67",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"059","68",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"060","69",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"061","70",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"062","71",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"On-behalf Services",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"063","72",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Issuer Chip Authentication",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"064","73",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Internal Use Only",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"065","74",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Additional Processing Information",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"066","75",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Fraud Scoring Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"067","76",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Electronic Acceptance Indicator",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"068","77",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Funding/Payment Transaction Type Indicator",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"069","78",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Payment Service Indicators (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"070","79",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Chip CVR/TVR Bit Error Result",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"071","80",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"PIN Service Code",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"072","81",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Reserved for Future Use N/A",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"073","82",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Address Verification Service IssuerRequest",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"074","83",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Address Verification Service IssuerResponse",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"075","84",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Merchant Advice Code|Visa IssuerResponse Codes (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"076","85",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Account Status (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"077","86",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Relationship Participant Indicator (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"078","87",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Card Validation Code Result|CVV2 IssuerResponse (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"079","88",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Magnetic Stripe Compliance Status Indicator",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"080","89",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Magnetic Stripe Compliance Error Indicator",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"081","90",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Lodging|Custom Payment Service IssuerRequest (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"082","91",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Custom Payment Service IssuerRequest Transaction ID (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"083","92",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"CVC 2|CVV2 Data (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"084","93",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Fleet Card ID IssuerRequest Data (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"085","94",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Commercial Card Inquiry IssuerRequest|IssuerResponse (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"086","95",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Promotion Code|American Express Customer ID Number (American Express Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"087","96",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Visa Market-specific Data Identifier (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"088","97",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Prestigious Properties Indicator (Visa Only)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"089","98",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Corporate Fleet Card® ID/Driver Number",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"090","99",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,999,"Mastercard Corporate Fleet Card® Vehicle Number",MCTagVarFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"049","49",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Currency code, transaction",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"050","50",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Currency code, settlement",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"051","51",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,null,"Currency code, cardholder billing",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"052","52",ASCII_TO_HEX.getId(),NONE.getId(),DEC.getId(),"[a-fA-F0-9]+",8,null,"Personal identification number data",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"053","53",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",16,null,"Security related control information",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"054","54",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,120,"Additional amounts",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"055","55",ASCII_TO_HEX.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"ICC data EMV having multiple tags",MCTagsVarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","5F2A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Transaction Currency Code",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"002","71",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Issuer Script Template 1 and 2",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"003","72",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Issuer Script Template 2",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"004","82",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Application Interchange Profile",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","84",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Dedicated File Name",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","91",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Issuer Authentication Data (Provides data to be transmitted to the card for issuer authentication.)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","95",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Terminal Verification Result (TVR)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","9A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Transaction Date",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","9C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Transaction Type",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"010","9F02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Amount Authorized",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"011","9F03",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Amount Other",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","9F09",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Application Version Number",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","9F10",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Issuer Application Data (IAD)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","9F1A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Terminal Country Code",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","9F1E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Interface Device (IFD) Serial Number",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"016","9F26",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Application Cryptogram (AC)",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"017","9F27",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Cryptogram Information Data",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","9F33",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Terminal Capabilities",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","9F34",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Cardholder Verification Method (CVM) Results",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"020","9F35",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Terminal Type",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"021","9F36",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Application Transaction Counter",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","9F37",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Unpredictable Number",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","9F41",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Transaction Sequence Counter",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"024","9F53",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Transaction Category Code",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"025","9F5B",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Issuer Script Results",MCTagVarFieldParser.class))
			.add(new FieldFormat(idGroup,"026","9F6E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,999,"Form Factor Indicator",MCTagVarFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"056","56",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (ISO)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"057","57",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"058","58",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"059","59",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"060","60",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (national) (e.g. settlement request: batch number, advice transactions: original transaction amount, batch upload: original MTI plus original RRN plus original STAN, etc)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"061","61",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (private) (e.g. CVV2/service code   transactions)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"062","62",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (private) (e.g. transactions: invoice number, key exchange transactions: TPK key, etc.)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"063","63",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (private)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"064","64",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-fA-F0-9]+",16,null,"Message authentication code (MAC)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"065","65",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-fA-F0-9]+",1,null,"Extended bitmap indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"066","66",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",1,null,"Settlement code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"067","67",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",2,null,"Extended payment code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"068","68",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Receiving institution country code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"069","69",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Settlement institution country code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"070","70",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",3,null,"Network management information code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"071","71",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",4,null,"Message number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"072","72",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",4,null,"Last message's number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"073","73",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",6,null,"Action date (YYMMDD)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"074","74",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Number of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"075","75",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Credits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"076","76",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Number of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"077","77",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Debits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"078","78",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Transfer number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"079","79",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Transfer, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"080","80",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Number of inquiries",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"081","81",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",10,null,"Number of authorizations",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"082","82",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",12,null,"Credits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"083","83",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",12,null,"Credits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"084","84",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",12,null,"Debits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"085","85",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",12,null,"Debits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"086","86",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",16,null,"Total amount of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"087","87",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",16,null,"Credits, reversal amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"088","88",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",16,null,"Total amount of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"089","89",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",16,null,"Debits, reversal amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"090","90",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",42,null,"Original data elements",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"091","91",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",1,null,"File update code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"092","92",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,null,"File security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"093","93",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",5,null,"IssuerResponse indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"094","94",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",7,null,"Service indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"095","95",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",42,null,"Replacement amounts",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"096","96",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-fA-F0-9]+",64,null,"Message security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"097","97",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(C|D)[0-9]+",16,null,"Net settlement amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"098","98",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",25,null,"Payee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"099","99",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",2,11,"Settlement institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"100","100",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[0-9]+",2,11,"Receiving institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"101","101",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",2,17,"File name",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"102","102",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",2,28,"Account identification 1",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"103","103",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",2,28,"Account identification 2",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"104","104",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,100,"Transaction description",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"105","105",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"106","106",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"107","107",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"108","108",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"109","109",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"110","110",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"111","111",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"112","112",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"113","113",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"114","114",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"115","115",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"116","116",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"117","117",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"118","118",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"119","119",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"120","120",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"121","121",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"122","122",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"123","123",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"124","124",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"125","125",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"126","126",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"127","127",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"128","128",EBCDIC_TO_ASCII.getId(),EBCDIC_TO_ASCII.getId(),DEC.getId(),"[a-fA-F0-9]+",64,null,"Message authentication code",FixedFieldParser.class));
	}

}
