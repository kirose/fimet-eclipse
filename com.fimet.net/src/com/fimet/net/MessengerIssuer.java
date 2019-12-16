package com.fimet.net;

import java.util.concurrent.TimeUnit;

import com.fimet.commons.console.Console;
import com.fimet.core.Activator;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCaseExecutor;
import com.fimet.simulator.SimulatorIssuer;

/**
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
	
public class MessengerIssuer extends Messenger {
	private SimulatorIssuer simulator;
	public MessengerIssuer(ISocket iap) {
		super(iap);
		simulator = (SimulatorIssuer)iap.getSimulator();
		if (simulator == null) {
			throw new NullPointerException("Simulator is null for "+iap);
		}
		reconnect = true;
	}
	public void wirteMessage(Message msg) {
		try {
			byte[] iso = iSocket.getParser().formatMessage(msg);
			socket.writeMessage(iso);
		} catch (Exception e) {
			Console.getInstance().error(UseCaseExecutor.class,"Format Exception: "+e.getMessage());
			Activator.getInstance().error("Format Exception: "+e.getMessage(), e);
		}
	}
	@Override
	public void onSocketRead(byte[] bytes) {
		try {
			fireReadIssuerRequest(bytes);
			Message request = (Message)iSocket.getParser().parseMessage(bytes);
			request.setAdapter(iSocket.getAdapter());
			fireParseIssuerRequest(request);
			Message response = simulator.simulate(request);
			if (response == null) {
				return;
			}
			//Console.getInstance().debug(NetworkConnection.class.getName(), "useCaseMonitor: "+useCaseMonitor);
			synchronized (this) {
				Integer timeout = fireOnSimulateIssuerResponse(response);
				if (timeout == null || timeout == 0) {
					byte[] iso = response.getParser().formatMessage(response);
					getSocket().writeMessage(iso);
					fireWriteIssuerResponse(iso);
				} else if (timeout != null && timeout > 0) {
            		try {
            			Console.getInstance().debug(Messenger.class,"Delay ("+timeout+" s)");
	         			TimeUnit.SECONDS.sleep(timeout);
	         			Console.getInstance().debug(Messenger.class,"Thread wakeup");
        	 		} catch (InterruptedException e) {}
					byte[] iso = response.getParser().formatMessage(response);
					getSocket().writeMessage(iso);
					fireWriteIssuerResponse(iso);
				} else {
					Console.getInstance().debug(Messenger.class,"Timeout not respond");
				}
			}
		} catch (Exception e) { 
			Activator.getInstance().warning("Error On Socket Read incoming message",e);
		}
	}
}
