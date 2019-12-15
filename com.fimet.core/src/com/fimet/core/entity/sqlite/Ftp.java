package com.fimet.core.entity.sqlite;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "Ftp")
public class Ftp {


	public static final int DISCONNECTED = 0;
	public static final int CONNECTING = 1;
	public static final int CONNECTED = 2;

	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private String address;
	@DatabaseField(canBeNull = false)
	private String port;
	@DatabaseField(canBeNull = false)
	private String user;
	@DatabaseField(canBeNull = false)
	private String password;
	@DatabaseField(canBeNull = false)
	private Boolean isValid;

	private int statusConnection;
	
	public Ftp() {}
	public Ftp(Integer id, String name, String address, String port, String user, String password) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.port = port;
		this.user = user;
		this.password = password;
		this.isValid = Boolean.FALSE;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Ftp other = (Ftp) obj;
		if (id != null && other.id != null) {
			return id == other.id;
		}
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
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
	public int getStatusConnection() {
		return statusConnection;
	}
	public void setStatusConnection(int statusConnection) {
		this.statusConnection = statusConnection;
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
	@Override
	public String toString() {
		return name;
	}
}
