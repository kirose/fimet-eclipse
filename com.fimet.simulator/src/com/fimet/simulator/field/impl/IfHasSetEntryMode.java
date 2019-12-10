package com.fimet.simulator.field.impl;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetEntryMode implements ISimulatorField {
	private static IfHasSetEntryMode instance;
	public static IfHasSetEntryMode getInstance() {
		if (instance == null) {
			instance = new IfHasSetEntryMode();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		if (!message.hasField(idField)) {
			return;
		}
		String pem = message.getValue("22");
		if (pem != null && pem.length() >= 2) {
			message.setValue(idField, pem.substring(0,2));
		}
	}
}
