package com.fimet.net.ftp;


import com.fimet.commons.console.Console;
import com.fimet.core.entity.sqlite.Ftp;
import com.fimet.net.Activator;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class Connection {

	public static final int TIMEOUT = 4000;
	
	ChannelSftp channelSftp = null;
    Session session = null;
    Channel channel = null;
    Ftp entity;
	public Connection(Ftp entity) {
		super();
		this.entity = entity;
	}
	public void connect() {
        try {
	
	        JSch jsch = new JSch();
	        if (entity.getPort() != null && entity.getPort().length() > 0) {
	        	session = jsch.getSession(entity.getUser(), entity.getAddress(), Integer.parseInt(entity.getPort()));
        	} else {
        		session = jsch.getSession(entity.getUser(), entity.getAddress());
        	}
	        //
	        session.setPassword(entity.getPassword());
	        java.util.Properties config = new java.util.Properties();
	        config.put("StrictHostKeyChecking", "no");
	        //config.put("PreferredAuthentications","publickey,gssapi-with-mic,keyboard-interactive,password");
	        //config.put("PreferredAuthentications","publickey,keyboard-interactive,password");
	        config.put("PreferredAuthentications","publickey,password");
	        session.setConfig(config);
        	Console.getInstance().debug(Connection.class,"Connecting to "+entity.getAddress()+" with "+entity.getUser());
	        session.connect(TIMEOUT);
        	Console.getInstance().debug(Connection.class,"Connected to "+entity.getAddress()+" with "+entity.getUser());
	        channel = session.openChannel("sftp");
	        channel.connect();
	        channelSftp = (ChannelSftp) channel;
	        //channelSftp.cd(SFTPWORKINGDIR); // Change Directory on SFTP Server
	        
	    } catch (Exception ex) {
	    	Activator.getInstance().error("Connecting SSH", ex);
	        disconnect();
	        throw new RuntimeException(ex);
	    }
	}
	public boolean disconnect() {
		boolean disconnected = false;
		if (channelSftp != null) {
			try {
				channelSftp.exit();
				disconnected = true;
			} catch (Exception ex) {}
		}
        if (channelSftp != null)
        	try {
        		channelSftp.disconnect();
        		disconnected = true;
        	} catch (Exception ex) {}
        if (channel != null)
        	try { 
        		channel.disconnect();
        		disconnected = true;
        	} catch (Exception ex) {}
        if (session != null) {
        	try {
        		session.disconnect();
        		disconnected = true;
        	} catch (Exception ex) {}
        }
        Console.getInstance().debug(FtpManager.class,"Disconnected from "+entity.getName()+" ("+entity.getAddress() + " " + entity.getPort()+")");
        return disconnected;
	}
}
