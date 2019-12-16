package com.fimet.core.net.listeners;

import com.fimet.core.net.IMessenger;

public interface IMessengerWriteIssuerResponse extends IMessengerListener {
	/**
	 * Invoked on Issuer response
	 * Acquirer (request) -> Authentic
	 * Issuer (response) -> Authentic 
	 * @param conn
	 * @param msg
	 */
	void onMessengerWriteIssuerResponse(IMessenger conn, byte[] message);
}
