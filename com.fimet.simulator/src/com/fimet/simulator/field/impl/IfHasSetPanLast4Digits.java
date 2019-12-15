package com.fimet.simulator.field.impl;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetPanLast4Digits implements ISimulatorField {
	private static IfHasSetPanLast4Digits instance;
	public static IfHasSetPanLast4Digits getInstance() {
		if (instance == null) {
			instance = new IfHasSetPanLast4Digits();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		if (!message.hasField(idField)) {
			return;
		}
		String pan = message.getPan();
		if (pan != null && pan.length() >= 16) {
			message.setValue(idField, pan.substring(12, 16));
		}
	}
}
