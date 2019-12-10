package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public class MessageIsoDAO extends AbstractDAO<MessageIso,Integer> {

	private static MessageIsoDAO instance;
	public static MessageIsoDAO getInstance() {
		if (instance == null) {
			instance = new MessageIsoDAO();
		}
		return instance;
	}
	public MessageIsoDAO() {
		super();
	}
	public MessageIsoDAO(ConnectionSource connection) {
		super(connection);
	}
	public List<MessageIso> findByName(String name) {
		try {
			QueryBuilder<MessageIso, Integer> qb = getDAO().queryBuilder();
			if (Manager.get(IEnviromentManager.class).getActive() != null) {
				qb.setWhere(qb.where().eq("idTypeEnviroment", Manager.get(IEnviromentManager.class).getActive().getIdType()).and().like("name", name == null || "".equals(name.trim())? "%" : "%"+name+"%"));			
			} else {
				qb.setWhere(qb.where().like("name", name == null || "".equals(name.trim())? "%" : "%"+name+"%"));
			}
			qb.limit(10L);
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
	public List<MessageIso> findByMti(String mti) {
		try {
			QueryBuilder<MessageIso, Integer> qb = getDAO().queryBuilder();
			if (Manager.get(IEnviromentManager.class).getActive() != null) {
				qb.setWhere(qb.where().eq("idTypeEnviroment", Manager.get(IEnviromentManager.class).getActive().getIdType()).and().eq("mti", mti));			
			} else {
				qb.setWhere(qb.where().eq("mti", mti));
			}
			qb.limit(10L);
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
	public List<MessageIso> findByMtiAndLikeName(String mti, String name) {
		try {
			QueryBuilder<MessageIso, Integer> qb = getDAO().queryBuilder();
			if (Manager.get(IEnviromentManager.class).getActive() != null) {
				qb.setWhere(qb.where().eq("idTypeEnviroment", Manager.get(IEnviromentManager.class).getActive().getIdType()).and().eq("mti", mti).and().like("name", name == null || "".equals(name.trim())? "%" : "%"+name+"%"));			
			} else {
				qb.setWhere(qb.where().eq("mti", mti).and().like("name", name == null || "".equals(name.trim())? "%" : "%"+name+"%"));
			}
			qb.limit(10L);
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
	public List<MessageIso> findByParameters(MessageIsoParameters params){
		try {
			QueryBuilder<MessageIso, Integer> qb = getDAO().queryBuilder();
			Where<MessageIso, Integer> where = qb.where();
			where = where.isNotNull("id");
			if (params.getIdTypeEnviroment() != null)
				where = where.and().eq("idTypeEnviroment", params.getIdTypeEnviroment());
			if (params.getIdParser() != null)
				where = where.and().eq("idParser", params.getIdParser());
			if (params.getMerchant() != null)
				where = where.and().like("merchant", "%"+params.getMerchant().replace('*', '%')+"%");
			if (params.getName() != null)
				where = where.and().like("name", "%"+params.getName().replace('*', '%')+"%");
			if (params.getType() != null)
				where = where.and().eq("type", params.getType());
			if (params.getMti() != null)
				where = where.and().like("mti", params.getMti().replace('*', '%')+"%");
			if (params.getProcessingCode() != null)
				where = where.and().like("processingCode", params.getProcessingCode()+"%");
			
			qb.setWhere(where);
			qb.orderBy("id", params.getAsc() == null ? true : params.getAsc());
			qb.limit(100L);
			return qb.query();
		} catch (SQLException e) {
			return null;
		}
	}
}
