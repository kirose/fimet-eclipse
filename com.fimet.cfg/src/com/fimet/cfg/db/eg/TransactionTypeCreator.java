package com.fimet.cfg.db.eg;


import static com.fimet.cfg.transaction.TransactionType.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fimet.core.entity.sqlite.TransactionType;
import com.fimet.persistence.sqlite.dao.TransactionTypeDAO;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class TransactionTypeCreator implements ICreator {
	private static final char MANDATORY = 'M';
	private static final char OPTIONAL = 'O';
	TransactionTypeDAO dao = TransactionTypeDAO.getInstance();
	ConnectionSource connection;
	public TransactionTypeCreator(ConnectionSource connection) {
		super();
		this.connection = connection;
		dao = new TransactionTypeDAO(connection);
	}
	public TransactionTypeCreator create() throws SQLException {
		TableUtils.createTableIfNotExists(connection, TransactionType.class);
		return this;
	}
	public TransactionTypeCreator drop() throws SQLException {
		TableUtils.dropTable(connection, TransactionType.class, true);
		return this;
	}
	public TransactionTypeCreator insertAll() {

		dao.insertOrUpdate(new TransactionType(0,null, FINANCIERA_0200.getId(), FINANCIERA_0200.getName(),"0200", null,
			new String[]{"mti","3.1","22.1","63.Q2"},
			null,
			new BitmapBuilder()
				.add( 3,MANDATORY)
				.add( 4,MANDATORY)
				.add( 7,MANDATORY)
				.add(11,MANDATORY)
				.add(12,MANDATORY)
				.add(13,MANDATORY)
				.add(17,MANDATORY)
				.add(18,MANDATORY)
				.add(22,MANDATORY)
				.add(24,OPTIONAL)
				.add(25,OPTIONAL)
				.add(32,MANDATORY)
				.add(35,MANDATORY)
				.add(37,MANDATORY)
				.add(41,MANDATORY)
				.add(43,MANDATORY)
				.add(48,MANDATORY)
				.add(49,MANDATORY)
				.add(54,OPTIONAL)
				.add(62,MANDATORY)
				.add(63,MANDATORY).getBytes(),
			new TokensBuilder()
				.add("Q1",MANDATORY)
				.add("Q2",MANDATORY)
				.add("04",OPTIONAL)
				.add("B2",OPTIONAL)
				.add("B3",OPTIONAL)
				.add("B4",OPTIONAL)
				.add("CE",OPTIONAL)
				.add("C6",OPTIONAL)
				.add("Q6",OPTIONAL)
				.add("R4",OPTIONAL).getMap()
		));
		
		dao.insertOrUpdate(new TransactionType(0,FINANCIERA_0200.getId(), F0200_COMPRA.getId(), F0200_COMPRA.getName(), "0200", "00",
			new String[]{"mti","3.1","22.1","63.Q2"},
			"(value('22.1') == '01' && value('63.Q2') in ('01','02','03','04','08','09','14','17','19','22','24')) || "+
			"(value('22.1') == '05' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '07' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '90' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '80' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '91' && value('63.Q2') in ('03','04'))",
			null,
			null
		));
		dao.insertOrUpdate(new TransactionType(0,FINANCIERA_0200.getId(), F0200_DEVOLUCION.getId(), F0200_DEVOLUCION.getName(), "0200", "20",
			new String[]{"mti","3.1","22.1","63.Q2"},
			"(value('22.1') == '01' && value('63.Q2') in ('01','02','03','04','08','09','14','17','19','22','24')) || "+
			"(value('22.1') == '05' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '07' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '90' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '80' && value('63.Q2') in ('03','04')) || "+
			"(value('22.1') == '91' && value('63.Q2') in ('03','04'))",
			null,
			null
		));
		dao.insertOrUpdate(new TransactionType(0,FINANCIERA_0200.getId(), F0200_CASHBACK.getId(), F0200_CASHBACK.getName(), "0200", "90",
			new String[]{"mti","3.1","22.1","63.Q2"},
			"value('22.1') == '01' || value('22.1') == '05'",
			null,
			null
		));
		
		
		dao.insertOrUpdate(new TransactionType(0,null, NOTIFICACION.getId(), NOTIFICACION.getName(),"0220", null,
			new String[]{"mti","3.1","22.1","63.Q2"},
			null,
			new BitmapBuilder()
				.add(03,MANDATORY)
				.add(04,MANDATORY)
				.add(07,MANDATORY)
				.add(11,MANDATORY)
				.add(12,MANDATORY)
				.add(13,MANDATORY)
				.add(17,MANDATORY)
				.add(18,MANDATORY)
				.add(22,MANDATORY)
				.add(24,OPTIONAL)
				.add(25,OPTIONAL)
				.add(32,MANDATORY)
				.add(35,MANDATORY)
				.add(37,MANDATORY)
				.add(38,MANDATORY)
				.add(39,MANDATORY)
				.add(41,MANDATORY)
				.add(43,MANDATORY)
				.add(48,MANDATORY)
				.add(49,MANDATORY)
				.add(54,OPTIONAL)
				.add(62,MANDATORY)
				.add(63,MANDATORY).getBytes(),
			new TokensBuilder()
				.add("04",OPTIONAL)
				.add("B2",OPTIONAL)
				.add("B3",OPTIONAL)
				.add("B4",OPTIONAL)
				.add("C0",OPTIONAL)
				.add("C4",MANDATORY)
				.add("C6",OPTIONAL)
				.add("CE",OPTIONAL)
				.add("Q1",MANDATORY)
				.add("Q2",MANDATORY)
				.add("Q6",OPTIONAL).getMap()
		));
		dao.insertOrUpdate(new TransactionType(0,NOTIFICACION.getId(), N_POSTPROPINA.getId(), N_POSTPROPINA.getName(), "0220", "07",
			new String[]{"mti","3.1","22.1","63.Q2"},
			null,
			null,
			null
		));
		dao.insertOrUpdate(new TransactionType(0,NOTIFICACION.getId(), N_OFFLINE.getId(), N_OFFLINE.getName(), "0220", "00",
			new String[]{"mti","3.1","22.1","63.Q2"},
			null,
			null,
			null
		));
		dao.insertOrUpdate(new TransactionType(0,NOTIFICACION.getId(), N_CHECKOUT.getId(), N_CHECKOUT.getName(), "0220", "06",
			new String[]{"mti","3.1","22.1","63.Q2"},
			null,
			null,
			null
		));
		
		
		
		dao.insertOrUpdate(new TransactionType(0,null, FINANCIERA_0100.getId(), FINANCIERA_0100.getName(),"0100", null,
			new String[]{"mti","3.1","22.1","63.Q2"},
			null,
			new BitmapBuilder()
				.add(03,MANDATORY)
				.add(04,MANDATORY)
				.add(07,MANDATORY)
				.add(11,MANDATORY)
				.add(12,MANDATORY)
				.add(13,MANDATORY)
				.add(17,MANDATORY)
				.add(18,MANDATORY)
				.add(22,MANDATORY)
				.add(23,OPTIONAL)
				.add(25,OPTIONAL)
				.add(32,MANDATORY)
				.add(35,MANDATORY)
				.add(37,MANDATORY)
				.add(41,MANDATORY)
				.add(43,MANDATORY)
				.add(48,MANDATORY)
				.add(49,MANDATORY)
				.add(62,MANDATORY)
				.add(63,MANDATORY).getBytes(),
			new TokensBuilder()
				.add("04",OPTIONAL)
				.add("B2",OPTIONAL)
				.add("B3",OPTIONAL)
				.add("B4",OPTIONAL)
				.add("C0",OPTIONAL)
				.add("C4",MANDATORY)
				.add("C6",OPTIONAL)
				.add("CE",OPTIONAL)
				.add("Q1",MANDATORY)
				.add("Q2",MANDATORY)
				.add("Q6",OPTIONAL).getMap()
		));
		dao.insertOrUpdate(new TransactionType(0,FINANCIERA_0100.getId(), F0100_CHEKIN.getId(), F0100_CHEKIN.getName(), "0200", "00",
			new String[]{"mti","3.1","22.1","63.Q2"},
			"value('22.1') in ('05','07','08')",
			null,
			null
		));
		dao.insertOrUpdate(new TransactionType(0,FINANCIERA_0100.getId(), F0100_REAUTORIZACION.getId(), F0100_REAUTORIZACION.getName(), "0200", "00",
			new String[]{"mti","3.1","22.1","63.Q2"},
			null,
			null,
			null
		));
		return this;
	}
	public TransactionTypeCreator showAll() {
		List<TransactionType> all = dao.findAll();
		for (TransactionType t : all) {
			System.out.println(t.getId()+","+t.getMti()+","+t.getTransactionType()+","+ t.getName()+","+t.getRule());
		}
		if (dao.count() > 0) {
			return this;
		}
		return this;
	}
	private class BitmapBuilder {
		byte[] bitmap = new byte[32];
		int index;
		private void checkSize() {
			if (index + 1 >= bitmap.length) {
				byte [] tmp = new byte[bitmap.length*2];
				System.arraycopy(bitmap, 0, tmp, 0, bitmap.length);
			}
		}
		public BitmapBuilder add(int id, char type) {
			checkSize();
			bitmap[index++] = (byte)  (type == MANDATORY ? (id-1) : -(id-1));
			return this;
		}
		public byte[] getBytes() {
			byte[] tmp = new byte[index];
			System.arraycopy(bitmap, 0, tmp, 0, index);
			return tmp;
		}
	}
	private class TokensBuilder {
		Map<String,Character> tokens = new HashMap<String,Character>();
		public TokensBuilder add(String token, char type) {
			tokens.put(token, type);
			return this;
		}
		public Map<String,Character> getMap() {
			return tokens;
		}
	}
}
