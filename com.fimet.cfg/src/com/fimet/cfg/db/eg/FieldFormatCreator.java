package com.fimet.cfg.db.eg;


import java.sql.SQLException;

import com.fimet.cfg.db.eg.fieldformat.DiscoverPOS;
import com.fimet.cfg.db.eg.fieldformat.MasterCard;
import com.fimet.cfg.db.eg.fieldformat.MasterCardATM;
import com.fimet.cfg.db.eg.fieldformat.MasterCardPOS;
import com.fimet.cfg.db.eg.fieldformat.Nacional;
import com.fimet.cfg.db.eg.fieldformat.NacionalATM;
import com.fimet.cfg.db.eg.fieldformat.NacionalCEL;
import com.fimet.cfg.db.eg.fieldformat.NacionalPOS;
import com.fimet.cfg.db.eg.fieldformat.Tpvs;
import com.fimet.cfg.db.eg.fieldformat.Visa;
import com.fimet.cfg.db.eg.fieldformat.VisaATM;
import com.fimet.cfg.db.eg.fieldformat.VisaPOS;
import com.fimet.cfg.db.eg.fieldformat.extract.*;
import com.fimet.cfg.enums.Parser;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;
import com.fimet.persistence.sqlite.dao.FieldFormatGroupDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import static com.fimet.cfg.enums.FieldFormatGroup.*;

