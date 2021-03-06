package com.fimet.core.net.listeners;

import com.fimet.core.net.IMessenger;

public interface IMessengerReadIssuerRequest extends IMessengerListener {
	/**
	 * Invoked on Issuer request
	 * Authentic (response) -> Acquirer 
	 * Authentic (request) -> Issuer 
	 * @param conn
	 * @param msg
	 * @return boolean true: if must response false: not respond to socket
	 */
	void onMessengerReadIssuerRequest(IMessenger conn, byte[] message);
}
