package com.fimet.core.stress;

import com.fimet.commons.console.Console;
import com.fimet.core.Activator;
import com.fimet.core.net.ISocketConnection;

public class StressInjector extends Thread {
	private IStressReader reader;
	private ISocketConnection socket;
	
	public StressInjector(IStressReader reader, ISocketConnection socket) {
		super();
		this.reader = reader;
		this.socket = socket;
		Console.setLevel(Console.NONE);
	}
	@Override
	public void run() {
		reader.open();
		try {
			while(reader.hasNext()) {
				socket.writeMessage(reader.next());
			}
		} catch (Exception e) {
			Activator.getInstance().error("Stress Injector Error",e);
		}
		reader.close();
	}
}
