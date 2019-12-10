package com.fimet.simulator;

import java.util.Map;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.simulator.ISimulator;
import com.fimet.persistence.sqlite.dao.SimulatorMessageDAO;
import com.fimet.simulator.field.SimulatorMessageAcquirer;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 */
public class SimulatorAcquirer implements ISimulator {

	public void free() {
		simulatorMessages.clear();
	}
	private final Integer idSimulator;
	private final String name;
	private Map<String, SimulatorMessageAcquirer> simulatorMessages = new java.util.HashMap<>();
	public SimulatorAcquirer(Integer idSimulator, String name) {
		this.idSimulator = idSimulator;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String toString() {
		return name;
	}
	public Integer getId() {
		return idSimulator;
	}
	private SimulatorMessageAcquirer getSimulatorMessage(String mti) {
		if (simulatorMessages.containsKey(mti)) {
			return simulatorMessages.get(mti);
		}
		com.fimet.core.entity.sqlite.SimulatorMessage sm = SimulatorMessageDAO.getInstance().findByMti(idSimulator, mti);
		if (sm == null) {
			simulatorMessages.put(mti, null);
			return null;
		}
		simulatorMessages.put(mti, new SimulatorMessageAcquirer(this, sm));
		return simulatorMessages.get(mti);
	} 
	public Message simulate(Message message) {
		if (message == null) {
			return null;
		}
		SimulatorMessageAcquirer simulator = this.getSimulatorMessage(message.getMti());
		if (simulator == null) {
			Activator.getInstance().error("Simulator Acquirer "+name+" not configured for message: "+message.getMti());
			return null;
		} else {
			return simulator.simulateResponse(message);
		}
	}
}
