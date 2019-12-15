package com.fimet.persistence.sqlite.dao;

import java.sql.SQLException;
import java.util.List;

import com.fimet.commons.exception.DBException;
import com.fimet.core.entity.sqlite.Socket;
import com.fimet.core.net.Machine;
import com.fimet.persistence.sqlite.PersistenceException;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class SocketDAO extends AbstractDAO<Socket,Integer> {
	private static SocketDAO instance;
	public static SocketDAO getInstance() {
		if (instance == null) {
			instance = new SocketDAO();
		}
		return instance;
	}
	public SocketDAO() {
		super();
	}
	public SocketDAO(ConnectionSource connection) {
		super(connection);
	}
	public Socket find(String name, String address, Integer port){
		try {
			QueryBuilder<Socket, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name).and().eq("address", address).and().eq("port", port));
			qb.limit(1L);
			return getDAO().queryForFirst(qb.prepare());
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public List<Socket> findByAddress(String address){
		if (address == null) {
			return null;
		} 
		try {
			QueryBuilder<Socket, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("address", address));
			return getDAO().query(qb.prepare());
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public Socket findByName(String name){
		try {
			QueryBuilder<Socket, Integer> qb = getDAO().queryBuilder();
			qb.setWhere(qb.where().eq("name", name));
			qb.limit(1L);
			return getDAO().queryForFirst(qb.prepare());
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public Integer getNextSequenceId() {
		if (sequence != null) {
			return sequence++;
		}
		try {
			Integer result = getDAO().queryRaw("select max(id)+1 from Socket", new RawRowMapper<Integer>() {
			       @Override
			       public Integer mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return resultColumns[0] != null ? Integer.parseInt(resultColumns[0]) : 1;
			       }
			}).getFirstResult();
			return sequence = result == null ? 0 : result;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public List<Machine> findMachines(){
		try {
			return getDAO().queryRaw("select distinct address from Socket", new RawRowMapper<Machine>() {
			       @Override
			       public Machine mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
			            return new Machine("Unknow",resultColumns[0]);
			       }
			}).getResults();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
	public int deleteByIdEnviroment(Integer idEnviroment) {
		try {
			DeleteBuilder<Socket, Integer> db = getDAO().deleteBuilder();
			db.setWhere(db.where().eq("enviroment", idEnviroment));
			return db.delete();
		} catch (SQLException e) {
			throw new DBException("Cannot delete Rules",e);
		}
	}
}
