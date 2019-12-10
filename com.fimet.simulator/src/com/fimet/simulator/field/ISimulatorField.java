package com.fimet.simulator.field;

import com.fimet.core.ISO8583.parser.Message;

public interface ISimulatorField {
	void simulate(Message message, String idField);
}
