package com.fimet.core.entity.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "FieldMapper")
public class FieldMapper {
	
	
	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private String fieldClass;
	@DatabaseField(canBeNull = false)
	private String field;
	@DatabaseField(canBeNull = false)
	private String mapperClass;
	public FieldMapper() {
	}
	public FieldMapper(int id, String field, String fieldType, String mapperClass) {
		super();
		this.id = id;
		this.field = field;
		this.fieldClass = fieldType;
		this.mapperClass = mapperClass;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFieldClass() {
		return fieldClass;
	}
	public void setFieldClass(String fieldClass) {
		this.fieldClass = fieldClass;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMapperClass() {
		return mapperClass;
	}
	public void setMapperClass(String mapperClass) {
		this.mapperClass = mapperClass;
	}
	@Override
	public String toString() {
		return field;
	}
}
