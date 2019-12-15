package com.fimet.core.impl.sqlite;

import java.sql.SQLException;

import com.fimet.core.IPreferencesManager;
import com.fimet.core.ISO8583.adapter.IAdapterManager;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.Parser;
import com.fimet.core.entity.sqlite.Preference;
import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.fimet.core.entity.sqlite.Socket;
import com.fimet.core.simulator.ISimulator;
import com.fimet.parser.msg.iso.impl.NacionalParser;
import com.fimet.persistence.sqlite.IDataBaseCreator;
import com.fimet.persistence.sqlite.dao.EnviromentDAO;
import com.fimet.persistence.sqlite.dao.EnviromentTypeDAO;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;
import com.fimet.persistence.sqlite.dao.FieldFormatGroupDAO;
import com.fimet.persistence.sqlite.dao.MessageIsoDAO;
import com.fimet.persistence.sqlite.dao.ParserDAO;
import com.fimet.persistence.sqlite.dao.PreferenceDAO;
import com.fimet.persistence.sqlite.dao.SimulatorDAO;
import com.fimet.persistence.sqlite.dao.SimulatorMessageDAO;
import com.fimet.persistence.sqlite.dao.SocketDAO;
import com.fimet.simulator.SimulatorAcquirer;
import com.fimet.simulator.SimulatorIssuer;
import com.fimet.simulator.field.impl.IfHasSetCorrectPanLastDigit;
import com.fimet.simulator.field.impl.IfHasSetNewDateMMdd;
import com.fimet.simulator.field.impl.IfHasSetNewDateMMddhhmmss;
import com.fimet.simulator.field.impl.IfHasSetNewDatehhmmss;
import com.fimet.simulator.field.impl.SetNewDateMMdd;
import com.fimet.simulator.field.impl.SetRandom6N;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import static com.fimet.commons.converter.Converter.NONE;
import static com.fimet.commons.converter.Converter.ASCII_TO_HEX;
import static com.fimet.commons.numericparser.NumericParser.DEC;
import static com.fimet.core.ISO8583.parser.IParser.ISO8583;
import static com.fimet.core.entity.sqlite.Simulator.ACQUIRER;
import static com.fimet.core.entity.sqlite.Simulator.ISSUER;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class SQLiteCreator implements IDataBaseCreator {
	ConnectionSource connection;
	@Override
	public void create(ConnectionSource connection) throws SQLException {
		this.connection = connection;
		createTables();
		insertData();
	}
	private void createTables() throws SQLException {
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.UseCaseReport.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Socket.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.EnviromentType.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Enviroment.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.DataBase.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Ftp.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldFormatGroup.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldFormat.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Simulator.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.SimulatorMessage.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Rule.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldMapper.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Parser.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.MessageIso.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.Preference.class);
	}
	Enviroment enviroment;
	IParser parserNational;
	ISimulator simulatorAcqurier;
	ISimulator simulatorIssuer;
	private void insertData() {
		insertPreferences();
		insertEnviroments();
		insertFields();
		insertSimulators();
		insertParsers();
		insertMessagesIso();
		insertSockets();
	}
	private void insertMessagesIso() {
		MessageIsoDAO dao = new MessageIsoDAO(connection);
		dao.insertOrUpdate(new MessageIso(0,0,"0800","000000",0,0,"ECHO","NETWORK","003E49534F3835383330303030303038303032323138383030303030303030303030303030303030303732363136323231333136323231333132333131323331"));
		dao.insertOrUpdate(new MessageIso(1,0,"0200","000000",0,0,"PURCHASE","MERCHANT","00D749534F383538333030303030303230303732333843363831303841313830303031363132333435363738393031323334353630303030303030303030303030313030303030373236313632323133363132343531313632323133313233313132333139393939303132303031353930323132303030303031363932303933303030333131393620202020202020204D45524348414E5420202020202020202020202020204C4F434154494F4E20202020205354455553303237393939393939392020202020202020202020203030303030303030343834"));
		dao.insertOrUpdate(new MessageIso(2,0,"0420","000000",0,0,"REVERSAL PURCHASE","MERCHANT","00DF49534F3835383330303030303034323037323338433638313045413138303030313631323334353637383930313233343536303030303030303030303030303130303030313233313136323231333631323435313136323231333132333131323331393939393031323030313539303231323030303030313639323039333132333435363030303030333131393620202020202020204D45524348414E5420202020202020202020202020204C4F434154494F4E20202020205354455553303237393939393939392020202020202020202020203030303030303030343834"));
	}
	private void insertPreferences() {
		new PreferenceDAO(connection).insert(new Preference(IPreferencesManager.ENVIROMENT_AUTOSTART, "0"));		
	}
	private void insertParsers() {
		Parser parser = new Parser(0, "National", 0, com.fimet.parser.msg.iso.impl.NacionalParser.class.getName(), NONE.getId(), ISO8583, "M3+N P");
		new ParserDAO(connection).insert(parser);		
		parserNational = new NacionalParser(parser);
	}
	private void insertEnviroments() {
		new EnviromentTypeDAO(connection).insert(new EnviromentType(0, "POS"));
		new EnviromentDAO(connection).insert(enviroment = new Enviroment(0, 0, "FIMET", null, "C:/", null, "127.0.0.1"));
	}
	private void insertSockets() {
		SocketDAO dao = new SocketDAO(connection);
		dao.insert(new Socket(0, enviroment, "Acquirer", "127.0.0.1", 8583, true, true, true, "Acquirers", parserNational, simulatorAcqurier, IAdapterManager.MLI_EXCLISIVE));
		dao.insert(new Socket(1, enviroment, "Issuer", "127.0.0.1", 8583, true, false, false, "Issuers", parserNational, simulatorIssuer, IAdapterManager.MLI_EXCLISIVE));
	}
	private void insertSimulators() {
		SimulatorDAO dao = new SimulatorDAO(connection);
		SimulatorMessageDAO daoMsg = new SimulatorMessageDAO(connection);
		int id = 0;
		simulatorIssuer = new SimulatorIssuer(id, "National Issuer");
		dao.insertOrUpdate(new Simulator(id,"National Issuer",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "","")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		
		id = 1;
		simulatorAcqurier = new SimulatorAcquirer(id, "National Acquirer");
		dao.insert(new Simulator(id,"National Acquirer",ACQUIRER));
		daoMsg.insert(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
		);		
	}
	private void insertFields() {
		int idGroup = 0;
		new FieldFormatGroupDAO(connection).insert(new FieldFormatGroup(0, "National"));
		FieldFormatDAO dao = new FieldFormatDAO(connection);
	    dao.insert(new FieldFormat(idGroup,"002","2",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9*]+",2,19,"Primary account number (PAN)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"003","3",NONE.getId(),null,null,".+",6,null,"Processing code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"004","4",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, transaction",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"005","5",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, settlement",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"006","6",NONE.getId(),null,null,"[0-9]+",12,null,"Amount, cardholder billing",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"007","7",NONE.getId(),null,null,"[0-9]+",10,null,"Transmission date & time",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"008","8",NONE.getId(),null,null,"[0-9]+",8,null,"Amount, cardholder billing fee",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"009","9",NONE.getId(),null,null,"[0-9]+",8,null,"Conversion rate, settlement",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"010","10",NONE.getId(),null,null,"[0-9]+",8,null,"Conversion rate, cardholder billing",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"011","11",NONE.getId(),null,null,"[0-9]+",6,null,"System trace audit number (STAN)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"012","12",NONE.getId(),null,null,"[0-9]+",6,null,"Local transaction time (hhmmss)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"013","13",NONE.getId(),null,null,"[0-9]+",4,null,"Local transaction date (MMDD)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"014","14",NONE.getId(),null,null,"[0-9]+",4,null,"Expiration date",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"015","15",NONE.getId(),null,null,"[0-9]+",4,null,"Settlement date",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"016","16",NONE.getId(),null,null,"[0-9]+",4,null,"Currency conversion date",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"017","17",NONE.getId(),null,null,"[0-9]+",4,null,"Capture date MMDD",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"018","18",NONE.getId(),null,null,"[A-Za-z0-9]+",4,null,"Merchant type, or merchant category code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"019","19",NONE.getId(),null,null,"[0-9]+",3,null,"Acquiring institution (country code)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"020","20",NONE.getId(),null,null,"[0-9]+",3,null,"PAN extended (country code)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"021","21",NONE.getId(),null,null,"[0-9]+",3,null,"Forwarding institution (country code)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"022","22",NONE.getId(),null,null,"[0-9]+",3,null,"Point of service entry mode",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"023","23",NONE.getId(),null,null,"[0-9]+",3,null,"Application PAN sequence number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"024","24",NONE.getId(),null,null,"[0-9]+",3,null,"Function code (ISO 8583:1993), or network international identifier (NII)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"025","25",NONE.getId(),null,null,"[A-Z0-9]+",2,null,"Point of service condition code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"026","26",NONE.getId(),null,null,"[0-9]+",2,null,"Point of service capture code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"027","27",NONE.getId(),null,null,"[0-9]+",1,null,"Authorizing identification response length",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"028","28",NONE.getId(),null,null,"[0-9]+",9,null,"Amount, transaction fee",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"029","29",NONE.getId(),null,null,"[0-9]+",9,null,"Amount, settlement fee",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"030","30",NONE.getId(),null,null,"[0-9]+",9,null,"Amount, transaction processing fee",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"031","31",NONE.getId(),null,null,"[0-9]+",9,null,"Amount, settlement processing fee",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"032","32",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,11,"Acquiring institution identification code",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"033","33",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,11,"Forwarding institution identification code",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"034","34",NONE.getId(),NONE.getId(),DEC.getId(),"[^a-zA-Z ]+",2,28,"Primary account number, extended",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"035","35",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9*]{8,19}(=|D).+",2,37,"Track 2 data",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"036","36",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",3,104,"Track 3 data",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"037","37",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",12,null,"Retrieval reference number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"038","38",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",6,null,"Authorization identification response",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"039","39",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",2,null,"Response code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"040","40",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Service restriction code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"041","41",NONE.getId(),null,null,"(?s).+",16,null,"Card acceptor terminal identification",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"042","42",NONE.getId(),null,null,"(?s).+",15,null,"Card acceptor identification code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"043","43",NONE.getId(),null,null,"(?s).+",40,null,"Card acceptor name/location",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"044","44",NONE.getId(),NONE.getId(),DEC.getId(),".*",2,25,"Additional response data",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"045","45",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",2,76,"Track 1 data",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"046","46",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (ISO)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"047","47",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]+",3,999,"Additional data (national)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"048","48",NONE.getId(),NONE.getId(),DEC.getId(),".*",3,999,"Additional data (private)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"049","49",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, transaction",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"050","50",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, settlement",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"051","51",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",3,null,"Currency code, cardholder billing",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"052","52",NONE.getId(),null,null,"[a-fA-F0-9* ]+",16,null,"Personal identification number data",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"053","53",NONE.getId(),null,null,"[0-9]+",16,null,"Security related control information",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"054","54",NONE.getId(),NONE.getId(),DEC.getId(),"[a-zA-Z0-9 ]*",3,12,"Additional amounts",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"055","55",ASCII_TO_HEX.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved ISO",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"056","56",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (ISO)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"057","57",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"058","58",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"059","59",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved (national)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"060","60",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (national) (e.g. settlement request: batch number, advice transactions: original transaction amount, batch upload: original MTI plus original RRN plus original STAN, etc)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"061","61",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (private) (e.g. CVV2/service code   transactions)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"062","62",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (private) (e.g. transactions: invoice number, key exchange transactions: TPK key, etc.)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"063","63",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Reserved (private)",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"064","64",NONE.getId(),null,null,"[a-fA-F0-9]+",16,null,"Message authentication code (MAC)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"065","65",NONE.getId(),null,null,"[a-fA-F0-9]+",1,null,"Extended bitmap indicator",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"066","66",NONE.getId(),null,null,"[0-9]+",1,null,"Settlement code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"067","67",NONE.getId(),null,null,"[0-9]+",2,null,"Extended payment code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"068","68",NONE.getId(),null,null,"[0-9]+",3,null,"Receiving institution country code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"069","69",NONE.getId(),null,null,"[0-9]+",3,null,"Settlement institution country code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"070","70",NONE.getId(),null,null,"[0-9]+",3,null,"Network management information code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"071","71",NONE.getId(),null,null,"[0-9]+",4,null,"Message number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"072","72",NONE.getId(),null,null,"[0-9]+",4,null,"Last message's number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"073","73",NONE.getId(),null,null,"[0-9]+",6,null,"Action date (YYMMDD)",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"074","74",NONE.getId(),null,null,"[0-9]+",10,null,"Number of credits",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"075","75",NONE.getId(),null,null,"[0-9]+",10,null,"Credits, reversal number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"076","76",NONE.getId(),null,null,"[0-9]+",10,null,"Number of debits",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"077","77",NONE.getId(),null,null,"[0-9]+",10,null,"Debits, reversal number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"078","78",NONE.getId(),null,null,"[0-9]+",10,null,"Transfer number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"079","79",NONE.getId(),null,null,"[0-9]+",10,null,"Transfer, reversal number",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"080","80",NONE.getId(),null,null,"[0-9]+",10,null,"Number of inquiries",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"081","81",NONE.getId(),null,null,"[0-9]+",10,null,"Number of authorizations",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"082","82",NONE.getId(),null,null,"[0-9]+",12,null,"Credits, processing fee amount",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"083","83",NONE.getId(),null,null,"[0-9]+",12,null,"Credits, transaction fee amount",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"084","84",NONE.getId(),null,null,"[0-9]+",12,null,"Debits, processing fee amount",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"085","85",NONE.getId(),null,null,"[0-9]+",12,null,"Debits, transaction fee amount",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"086","86",NONE.getId(),null,null,"[0-9]+",16,null,"Total amount of credits",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"087","87",NONE.getId(),null,null,"[0-9]+",16,null,"Credits, reversal amount",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"088","88",NONE.getId(),null,null,"[0-9]+",16,null,"Total amount of debits",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"089","89",NONE.getId(),null,null,"[0-9]+",16,null,"Debits, reversal amount",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"090","90",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",42,null,"Original data elements",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"091","91",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",1,null,"File update code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"092","92",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",2,null,"File security code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"093","93",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",5,null,"IssuerResponse indicator",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"094","94",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",7,null,"Service indicator",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"095","95",NONE.getId(),null,null,"[a-zA-Z0-9 ]+",42,null,"Replacement amounts",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"096","96",NONE.getId(),null,null,"[a-fA-F0-9]+",64,null,"Message security code",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"097","97",NONE.getId(),null,null,"[0-9]+",16,null,"Net settlement amount",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"098","98",NONE.getId(),null,null,"(?s).+",25,null,"Payee",FixedFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"099","99",NONE.getId(),NONE.getId(),DEC.getId(),"[0-9]+",2,11,"Settlement institution identification code",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"100","100",NONE.getId(),NONE.getId(),DEC.getId(),"[A-Za-z0-9 ]*",2,11,"Receiving institution identification code",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"101","101",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,17,"File name",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"102","102",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",2,28,"Account identification 1",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"103","103",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",2,28,"Account identification 2",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"104","104",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,100,"Transaction description",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"105","105",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"106","106",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"107","107",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"108","108",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"109","109",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"110","110",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"111","111",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for ISO use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"112","112",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"113","113",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"114","114",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"115","115",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"116","116",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"117","117",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"118","118",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"119","119",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for national use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"120","120",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).*",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"121","121",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"122","122",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"123","123",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"124","124",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"125","125",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"126","126",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"127","127",NONE.getId(),NONE.getId(),DEC.getId(),"(?s).+",3,999,"Reserved for private use",VarFieldParser.class));
	    dao.insert(new FieldFormat(idGroup,"128","128",NONE.getId(),null,null,"[a-fA-F0-9]+",64,null,"Message authentication code",FixedFieldParser.class));

	}
}
