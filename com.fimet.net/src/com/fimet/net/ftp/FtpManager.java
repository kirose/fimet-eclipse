package com.fimet.net.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import com.fimet.commons.console.Console;
import com.fimet.commons.exception.FtpException;
import com.fimet.core.entity.sqlite.Ftp;
import com.fimet.core.listener.IFtpConnected;
import com.fimet.core.listener.IFtpConnecting;
import com.fimet.core.listener.IFtpDeleted;
import com.fimet.core.listener.IFtpDisconnected;
import com.fimet.core.listener.IFtpInserted;
import com.fimet.core.listener.IFtpListener;
import com.fimet.core.listener.IFtpUpdated;
import com.fimet.core.net.IFtpManager;
import com.fimet.net.Activator;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.fimet.persistence.sqlite.dao.FtpDAO;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FtpManager implements IFtpManager {

	//Miliseconds
	private static long TIME_TO_ATTEMP_RECONNECT = 1000L * 45 ;// 45 Seconds
	public static final int TIMEOUT = 4000;

	private Date timeCheckConnection;
	private Connection connection;
	private List<Ftp> connections;
	private LinkedBlockingDeque<Listener> listeners = new LinkedBlockingDeque<>();

	public FtpManager() {
		connections = FtpDAO.getInstance().findAll();
	}
	public boolean isConnected() {
		return connection != null;
	}
	public boolean isConnected(Ftp db) {
		return db != null && connection != null && connection.entity.getName().equals(db.getName());
	}
	public void connect(Ftp entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		if (connection != null && entity.equals(connection.entity)) {
			return;
		}
		fireOnConnecting(entity);
		Connection connection = new Connection(entity);
        try { 
        	connection.connect();
	    } catch (Exception ex) {
			throw new FtpException("Cannot create connection, invalid parameters",ex);
	    }
		if (connection != null) {
			//Si fue exitosa la conexion desconectamos la conexion previa
			//Ftp temp = connected;
			disconnect();
			this.connection = connection;
			fireOnConnected(entity);
			timeCheckConnection = new Date(new Date().getTime() + TIME_TO_ATTEMP_RECONNECT);
			//fireOnChangeConnection(temp, connected);
			Console.getInstance().debug(FtpManager.class, "Connected to "+entity.getName()+" ("+entity.getAddress() + " " + entity.getPort()+")");
		}
		
	}
	public void disconnect() {
		if (connection != null) {
			Ftp entity = connection.entity;
			if (connection.disconnect()) {
				fireOnDisconnected(entity);
				connection = null;
			}
		}
	}
	public void download(String src, String tgt) {
		if (connection != null) {
			try {
				checkConnection();
				connection.channelSftp.get(src, tgt);
			} catch (SftpException e) {
				Console.getInstance().debug(FtpManager.class,"Downloading "+connection.entity.getName()+" ("+connection.entity.getAddress() + " " + connection.entity.getPort()+") src:"+src + ", tgt: "+tgt);
			}
		}
	}
	public void testConnection(Ftp entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		Connection connection = new Connection(entity);
		try {
			connection.connect();
		} catch (Exception ex) {
			throw new FtpException("Cannot create connection, invalid parameters");
		}
		try {
			connection.channelSftp.pwd();
		} catch (SftpException e) {
			throw new FtpException("Connection established, but cannot execute query.");
		}
		if (connection != null) {
			connection.disconnect();
		}
	}
	public Ftp getDbConnection() {
		return connection == null ? null : connection.entity;
	}
	private void checkConnection() {
		if (connection != null && timeCheckConnection != null && new Date().after(timeCheckConnection)) {
			timeCheckConnection = new Date(new Date().getTime() + TIME_TO_ATTEMP_RECONNECT);
			try {
				connection.channelSftp.pwd();
				if (!connection.session.isConnected()) {
					reconnect();
				}
			} catch (SftpException e) {
				reconnect();
			}
		}
	}
	private void reconnect() {
		Ftp entity = connection.entity;
		disconnect();
		connect(entity);
	}
	
	public String execute(String command) throws FtpException {
		if (connection != null) {
			try {
				checkConnection();
		        Channel channel = connection.session.openChannel("exec");
		        ((ChannelExec) channel).setCommand(command);
		        //channel.setInputStream(null);
		        InputStream output = channel.getInputStream();
		        channel.connect();
		        String result = read(output);
		        channel.disconnect();
		        return result;
			} catch (JSchException | IOException e) {
				throw new FtpException(e);
			}
		}
		return null;
	}
	public void execute(String command, File out) throws FtpException {
		if (connection != null) {
			InputStream in = null;
			Channel channel = null;
			try {
				checkConnection();
		        channel = connection.session.openChannel("exec");
		        ((ChannelExec) channel).setCommand(command);
		        in = channel.getInputStream();
		        channel.connect();
		        read(in, out);
		        
		        channel.disconnect();

			} catch (JSchException | IOException e) {
				throw new FtpException(e);
			} finally {
				if (in != null) {
					try {
			        	in.close();
			        } catch (IOException e) {}
				}
				if (channel != null) {
					try {
						channel.disconnect();
			        } catch (Exception e) {}
				}
			}
		}
	}
	private String read(InputStream in) throws IOException {
		StringBuffer s = new StringBuffer();
		byte[] bytes = new byte[512];
		int ln;
		while((ln = in.read(bytes)) > 0) {
			s.append(new String(bytes, 0, ln));
		}
		return s.toString();
	}
	private void read(InputStream in, File out) throws IOException {
		FileOutputStream writer = null;
		try {
			writer = new FileOutputStream(out);
			byte[] bytes = new byte[512];
			int ln;
			while((ln = in.read(bytes)) > 0) {
				writer.write(bytes,0,ln);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
	}
	public long executeAndReadLines(String command, File out) throws FtpException {
		long numberOfLines = 0;
		if (connection != null) {
			InputStream in = null;
			Channel channel = null;
			try {
				checkConnection();
		        channel = connection.session.openChannel("exec");
		        ((ChannelExec) channel).setCommand(command);
		        in = channel.getInputStream();
		        channel.connect();
		        numberOfLines = readLines(in, out);
		        
		        channel.disconnect();

			} catch (JSchException | IOException e) {
				//throw new FtpException(e);
				return 0L;
			} finally {
				if (in != null) {
					try {
			        	in.close();
			        } catch (IOException e) {}
				}
				if (channel != null) {
					try {
						channel.disconnect();
			        } catch (Exception e) {}
				}
			}
		}
		return numberOfLines;
	}
	private long readLines(InputStream in, File out) throws IOException {
		FileOutputStream writer = null;
		long numberOfLines = 0;
		try {
			writer = new FileOutputStream(out,true);
			byte[] bytes = new byte[512];
			int ln;
			while((ln = in.read(bytes)) > 0) {
				for (byte b : bytes) {
					if (b == (byte)10) {
						numberOfLines++;
					}
				}
				writer.write(bytes,0,ln);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
		return numberOfLines;
	}
	public long executeAndRead(String command, File out) throws FtpException {
		long offset = 0;
		if (connection != null) {
			InputStream in = null;
			Channel channel = null;
			try {
				checkConnection();
		        channel = connection.session.openChannel("exec");
		        ((ChannelExec) channel).setCommand(command);
		        in = channel.getInputStream();
		        channel.connect();
		        offset = readOffset(in, out);
		        
		        channel.disconnect();

			} catch (JSchException | IOException e) {
				//throw new FtpException(e);
				return 0L;
			} finally {
				if (in != null) {
					try {
			        	in.close();
			        } catch (IOException e) {}
				}
				if (channel != null) {
					try {
						channel.disconnect();
			        } catch (Exception e) {}
				}
			}
		}
		return offset;
	}
	private long readOffset(InputStream in, File out) throws IOException {
		FileOutputStream writer = null;
		long offset = 0;
		try {
			writer = new FileOutputStream(out,true);
			byte[] bytes = new byte[512];
			int ln;
			while((ln = in.read(bytes)) > 0) {
				offset += ln;
				writer.write(bytes,0,ln);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
		return offset;
	}
	@Override
	public void free() {}

	@Override
	public Ftp get(Integer id) {
		for (Ftp ftp : connections) {
			if (ftp.getId().equals(id)) {
				return ftp;
			}
		}
		return null;
	}

	@Override
	public void disconnect(Ftp entity) {
		if (entity != null && connection != null && connection.entity == entity) {
			disconnect();
		}
	}
	@Override
	public List<Ftp> getFtps() {
		List<Ftp> list = new ArrayList<>(connections.size());
		for (Ftp ftp : connections) {
			list.add(ftp);
		}
		return list;
	}
	@Override
	public Ftp getActive() {
		return connection != null ? connection.entity : null;
	}
	@Override
	public void saveState() {
		
	}
	@Override
	public boolean executeBoolean(String command) throws FtpException {
		boolean value = false;
		if (connection != null) {
			InputStream in = null;
			Channel channel = null;
			try {
				checkConnection();
		        channel = connection.session.openChannel("exec");
		        ((ChannelExec) channel).setCommand(command);
		        in = channel.getInputStream();
		        channel.connect();
		        value = "true".equalsIgnoreCase(read(in));
		        channel.disconnect();
			} catch (JSchException | IOException e) {
				throw new FtpException(e);
			} finally {
				if (in != null) {
					try {
			        	in.close();
			        } catch (IOException e) {}
				}
				if (channel != null) {
					try {
						channel.disconnect();
			        } catch (Exception e) {}
				}
			}
		}
		return value;
	}
	@Override
	public Ftp insert(Ftp ftp) {
		if (ftp.getId() == null) {
			ftp.setId(getNextIdFtp());
		}
		FtpDAO.getInstance().insert(ftp);
		connections.add(ftp);
		fireInserted(ftp);
		return ftp;
	}
	@Override
	public Ftp update(Ftp ftp) {
		FtpDAO.getInstance().update(ftp);
		fireUpdated(ftp);
		return ftp;
	}
	@Override
	public Ftp delete(Ftp ftp) {
		FtpDAO.getInstance().delete(ftp);
		connections.remove(ftp);
		fireDeleted(ftp);
		return ftp;
	}
	private class Listener {
		int type;
		IFtpListener listener;
		public Listener(int type, IFtpListener listener) {
			super();
			this.type = type;
			this.listener = listener;
		}
		public String toString() {
			return type+" "+listener;
		}
	}
	public void addFirstListener(int type, IFtpListener listener) {
		listeners.addFirst(new Listener(type, listener));
	}
	public void addListener(int type, IFtpListener listener) {
		listeners.add(new Listener(type, listener));
	}
	private Listener getListener(int type, IFtpListener listener) {
		for (Listener l : listeners) {
			if (l.type == type && l.listener == listener) {
				return l;
			}
		}
		return null;
	}
	public void removeListener(int type, IFtpListener listener) {
		Listener l = getListener(type, listener);
		if (l != null)
			listeners.remove(l);
	}
	private void fireInserted(Ftp e) {
		for (Listener l : listeners) {
			if (l.type == IFtpListener.ON_INSERT)
				try {((IFtpInserted)l.listener).onFtpInserted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on inserted event: "+ex.getMessage(), ex);}
		}
	}
	private void fireDeleted(Ftp e) {
		for (Listener l : listeners) {
			if (l.type == IFtpListener.ON_DELETE)
				try {((IFtpDeleted)l.listener).onFtpDeleted(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on deleted event: "+ex.getMessage(), ex);}
		}
	}
	private void fireUpdated(Ftp e) {
		for (Listener l : listeners) {
			if (l.type == IFtpListener.ON_UPDATE)
				try {((IFtpUpdated)l.listener).onFtpUpdated(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on updated event: "+ex.getMessage(), ex);}
		}
	}
	private void fireOnDisconnected(Ftp e) {
		e.setStatusConnection(Ftp.DISCONNECTED);
		for (Listener l : listeners) {
			if (l.type == IFtpListener.ON_DISCONNECTED) {
				try {((IFtpDisconnected)l.listener).onFtpDisconnected(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on disconnected event: "+ex.getMessage(), ex);}
			}
		}
	}
	private void fireOnConnected(Ftp e) {
		e.setStatusConnection(Ftp.CONNECTED);
		for (Listener l : listeners) {
			if (l.type == IFtpListener.ON_CONNECTED) {
				try {((IFtpConnected)l.listener).onFtpConnected(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on connected event: "+ex.getMessage(), ex);}
			}
		}
	}
	private void fireOnConnecting(Ftp e) {
		e.setStatusConnection(Ftp.CONNECTING);
		for (Listener l : listeners) {
			if (l.type == IFtpListener.ON_CONNECTING) {
				try {((IFtpConnecting)l.listener).onFtpConnecting(e);}
				catch (Exception ex) {Activator.getInstance().warning("Error on connecting event: "+ex.getMessage(), ex);}
			}
		}
	}
	@Override
	public Integer getNextIdFtp() {
		return FtpDAO.getInstance().getNextSequenceId();
	}
	@Override
	public Integer getPrevIdFtp() {
		return FtpDAO.getInstance().getPrevSequenceId();
	}
}