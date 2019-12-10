package com.fimet.simulator;

import java.util.Map;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.simulator.ISimulator;
import com.fimet.simulator.field.SimulatorMessageAcquirer;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 */
public class SimulatorAcquirerNone implements ISimulator {
	public void free() {
		simulatorMessages.clear();
	}
	private final String name;
	private Map<String, SimulatorMessageAcquirer> simulatorMessages = new java.util.HashMap<>();
	public SimulatorAcquirerNone() {
		this.name = "None";
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return name;
	}
	public Message simulate(Message message) {
		return message;
	}
	@Override
	public Integer getId() {
		return null;
	}
}
