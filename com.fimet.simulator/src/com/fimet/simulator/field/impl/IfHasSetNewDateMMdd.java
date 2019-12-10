package com.fimet.simulator.field.impl;

import java.util.Date;

import com.fimet.commons.utils.DateUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class IfHasSetNewDateMMdd implements ISimulatorField {
	private static IfHasSetNewDateMMdd instance;
	public static IfHasSetNewDateMMdd getInstance() {
		if (instance == null) {
			instance = new IfHasSetNewDateMMdd();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		if (message.hasField(idField))
			message.setValue(idField, DateUtils.MMdd_FMT.format(new Date()));
	}
}