public class FieldFormatCreator implements ICreator {
	FieldFormatGroupDAO daoGroup;
	FieldFormatDAO dao;
	ConnectionSource connection;
	public FieldFormatCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		daoGroup = new FieldFormatGroupDAO(connection);
		dao = new FieldFormatDAO(connection);
	}
	public FieldFormatCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, FieldFormatGroup.class);
		TableUtils.createTableIfNotExists(connection, com.fimet.core.entity.sqlite.FieldFormat.class);
		return this;
	}
	public FieldFormatCreator drop() throws SQLException {
		TableUtils.dropTable(connection, com.fimet.core.entity.sqlite.FieldFormat.class, true);
		TableUtils.dropTable(connection, FieldFormatGroup.class, true);
		return this;
	}
	public FieldFormatCreator delete(Parser parser) throws SQLException {
		if (parser != null) {
			FieldFormatDAO dao = FieldFormatDAO.getInstance();
			int i = dao.deleteByParser(parser.getId());
			System.out.println("Deleted "+i+" rows");
		}
		return this;
	}
	public FieldFormatCreator insertAll() {
		
		insertGroups();
		
		insertNacional();
		insertMasterCard();
		insertVisa();
		
		insertPOSNacional();
		insertPOSMasterCard();
		insertPOSVisa();
		insertPOSTpvs();
		insertPOSDiscover();
		
		insertATMMasterCard();
		insertATMVisa();
		insertATMNacional();
		
		insertCELNacional();

		insertPOSNacionalExtractBase();
		insertPOSNacionalExtractAdd1();
		insertPOSNacionalExtractAdd2();
		insertPOSNacionalExtractAdd3();
		insertPOSNacionalExtractAdd4();
		insertPOSNacionalExtractAdd5();
		insertPOSNacionalExtractAdd6();
		insertPOSNacionalExtractAdd7();
		insertPOSNacionalExtractAdd8();
		insertPOSNacionalExtractAdd9();
		insertPOSNacionalExtractAdd10();
		insertPOSNacionalExtractAdd11();
		insertPOSNacionalExtractAdd12();
		insertPOSNacionalExtractAdd13();
		insertPOSNacionalExtractAdd14();
		insertPOSNacionalExtractAdd15();
		insertPOSNacionalExtractAdd16();
		insertPOSNacionalExtractAdd17();
		insertPOSNacionalExtractAdd18();
		
		Long count = dao.count();
		if (count > 0) {
			return this;
		}
		return this;
	}
	private void insertGroups() {
		daoGroup.insertOrUpdate(new FieldFormatGroup(VISA.getId(),VISA.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(MASTERCARD.getId(),MASTERCARD.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(NACIONAL.getId(),NACIONAL.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(TPVS.getId(),TPVS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(NACIONAL_CEL.getId(),NACIONAL_CEL.getName(),NACIONAL.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(NACIONAL_COR.getId(),NACIONAL_COR.getName(),NACIONAL.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(NACIONAL_POS.getId(),NACIONAL_POS.getName(),NACIONAL.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(NACIONAL_ATM.getId(),NACIONAL_ATM.getName(),NACIONAL.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(VISA_ATM.getId(),VISA_ATM.getName(),VISA.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(VISA_POS.getId(),VISA_POS.getName(),VISA.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(MASTERCARD_ATM.getId(),MASTERCARD_ATM.getName(),MASTERCARD.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(MASTERCARD_POS.getId(),MASTERCARD_POS.getName(),MASTERCARD.getId()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(DISCOVER_POS.getId(),DISCOVER_POS.getName()));
		
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_BASE_POS.getId(),EXTRACT_BASE_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD001_POS.getId(),EXTRACT_ADD001_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD002_POS.getId(),EXTRACT_ADD002_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD003_POS.getId(),EXTRACT_ADD003_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD004_POS.getId(),EXTRACT_ADD004_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD005_POS.getId(),EXTRACT_ADD005_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD006_POS.getId(),EXTRACT_ADD006_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD007_POS.getId(),EXTRACT_ADD007_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD008_POS.getId(),EXTRACT_ADD008_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD009_POS.getId(),EXTRACT_ADD009_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD010_POS.getId(),EXTRACT_ADD010_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD011_POS.getId(),EXTRACT_ADD011_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD012_POS.getId(),EXTRACT_ADD012_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD013_POS.getId(),EXTRACT_ADD013_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD014_POS.getId(),EXTRACT_ADD014_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD015_POS.getId(),EXTRACT_ADD015_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD016_POS.getId(),EXTRACT_ADD016_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD017_POS.getId(),EXTRACT_ADD017_POS.getName()));
		daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD018_POS.getId(),EXTRACT_ADD018_POS.getName()));
		//daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD019_POS.getId(),EXTRACT_ADD019_POS.getName()));
		//daoGroup.insertOrUpdate(new FieldFormatGroup(EXTRACT_ADD020_POS.getId(),EXTRACT_ADD020_POS.getName()));
		
	}
	public FieldFormatCreator insertNacional() {
		new Nacional(dao).insert();
		return this;
	}
	public FieldFormatCreator insertMasterCard() {
		new MasterCard(dao).insert();
		return this;
	}
	public FieldFormatCreator insertVisa() {
		new Visa(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSNacional() {
		new NacionalPOS(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractBase() {
		new NacionalPOSExtractBase(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd1() {
		new NacionalPOSExtractAdd001(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd2() {
		new NacionalPOSExtractAdd002(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd3() {
	  new NacionalPOSExtractAdd003(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd4() {
	  new NacionalPOSExtractAdd004(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd5() {
	  new NacionalPOSExtractAdd005(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd6() {
	  new NacionalPOSExtractAdd006(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd7() {
	  new NacionalPOSExtractAdd007(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd8() {
	  new NacionalPOSExtractAdd008(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd9() {
	  new NacionalPOSExtractAdd009(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd10() {
	  new NacionalPOSExtractAdd010(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd11() {
	  new NacionalPOSExtractAdd011(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd12() {
	  new NacionalPOSExtractAdd012(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd13() {
	  new NacionalPOSExtractAdd013(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd14() {
	  new NacionalPOSExtractAdd014(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd15() {
	  new NacionalPOSExtractAdd015(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd16() {
	  new NacionalPOSExtractAdd016(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd17() {
	  new NacionalPOSExtractAdd017(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSNacionalExtractAdd18() {
	  new NacionalPOSExtractAdd018(dao).insert();
	  return this;
	}
	public FieldFormatCreator insertPOSVisa(){
		new VisaPOS(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSMasterCard(){
		new MasterCardPOS(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSDiscover(){
		new DiscoverPOS(dao).insert();
		return this;
	}
	public FieldFormatCreator insertPOSTpvs(){
		new Tpvs(dao).insert();
		return this;
	}
	public FieldFormatCreator insertATMNacional() {
		new NacionalATM(dao).insert();
		return this;
	}
	public FieldFormatCreator insertATMVisa(){
		new VisaATM(dao).insert();
		return this;
	}
	public FieldFormatCreator insertATMMasterCard(){
		new MasterCardATM(dao).insert();
		return this;
	}
	public FieldFormatCreator insertCELNacional() {
		new NacionalCEL(dao).insert();
		return this;
	}
}
