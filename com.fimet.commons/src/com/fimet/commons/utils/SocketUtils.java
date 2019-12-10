package com.fimet.commons.utils;

import java.net.DatagramSocket;
import java.net.InetAddress;

public final class SocketUtils {
	private SocketUtils() {}
	public static String getHostnameLocalHost() {
		String hostname = null;
		DatagramSocket socket = null;
		try{
			socket = new DatagramSocket();
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			hostname = socket.getLocalAddress().getHostName();
		} catch (Exception e) {
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		return hostname;
	}
	public static String getAddressLocalHost() {
		String address = null;
		DatagramSocket socket = null;
		try{
			socket = new DatagramSocket();
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			address = socket.getLocalAddress().getHostAddress();
		} catch (Exception e) {
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
		return address;
	}
	public static void main(String[] args) {
		DatagramSocket socket = null;
		try{
			  socket = new DatagramSocket();
			  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			  String ip = socket.getLocalAddress().getHostAddress();
			  String host = socket.getLocalAddress().getHostName();
			  String hostname = socket.getLocalAddress().getCanonicalHostName();
			  if (host.matches("^[$A-Za-z0-9_\\-]+(\\.[A-Za-z0-9]+)+$")) {
				  
			  }
			  System.out.println(ip);
			  System.out.println(host);
			  System.out.println(hostname);
		} catch (Exception e) {
			
		} finally {
			if (socket != null) {
				socket.close();
			}
		}
	}
}
