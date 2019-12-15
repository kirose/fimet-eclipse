package com.fimet.core.entity.sqlite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fimet.commons.exception.FimetException;
import com.fimet.core.entity.sqlite.pojo.SimulatorField;
import com.fimet.core.entity.sqlite.type.ListSimulatorFieldType;
import com.fimet.core.entity.sqlite.type.ListStringType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "SimulatorMessage")
public class SimulatorMessage implements Cloneable {
	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private Integer idSimulator;
	@DatabaseField(canBeNull = true)
	private String header;
	@DatabaseField(canBeNull = false)
	private String mti;
	@DatabaseField(persisterClass=ListStringType.class, canBeNull = true)
	private List<String> requiredFields;
	@DatabaseField(persisterClass=ListStringType.class, canBeNull = true)
	private List<String> excludeFields;
	@DatabaseField(persisterClass=ListSimulatorFieldType.class, canBeNull = true)
	private List<SimulatorField> includeFields;
	public SimulatorMessage() {
		super();
	}
	public SimulatorMessage(Integer idSimulator, String header, String mti, String requiredFields, String excludeFields) {
		super();
		this.header = header;
		this.idSimulator = idSimulator;
		this.mti = mti;
		this.requiredFields = requiredFields == null || "".equals(requiredFields.trim()) ? null : Arrays.asList(requiredFields.split(","));
		this.excludeFields = excludeFields == null || "".equals(excludeFields.trim()) ? null : Arrays.asList(excludeFields.split(","));
		this.includeFields = new ArrayList<>();
	}
	public SimulatorMessage(Integer idSimulator, String mti,String requiredFields, String excludeFields) {
		this(idSimulator, null, mti, requiredFields, excludeFields);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public Integer getIdSimulator() {
		return idSimulator;
	}
	public void setIdSimulator(Integer idSimulator) {
		this.idSimulator = idSimulator;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public List<String> getRequiredFields() {
		return requiredFields;
	}
	public void setRequiredFields(List<String> requiredFields) {
		this.requiredFields = requiredFields;
	}
	public List<String> getExcludeFields() {
		return excludeFields;
	}
	public void setExcludeFields(List<String> excludeFields) {
		this.excludeFields = excludeFields;
	}
	public List<SimulatorField> getIncludeFields() {
		return includeFields;
	}
	public void setIncludeFields(List<SimulatorField> includeFields) {
		this.includeFields = includeFields;
	}
	public SimulatorMessage addIncludeFieldCls(String key, Class<?> clazz) {
		includeFields.add(new SimulatorField(key, SimulatorField.CUSTOM, clazz.getName()));
		return this;
	}
	public SimulatorMessage addIncludeFieldFix(String key, String value) {
		includeFields.add(new SimulatorField(key, SimulatorField.FIXED, value));
		return this;
	}
	public SimulatorMessage clone() throws CloneNotSupportedException {
		try {
			SimulatorMessage sm = (SimulatorMessage) super.clone();
			if (includeFields != null) {
				sm.includeFields = new ArrayList<>();
				for (SimulatorField f : includeFields) {
					sm.includeFields.add(f.clone());	
				}
			}
			return sm;
		} catch (CloneNotSupportedException e) {
			throw new FimetException(e);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((excludeFields == null) ? 0 : excludeFields.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((idSimulator == null) ? 0 : idSimulator.hashCode());
		result = prime * result + ((includeFields == null) ? 0 : includeFields.hashCode());
		result = prime * result + ((mti == null) ? 0 : mti.hashCode());
		result = prime * result + ((requiredFields == null) ? 0 : requiredFields.hashCode());
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
		SimulatorMessage other = (SimulatorMessage) obj;
		if (id != null && other.id != null && id == other.id) {
			return true;
		}
		if (excludeFields == null) {
			if (other.excludeFields != null)
				return false;
		} else if (!excludeFields.equals(other.excludeFields))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (idSimulator == null) {
			if (other.idSimulator != null)
				return false;
		} else if (!idSimulator.equals(other.idSimulator))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (includeFields == null) {
			if (other.includeFields != null)
				return false;
		} else if (!includeFields.equals(other.includeFields))
			return false;
		if (mti == null) {
			if (other.mti != null)
				return false;
		} else if (!mti.equals(other.mti))
			return false;
		if (requiredFields == null) {
			if (other.requiredFields != null)
				return false;
		} else if (!requiredFields.equals(other.requiredFields))
			return false;
		return true;
	}
}
