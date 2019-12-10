package com.fimet.cfg.db.eg.fieldformat;

import static com.fimet.commons.converter.Converter.ASCII_TO_HEX;
import static com.fimet.commons.converter.Converter.NONE;
import static com.fimet.commons.numericparser.NumericParser.DEC;

import com.fimet.cfg.FieldFormatBuilder;
import com.fimet.cfg.enums.FieldFormatGroup;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.parser.field.impl.nac.NacTokenEZVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTokenVarFieldParser;
import com.fimet.parser.field.impl.nac.NacTokensVarFieldParser;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class NacionalPOS {
	FieldFormatDAO dao;
	int idGroup = FieldFormatGroup.NACIONAL_POS.getId();
	public NacionalPOS(FieldFormatDAO dao) {
		this.dao = dao;
	}
	public void insert() {
		dao.insertOrUpdate(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,19,"Primary account number (PAN)",VarFieldParser.class));
		dao.insertOrUpdate(new FieldFormat(idGroup,"035","35",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]{8,19}(=|D).+",2,37,"Track 2 data",VarFieldParser.class));
		new FieldFormatBuilder(new FieldFormat(idGroup,"048","48",NONE.getId(),NONE.getId(),DEC.getId(),".*",3,999,"Additional data (private)",VarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",19,null,"Afiliacion",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",4,null,"Indicador de Cadena",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",4,null,"Indicador de Cadena",FixedFieldParser.class))
		.insertOrUpdate(dao);
		new FieldFormatBuilder(new FieldFormat(idGroup,"063","63",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (private)",NacTokensVarFieldParser.class))
		.add(new FieldFormat(idGroup,"001","01",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 01",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"002","04",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token 04",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"003","17",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Información Autorización VISA",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"004","20",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Información Autorización MasterCard",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"005","B0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B0",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"006","B1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B1",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"007","B2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B2",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"008","B3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B3",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"009","B4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B4",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"010","B5",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token B4",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"011","C0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Código de Validación 2 e Identificadores de Comercio Electrónico",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"012","C4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Datos del Punto de Servicio",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"013","C5",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Información de Multipagos",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"014","C6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token C6",NacTokenVarFieldParser.class))
		.add(new FieldFormatBuilder(new FieldFormat(idGroup,"015","CE",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token CE",NacTokenVarFieldParser.class))
				.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",2,null,"Indicador de Atencion",FixedFieldParser.class))
				.add(new FieldFormat(idGroup,"002","2",ASCII_TO_HEX.getId(),null,null,"(?s).+",200,null,"Datos de autenticación del tarjetahabiente",FixedFieldParser.class))
		)
		.add(new FieldFormat(idGroup,"016","CZ",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Form Facctor Indicator",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"017","ER",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Resultado de Cifrado",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"018","ES",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Terminal Status",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"019","ET",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Tabla de BINES que no Cifran",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"020","EW",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Requerimiento Nueva Llave",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"021","EX",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Respuesta Nueva Llave",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"022","EY",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Cifrado del Track1",NacTokenVarFieldParser.class))
		.add(new FieldFormatBuilder(new FieldFormat(idGroup,"023","EZ",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Banderas y Datos Sensitivos Cifrados",NacTokenEZVarFieldParser.class))
			.add(new FieldFormat(idGroup,"001","1",NONE.getId(),null,null,"(?s).+",20,null,"Key Serial Number",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"002","2",NONE.getId(),null,null,"(?s).+",7,null,"Contador Real de Cifrados ",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,"(?s).+",2,null,"Contador de Cifrados Fallidos Consecutivos",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"(?s).+",1,null,"Bandera de TRACK2",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"(?s).+",2,null,"Modo de Lectura de la Tarjeta",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"(?s).+",2,null,"Longitud de TRACK2",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"(?s).+",1,null,"Bandera de CVV2",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"(?s).+",2,null,"Longitud de cvv2 en claro",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"(?s).+",1,null,"Bandera de TRACK1",FixedFieldParser.class))
			.add(new FieldFormatBuilder(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"(?s).+",48,null,"Datos Sensitivos Cifrados",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"001","track2",NONE.getId(),null,null,"(?s).+",38,null,"Track2 Cifrado",FixedFieldParser.class))
					.add(new FieldFormat(idGroup,"002","cvv2",NONE.getId(),null,null,"(?s).+",10,null,"CVV2 Cifrado",FixedFieldParser.class))
			)
			.add(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"(?s).+",4,null,"4 Ultimos Digitos del PAN",FixedFieldParser.class))
			.add(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"(?s).+",8,null,"CRC32 sobre Datos Sensitivos",FixedFieldParser.class))
		)
		.add(new FieldFormat(idGroup,"024","Q1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Identificador del Modo de Autorización",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"025","Q2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Identificador del Medio de Acceso",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"026","Q3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token Q3",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"027","Q4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token Q4",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"028","Q6",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Información de Cargos Parciales",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"029","QC",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QC",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"030","QF",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"ID de agregador y subafiliado",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"031","QM",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QM",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"032","QN",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token QN",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"033","QO",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Montos Acumulados DCC POS",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"034","QP",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Montos y Folio de DCC POS",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"035","QR",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Información de DCC POS",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"036","QS",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Información del Emisor",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"037","R0",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R0",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"038","R1",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"PAN del Corresponsal en el PIN PAD",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"039","R2",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R2",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"040","R3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token R3",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"041","R4",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Número de Contrato en Transacciones de Cargos Recurrentes",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"042","R7",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Indicador Bonus Merchant y Numero de Referencia de Campañas",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"043","R8",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Línea de Acción CRM",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"044","S3",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Conversión Dinámica de Moneda",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"045","TM",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token TM",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"046","TV",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token TV",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"047","PO",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token PO",NacTokenVarFieldParser.class))
		.add(new FieldFormat(idGroup,"048","PY",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",5,999,"Token PY",NacTokenVarFieldParser.class))
		.insertOrUpdate(dao);
	}
}
