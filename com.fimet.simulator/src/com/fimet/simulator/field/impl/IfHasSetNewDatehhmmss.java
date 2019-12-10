package com.fimet.simulator.field.impl;

import java.util.Date;

import com.fimet.commons.utils.DateUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetNewDatehhmmss implements ISimulatorField {
	private static IfHasSetNewDatehhmmss instance;
	public static IfHasSetNewDatehhmmss getInstance() {
		if (instance == null) {
			instance = new IfHasSetNewDatehhmmss();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		if (message.hasField(idField))
			message.setValue(idField, DateUtils.hhmmss_FMT.format(new Date()));
	}
}
