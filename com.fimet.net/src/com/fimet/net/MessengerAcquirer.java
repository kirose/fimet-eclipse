package com.fimet.net;

import com.fimet.commons.console.Console;
import com.fimet.core.Activator;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.net.ISocket;
import com.fimet.core.simulator.ISimulator;
import com.fimet.core.usecase.UseCaseExecutor;

/**
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
	
public class MessengerAcquirer extends Messenger {
	private ISimulator simulator;
	public MessengerAcquirer(ISocket sourceConnection) {
		super(sourceConnection);
		this.simulator = sourceConnection.getSimulator();
	}
	public void wirteMessage(Message msg) {
		try {
			msg = simulator.simulate(msg);
			byte[] iso = iSocket.getParser().formatMessage(msg);
			fireWriteAcquirerRequest(iso);
			socket.writeMessage(iso);
		} catch (Exception e) {
			Console.getInstance().error(UseCaseExecutor.class,"Format Exception: "+e.getMessage());
			Activator.getInstance().error("Format Exception: "+e.getMessage(), e);
		}
	}
	@Override
	public void onSocketRead(byte[] bytes) {
		try {
			fireReadAcquirerResponse(bytes);
		} catch (Exception e) {
			Activator.getInstance().warning("Error onReadAcquirerResponse",e);
		}
		try {
			Message response = (Message)iSocket.getParser().parseMessage(bytes);
			response.setAdapter(iSocket.getAdapter());
			fireParseAcquirerResponse(response);
		} catch (Exception e) {
			Activator.getInstance().warning("Error parsing incoming message",e);
		}
		fireOnComplete();
	}
}
