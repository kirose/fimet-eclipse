package com.fimet.core.entity.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
@DatabaseTable(tableName = "MessageIso")
public class MessageIso {

	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private Integer idTypeEnviroment;//POS, ATM, Corresponsales,...
	@DatabaseField(canBeNull = false)
	private Integer idParser;
	@DatabaseField(canBeNull = false)
	private Short type;
	@DatabaseField(canBeNull = false)
	private String mti;
	@DatabaseField(canBeNull = false)
	private String processingCode;
	@DatabaseField(canBeNull = false)
	private String merchant;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private String message; // Hex Message
	public MessageIso() {}
	public MessageIso(Integer idTypeEnviroment, Integer idParser, Short type, String merchant, String processingCode, String mti, String name, String message) {
		super();
		this.idTypeEnviroment = idTypeEnviroment;
		this.type = type;
		this.name = name;
		this.merchant = merchant;
		this.processingCode = processingCode;
		this.mti = mti;
		this.idParser = idParser;
		this.message = message;
	}
	public MessageIso(Integer id,
			Integer idTypeEnviroment,
			String mti,
			String processingCode,
			Integer idParser,
			Integer type,
			String name,
			String merchant,
			String message) {
		super();
		this.id = id;
		this.idTypeEnviroment = idTypeEnviroment;
		this.idParser = idParser;
		this.type = type.shortValue();
		this.mti = mti;
		this.processingCode = processingCode;
		this.merchant = merchant;
		this.name = name;
		this.message = message;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdTypeEnviroment() {
		return idTypeEnviroment;
	}
	public void setIdTypeEnviroment(Integer idTypeEnviroment) {
		this.idTypeEnviroment = idTypeEnviroment;
	}
	public Integer getIdParser() {
		return idParser;
	}
	public void setIdParser(Integer idParser) {
		this.idParser = idParser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getProcessingCode() {
		return processingCode;
	}
	public void setProcessingCode(String processingCode) {
		this.processingCode = processingCode;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idTypeEnviroment == null) ? 0 : idTypeEnviroment.hashCode());
		result = prime * result + ((idParser == null) ? 0 : idParser.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((mti == null) ? 0 : mti.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		MessageIso other = (MessageIso) obj;
		if (id != null && other.id != null && id.equals(other.id)) {
			return true;
		}
		if (idTypeEnviroment == null) {
			if (other.idTypeEnviroment != null)
				return false;
		} else if (!idTypeEnviroment.equals(other.idTypeEnviroment))
			return false;
		if (idParser == null) {
			if (other.idParser != null)
				return false;
		} else if (!idParser.equals(other.idParser))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (mti == null) {
			if (other.mti != null)
				return false;
		} else if (!mti.equals(other.mti))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
