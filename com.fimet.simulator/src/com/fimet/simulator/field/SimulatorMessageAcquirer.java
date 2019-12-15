package com.fimet.simulator.field;


import com.fimet.commons.console.Console;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.simulator.ISimulator;

public class SimulatorMessageAcquirer extends SimulatorMessage {
	public SimulatorMessageAcquirer(ISimulator simulator, com.fimet.core.entity.sqlite.SimulatorMessage sm) {
		super(simulator, sm);
	}
	public Message simulateResponse(Message message) {
		if (message != null) {
			if (Console.isEnabledInfo()) {
				Console.getInstance().info(SimulatorMessageAcquirer.class, "Simulator acquirer "+simulator.toString() +", mti: " + message.getMti());
			}
			if (requiredFields != null && !existsRequiredFields(message, requiredFields)) {
				if (header != null) {
					message.setHeader(header);
				}
				message.setMti("9"+mti.substring(1));
				message.setValue("39", "99");
				return message;
			}
			if (simulatedFields != null) {
				for (SimulatorField f : simulatedFields) {
					f.simulate(message);
				}
			}
			if (header != null) {
				message.setHeader(header);
			}
			return message;
		}
		return null;
	}
}
