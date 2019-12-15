package com.fimet.simulator.field.impl;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetAmount implements ISimulatorField {
	private static IfHasSetAmount instance;
	public static IfHasSetAmount getInstance() {
		if (instance == null) {
			instance = new IfHasSetAmount();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		if (!message.hasField(idField)) {
			return;
		}
		String amount = message.getValue("4");
		if (amount != null) {
			message.setValue(idField, amount);
		}
	}
}
