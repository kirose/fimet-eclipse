package com.fimet.cfg.db;                                                                                                                                                               
                                                                                                                                                                                        
                                                                                                                                                                                        
import static com.fimet.cfg.db.eg.SimulatorCreator.*;
import static com.fimet.cfg.enums.Parser.*;                                                                                                                                             
import static com.fimet.core.ISO8583.adapter.IAdapterManager.*;                                                                                                                         
                                                                                                                                                                                        
import java.sql.SQLException;                                                                                                                                                           
                                                                                                                                                                                        
import com.fimet.cfg.enums.EnviromentType;                                                                                                                                              
import com.fimet.core.IParserManager;                                                                                                                                                   
import com.fimet.core.ISimulatorManager;                                                                                                                                                
import com.fimet.core.ISO8583.adapter.IAdapter;                                                                                                                                         
import com.fimet.core.ISO8583.adapter.IAdapterManager;                                                                                                                                  
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.entity.sqlite.FieldMapper;
import com.fimet.core.entity.sqlite.Rule;
import com.fimet.core.simulator.ISimulator;
import com.fimet.persistence.sqlite.dao.FieldMapperDAO;
import com.fimet.persistence.sqlite.dao.RuleDAO;
import com.j256.ormlite.support.ConnectionSource;                                                                                                                                       
import com.j256.ormlite.table.TableUtils;                                                                                                                                               
                                                                                                                                                                                        
public class RulesCreator implements ICreator {                                                                                                                                         
	FieldMapperDAO daoField;
	RuleDAO daoRule;
	final char EQUALS = Rule.EQUALS;                                                                                                                                                    
	final char MATCHES = Rule.MATCHES;                                                                                                                                                  
	final char CONTAINS = Rule.CONTAINS;                                                                                                                                                
                                                                                                                                                                                        
	public static final Integer PARSER = 0;                                                                                                                                             
	public static final Integer ADAPTER = 1;                                                                                                                                            
	public static final Integer SIMULATOR = 2;
	public static final Integer ADDRESS = 3;
	ConnectionSource connection;
	public RulesCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		daoField = new FieldMapperDAO(connection);
		daoRule = new RuleDAO(connection);
	}
	public RulesCreator create() throws SQLException {                                                                                                                                  
		TableUtils.createTableIfNotExists(connection, Rule.class);                                                                                                                      
		TableUtils.createTableIfNotExists(connection, FieldMapper.class);                                                                                                               
		return this;                                                                                                                                                                    
	}                                                                                                                                                                                   
	public RulesCreator drop() throws SQLException {                                                                                                                                    
		TableUtils.dropTable(connection, Rule.class, true);                                                                                                                             
		TableUtils.dropTable(connection, FieldMapper.class, true);                                                                                                                      
		return this;                                                                                                                                                                    
	}                                                                                                                                                                                   
	public RulesCreator insertAll() {                                                                                                                                                   
		insertFieldMappers();
		                                                                                                                                                                                
		insertPOS34AdapterRules();
//		insertPOS45AdapterRules();
		insertATM34AdapterRules();
//		insertATM45AdapterRules();
		insertCEL34AdapterRules();
//		insertCEL45AdapterRules();
		insertCor34AdapterRules();
//		insertCor45AdapterRules();
		                                                                                                                                                                                
		insertPOS34SimulatorRules();
		insertATM34SimulatorRules();
		insertCEL34SimulatorRules();
		insertCOR34SimulatorRules();
		                                                                                                                                                                                
		insertPOS34ParserRules();
		insertATM34ParserRules();
		insertCEL34ParserRules();
		insertCOR34ParserRules();
		                                                                                                                                                                                
		return this;                                                                                                                                                                    
	}                                                                                                                                                                                   
	public RulesCreator insertFieldMappers() {                                                                                                                                                 
		daoField.insertOrUpdate(new FieldMapper(PARSER, "parser",IParser.class.getName(), IParserManager.class.getName()));
		daoField.insertOrUpdate(new FieldMapper(ADAPTER, "adapter",IAdapter.class.getName(), IAdapterManager.class.getName()));
		daoField.insertOrUpdate(new FieldMapper(SIMULATOR, "siulator",ISimulator.class.getName(), ISimulatorManager.class.getName()));
		//daoField.insertOrUpdate(new FieldMapper(ADDRESS, "address",String.class.getName(), ISimulatorManager.class.getName()));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertPOS34AdapterRules() {                                                                                                                                             
		int id = EnviromentType.POS34.getId();
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
		return this;                                                                                                                                                                    
	}                                                                                                                                                                                   
