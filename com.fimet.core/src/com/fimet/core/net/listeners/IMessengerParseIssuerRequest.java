package com.fimet.core.net.listeners;

import com.fimet.core.ISO8583.parser.Message;

public interface IMessengerParseIssuerRequest extends IMessengerListener {
	/**
	 * Invoked after the message is parsed
	 * @param request
	 */
	void onMessengerParseIssuerRequest(Message request);
}
