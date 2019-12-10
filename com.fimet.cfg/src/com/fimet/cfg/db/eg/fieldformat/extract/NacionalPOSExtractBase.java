package com.fimet.cfg.db.eg.fieldformat.extract;

import static com.fimet.commons.converter.Converter.NONE;

import com.fimet.cfg.FieldFormatBuilder;
import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalPOSExtractBase {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.EXTRACT_BASE_POS.getId();
	public NacionalPOSExtractBase(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS-TIPO-DESC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS-SITE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"DESC_POS-PERIODO-DESC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"(?s).+",7,null,"DESC_POS-SEQ-DESC.",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"(?s).+",5,null,"DESC_POS-TRML-NBR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"(?s).+",5,null,"DESC_POS-OPERATING-FI-NBR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"(?s).+",4,null,"DESC_POS-TRAN-CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",4,null,"DESC_POS-RESULT-CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",8,null,"DESC_POS-TRAN-DATE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",6,null,"DESC_POS-TRAN-TIME",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS-FORCE-POST-IND",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS-REVERSAL-IND",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"(?s).+",5,null,"DESC_POS-CARD-FI-NBR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS-AUTH-METHOD",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"(?s).+",13,null,"DESC_POS-AMT-DEBIT",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"(?s).+",6,null,"DESC_POS-SWITCH-SEQ-NBR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"017","17",NONE.getId(),null,null,"(?s).+",4,null,"POS-SEG-TRAN-TYPE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"(?s).+",12,null,"POS-SEG-RRN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"(?s).+",4,null,"POS-SEG-TRAN-DATE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"(?s).+",6,null,"POS-SEG-TRAN-TIME",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"(?s).+",6,null,"POS-SEG-PROCESSING-CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"022","22",NONE.getId(),null,null,"(?s).+",4,null,"POS-SEG-MERCHANT-TYPE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"023","23",NONE.getId(),null,null,"(?s).+",3,null,"POS-SEG-POS-ENTRY-MODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"024","24",NONE.getId(),null,null,"(?s).+",2,null,"POS-SEG-RESPONSE-CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"025","25",NONE.getId(),null,null,"(?s).+",6,null,"POS-SEG-AUTH-CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"026","26",NONE.getId(),null,null,"(?s).+",6,null,"POS-SEG-STAN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"027","27",NONE.getId(),null,null,"(?s).+",4,null,"POS-SEG-SETTLEMENT-DATE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"028","28",NONE.getId(),null,null,"(?s).+",16,null,"POS-SEG-CARD-ACCEPTR-ID",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"029","29",NONE.getId(),null,null,"(?s).+",3,null,"POS-SEG-CCY-CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"030","30",NONE.getId(),null,null,"(?s).+",5,null,"POS-SEG-OUT-SWAP",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"031","31",NONE.getId(),null,null,"(?s).+",19,null,"POS-SEG-TRACK2",FixedFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",16,null,"POS-SEG-TRACK2-PAN",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",6,null,"POS-SEG-TRACK2-BIN",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",10,null,"POS-SEG-TRACK2-PLASTICO",FixedFieldParser.class))
			)
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",3,null,"POS-SEG-TRACK2-EXT-PLASTICO",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"032","32",NONE.getId(),null,null,"(?s).+",11,null,"POS-SEG-ACQ-INST-ID",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"033","33",NONE.getId(),null,null,"(?s).+",7,null,"POS-SEG-ADDL-POS-DATA",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"034","34",NONE.getId(),null,null,"(?s).+",13,null,"POS-SEG-FROM-ACCT",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),null,null,"(?s).+",1,null,"POS-SEG-ADVISE_LINEA_BMER",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"036","36",NONE.getId(),null,null,"(?s).+",4,null,"DESC_REC_C60-BANK",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"037","37",NONE.getId(),null,null,"(?s).+",4,null,"DESC_REC_C60-NET",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"038","38",NONE.getId(),null,null,"(?s).+",2,null,"DESC_DIF_DATECAPTURE_TIMESTAMP",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"039","39",NONE.getId(),null,null,"(?s).+",2,null,"FiLLER",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"040","40",NONE.getId(),null,null,"(?s).+",1,null,"POS-SEG-ECOMERCE-IND",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"041","41",NONE.getId(),null,null,"(?s).+",1,null,"POS-SEG-CAVV-UCAF-IND",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"042","42",NONE.getId(),null,null,"(?s).+",4,null,"POS-SEG-JIFFY-POP",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"043","43",NONE.getId(),null,null,"(?s).+",19,null," POS-SEG_PAN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"044","44",NONE.getId(),null,null,"(?s).+",2,null,"POS-SEG_POS_CC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"045","45",NONE.getId(),null,null,"(?s).+",12,null,"POS-SEG-ADD-AMOUNT",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"046","46",NONE.getId(),null,null,"(?s).+",2,null,"POS-SEG-PLATFORM",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"047","47",NONE.getId(),null,null,"(?s).+",5,null,"DESC_POS_DISPATCHER_ID",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"048","48",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_CVV2_PRESENT",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"049","49",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_CVV2_VERIFY",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"050","50",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_CARD_SERVICE_CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"051","51",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_TERMINAL_CAPABILITY",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"052","52",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_FLAG_EMV_FULL",FixedFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"053","53",NONE.getId(),null,null,"(?s).+",6,null,"DESC_POS_PROMO_Q6",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"DESC_POS_PROMO_Q6_DIFERIMIENTO",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",2,null,"DESC_POS_PROMO_Q6_NUM_PAGOS",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"DESC_POS_PROMO_Q6_TIPO_PLAN",FixedFieldParser.class))
		.insertOrUpdate(dao);
		dao.insertOrUpdate(new FieldFormat(idGroup,"054","54",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_FLAG_RESPONDER",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"055","55",NONE.getId(),null,null,"(?s).+",2,null,"DESC_POS_MEDIO_ACCESO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"056","56",NONE.getId(),null,null,"(?s).+",18,null,"DESC_POS_SERIE_DISPOSITIVO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"057","57",NONE.getId(),null,null,"(?s).+",16,null,"DESC_POS_APLICACION_DISPOSITIVO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"058","58",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_TRACK_COMPLETO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"059","59",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_AUTHENTICATION_COLLECTOR_INDICATOR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"060","60",NONE.getId(),null,null,"(?s).+",12,null,"DESC_POS_POS_TERMINAL_DATA",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"061","61",NONE.getId(),null,null,"(?s).+",16,null,"DESC_POS_FILLER_ARQC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"062","62",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_CARRIER_TPV",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"063","63",NONE.getId(),null,null,"(?s).+",20,null,"DESC_POS_SERIE_SIM_TPV",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"064","64",NONE.getId(),null,null,"(?s).+",1,null,"DESC_POS_FLAG_ENVIO_TABLA_BINES",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"065","65",NONE.getId(),null,null,"(?s).+",20,null,"DESC_POS_KSN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"066","66",NONE.getId(),null,null,"(?s).+",1,null,"DESC_VENTANA_CORTE_BX",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"067","67",NONE.getId(),null,null,"(?s).+",1,null,"DESC_ID_BONUS_MERCHANT_BR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"068","68",NONE.getId(),null,null,"(?s).+",6,null,"DESC_ID_REFERENCIA_CMP_BR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"069","69",NONE.getId(),null,null,"(?s).+",20,null,"DESC_NUM_CONTRATO_CR",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"070","70",NONE.getId(),null,null,"(?s).+",1,null,"DESC_BANDERA_VALIDACION_ARQC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"071","71",NONE.getId(),null,null,"(?s).+",12,null,"DESC_MONTO_LOCAL_ADQ_EXTRANJERO (EMISOR 360)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"072","72",NONE.getId(),null,null,"(?s).+",3,null,"DESC_MONEDA_LOCAL_ADQ_EXTRANJERO (EMISOR 360)",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"073","73",NONE.getId(),null,null,"(?s).+",2,null,"DESC-FLAG-TOKEN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"074","74",NONE.getId(),null,null,"(?s).+",1,null,"DESC_FLAG_RESP_VALIDACION_EMV",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"075","75",NONE.getId(),null,null,"(?s).+",1,null,"DESC_FLAG_TIPO-RUTEO",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"076","76",NONE.getId(),null,null,"(?s).+",1,null,"DESC_FLAG_ORIGEN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"077","77",NONE.getId(),null,null,"(?s).+",4,null,"DESC_VISA_MSG_REASON_CODE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"078","78",NONE.getId(),null,null,"(?s).+",1,null,"DESC_VISA_POS_ENVIRONMENT",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"079","79",NONE.getId(),null,null,"(?s).+",1,null,"DESC_UCAF_CI_ORIGINAL",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"080","80",NONE.getId(),null,null,"(?s).+",1,null,"DESC_RAZON_DEGRAD_UCAF_CI",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"081","81",NONE.getId(),null,null,"(?s).+",3,null,"DESC_PAIS_ADQUIRIENTE",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"082","82",NONE.getId(),null,null,"(?s).+",1,null,"DESC_BAND_DCC_ORIGEN",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"083","83",NONE.getId(),null,null,"(?s).+",1,null,"DESC_BAND_SELECCION_DCC",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"084","84",NONE.getId(),null,null,"(?s).+",1,null,"DESC_FLAG_AAV",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"085","85",NONE.getId(),null,null,"(?s).+",1,null,"DESC_FLAG_DICTAMEN_DECLINACION",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"086","86",NONE.getId(),null,null,"(?s).+",3,null,"DESC_ERROR_ISS",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"087","87",NONE.getId(),null,null,"(?s).+",1,null,"POS-SEG-ECOMM-IND-FINAL",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"088","88",NONE.getId(),null,null,"(?s).+",1,null,"POS-SEG-FLAG-RV-CAVV-VISA",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"089","89",NONE.getId(),null,null,"(?s).+",2,null,"DESC_POS_NBR_VAR_FIELDS",FixedFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"090","90",NONE.getId(),null,null,"(?s).+",50,null,"FILLER",FixedFieldParser.class));
	}
}