//	public RulesCreator insertPOS45AdapterRules() {                                                                                                                                             
//		int id = EnviromentType.POS45.getId();
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
//                                                                                                                                                                                        
//	}                                                                                                                                                                                   
	public RulesCreator insertATM34AdapterRules() {                                                                                                                                             
		int id = EnviromentType.ATM34.getId();
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
		return this;
	}                                                                                                                                                                                   
//	public RulesCreator insertATM45AdapterRules() {                                                                                                                                             
//		int id = EnviromentType.ATM45.getId();
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
//	}                                                                                                                                                                                   
	public RulesCreator insertCEL34AdapterRules() {                                                                                                                                             
		int id = EnviromentType.CEL34.getId();
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
		return this;
	}                                                                                                                                                                                   
//	public RulesCreator insertCEL45AdapterRules() {                                                                                                                                             
//		int id = EnviromentType.CEL45.getId();
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
//	}                                                                                                                                                                                   
	public RulesCreator insertCor34AdapterRules() {                                                                                                                                             
		int id = EnviromentType.COR34.getId();
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
		return this;
	}                                                                                                                                                                                   
//	public RulesCreator insertCor45AdapterRules() {                                                                                                                                             
//		int id = EnviromentType.COR45.getId();
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"00", "mli", CONTAINS, "VISA", MLI_VISA_EXCLUSIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"01", "mli", CONTAINS, "EXCLUSIVE", MLI_EXCLISIVE.getId()));
//		daoRule.insertOrUpdate(new Rule(id,ADAPTER,"02", "mli", CONTAINS, "", MLI_INCLUSIVE.getId()));
//	}                                                                                                                                                                                   
	                                                                                                                                                                                    
	public RulesCreator insertPOS34ParserRules() {                                                                                                                                              
		int POS34 = EnviromentType.POS34.getId();
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"00", "interchange", CONTAINS, "VISA",POS_VISA.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"01", "interface", CONTAINS, "VISA",POS_VISA.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"02", "interchange", CONTAINS, "MASTER",POS_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"03", "interface", CONTAINS, "MASTER",POS_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"04", "interchange", CONTAINS, "MDS",POS_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"05", "interface", CONTAINS, "MDS",POS_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"06", "interface", CONTAINS, "INTERREDES_BCMR",POS_INT_BR.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"07", "interface", CONTAINS, "INTERREDES_BNMX",POS_INT_BX.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"08", "interchange", MATCHES, ".*TPV.*(BR|BCMR|BANCOMER|BBV).*",POS_TPV_BR.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"09", "interface", MATCHES, ".*TPV.*(BR|BCMR|BANCOMER|BBV).*",POS_TPV_BR.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"10", "interchange", MATCHES, ".*TPV.*(BX|BANAMEX|BNMX).*",POS_TPV_BX.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"11", "interface", MATCHES, ".*TPV.*(BX|BANAMEX|BNMX).*",POS_TPV_BX.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"12", "interchange", CONTAINS, "BANCOMER",POS_BANCOMER.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"13", "interface", CONTAINS, "DISCOVER",POS_DISCOVER.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"14", "interchange", CONTAINS, "DISCOVER",POS_DISCOVER.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"15", "interface", CONTAINS, "AMEX",POS_AMEX.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"16", "interchange", CONTAINS, "AMEX",POS_AMEX.getId()));
		daoRule.insertOrUpdate(new Rule(POS34, PARSER,"17", "interchange", CONTAINS, "",POS_NACIONAL.getId()));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertATM34ParserRules() {                                                                                                                                              
		int ATM34 = EnviromentType.ATM34.getId();
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"00", "interchange", CONTAINS, "VISA",ATM_VISA.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"01", "interface", CONTAINS, "VISA",ATM_VISA.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"02", "interchange", CONTAINS, "MASTER",ATM_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"03", "interface", CONTAINS, "MASTER",ATM_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"04", "interchange", CONTAINS, "MDS",ATM_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"05", "interface", CONTAINS, "MDS",ATM_MASTERCARD.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"06", "interchange", CONTAINS, "BANCOMER",ATM_BANCOMER.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"07", "interchange", CONTAINS, "",ATM_NACIONAL.getId()));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertCEL34ParserRules() {                                                                                                                                              
		int ATM34 = EnviromentType.CEL34.getId();
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"00", "name", CONTAINS, "BBVCL",CEL_BBVA.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"01", "interchange", CONTAINS, "COBRANZALINEA",CEL_ADQUIRA.getId()));
		daoRule.insertOrUpdate(new Rule(ATM34, PARSER,"02", "interface", CONTAINS, "COBRANZALINEA",CEL_ADQUIRA.getId()));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertCOR34ParserRules() {                                                                                                                                              
		int COR34 = EnviromentType.COR34.getId();
                                                                                                                                                                                        
		daoRule.insertOrUpdate(new Rule(COR34, PARSER,"00", "interchange", CONTAINS, "CORR",COR_NACIONAL.getId()));
		daoRule.insertOrUpdate(new Rule(COR34, PARSER,"01", "interface", CONTAINS, "CORR",COR_NACIONAL.getId()));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertPOS34SimulatorRules() {                                                                                                                                           
		int POS34 = EnviromentType.POS34.getId();
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00", "type", EQUALS, "ISSUER"));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.00", "interchange", CONTAINS, "VISA", ISS_POS34_VISA));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.01", "interface", CONTAINS, "VISA", ISS_POS34_VISA));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.02", "interchange", CONTAINS, "MASTER", ISS_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.03", "interface", CONTAINS, "MASTER", ISS_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.04", "interchange", CONTAINS, "MDS", ISS_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.05", "interface", CONTAINS, "MDS", ISS_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.06", "interchange", MATCHES, ".*(BANCOMER|BCMR|BBV).*", ISS_POS34_BANCOMER));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.07", "interface", MATCHES, ".*(BANCOMER|BCMR|BBV).*", ISS_POS34_BANCOMER));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.08", "interchange", MATCHES, ".*(BANAMEX|BNMX|BMNX).*", ISS_POS34_BANAMEX));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.09", "interface", MATCHES, ".*(BANAMEX|BNMX|BMNX).*", ISS_POS34_BANAMEX));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.10", "interchange", CONTAINS, "AMEX", ISS_POS34_AMEX));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.11", "interface", CONTAINS, "AMEX", ISS_POS34_AMEX));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.12", "interchange", CONTAINS, "PROSA", ISS_POS34_PROSA));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.13", "interface", CONTAINS, "PROSA", ISS_POS34_PROSA));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.14", "interchange", CONTAINS, "EVERTEC", ISS_POS34_EVERTEC));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.15", "interface", CONTAINS, "EVERTEC", ISS_POS34_EVERTEC));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.16", "interchange", MATCHES, ".*(EMI_360|A360_EMI).*", ISS_POS34_EMI360));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.17", "interface", MATCHES, ".*(EMI_360|A360_EMI).*", ISS_POS34_EMI360));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.18", "interchange", CONTAINS, "BANCOPPEL", ISS_POS34_BANCOPPEL));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.19", "interface", CONTAINS, "BANCOPPEL", ISS_POS34_BANCOPPEL));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.20", "interchange", CONTAINS, "DISCOVER", ISS_POS34_DISCOER));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"00.21", "interface", CONTAINS, "DISCOVER", ISS_POS34_DISCOER));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01", "type", EQUALS, "ACQUIRER"));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.00", "interchange", CONTAINS, "VISA", ACQ_POS34_VISA));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.01", "interface", CONTAINS, "VISA", ACQ_POS34_VISA));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.02", "interchange", CONTAINS, "TPV", ACQ_POS34_TPV));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.03", "interface", CONTAINS, "TPV", ACQ_POS34_TPV));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.04", "interchange", CONTAINS, "MASTER", ACQ_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.05", "interface", CONTAINS, "MASTER", ACQ_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.06", "interchange", CONTAINS, "MDS", ACQ_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.07", "interface", CONTAINS, "MDS", ACQ_POS34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.08", "interchange", CONTAINS, "", ACQ_POS34_NACIONAL));
		daoRule.insertOrUpdate(new Rule(POS34,SIMULATOR,"01.09", "interface", CONTAINS, "", ACQ_POS34_NACIONAL));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertATM34SimulatorRules() {                                                                                                                                           
		int ATM34 = EnviromentType.ATM34.getId();
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00", "type", EQUALS, "ISSUER"));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.00", "interchange", CONTAINS, "VISA", ISS_ATM34_VISA));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.01", "interface", CONTAINS, "VISA", ISS_ATM34_VISA));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.02", "interchange", MATCHES, ".*(MDS|MASTER).*", ISS_ATM34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.03", "interface", MATCHES, ".*(MDS|MASTER).*", ISS_ATM34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.04", "interchange", MATCHES, ".*(BANCOMER|BCMR|BBV).*", ISS_ATM34_BANCOMER));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.05", "interface", MATCHES, ".*(BANCOMER|BCMR|BBV).*", ISS_ATM34_BANCOMER));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.06", "interchange", MATCHES, ".*(BANAMEX|BNMX|BMNX).*", ISS_ATM34_BANAMEX));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.07", "interface", MATCHES, ".*(BANAMEX|BNMX|BMNX).*", ISS_ATM34_BANAMEX));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.08", "interchange", CONTAINS, "PROSA", ISS_ATM34_PROSA));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.09", "interface", CONTAINS, "PROSA", ISS_ATM34_PROSA));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.10", "interchange", CONTAINS, "EVERTE", ISS_ATM34_EVERTEC));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.11", "interface", CONTAINS, "EVERTE", ISS_ATM34_EVERTEC));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.12", "interchange", MATCHES, ".*(EMI.*360|360.*EMI).*", ISS_ATM34_EMI360));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.13", "interface", MATCHES, ".*(EMI.*360|360.*EMI).*", ISS_ATM34_EMI360));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.14", "interchange", CONTAINS, "BANCOPPEL", ISS_ATM34_BANCOPPEL));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"00.15", "interface", CONTAINS, "BANCOPPEL", ISS_ATM34_BANCOPPEL));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01", "type", EQUALS, "ACQUIRER"));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.00", "interchange", CONTAINS, "VISA", ACQ_ATM34_VISA));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.01", "interface", CONTAINS, "VISA", ACQ_ATM34_VISA));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.02", "interchange", CONTAINS, "MASTER", ACQ_ATM34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.03", "interface", CONTAINS, "MASTER", ACQ_ATM34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.04", "interchange", CONTAINS, "MDS", ACQ_ATM34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.05", "interface", CONTAINS, "MDS", ACQ_ATM34_MASTERCARD));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.06", "interchange", CONTAINS, "", ACQ_ATM34_NACIONAL));
		daoRule.insertOrUpdate(new Rule(ATM34,SIMULATOR,"01.07", "interface", CONTAINS, "", ACQ_ATM34_NACIONAL));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertCEL34SimulatorRules() {                                                                                                                                           
		int CEL34 = EnviromentType.CEL34.getId();
		daoRule.insertOrUpdate(new Rule(CEL34,SIMULATOR,"00", "interchange", CONTAINS, "COBRANZA EN LINEA ISS", ISS_CEL34_CEL));
		daoRule.insertOrUpdate(new Rule(CEL34,SIMULATOR,"01", "interchange", CONTAINS, "", ISS_CEL34_CEL));
		return this;
	}                                                                                                                                                                                   
	public RulesCreator insertCOR34SimulatorRules() {                                                                                                                                           
		int COR34 = EnviromentType.COR34.getId();
		daoRule.insertOrUpdate(new Rule(COR34,SIMULATOR,"00", "interchange", CONTAINS, "CORR", ISS_COR34_BANAMEX));
		daoRule.insertOrUpdate(new Rule(COR34,SIMULATOR,"01", "interchange", CONTAINS, "CORR", ISS_COR34_BANAMEX));
		return this;
	}                                                                                                                                                                                   
	                                                                                                                                                                                    
}                                                                                                                                                                                       
                                                                                                                                                                                        