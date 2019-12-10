
package com.fimet.core.entity.sqlite;

import java.util.Map;

import com.fimet.core.entity.sqlite.type.ArrayByteType;
import com.fimet.core.entity.sqlite.type.ArrayStringType;
import com.fimet.core.entity.sqlite.type.MapStringCharacter;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "TransactionType")
public class TransactionType {

	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = true)
	private Integer idParent;
	@DatabaseField(canBeNull = false)
	private Integer idDialect;// Dialecto del ISO8583 (Variante)
	@DatabaseField(canBeNull = false)
	private String name;// El nombre del tipo de transaccion
	@DatabaseField(canBeNull = false)
	private String mti;// The mti
	@DatabaseField(canBeNull = true)
	private String transactionType;// The transaction type (DE3.1)
	@DatabaseField(persisterClass=ArrayByteType.class,canBeNull = true)
	private byte[] bitmap;// Los reglas de presencia de los campos
	@DatabaseField(persisterClass=ArrayStringType.class,canBeNull = false)
	private String[] primaryKey;// La llave primaria
	@DatabaseField(persisterClass=MapStringCharacter.class,canBeNull = true)
	private Map<String, Character> tokens;// las reglas de presencia de los token
	@DatabaseField(canBeNull = true)
	private String rule;// La regla de restriccion sobre valores

	public TransactionType() {
		super();
	}
	public TransactionType(Integer idDialect, Integer idParent, Integer id, String name, String mti, String transactionType, String[] primaryKey, String rule,  byte[] bitmap, Map<String, Character> tokens) {
		super();
		this.id = id;
		this.idParent = idParent;
		this.idDialect = idDialect;
		this.mti = mti;
		this.transactionType = transactionType;
		this.primaryKey = primaryKey;
		this.bitmap = bitmap;
		this.name = name;
		this.tokens = tokens;
		this.rule = rule;
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
	public Integer getIdDialect() {
		return idDialect;
	}
	public void setIdDialect(Integer idDialect) {
		this.idDialect = idDialect;
	}
	public byte[] getBitmap() {
		return bitmap;
	}
	public void setBitmap(byte[] bitmap) {
		this.bitmap = bitmap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Character> getTokens() {
		return tokens;
	}
	public void setTokens(Map<String, Character> tokens) {
		this.tokens = tokens;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String[] getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String[] primaryKey) {
		this.primaryKey = primaryKey;
	}
}
