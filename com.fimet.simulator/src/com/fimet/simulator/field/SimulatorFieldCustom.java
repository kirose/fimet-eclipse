package com.fimet.simulator.field;

import com.fimet.core.ISO8583.parser.Message;

public class SimulatorFieldCustom extends SimulatorField {
	private String idField;
	private ISimulatorField sf;
	public SimulatorFieldCustom(String idField, ISimulatorField sf) {
		super();
		this.idField = idField;
		this.sf = sf;
	}
	@Override
	public void simulate(Message message) {
		sf.simulate(message, idField);
	}
}
