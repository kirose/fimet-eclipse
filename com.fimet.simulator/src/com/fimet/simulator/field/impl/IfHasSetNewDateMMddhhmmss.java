package com.fimet.simulator.field.impl;

import java.util.Date;

import com.fimet.commons.utils.DateUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetNewDateMMddhhmmss implements ISimulatorField {
	private static IfHasSetNewDateMMddhhmmss instance;
	public static IfHasSetNewDateMMddhhmmss getInstance() {
		if (instance == null) {
			instance = new IfHasSetNewDateMMddhhmmss();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		if (message.hasField(idField))
			message.setValue(idField, DateUtils.MMddhhmmss_FMT.format(new Date()));
	}
}
