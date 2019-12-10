package com.fimet.cfg.db;

import static com.fimet.core.entity.sqlite.Simulator.*;

import java.sql.SQLException;

import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.fimet.persistence.sqlite.dao.SimulatorDAO;
import com.fimet.persistence.sqlite.dao.SimulatorMessageDAO;
import com.fimet.simulator.field.impl.IfHasSetNewDateMMdd;
import com.fimet.simulator.field.impl.IfHasSetNewDateMMddhhmmss;
import com.fimet.simulator.field.impl.IfHasSetNewDatehhmmss;
import com.fimet.simulator.field.impl.IfHasSetAmount;
import com.fimet.simulator.field.impl.IfHasSetPanLast4Digits;
import com.fimet.simulator.field.impl.IfHasSetRRN;
import com.fimet.simulator.field.impl.SetNewDateMMdd;
import com.fimet.simulator.field.impl.SetNewDatehhmmss;
import com.fimet.simulator.field.impl.SetRandom15N;
import com.fimet.simulator.field.impl.SetRandom6N;
import com.fimet.simulator.field.impl.SetRandom9N;
import com.fimet.simulator.field.impl.IfHasSetCorrectPanLastDigit;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class SimulatorCreator implements ICreator {
	
	public static final int ISS_POS34_VISA = 0;
	public static final int ISS_POS34_MASTERCARD = 1;
	public static final int ISS_POS34_AMEX = 2;
	public static final int ISS_POS34_BANCOMER = 3;
	public static final int ISS_POS34_BANAMEX = 4;
	public static final int ISS_POS34_PROSA = 5;
	public static final int ISS_POS34_EMI360 = 6;
	public static final int ISS_POS34_EVERTEC = 7;
	public static final int ISS_POS34_BANCOPPEL = 8;
	public static final int ISS_ATM34_VISA = 9;
	public static final int ISS_ATM34_MASTERCARD = 10;
	public static final int ISS_ATM34_BANCOMER = 11;
	public static final int ISS_ATM34_BANAMEX = 12;
	public static final int ISS_ATM34_PROSA = 13;
	public static final int ISS_ATM34_BANCOPPEL = 14;
	public static final int ISS_ATM34_EVERTEC = 15;
	public static final int ISS_ATM34_EMI360 = 16;
	public static final int ISS_ATM34_TELEFONICAS = 17;
	public static final int ISS_CEL34_CEL = 18;
	public static final int ISS_CEL34_BANCOMER = 19;
	public static final int ISS_COR34_BANAMEX = 20;
	public static final int ISS_POS34_DISCOER = 21;
	public static final int ACQ_POS34_VISA = 22;
	public static final int ACQ_POS34_MASTERCARD = 23;
	public static final int ACQ_POS34_TPV = 24;
	public static final int ACQ_POS34_NACIONAL = 25;
	public static final int ACQ_ATM34_VISA = 26;
	public static final int ACQ_ATM34_MASTERCARD = 27;
	public static final int ACQ_ATM34_NACIONAL = 28;
	
	
	SimulatorMessageDAO daoMsg;
	SimulatorDAO dao;
	ConnectionSource connection;
	public SimulatorCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new SimulatorDAO(connection);
		daoMsg = new SimulatorMessageDAO(connection);
	}
	public SimulatorCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, Simulator.class);
		TableUtils.createTableIfNotExists(connection, SimulatorMessage.class);
		return this;
	}
	public SimulatorCreator drop() throws SQLException {
		TableUtils.dropTable(connection, Simulator.class, true);
		TableUtils.dropTable(connection, SimulatorMessage.class, true);
		return this;
	}
	public SimulatorCreator insertAll() {
		insertAllIssuers();
		insertAllAcquirers();
		return this;
	}
	public SimulatorCreator insertAllIssuers() {
		
		// SimulatorMessage
		insertPOS34IssVisa();
		insertPOS34IssMasterCard();
		insertPOS34IssBancomer();
		insertPOS34IssBanamex();
		insertPOS34IssProsa();
		insertPOS34IssEvertec();
		insertPOS34IssEmi360();
		insertPOS34IssBancoppel();
		insertPOS34IssAmex();
		insertPOS34IssDiscover();
		
		insertATM34IssVisa();
		insertATM34IssMasterCard();
		insertATM34IssBancomer();
		insertATM34IssBanamex();
		insertATM34IssEmi360();
		insertATM34IssProsa();
		insertATM34IssBancoppel();
		insertATM34IssEvertec();
		insertATM34IssTelefonicas();
		
		insertCEL34IssCobranzaEnLinea();
		insertCEL34IssBancomer();

		
		insertCOR34IssBanamex();

		long count = dao.count();
		System.out.println("\n\n ******************* Number of rows: " + count + " **********************\n\n");
		return this;
	}
	public SimulatorCreator insertAllAcquirers() {
		
		// SimulatorMessage
		
		insertPOS34AcqVisa();
		insertPOS34AcqTPV();
		insertPOS34AcqMC();
		insertPOS34AcqNacional();
		insertATM34AcqVisa();
		insertATM34AcqMC();
		insertATM34AcqNacional();

		long count = dao.count();
		System.out.println("\n\n ******************* Number of rows: " + count + " **********************\n\n");
		return this;
	}
	public void insertPOS34IssVisa() {
		int id = ISS_POS34_VISA;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Visa",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "14,18,22,35,43,55,59,60,126,38,39,44,104")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "5")
			.addIncludeFieldFix("62.1", "A")
			.addIncludeFieldCls("62.2", SetRandom15N.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0120", "", "")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "14,18,22,43,60,59,38")
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("62.1", "A")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssMasterCard() {
		int id = ISS_POS34_MASTERCARD;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 MasterCard",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "12,13,14,18,22,23,42,43,61,35,43,61")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("48", "R26032178701P8801Y920309433540101M0216549138005211069003042005061150139059239080204")
			.addIncludeFieldFix("63", "MCWAHG1DW")
			.addIncludeFieldFix("124", "2020000009001150715491389999001300   000000BNMR    0000000000051V0                    19 0                    ")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "", "12,13,14,18,22,23,35,42,43,52,61")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldCls("63", SetRandom9N.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0302", "", "12,13,14,18,22,23,38,42,43,61")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "12,13,14,18,22,23,38,42,43,61")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "12,13,14,18,22,28,38,42,43,60,61,90")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssDiscover() {
		int id = ISS_POS34_DISCOER;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Discover",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "12,13,14,18,22,23,24,35,61,62,63,104,126")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", " M")
			.addIncludeFieldFix("48", "          809117255751063")
			.addIncludeFieldFix("49", "840")
			.addIncludeFieldFix("124", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "", "12,13,14,18,22,23,35,42,43,52,61")
			.addIncludeFieldCls("12", SetNewDatehhmmss.class)
			.addIncludeFieldCls("13", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldCls("63", SetRandom9N.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0302", "", "12,13,14,18,22,23,38,42,43,61")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "12,13,14,18,22,23,38,42,43,61")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "12,13,14,18,22,24,28,38,42,43,60,61,62,90")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssBancomer() {
		int id = ISS_POS34_BANCOMER;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Bancomer",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,18,22,25,32,35,37,41,43,48,49,60,61,62","18,43,48,60,62,63.B2,63.B3,63.B4")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssBanamex() {
		int id = ISS_POS34_BANAMEX;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Banamex",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,18,22,32,35,37,41,43,48,49,60,61,62,63","18,43,48,60,62,63.Q1,63.B2,63.B3,63.B4")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("63.C4", "111111111111")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssProsa() {
		int id = ISS_POS34_PROSA;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Prosa",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,18,22,32,35,37,41,43,48,49,60,61,62,63","18,43,48,60,62,63.Q1,63.B2,63.B3,63.B4")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("63.C4", "111111111111")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssEvertec() {
		int id = ISS_POS34_EVERTEC;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Evertec",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,18,22,32,35,37,41,43,48,49,60,61,62,63","18,43,48,60,62,63.Q1,63.B2,63.B3,63.B4")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("63.C4", "111111111111")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssEmi360() {
		int id = ISS_POS34_EMI360;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Emisor 360",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,18,22,32,35,37,41,43,48,49,60,61,62,63","6,18,43,48,51,60,62,63.Q1,63.Q2,63.B2,63.B3,63.B4,63.C6")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("63.C4", "111111111111")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","6,12,13,15,17,38,43,48,51,60,62,63")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","6,12,13,15,17,38,43,48,51,60,62,63")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "6,12,13,15,17,38,43,48,51,60,62,63")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "6,12,13,15,17,38,43,48,51,60,62,63")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssBancoppel() {
		int id = ISS_POS34_BANCOPPEL;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 BanCoppel",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,18,22,32,35,37,39,41,42,43,48,49,60,61,63,125","18,42,43,48,60,62,63.Q1,63.B2,63.B3,63.B4,125")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("63.C4", "111111111111")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,18,38,42,43,48,60,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,18,38,42,43,48,60,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "ISO026000070", "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,42,43,48,60,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "ISO026000070", "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,42,43,48,60,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34IssAmex() {
		int id = ISS_POS34_AMEX;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Amex",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,18,22,32,35,37,41,43,48,49,60,61,62,63","18,43,48,60,62,63.Q1,63.B2,63.B3,63.B4")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("63.C4", "111111111111")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssVisa() {
		int id = ISS_ATM34_VISA;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Visa",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id,"0102006700000000000000", "0100", "", "12,13,14,18,19,22,28,35,43,52,53,55,59,60,63,126,38,39,44,104")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("63", "00000001")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "", "12,13,14,18,22,28,35,43,52,53,55,59,60,63,126,38,39,44,104")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "5")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0120", "", "")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "14,18,22,43,60,59,38")
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("62.1", "A")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "12,13,14,17,18,22,28,38,42,43,60,61,90")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssMasterCard() {
		int id = ISS_ATM34_MASTERCARD;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 MasterCard",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "12,13,14,18,22,23,42,43,61,35,43,61")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("48", "R26032178701P8801Y920309433540101M0216549138005211069003042005061150139059239080204")
			.addIncludeFieldFix("63", "MCWAHG1DW")
			.addIncludeFieldFix("124", "2020000009001150715491389999001300   000000BNMR    0000000000051V0                    19 0                    ")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "", "12,13,14,18,22,23,35,42,43,52,61")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldCls("63", SetRandom9N.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0302", "", "12,13,14,18,22,23,38,42,43,61")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "12,13,14,18,22,23,38,42,43,61")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "12,13,14,18,22,28,38,42,43,60,61,90")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssBancomer() {
		int id = ISS_ATM34_BANCOMER;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Bancomer",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "","9,18,43,48,52,60,62")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
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
			new SimulatorMessage(id, "0420", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssBanamex() {
		int id = ISS_ATM34_BANAMEX;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Banamex",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id,"ISO015000077", "0200", "3,4,7,11,12,13,17,32,35,37,41,43,49,60,61,126","15,18,48,52,120")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "000000485282")
			.addIncludeFieldFix("102", "0165464668")
			.addIncludeFieldFix("126.Q1", "0 ")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssProsa() {
		int id = ISS_ATM34_PROSA;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Prosa",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "","18,43,48,52,60,62,120")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("60", "BNMXINT1+000")
			.addIncludeFieldFix("102", "")
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
			new SimulatorMessage(id, "0420", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssBancoppel() {
		int id = ISS_ATM34_BANCOPPEL;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 BanCoppel",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "","9,18,42,43,48,52,60,62,120,125")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "","12,13,15,17,18,38,42,43,48,60,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "","12,13,15,17,18,38,42,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssEvertec() {
		int id = ISS_ATM34_EVERTEC;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Evertec",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "","9,18,43,48,52,60,62")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
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
			new SimulatorMessage(id, "0420", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssEmi360() {
		int id = ISS_ATM34_EMI360;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Emisor 360",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "","18,43,48,52,60,62,120")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("60", "BNMXINT1+000")
			.addIncludeFieldFix("102", "")
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
			new SimulatorMessage(id, "0420", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertATM34IssTelefonicas() {
		int id = ISS_ATM34_TELEFONICAS;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Telefonicas",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "","18,43,48,52,60,62,120")
			.addIncludeFieldCls("15", SetNewDateMMdd.class)
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
			.addIncludeFieldFix("60", "BNMXINT1+000")
			.addIncludeFieldFix("102", "")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertCEL34IssCobranzaEnLinea() {
		int id = ISS_CEL34_CEL;
		dao.insertOrUpdate(new Simulator(id,"CEL 3.4 CobranzaLinea",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,32,35,37,41,43,48,49,60,61","15,18,43,48,52,60,62,63.Q1,63.B2,63.B3,63.B4")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,90", "12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertCEL34IssBancomer() {
		int id = ISS_CEL34_BANCOMER;
		dao.insertOrUpdate(new Simulator(id,"CEL 3.4 Bancomer",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "3,4,7,11,12,13,17,32,35,37,41,43,48,49,60,61","15,18,43,48,52,60,62,63.Q1,63.B2,63.B3,63.B4")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "02 0")
		);
		id++;
	}
	public void insertCOR34IssBanamex() {
		int id = ISS_COR34_BANAMEX;
		dao.insertOrUpdate(new Simulator(id,"COR 3.4 Banamex",ISSUER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id,"ISO015000077", "0200", "3,4,7,11,12,13,17,32,35,37,41,43,49,60,61,126","15,18,48,52,120")
			.addIncludeFieldCls("38", SetRandom6N.class)
			.addIncludeFieldFix("39", "00")
			.addIncludeFieldFix("44", "000000485282")
			.addIncludeFieldFix("102", "0165464668")
			.addIncludeFieldFix("126.Q1", "0 ")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0221", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90","12,13,15,17,38,43,48,60,62")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0421", "3,4,7,11,12,13,15,17,32,35,37,38,39,41,43,48,49,60,61,62,90", "12,13,15,17,38,43,48,60,61,62,100")
			.addIncludeFieldFix("39", "00")
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldFix("39", "00")
		);
		id++;
	}
	public void insertPOS34AcqVisa() {
		int id = ACQ_POS34_VISA;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Visa Acq",ACQUIRER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0120", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
		);
		id++;
	}
	public void insertPOS34AcqTPV() {
		int id = ACQ_POS34_TPV;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 TPV Acq",ACQUIRER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.9.track2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.10", IfHasSetPanLast4Digits.class)
			.addIncludeFieldCls("63.QK.1.1", IfHasSetPanLast4Digits.class)
			.addIncludeFieldCls("63.QK.1.8", IfHasSetAmount.class)
			.addIncludeFieldCls("63.QK.1.13", IfHasSetRRN.class)
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
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.9.track2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.10", IfHasSetPanLast4Digits.class)
			.addIncludeFieldCls("63.QK.1.1", IfHasSetPanLast4Digits.class)
			.addIncludeFieldCls("63.QK.1.8", IfHasSetAmount.class)
			.addIncludeFieldCls("63.QK.1.13", IfHasSetRRN.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.9.track2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.10", IfHasSetPanLast4Digits.class)
			.addIncludeFieldCls("63.QK.1.1", IfHasSetPanLast4Digits.class)
			.addIncludeFieldCls("63.QK.1.8", IfHasSetAmount.class)
			.addIncludeFieldCls("63.QK.1.13", IfHasSetRRN.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
		);
		id++;
	}
	public void insertPOS34AcqMC() {
		int id = ACQ_POS34_MASTERCARD;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 MasterCard Acq",ACQUIRER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0120", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
		);
		id++;
	}
	public void insertPOS34AcqNacional() {
		int id = ACQ_POS34_NACIONAL;
		dao.insertOrUpdate(new Simulator(id,"POS 3.4 Nacional Acq",ACQUIRER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.10.track2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.11", IfHasSetPanLast4Digits.class)
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
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.10.track2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.11", IfHasSetPanLast4Digits.class)
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
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.10.track2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.11", IfHasSetPanLast4Digits.class)
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
			.addIncludeFieldCls("35", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.10.track2", IfHasSetCorrectPanLastDigit.class)
			.addIncludeFieldCls("63.EZ.11", IfHasSetPanLast4Digits.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0800", "", "")
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
		);
		id++;
	}
	public void insertATM34AcqVisa() {
		int id = ACQ_ATM34_VISA;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Visa Acq",ACQUIRER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0120", "", "")
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "")
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
		id++;
	}
	public void insertATM34AcqMC() {
		int id = ACQ_ATM34_MASTERCARD;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 MasterCard Acq",ACQUIRER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0120", "", "")
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0400", "", "")
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
		id++;
	}
	public void insertATM34AcqNacional() {
		int id = ACQ_ATM34_NACIONAL;
		dao.insertOrUpdate(new Simulator(id,"ATM 3.4 Nacional Acq",ACQUIRER));
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0100", "", "")
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0200", "", "")
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0220", "", "")
			.addIncludeFieldCls("7", IfHasSetNewDateMMddhhmmss.class)
			.addIncludeFieldCls("12", IfHasSetNewDatehhmmss.class)
			.addIncludeFieldCls("13", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("15", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("16", IfHasSetNewDateMMdd.class)
			.addIncludeFieldCls("17", IfHasSetNewDateMMdd.class)
		);
		daoMsg.insertOrUpdate(
			new SimulatorMessage(id, "0420", "", "")
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
		id++;
	}
}

