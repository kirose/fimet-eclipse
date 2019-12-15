package com.fimet.core.net;

import java.io.File;
import java.util.List;

import com.fimet.commons.exception.FtpException;
import com.fimet.core.IManager;
import com.fimet.core.entity.sqlite.Ftp;
import com.fimet.core.listener.IFtpListener;

public interface IFtpManager extends IManager {
	public Ftp insert(Ftp ftp);
	public Ftp update(Ftp ftp);
	public Ftp delete(Ftp ftp);
	public Ftp get(Integer id);
	public boolean isConnected();
	public boolean isConnected(Ftp db);
	public void connect(Ftp entity);
	public void disconnect();
	public void disconnect(Ftp entity);
	public void download(String src, String tgt);
	public void testConnection(Ftp entity);
	public void execute(String string, File file) throws FtpException;
	public String execute(String command) throws FtpException;
	public long executeAndReadLines(String string, File file) throws FtpException;
	public long executeAndRead(String string, File file) throws FtpException;
	public boolean executeBoolean(String string) throws FtpException;
	public List<Ftp> getFtps();
	public Ftp getActive();
	public Integer getNextIdFtp();
	public Integer getPrevIdFtp();
	void addFirstListener(int type, IFtpListener listener);
	void addListener(int type, IFtpListener listener);
	void removeListener(int type, IFtpListener listener);
}
