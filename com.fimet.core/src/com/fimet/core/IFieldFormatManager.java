package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.entity.sqlite.FieldFormatGroup;

public interface IFieldFormatManager extends IManager {
	FieldFormatGroup getGroup(Integer idGroup);
	List<FieldFormatGroup> getRootGroups();
	List<FieldFormatGroup> getGroups();
	List<FieldFormat> getFieldsFormat(Integer idGroup);
	List<FieldFormat> getFieldsFormatOnlyGroup(Integer idGroup);
	List<FieldFormatGroup> getSubGroups(Integer idGroup);
	FieldFormatGroup saveGroup(FieldFormatGroup group);
	FieldFormatGroup deleteGroup(FieldFormatGroup group);
	FieldFormat saveField(FieldFormat field);
	FieldFormat deleteField(FieldFormat field);
	void free(List<Integer> groups);
	Integer getNextIdGroup();
	Integer getPrevIdGroup();
}
