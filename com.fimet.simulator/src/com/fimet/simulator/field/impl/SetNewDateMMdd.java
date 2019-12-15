package com.fimet.simulator.field.impl;

import java.util.Date;

import com.fimet.commons.utils.DateUtils;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.ISimulatorField;

public class SetNewDateMMdd implements ISimulatorField {
	private static SetNewDateMMdd instance;
	public static SetNewDateMMdd getInstance() {
		if (instance == null) {
			instance = new SetNewDateMMdd();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		message.setValue(idField, DateUtils.MMdd_FMT.format(new Date()));
	}
}
