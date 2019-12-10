package com.fimet.cfg.db;


import java.sql.SQLException;

import static com.fimet.cfg.enums.FieldFormatGroup.*;
import static com.fimet.cfg.enums.Parser.*;
import static com.fimet.commons.converter.Converter.*;
import static com.fimet.core.ISO8583.parser.IParser.*;

import com.fimet.core.entity.sqlite.Parser;
import com.fimet.parser.extract.impl.ExtractParser;
import com.fimet.parser.msg.iso.impl.*;
import com.fimet.persistence.sqlite.dao.ParserDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ParserCreator implements ICreator {
	ParserDAO dao;
	ConnectionSource connection;
	public ParserCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new ParserDAO(connection);
	}
	public ParserCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, Parser.class);
		return this;
	}
	public ParserCreator drop() throws SQLException {
		TableUtils.dropTable(connection, Parser.class, true);
		return this;
	}
	public ParserCreator insertAll() {
		
		dao.insertOrUpdate(new Parser(POS_INT_BR.getId(),         POS_INT_BR.getName(),         NACIONAL_POS.getId(),        NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+B I"));
		dao.insertOrUpdate(new Parser(POS_BANCOMER.getId(),       POS_BANCOMER.getName(),       NACIONAL_POS.getId(),        NacionalParser.class.getName(),  EBCDIC_TO_ASCII.getId(),ISO8583, "M3+B B"));
		dao.insertOrUpdate(new Parser(POS_TPV_BR.getId(),         POS_TPV_BR.getName(),         TPVS.getId(),                TpvParser.class.getName(),       ASCII_TO_HEX.getId(),   ISO8583, "M3+B T"));
		dao.insertOrUpdate(new Parser(ATM_BANCOMER.getId(),       ATM_BANCOMER.getName(),       NACIONAL_ATM.getId(),        NacionalParser.class.getName(),  EBCDIC_TO_ASCII.getId(),ISO8583, "M3+B A"));
		dao.insertOrUpdate(new Parser(CEL_ADQUIRA.getId(),        CEL_ADQUIRA.getName(),        NACIONAL_CEL.getId(),        NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+B Q"));// Emisor Adquira BBVA
		dao.insertOrUpdate(new Parser(CEL_BBVA.getId(),           CEL_BBVA.getName(),           NACIONAL_CEL.getId(),        NacionalParser.class.getName(),  EBCDIC_TO_ASCII.getId(),ISO8583, "M3+B C"));// Adquirente BBVA
		dao.insertOrUpdate(new Parser(POS_INT_BR_STRATUS.getId(), POS_INT_BR_STRATUS.getName(), NACIONAL_POS_STRATUS.getId(),NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+B S"));
		dao.insertOrUpdate(new Parser(POS_INT_BX.getId(),         POS_INT_BX.getName(),         NACIONAL_POS.getId(),        NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+C I"));
		dao.insertOrUpdate(new Parser(POS_TPV_BX.getId(),         POS_TPV_BX.getName(),         TPVS.getId(),                TpvParser.class.getName(),       ASCII_TO_HEX.getId(),   ISO8583, "M3+C T"));
		dao.insertOrUpdate(new Parser(POS_NACIONAL.getId(),       POS_NACIONAL.getName(),       NACIONAL_POS.getId(),        NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+N S"));
		dao.insertOrUpdate(new Parser(ATM_NACIONAL.getId(),       ATM_NACIONAL.getName(),       NACIONAL_ATM.getId(),        NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+N A"));
		dao.insertOrUpdate(new Parser(COR_NACIONAL.getId(),       COR_NACIONAL.getName(),       NACIONAL_COR.getId(),        NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+N C"));
		dao.insertOrUpdate(new Parser(POS_MASTERCARD.getId(),     POS_MASTERCARD.getName(),     MASTERCARD_POS.getId(),      MasterCardParser.class.getName(),NONE.getId(),           ISO8583, "M3+M S"));
		dao.insertOrUpdate(new Parser(ATM_MASTERCARD.getId(),     ATM_MASTERCARD.getName(),     MASTERCARD_ATM.getId(),      MasterCardParser.class.getName(),NONE.getId(),           ISO8583, "M3+M A"));
		dao.insertOrUpdate(new Parser(POS_VISA.getId(),           POS_VISA.getName(),           VISA_POS.getId(),            VisaParser.class.getName(),      ASCII_TO_HEX.getId(),   ISO8583, "M3+V S"));
		dao.insertOrUpdate(new Parser(ATM_VISA.getId(),           ATM_VISA.getName(),           VISA_ATM.getId(),            VisaParser.class.getName(),      ASCII_TO_HEX.getId(),   ISO8583, "M3+V A"));
		dao.insertOrUpdate(new Parser(POS_AMEX.getId(),           POS_AMEX.getName(),           NACIONAL_POS.getId(),        NacionalParser.class.getName(),  NONE.getId(),           ISO8583, "M3+A S"));
		dao.insertOrUpdate(new Parser(POS_DISCOVER.getId(),       POS_DISCOVER.getName(),       DISCOVER_POS.getId(),        DiscoverParser.class.getName(),  NONE.getId(),           ISO8583, "M3+D S"));
		// Extract
		dao.insertOrUpdate(new Parser(POS_EXTRACT_BASE.getId(),   POS_EXTRACT_BASE.getName(),   EXTRACT_BASE_POS.getId(),    ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D B"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD001.getId(), POS_EXTRACT_ADD001.getName(), EXTRACT_ADD001_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 1"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD002.getId(), POS_EXTRACT_ADD002.getName(), EXTRACT_ADD002_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 2"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD003.getId(), POS_EXTRACT_ADD003.getName(), EXTRACT_ADD003_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 3"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD004.getId(), POS_EXTRACT_ADD004.getName(), EXTRACT_ADD004_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 4"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD005.getId(), POS_EXTRACT_ADD005.getName(), EXTRACT_ADD005_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 5"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD006.getId(), POS_EXTRACT_ADD006.getName(), EXTRACT_ADD006_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 6"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD007.getId(), POS_EXTRACT_ADD007.getName(), EXTRACT_ADD007_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 7"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD008.getId(), POS_EXTRACT_ADD008.getName(), EXTRACT_ADD008_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 8"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD009.getId(), POS_EXTRACT_ADD009.getName(), EXTRACT_ADD009_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 9"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD010.getId(), POS_EXTRACT_ADD010.getName(), EXTRACT_ADD010_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+D 0"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD011.getId(), POS_EXTRACT_ADD011.getName(), EXTRACT_ADD011_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 1"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD012.getId(), POS_EXTRACT_ADD012.getName(), EXTRACT_ADD012_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 2"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD013.getId(), POS_EXTRACT_ADD013.getName(), EXTRACT_ADD013_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 3"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD014.getId(), POS_EXTRACT_ADD014.getName(), EXTRACT_ADD014_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 4"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD015.getId(), POS_EXTRACT_ADD015.getName(), EXTRACT_ADD015_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 5"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD016.getId(), POS_EXTRACT_ADD016.getName(), EXTRACT_ADD016_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 6"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD017.getId(), POS_EXTRACT_ADD017.getName(), EXTRACT_ADD017_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 7"));
		dao.insertOrUpdate(new Parser(POS_EXTRACT_ADD018.getId(), POS_EXTRACT_ADD018.getName(), EXTRACT_ADD018_POS.getId(),  ExtractParser.class.getName(),   NONE.getId(),           LAYOUT, "M3+S 8"));
		return this;
	}
}
