package com.fimet.simulator.field.impl;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetRRN implements ISimulatorField {
	private static IfHasSetRRN instance;
	public static IfHasSetRRN getInstance() {
		if (instance == null) {
			instance = new IfHasSetRRN();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		if (!message.hasField(idField)) {
			return;
		}
		String rrn = message.getValue("37");
		if (rrn != null) {
			message.setValue(idField, rrn);
		}
	}
}
