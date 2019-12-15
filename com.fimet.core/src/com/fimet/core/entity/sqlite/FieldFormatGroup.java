package com.fimet.core.entity.sqlite;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Field Format description for the fields
 * @author Marco Antonio
 *
 */
@DatabaseTable(tableName = "FieldFormatGroup")
public class FieldFormatGroup {

	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = true)
	private Integer idParent;
	@DatabaseField(canBeNull = false)
	private String name;// 
	private FieldFormatGroup parent;

	public FieldFormatGroup() {}
	public FieldFormatGroup(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public FieldFormatGroup(Integer id, String name, Integer idParent) {
		super();
		this.id = id;
		this.idParent = idParent;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdParent() {
		return idParent;
	}
	public void setIdParent(Integer idParent) {
		this.idParent = idParent;
	}
	public FieldFormatGroup getParent() {
		return parent;
	}
	public void setParent(FieldFormatGroup parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
