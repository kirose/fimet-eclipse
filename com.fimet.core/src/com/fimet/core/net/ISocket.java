package com.fimet.core.net;

import com.fimet.core.ISO8583.adapter.IAdapter;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.simulator.ISimulator;

public interface ISocket {
	
	String getName();
	String getAddress();
	Integer getPort();
	String getProcess();
	boolean isAcquirer();
	boolean isServer();
	boolean isActive();
	IAdapter getAdapter();
	IParser getParser();
	ISimulator getSimulator();

	void setName(String name);
	void setAddress(String address);
	void setPort(Integer port);
	void setProcess(String process);
	void setIsActive(boolean active);
	void setIsServer(boolean server);
	void setIsAcquirer(boolean acquirer);
	void setAdapter(IAdapter adapter);
	void setParser(IParser parser);
	void setSimulator(ISimulator simulator);
	
}
