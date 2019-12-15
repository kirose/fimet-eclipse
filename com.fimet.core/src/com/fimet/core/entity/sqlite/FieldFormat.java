package com.fimet.core.entity.sqlite;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Field Format description for the fields
 * @author Marco Antonio
 *
 */
@DatabaseTable(tableName = "FieldFormat")
public class FieldFormat {

	@DatabaseField(id = true)
	private Integer idFieldFormat;
	@DatabaseField(canBeNull = false)
	private Integer idGroup;
	@DatabaseField(canBeNull = false)
	private String idOrder;// 063.16, se utiliza para visualizar en Preferences->Parsers y para generar address de un field
	@DatabaseField(canBeNull = false)
	private String idField;// 63.EZ
	@DatabaseField(canBeNull = true)
	private String idFieldParent;// 063
	@DatabaseField(canBeNull = false)
	private String key;// EZ
	@DatabaseField(canBeNull = true)
	private String childs;//1,2,3,4,5,6,7,8,9,10,11,12
	@DatabaseField(canBeNull = false)
	private Integer idConverterValue;// NONE, HEX_TO_ASCII, ASCII_TO_HEX, EBCDIC_TO_ASCII
	@DatabaseField(canBeNull = true)
	private Integer idConverterLength;// HEX_TO_ASCII, ASCII_TO_HEX, EBCDIC_TO_ASCII
	@DatabaseField(canBeNull = true)
	private Integer idParserLength;// Hex, HexDouble, Decimal
	@DatabaseField(canBeNull = false)
	private String type;// [A-Z]+
	@DatabaseField(canBeNull = false)
	private Integer length;// 12, 1 numero de bytes que debe de tomar del padre
	@DatabaseField(canBeNull = true)
	private Integer maxLength;// Para campos variable
	@DatabaseField(canBeNull = false)
	private String name;// Token EZ
	@DatabaseField(canBeNull = false)
	private String classParser;	// com.fimet.fmt.impl.FieldParser

	public FieldFormat() {}
	public FieldFormat(Integer idGroup, String idOrder, String idField, Integer idConverterValue, Integer idConverterLength, Integer idParserLength, String type, Integer length, Integer maxLength, String name, Class<?> classParser) {
		super();
		this.idGroup = idGroup;
		this.idOrder = idOrder;
		this.idField = idField;
		this.key = idField;
		this.idConverterValue = idConverterValue;
		this.idConverterLength = idConverterLength;
		this.idParserLength = idParserLength;
		this.type = type;
		this.length = length;
		this.maxLength = maxLength;
		this.name = name;
		this.classParser = classParser.getName();
	}
	public Integer getIdFieldFormat() {
		return idFieldFormat;
	}
	public void setIdFieldFormat(Integer idFieldFormat) {
		this.idFieldFormat = idFieldFormat;
	}
	public String getIdField() {
		return idField;
	}
	public void setIdField(String idField) {
		this.idField = idField;
	}
	public Integer getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getIdFieldParent() {
		return idFieldParent;
	}
	public void setIdFieldParent(String idFieldParent) {
		this.idFieldParent = idFieldParent;
	}
	public String getChilds() {
		return childs;
	}
	public void setChilds(String childs) {
		this.childs = childs;
	}
	public Integer getIdConverterValue() {
		return idConverterValue;
	}
	public void setIdConverterValue(Integer idConverterValue) {
		this.idConverterValue = idConverterValue;
	}
	public Integer getIdConverterLength() {
		return idConverterLength;
	}
	public void setIdConverterLength(Integer idConverterLength) {
		this.idConverterLength = idConverterLength;
	}
	public Integer getIdParserLength() {
		return idParserLength;
	}
	public void setIdParserLength(Integer idParserLength) {
		this.idParserLength = idParserLength;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassParser() {
		return classParser;
	}
	public void setClassParser(String classParser) {
		this.classParser = classParser;
	}
	public String getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}
	@Override
	public String toString() {
		return "[idFieldFormat="+idFieldFormat +",idOrder="+idOrder+", idField=" + idField+ ", key=" + key + ", idFieldParent=" + idFieldParent + ", childs="
				+ childs + ", type=" + type + ", length=" + length
				+ ", name=" + name + ", classParser=" + classParser + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idOrder == null) ? 0 : idOrder.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FieldFormat other = (FieldFormat) obj;
		/*if (idFieldFormat != null && other.idFieldFormat != null && idFieldFormat == other.idFieldFormat) {
			return true;
		}*/
		if (idOrder == null) {
			if (other.idOrder != null)
				return false;
		} else if (!idOrder.equals(other.idOrder))
			return false;
		return true;
	}
}
