package com.fimet.core.net.listeners;

import com.fimet.core.ISO8583.parser.Message;

public interface IMessengerParseAcquirerResponse extends IMessengerListener {
	/**
	 * Invoked after parse the acquirer message
	 * @param response
	 */
	void onMessengerParseAcquirerResponse(Message response);
}
