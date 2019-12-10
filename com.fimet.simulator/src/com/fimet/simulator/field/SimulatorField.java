package com.fimet.simulator.field;

import com.fimet.core.ISO8583.parser.Message;

public abstract class SimulatorField {
	abstract public void simulate(Message message);
}
