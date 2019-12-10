package com.fimet.simulator.field.impl;

import com.fimet.commons.utils.PanUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetCorrectPanLastDigit implements ISimulatorField {
	private static IfHasSetCorrectPanLastDigit instance;
	public static IfHasSetCorrectPanLastDigit getInstance() {
		if (instance == null) {
			instance = new IfHasSetCorrectPanLastDigit();
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
			String value = message.getValue(idField);
			if (value != null && value.length() >= 16) {
				char lastDigit = PanUtils.calculateLastDigit(pan);
				value = value.substring(0,15)+lastDigit+value.substring(16);
				message.setValue(idField, value);
			}
		}
	}
}
