package com.fimet.core.net.listeners;

import com.fimet.core.ISO8583.parser.Message;

public interface IMessengerSimulateResponse extends IMessengerListener {
	/**
	 * Invoked after generate a response  
	 * @param response
	 * @return timeout if -1 not respond, if timeout > 0 sleep for timeout seconds then respond 
	 */
	Integer onMessengerSimulateResponse(Message response);
}
