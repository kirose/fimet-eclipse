package com.fimet.core.entity.sqlite;

import com.fimet.core.ISO8583.adapter.IAdapter;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.entity.sqlite.type.AdapterType;
import com.fimet.core.entity.sqlite.type.ParserType;
import com.fimet.core.entity.sqlite.type.SimulatorType;
import com.fimet.core.net.ISocket;
import com.fimet.core.simulator.ISimulator;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.fimet.core.entity.sqlite.type.EnviromentType;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
@DatabaseTable(tableName = "Socket")
public class Socket implements IRuleValue, ISocket {
	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(persisterClass=EnviromentType.class,canBeNull = false)
	private Enviroment enviroment;
	@DatabaseField(canBeNull = false)
	private String name;
	@DatabaseField(canBeNull = false)
	private String address;
	@DatabaseField(canBeNull = false)
	private Integer port;
	@DatabaseField(canBeNull = false)
	private boolean isActive;	
	@DatabaseField(canBeNull = false)
	private boolean isServer;
	@DatabaseField(canBeNull = false)
	private boolean isAcquirer;
	@DatabaseField(canBeNull = false)
	private String process;
	@DatabaseField(persisterClass=ParserType.class,canBeNull = false)
	private IParser parser;
	@DatabaseField(persisterClass=SimulatorType.class,canBeNull = false)
	private ISimulator simulator;
	@DatabaseField(persisterClass=AdapterType.class,canBeNull = false)
	private IAdapter adapter;
	public Socket() {}
	public Integer getId() {
		return id;
	}
	public void setId(Integer idSocket) {
		this.id = idSocket;
	}
	public Enviroment getEnviroment() {
		return enviroment;
	}
	public void setEnviroment(Enviroment enviroment) {
		this.enviroment = enviroment;
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
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setIsActive(boolean active) {
		this.isActive = active;
	}
	public boolean isServer() {
		return isServer;
	}
	public void setIsServer(boolean server) {
		this.isServer = server;
	}
	public boolean isAcquirer() {
		return isAcquirer;
	}
	public void setIsAcquirer(boolean isAcquirer) {
		this.isAcquirer = isAcquirer;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public IParser getParser() {
		return parser;
	}
	public void setParser(IParser parser) {
		this.parser = parser;
	}
	public ISimulator getSimulator() {
		return simulator;
	}
	public void setSimulator(ISimulator simulator) {
		this.simulator = simulator;
	}
	public IAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(IAdapter adapter) {
		this.adapter = adapter;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
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
		Socket other = (Socket) obj;
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
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return name;
	}
}
