package com.fimet.core.entity.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "Enviroment")
public class Enviroment {

	public static final int DISCONNECTED = 0;
	public static final int CONNECTING = 1;
	public static final int CONNECTED = 2;
	
	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private Integer idType;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private Boolean isLocal ;
	@DatabaseField(canBeNull = true)
	private String path;
	@DatabaseField(canBeNull = true)
	private Integer idDataBase;
	@DatabaseField(canBeNull = true)
	private Integer idFtp;
	@DatabaseField(canBeNull = true)
	private String address;
	private EnviromentType type;
	private DataBase dataBase;
	private Ftp ftp;
	private int statusConnection;
	
	public Enviroment() {
	}
	public Enviroment(Integer id, Integer idType, String name, Integer idDbConnection, String path, String address) {
		super();
		this.idType = idType;
		this.id = id;
		this.name = name;
		this.isLocal = true;
		this.path = path;
		this.idDataBase = idDbConnection;
		this.statusConnection = DISCONNECTED;
		this.address = address;
	}
	public Enviroment(Integer id, Integer idType, String name, Integer idDbConnection, String path, Integer idFtpConnection, String address) {
		super();
		this.id = id;
		this.idType = idType;
		this.name = name;
		this.path = path;
		this.idDataBase = idDbConnection;
		this.isLocal = idFtpConnection == null;
		this.idFtp = idFtpConnection;
		this.statusConnection = DISCONNECTED;
		this.address = address;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIdDataBase() {
		return idDataBase;
	}
	public void setIdDataBase(Integer idDataBase) {
		this.idDataBase = idDataBase;
	}
	public Integer getIdFtp() {
		return idFtp;
	}
	public void setIdFtp(Integer idFtp) {
		this.idFtp = idFtp;
	}
	public DataBase getDataBase() {
		return dataBase;
	}
	public void setDataBase(DataBase dataBase) {
		this.dataBase = dataBase;
	}
	public Ftp getFtp() {
		return ftp;
	}
	public void setFtp(Ftp ftp) {
		this.ftp = ftp;
	}
	public Boolean getIsLocal() {
		return isLocal;
	}
	public void setIsLocal(Boolean isLocal) {
		this.isLocal = isLocal;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getIdType() {
		return idType;
	}
	public void setIdType(Integer idType) {
		this.idType = idType;
	}
	public EnviromentType getType() {
		return type;
	}
	public void setType(EnviromentType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enviroment other = (Enviroment) obj;
		if (id != null && other.id != null) {
			return id == other.id;
		}
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	public String getStatus() {
		switch (statusConnection) {
		case CONNECTED:
			return "Connected";
		case CONNECTING:
			return "Connecting";
		default:
			return "Disconnected";
		}
	}
	public boolean isConnected() {
		return statusConnection == CONNECTED;
	}
	public boolean isDisconnected() {
		return statusConnection == DISCONNECTED;
	}
	public boolean isConnecting() {
		return statusConnection == CONNECTING;
	}
	public void setStatusConnection(int status) {
		this.statusConnection = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
