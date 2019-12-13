package com.fimet.core;

import java.util.List;

import com.fimet.commons.history.History;
import com.fimet.core.entity.sqlite.Socket;
import com.fimet.core.listener.ISocketListener;
import com.fimet.core.net.ISocket;

public interface ISocketManager extends IManager {
	
	List<Socket> findAllEntities();
	ISocket find(String name, String address, Integer port);
	List<ISocket> findByAddress(String address);
	List<ISocket> findAcquirers(String address);
	List<ISocket> findIssuers(String address);
	void refresh(List<Integer> idFields);
	void refresh(Integer idField);
	Integer getNextIdSocket();
	Integer getPrevIdSocket();
	void insert(ISocket socket);
	void update(ISocket socket);
	void delete(ISocket socket);
	void deleteByIdEnviroment(Integer id);
	
	public void addFirstListener(int type, ISocketListener listener);
	public void addListener(int type, ISocketListener listener);
	public void removeListener(int type, ISocketListener listener);
	void commit(History<Socket> history);
}
