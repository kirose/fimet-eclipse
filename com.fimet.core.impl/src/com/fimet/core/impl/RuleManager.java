package com.fimet.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.history.History;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IRuleManager;
import com.fimet.core.ISocketManager;
import com.fimet.core.ISocketFieldMapper;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldMapper;
import com.fimet.core.entity.sqlite.IRuleValue;
import com.fimet.core.entity.sqlite.Rule;
import com.fimet.persistence.sqlite.dao.RuleDAO;
import com.fimet.persistence.sqlite.dao.FieldMapperDAO;

public class RuleManager implements IRuleManager {

	private IEnviromentManager enviromentManager = Manager.get(IEnviromentManager.class);
	@Override
	public List<FieldMapper> getFieldMappers() {
		return FieldMapperDAO.getInstance().findAll();
	}

	@Override
	public List<Rule> getRules(int idTypeEnviroment, int type) {
		return RuleDAO.getInstance().findByIdTypeAndField(idTypeEnviroment, type);
	}

	@Override
	public void save(List<Rule> rules) {
		List<Integer> reload = new ArrayList<>();
		Integer idEnviromentType = enviromentManager.getActive() != null ? enviromentManager.getActive().getIdType() : null;
		for (Rule rule : rules) {
			if (idEnviromentType == rule.getIdTypeEnviroment() && !reload.contains(rule.getIdField())) {
				reload.add(rule.getIdField());
			}
			RuleDAO.getInstance().insertOrUpdate(rule);
		}
		Manager.get(ISocketManager.class).refresh(reload);
	}

	@Override
	public void save(Rule rule) {
		RuleDAO.getInstance().insertOrUpdate(rule);
		if (enviromentManager.getActive() != null && enviromentManager.getActive().getIdType() == rule.getIdTypeEnviroment()) {
			Manager.get(ISocketManager.class).refresh(rule.getIdField());
		}
	}

	@Override
	public void delete(Rule rule) {
		RuleDAO.getInstance().delete(rule);
		if (enviromentManager.getActive() != null && enviromentManager.getActive().getIdType() == rule.getIdTypeEnviroment()) {
			Manager.get(ISocketManager.class).refresh(rule.getIdField());
		}
	}

	@Override
	public Integer getNextIdRule() {
		return RuleDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdRule() {
		return RuleDAO.getInstance().getPrevSequenceId();
	}
	@Override
	public void free(List<Integer> ids) {

	}

	@Override
	public void commit(History<Rule> history) {
		List<Integer> reload = new ArrayList<>();
		Integer idEnviromentType = enviromentManager.getActive() != null ? enviromentManager.getActive().getIdType() : null;
		for (Rule rule : history.getDeletes()) {
			if (idEnviromentType == rule.getIdTypeEnviroment() && !reload.contains(rule.getIdField())) {
				reload.add(rule.getIdField());
			}
			RuleDAO.getInstance().delete(rule);
		}
		for (Rule rule : history.getInserts()) {
			if (idEnviromentType == rule.getIdTypeEnviroment() && !reload.contains(rule.getIdField())) {
				reload.add(rule.getIdField());
			}
			RuleDAO.getInstance().insert(rule);
		}
		for (Rule rule : history.getUpdates()) {
			if (idEnviromentType == rule.getIdTypeEnviroment() && !reload.contains(rule.getIdField())) {
				reload.add(rule.getIdField());
			}
			RuleDAO.getInstance().update(rule);
		}
		Manager.get(ISocketManager.class).refresh(reload);
	}

	@Override
	public FieldMapper getFieldMapper(Integer idFile) {
		return FieldMapperDAO.getInstance().findById(idFile);
	}

	@Override
	public String getResult(FieldMapper field, Integer id) {
		try {
			ISocketFieldMapper binder = (ISocketFieldMapper)Manager.get(Class.forName(field.getMapperClass()));
			return binder.getBind(id)+"";
		} catch (ClassNotFoundException e) {
			Activator.getInstance().warning("Error Mapper Class",e);
			return null;
		}
	}

	@Override
	public List<IRuleValue> getValues(FieldMapper field) {
		try {
			ISocketFieldMapper binder = (ISocketFieldMapper)Manager.get(Class.forName(field.getMapperClass()));
			return binder.getValues();
		} catch (ClassNotFoundException e) {
			Activator.getInstance().warning("Error Mapper Class",e);
			return null;
		}
	}

	@Override
	public void free() {
		
	}

	@Override
	public void saveState() {
		
	}

	@Override
	public String getResult(Integer idField, Integer id) {
		return getResult(getFieldMapper(idField),id);
	}

}
