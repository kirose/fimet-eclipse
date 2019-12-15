package com.fimet.core;

import java.util.Map;

import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.Message;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
public interface IValidator {
	
	/**
	 * This method will be invoked on the issuer request 
	 * @param request message
	 */
	public void onIssuerRequest(Message msg);
	/**
	 * This method will be invoked on the issuer response 
	 * @param request message
	 */
	public void onIssuerResponse(Message msg);
	/**
	 * This method will be invoked on the acquirer request 
	 * @param response message
	 */
	public void onAcquirerRequest(Message msg);
	/**
	 * This method will be invoked on the acquirer response 
	 * @param response message
	 */
	public void onAcquirerResponse(Message msg);
	/**
	 * This method will be executed when the transaction is extracted 
	 * @param extract
	 */
	public void onExtract(IMessage base, Map<Integer, IMessage> additionals);
}
