package com.fimet.simulator.field.impl;

import java.util.Date;

import com.fimet.commons.utils.DateUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class SetNewDateMMddhhmmss implements ISimulatorField {
	private static SetNewDateMMddhhmmss instance;
	public static SetNewDateMMddhhmmss getInstance() {
		if (instance == null) {
			instance = new SetNewDateMMddhhmmss();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		message.setValue(idField, DateUtils.MMddhhmmss_FMT.format(new Date()));
	}
}
