package com.fimet.core.net.listeners;

import com.fimet.core.net.IMessenger;

public interface IMessengerReadAcquirerResponse extends IMessengerListener {
	/**
	 * Invoked on Acquirer response
	 * Authentic (response) -> Acquirer 
	 * Authentic (request) -> Issuer 
	 * @param conn
	 * @param msg
	 * @return boolean true: if must response false: not respond to socket
	 */
	void onMessangerReadAcquirerResponse(IMessenger conn, byte[] message);
}
