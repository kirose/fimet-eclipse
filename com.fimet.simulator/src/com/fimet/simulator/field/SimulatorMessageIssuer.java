package com.fimet.simulator.field;

import com.fimet.commons.console.Console;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.simulator.ISimulator;

public class SimulatorMessageIssuer extends SimulatorMessage {
	private String mtiResponse;
	public SimulatorMessageIssuer(ISimulator simulator, com.fimet.core.entity.sqlite.SimulatorMessage sm) {
		super(simulator, sm);
		mtiResponse = String.format("%04d", Integer.parseInt(sm.getMti())+10);
	}
	public Message simulateResponse(Message message) {
		if (message != null) {
			if (Console.isEnabledInfo()) {
				Console.getInstance().info(SimulatorMessageIssuer.class, "Simulator issuer "+simulator.toString() +", mti: " + message.getMti());
			}
			Message simulated = message.clone(excludeFields);
			if (requiredFields != null && !existsRequiredFields(message, requiredFields)) {
				if (header != null) {
					simulated.setHeader(header);
				}
				simulated.setMti("9"+mti.substring(1));
				simulated.setValue("39", "99");
				return simulated;
			}
			if (simulatedFields != null) {
				for (SimulatorField f : simulatedFields) {
					f.simulate(simulated);
				}
			}
			simulated.setMti(mtiResponse);
			if (header != null) {
				simulated.setHeader(header);
			}
			return simulated;
		}
		return null;
	}
}
