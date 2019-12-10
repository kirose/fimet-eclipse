package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.HEXEBCDIC_TO_ASCII;
import static com.fimet.commons.converter.Converter.HEX_TO_ASCII;
import static com.fimet.commons.converter.Converter.HEX_TO_BINARY;
import static com.fimet.commons.converter.Converter.NONE;
import static com.fimet.commons.numericparser.NumericParser.HEX;
import static com.fimet.commons.numericparser.NumericParser.HEX_DOUBLE;

import com.fimet.cfg.FieldFormatBuilder;
import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.TrimFixedFieldParser;
import com.fimet.core.ISO8583.parser.TrimVarFieldParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.field.impl.visa.Visa126VarFieldParser;
import com.fimet.parser.field.impl.visa.Visa62VarFieldParser;
import com.fimet.parser.field.impl.visa.Visa63VarFieldParser;
import com.fimet.parser.field.impl.visa.VisaDatasetVarFieldParser;
import com.fimet.parser.field.impl.visa.VisaDatasetsVarFieldParser;
import com.fimet.parser.field.impl.visa.VisaTagVarFieldParser;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class Visa {
	FieldFormatDAO dao;
	public Visa(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		int idGroup = FieldFormatGroup.VISA.getId();
		dao.insertOrUpdate(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"[a-fA-F0-9]+",64,null,"Second Bitmap",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),HEX.getId(),".+",2,19,"Primary account number (PAN)",TrimVarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"[0-9]+",6,null,"Processing code",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"Transaction Type",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (From)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"Account Type (To)",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, transaction",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, settlement",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, cardholder billing",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"[0-9]+",10,null,"Transmission date & time",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"[0-9]+",8,null,"Amount, cardholder billing fee",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"[0-9]+",8,null,"Conversion rate, settlement",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"[0-9]+",8,null,"Conversion rate, cardholder billing",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"[0-9]+",6,null,"System trace audit number (STAN)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"[0-9]+",6,null,"Local transaction time (hhmmss)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"[0-9]+",4,null,"Local transaction date (MMDD)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"(?s).+",4,null,"Expiration date",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"[0-9]+",4,null,"Settlement date",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"[0-9]+",4,null,"Currency conversion date",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",NONE.getId(),null,null,"[0-9]+",4,null,"Capture date",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"[0-9]+",4,null,"Merchant type, or merchant category code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"[0-9]+",3,null,"Acquiring institution (country code)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"[0-9]+",3,null,"PAN extended (country code)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"[0-9]+",3,null,"Forwarding institution (country code)",TrimFixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"022","22",NONE.getId(),null,null,"[0-9]+",3,null,"Point of service entry mode",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"PAN/date entry mode",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"PIN entry capability",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",NONE.getId(),null,null,"[0-9]+",3,null,"Application PAN sequence number",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",NONE.getId(),null,null,"[0-9]+",3,null,"Function code (ISO 8583:1993), or network international identifier (NII)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"025","25",NONE.getId(),null,null,"[A-Z0-9]+",2,null,"Point of service condition code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"026","26",NONE.getId(),null,null,"[0-9]+",2,null,"Point of service capture code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"027","27",NONE.getId(),null,null,"[0-9]+",1,null,"Authorizing identification response length",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",HEXEBCDIC_TO_ASCII.getId(),null,null,"(C|D)[0-9]+",18,null,"Amount, transaction fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"029","29",HEX_TO_ASCII.getId(),null,null,"[0-9]+",16,null,"Amount, settlement fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"030","30",HEX_TO_ASCII.getId(),null,null,"[0-9]+",16,null,"Amount, transaction processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"031","31",HEX_TO_ASCII.getId(),null,null,"[0-9]+",16,null,"Amount, settlement processing fee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"032","32",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",2,12,"Acquiring institution identification code",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"033","33",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",2,12,"Forwarding institution identification code",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"034","34",NONE.getId(),NONE.getId(),HEX.getId(),"[^a-zA-Z ]+",2,28,"Primary account number, extended",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"036","36",NONE.getId(),NONE.getId(),HEX.getId(),"[0-9]+",3,104,"Track 3 data",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"037","37",HEXEBCDIC_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",24,null,"Retrieval reference number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"038","38",HEXEBCDIC_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",12,null,"Authorization identification response",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"039","39",HEXEBCDIC_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",4,null,"Response code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"040","40",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Service restriction code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"041","41",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",16,null,"Card acceptor terminal identification",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"042","42",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",30,null,"Card acceptor identification code",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"043","43",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",80,null,"Card acceptor name/location",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",25,null,"Card acceptor name or ATM location",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",13,null,"City name",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"Country code",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"044","44",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-zA-Z0-9 ]+",2,24,"Additional response data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"045","45",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,76,"Track 1 data",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"046","46",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (ISO)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"047","47",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"048","48",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,510,"Additional data (private)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"049","49",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, transaction",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"050","50",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, settlement",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"051","51",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, cardholder billing",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"052","52",NONE.getId(),null,null,"[a-fA-F0-9]+",16,null,"Personal identification number data",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"053","53",NONE.getId(),null,null,"[a-fA-F0-9]+",16,null,"Security related control information",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"054","54",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-zA-Z0-9 ]+",2,120,"Additional amounts",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"055","55",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Integrated Circuit Card (ICC)-Related Data",VisaDatasetsVarFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"001","01",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",4,510,"Account Data",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","01",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Payment account reference",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Payment account reference creation date",VisaTagVarFieldParser.class))				
				.add(new FieldFormat(idGroup,"003","71",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Issuer Script Template 1",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"004","72",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Issuer Script Template 2",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"005","82",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Application Interchange Profile",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"006","84",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Dedicated File Name",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"007","91",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Issuer Authentication Data",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"008","95",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Terminal Verification Results",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"009","9A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Transaction Data",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"010","9C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Transaction Type",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"011","C0",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Secondary PIN Block",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"012","5F2A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Transaction Currency Code",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"013","9F02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Amount, Authorized",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"014","9F03",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Amount, Other",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"015","9F10",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Issuer Application Data",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"016","9F1A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Terminal Country Code",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"017","9F26",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Application Cryptogram",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"018","9F33",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Terminal Capability Profile",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"019","9F36",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Application Transaction Counter",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"020","9F37",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Unpredictable Number",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"021","9F41",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Transaction Sequence Counter",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"022","9F5B",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Issuer Script Results",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"023","9F6E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Form Factor Indicator",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"024","9F7C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Customer Exclusive Data (U.S. only)",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"025","9F53",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Transaction Category Code",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"026","9F09",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Application Version Number",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"027","9F1E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Interface Device (IFD) Serial Number",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"028","9F27",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Cryptogram information data",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"029","9F34",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Cardholder Verification Method (CVM) results",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"030","9F35",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Terminal type",VisaTagVarFieldParser.class))
			)
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"002","02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",4,510,"Merchant Payment Transactions",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","83",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Other phone number",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","86",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Other email address",VisaTagVarFieldParser.class))
			)
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"003","03",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",4,510,"Customer Identification Data",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","99",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,510,"Customer Identification",VisaTagVarFieldParser.class))
			)
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"056","56",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved (ISO)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"057","57",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"058","58",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"059","59",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,24,"Reserved (national)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"060","60",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,12,"Reserved (national) (e.g. settlement request: batch number, advice transactions: original transaction amount, batch upload: original MTI plus original RRN plus original STAN, etc)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"061","61",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,36,"Reserved (private) (e.g. CVV2/service code   transactions)",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"062","62",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved (private) (e.g. transactions: invoice number, key exchange transactions: TPK key, etc.)",Visa62VarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",2,null,"Authorization Characteristics Indicator",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",15,null,"Transaction Identifier",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",8,null,"Validation Code",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"004","4",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",2,null,"Market-Specific Data Identifier",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"(?s).+",2,null,"Duration",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"006","6",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",2,null,"Prestigious Property Indicator",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"017","17",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",30,null,"Gateway Transaction Identifier",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"(?s).+",10,null,"Merchant Verification Value",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"021","21",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",8,null,"Online Risk Assessment Risk Score and Reason Codes",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"022","22",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",12,null,"Online Risk Assessment Condition Codes",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"023","23",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",4,null,"Product ID",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"024","24",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",12,null,"Program Identifier",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"025","25",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",2,null,"Spend Qualified Indicator",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"026","26",HEXEBCDIC_TO_ASCII.getId(),null,null,"(?s).+",2,null,"Account Status",FixedFieldParser.class))
		.insertOrUpdate(dao);
		new FieldFormatBuilder(new FieldFormat(idGroup,"063","63",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).*",2,510,"V.I.P. Private-Use Field",Visa63VarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"[a-fA-F0-9]*",4,null,"Network ID",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"[a-fA-F0-9]*",4,null,"Time (Preauth Time Limit)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"[a-fA-F0-9]*",4,null,"Message Reason Code",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"[a-fA-F0-9]*",4,null,"STIP/Switch Reason Code",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"[a-fA-F0-9]*",6,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"[a-fA-F0-9]*",14,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"[a-fA-F0-9]*",16,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"[a-fA-F0-9]*",8,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"[a-fA-F0-9]*",28,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"[a-fA-F0-9]*",26,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"[a-fA-F0-9]*",2,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"[a-fA-F0-9]*",60,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"[a-fA-F0-9]*",6,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"[a-fA-F0-9]*",72,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"[a-fA-F0-9]*",18,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"[a-fA-F0-9]*",6,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"[a-fA-F0-9]*",2,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"[a-fA-F0-9]*",6,null,"Fee Program indicator",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"[a-fA-F0-9]*",2,null,"n/a",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"[a-fA-F0-9]*",2,null,"n/a",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"064","64",HEX_TO_ASCII.getId(),null,null,"[a-fA-F0-9]+",32,null,"Message authentication code (MAC)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"065","65",HEX_TO_ASCII.getId(),null,null,"[a-fA-F0-9]+",2,null,"Extended bitmap indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"066","66",HEX_TO_ASCII.getId(),null,null,"[0-9]+",2,null,"Settlement code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"067","67",HEX_TO_ASCII.getId(),null,null,"[0-9]+",4,null,"Extended payment code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"068","68",NONE.getId(),null,null,"[0-9]+",3,null,"Receiving institution country code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"069","69",HEX_TO_ASCII.getId(),null,null,"[0-9]+",6,null,"Settlement institution country code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"070","70",NONE.getId(),null,null,"[0-9]+",3,null,"Network management information code",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"071","71",HEX_TO_ASCII.getId(),null,null,"[0-9]+",8,null,"Message number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"072","72",HEX_TO_ASCII.getId(),null,null,"[0-9]+",8,null,"Last message's number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"073","73",NONE.getId(),null,null,"[0-9]+",6,null,"Action date (YYMMDD)",TrimFixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"074","74",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Number of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"075","75",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Credits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"076","76",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Number of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"077","77",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Debits, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"078","78",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Transfer number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"079","79",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Transfer, reversal number",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"080","80",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Number of inquiries",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"081","81",HEX_TO_ASCII.getId(),null,null,"[0-9]+",20,null,"Number of authorizations",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"082","82",HEX_TO_ASCII.getId(),null,null,"[0-9]+",24,null,"Credits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"083","83",HEX_TO_ASCII.getId(),null,null,"[0-9]+",24,null,"Credits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"084","84",HEX_TO_ASCII.getId(),null,null,"[0-9]+",24,null,"Debits, processing fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"085","85",HEX_TO_ASCII.getId(),null,null,"[0-9]+",24,null,"Debits, transaction fee amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"086","86",HEX_TO_ASCII.getId(),null,null,"[0-9]+",32,null,"Total amount of credits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"087","87",HEX_TO_ASCII.getId(),null,null,"[0-9]+",32,null,"Credits, reversal amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"088","88",HEX_TO_ASCII.getId(),null,null,"[0-9]+",32,null,"Total amount of debits",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"089","89",HEX_TO_ASCII.getId(),null,null,"[0-9]+",32,null,"Debits, reversal amount",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"090","90",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",42,null,"Original data elements",TrimFixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",4,null,"Original message type",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",6,null,"Original trace number (Field 11)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",10,null,"Original Transaction Data and Time",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"(?s).+",11,null,"Original acquirer ID",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"(?s).+",11,null,"Original forwarding institution ID",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"091","91",HEXEBCDIC_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",4,null,"File update code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"092","92",HEX_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",4,null,"File security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"093","93",HEX_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",10,null,"IssuerResponse indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"094","94",HEX_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",14,null,"Service indicator",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"095","95",HEXEBCDIC_TO_ASCII.getId(),null,null,"[a-zA-Z0-9 ]+",84,null,"Replacement amounts",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"096","96",HEX_TO_ASCII.getId(),null,null,"[a-fA-F0-9]+",128,null,"Message security code",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"097","97",HEX_TO_ASCII.getId(),null,null,"(C|D)[0-9]+",32,null,"Net settlement amount",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"098","98",HEX_TO_ASCII.getId(),null,null,"(?s).+",50,null,"Payee",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"099","99",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[0-9]+",2,11,"Settlement institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"100","100",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[0-9]+",2,11,"Receiving institution identification code",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"101","101",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,17,"File name",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"102","102",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,28,"Account identification 1",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"103","103",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,28,"Account identification 2",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"104","104",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Transaction description",VisaDatasetsVarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Purchase Line-Item Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"002","56",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Payment Facilitator Data",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","01",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,22,"Marketplace ID or Payment Facilitator ID",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","02",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,30,"Sub-Merchant ID",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"003","03",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,22,"Sub-Merchant ID",VisaTagVarFieldParser.class))
			)
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"003","57",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Business Application Identifier|Related Transaction Data (Payment Transactions and Plus Shared",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","01",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,4,"Business Application Identifier",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","02",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,2,"Source of Funds",VisaTagVarFieldParser.class))
			)
			.add(new FieldFormat(idGroup,"004","58",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Healthcare Eligibility Inquiry",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","59",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Promotion Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"006","5B",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Visa Risk Assessment Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"007","5C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Commercial Card Data (Fuel Transactions)",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"008","5D",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Installment Payment Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"009","5E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"TC 50 Destination BIN (Issuer Clearing BIN)",VisaDatasetVarFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"010","5F",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Sender Data",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","01",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,32,"Sender Reference Number",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","02",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,68,"Sender Account Number",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"003","03",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,60,"Sender Name",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"004","04",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,70,"Sender Address",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"005","05",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,50,"Sender City",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"006","06",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,4,"Sender State",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"007","07",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,6,"Sender Country",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"008","08",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,4,"Source of Funds",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"009","09",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,40,"Claim Code",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"010","10",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,60,"Recipient Name",VisaTagVarFieldParser.class))
			)
			.add(new FieldFormat(idGroup,"011","60",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Airline Industry-Specific Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"012","61",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Car Rental Industry-Specific Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"013","62",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Lodging Industry-Specific Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"014","63",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Non-Industry-Specific Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"015","64",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Visa Advanced Authorization Data, VAA data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"016","65",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Mastercard Client-Defined Data",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","01",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"N/A",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","02",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,196,"Client-defined data",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"003","03",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,12,"Mastercard Data Element, 121-Authorizing Agent ID Code",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"004","04",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,4,"Mastercard Data Element DE48, Subelement 23- Payment Initiation Channel",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"005","05",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,12,"Mastercard Data Element DE48, Subelement 95- Promotion Code",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"006","06",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,340,"Mastercard Data Element DE112- Additional Data, National Use",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"007","07",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,12,"Mastercard Data Element DE48, Subelement 32- Mastercard Assigned ID",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"008","09",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,8,"Mastercard Data Element DE48, Subelement 64- Transit Program",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"009","11",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,40,"Mastercard Data Element DE54",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"010","12",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Data Element DE48, Subelement 61, Subfield 5",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"011","13",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Data Element DE61, Subelement 11",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"012","14",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,6,"Mastercard Data Element DE48, Subelement 74, Subfield 1,Subfield 2",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"013","15",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Data Element DE48, Subelement 42- Electronic Commerce Indicators, Subfield 1 (Electronic Commerce Security Level Indicator and UCAF Collection Indicator)",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"014","16",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Data Element DE61, Subfield 3",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"015","18",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,192,"Mastercard Data Element DE48, Subelement 57- Security Services Additional Data for Acquirers, Subfield 1 (Security Services Indicator), Subfield 2 (Security Services Data)",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"016","19",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,4,"Mastercard Data Element DE48, Subelement 65-Terminal Compliant Indicator, Subfield 1 (Terminal Line Encryption), Subfield 2 (UKPT/DUKPT Compliant)",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"017","21",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,98,"Mastercard Mobile Remote Payment Transaction Types",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"018","22",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Tag 22",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"019","23",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,6,"Tag 23",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"020","24",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Tag 24",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"021","25",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,4,"Tag 25",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"022","26",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,22,"Tag 26",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"023","28",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,4,"Tag 28",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"024","29",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Tag 29",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"025","30",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Tag 30",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"026","31",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,64,"Tag 31",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"027","32",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,6,"Tag 32",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"028","33",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Tag 33",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"029","34",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,20,"Tag 34",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"030","35",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,4,"Tag 35",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"031","36",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Tag 36",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"032","37",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,74,"Tag 37",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"033","0A",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,4,"Tag 0A",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"034","0B",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Tag 0B",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"035","0C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,6,"Tag 0C",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"036","0D",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,500,"Tag 0D",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"037","0E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,20,"Tag 0E",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"038","0F",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,56,"Tag 0F",VisaTagVarFieldParser.class))
			/*.add(new FieldFormatBuilder(new FieldFormat(idGroup,"001","21",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Mobile Remote Payment Transaction Types,",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","01",NONE.getId(),ASCII_AS_DEC.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Mobile Remote Payment Transaction Types,",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","02",NONE.getId(),ASCII_AS_DEC.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Mobile Remote Payment Transaction Types,",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"003","03",NONE.getId(),ASCII_AS_DEC.getId(),"[a-fA-F0-9]*",2,2,"Mastercard Mobile Remote Payment Transaction Types,",VisaTagVarFieldParser.class))
			)*/
			)
			.add(new FieldFormat(idGroup,"017","66",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"American Express Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"018","67",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Argentina Payment Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"019","69",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Multiple Payment Forms",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"020","6B",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Passenger Transport Ancillary Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"021","6C",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Travel Tag Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"022","6D",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Issuer-Supplied Data",VisaDatasetVarFieldParser.class))
			.add(new FieldFormat(idGroup,"023","6E",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Loan Details",VisaDatasetVarFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"024","71",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"[a-fA-F0-9]*",4,510,"Free-Form Description Data (Client-to-Client Data)|Additional Sender Data|Free Form Text (Original Credit Transactions)",VisaDatasetVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","01",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Free-form data",VisaTagVarFieldParser.class))
				.add(new FieldFormat(idGroup,"002","02",HEXEBCDIC_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Original Credit Application Data",VisaTagVarFieldParser.class))
			)
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"105","105",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"106","106",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"107","107",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"108","108",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"109","109",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"110","110",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"111","111",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for ISO use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"112","112",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"113","113",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"114","114",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"115","115",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"116","116",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"117","117",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"118","118",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"119","119",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for national use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"120","120",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"121","121",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for private use",TrimVarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"122","122",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"123","123",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"124","124",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"125","125",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for private use",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"126","126",NONE.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",2,510,"Reserved for private use",Visa126VarFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",HEX_TO_ASCII.getId(),null,null,"(?s).+",16,null,"Visa Merchant Identifier",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"(?s).+",34,null,"Cardholder Certificate Serial Number",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"(?s).+",34,null,"Merchant Certificate Serial Number",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",40,null,"Transaction ID (XID)",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",40,null,"CAVV Data",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",12,null,"CVV2 Authorization IssuerRequest Data and American Express CID Data",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"012","12",HEX_TO_BINARY.getId(),null,null,"(?s).+",6,null,"Service Indicators",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"013","13",HEX_TO_ASCII.getId(),null,null,"(?s).+",2,null,"POS Environment",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"015","15",HEX_TO_ASCII.getId(),null,null,"(?s).+",2,null,"MasterCard UCAF Collection Indicator",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"016","16",HEX_TO_ASCII.getId(),null,null,"(?s).+",66,null,"MasterCard UCAF Field",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"(?s).+",24,null,"Agent Unique Account Result",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"(?s).+",2,null,"Dynamic Currency Conversion Indicator",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"127","127",HEX_TO_ASCII.getId(),NONE.getId(),HEX_DOUBLE.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"128","128",HEX_TO_ASCII.getId(),null,null,"[a-fA-F0-9]+",64,null,"Message authentication code",FixedFieldParser.class));
	}

}
