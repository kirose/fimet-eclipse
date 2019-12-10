package com.fimet.cfg;

import java.util.ArrayList;
import java.util.List;

import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.persistence.sqlite.dao.FieldFormatDAO;

public class FieldFormatBuilder {
	private FieldFormat fieldFormat;
	private List<FieldFormatBuilder> childs;
	public FieldFormatBuilder(FieldFormat fieldFormat) {
		super();
		if (fieldFormat == null) {
			throw new NullPointerException();
		}
		this.fieldFormat = fieldFormat;
	}
	public FieldFormatBuilder add(FieldFormat fieldFormat) {
		return add(new FieldFormatBuilder(fieldFormat));
	}
	public FieldFormatBuilder add(FieldFormatBuilder fieldFormatBuilder) {
		getChildsBuilders().add(fieldFormatBuilder);
		if (fieldFormat.getChilds() == null) {
			fieldFormat.setChilds(fieldFormatBuilder.fieldFormat.getKey());
		} else {
			fieldFormat.setChilds(fieldFormat.getChilds() + "," + fieldFormatBuilder.fieldFormat.getKey());
		}
		return this;
	}
	public FieldFormatBuilder insert(FieldFormatDAO dao) {
		buildIds();
		dao.insert(fieldFormat);
		if (childs != null) {
			for (FieldFormatBuilder ffb : childs) {
				ffb._insert(dao);
			}
		}
		return this;
	}
	private FieldFormatBuilder _insert(FieldFormatDAO dao) {
		dao.insert(fieldFormat);
		if (childs != null) {
			for (FieldFormatBuilder ffb : childs) {
				ffb._insert(dao);
			}
		}
		return this;
	}
	public FieldFormatBuilder insertOrUpdate(FieldFormatDAO dao) {
		buildIds();
		dao.insertOrUpdate(fieldFormat);
		if (childs != null) {
			for (FieldFormatBuilder ffb : childs) {
				ffb._insertOrUpdate(dao);
			}
		}
		return this;
	}
	private FieldFormatBuilder _insertOrUpdate(FieldFormatDAO dao) {
		dao.insertOrUpdate(fieldFormat);
		if (childs != null) {
			for (FieldFormatBuilder ffb : childs) {
				ffb._insertOrUpdate(dao);
			}
		}
		return this;
	}
	private void buildIds() {
		if (childs != null && !childs.isEmpty()) {
			for (FieldFormatBuilder ffb : childs) {
				ffb.fieldFormat.setIdOrder(fieldFormat.getIdOrder() +"."+ ffb.fieldFormat.getIdOrder());
				ffb.fieldFormat.setIdField(fieldFormat.getIdField() +"."+ ffb.fieldFormat.getKey());
				ffb.fieldFormat.setIdFieldParent(fieldFormat.getIdField());
				ffb.buildIds();
			}
		}
	}
	private List<FieldFormatBuilder> getChildsBuilders(){
		if (childs == null) {
			childs = new ArrayList<>(); 
		}
		return childs;
	}
}
