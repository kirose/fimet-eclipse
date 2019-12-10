package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.FieldMapper;
import com.fimet.core.entity.sqlite.IRuleValue;
import com.fimet.core.entity.sqlite.Rule;

public interface IRuleManager extends IManager {
	List<FieldMapper> getFieldMappers();
	List<Rule> getRules(int idTypeEnviroment, int idField);
	void save(List<Rule> rules);
	void save(Rule rule);
	void delete(Rule rule);
	Integer getNextIdRule();
	Integer getPrevIdRule();
	void free(List<Integer> ids);
	void commit(List<Rule> deletes, List<Rule> save);
	FieldMapper getFieldMapper(Integer idFile);
	//List<IRuleValue> getValues(FieldMapper field);
	String getResult(FieldMapper field, Integer id);
	String getResult(Integer idField, Integer id);
	List<IRuleValue> getValues(FieldMapper field);
}
