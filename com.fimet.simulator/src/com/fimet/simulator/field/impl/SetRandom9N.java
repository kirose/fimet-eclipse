package com.fimet.simulator.field.impl;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.simulator.field.AbstractRandom;
import com.fimet.simulator.field.ISimulatorField;

public class SetRandom9N extends AbstractRandom implements ISimulatorField {
	private static SetRandom9N instance;
	public static SetRandom9N getInstance() {
		if (instance == null) {
			instance = new SetRandom9N();
		}
		return instance;
	}
	@Override
	public void simulate(Message message, String idField) {
		message.setValue(idField, random(9));
	}
}
