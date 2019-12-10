package com.fimet.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fimet.core.ISimulatorManager;
import com.fimet.core.entity.sqlite.IRuleValue;
import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.fimet.core.simulator.ISimulator;
import com.fimet.persistence.sqlite.dao.SimulatorDAO;
import com.fimet.persistence.sqlite.dao.SimulatorMessageDAO;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 */
public class SimulatorManager implements ISimulatorManager {
	private static final SimulatorAcquirerNone NONE = new SimulatorAcquirerNone();
	private Map<Integer, ISimulator> issuers = new HashMap<>();
	private Map<Integer, ISimulator> acquirers = new HashMap<>();
	public SimulatorManager() {
	}
	public List<Simulator> getEntities(){
		return SimulatorDAO.getInstance().findAll();
	}
	public  ISimulator getSimulatorIssuer(Integer id) {
		if (issuers.containsKey(id)) {
			return issuers.get(id);
		}
		Simulator entity = getEntity(id);
		issuers.put(id, new SimulatorIssuer(entity.getId(), entity.getName()));
		return issuers.get(id);
	}
	public  ISimulator getSimulatorAcquirer(Integer id) {
		if (acquirers.containsKey(id)) {
			return acquirers.get(id);
		}
		Simulator entity = getEntity(id);
		acquirers.put(id, entity != null ? new SimulatorAcquirer(entity.getId(), entity.getName()) : NONE);
		return acquirers.get(id);
	}
	public  List<SimulatorMessage> getEntityMessages(int id) {
		return SimulatorMessageDAO.getInstance().findByIdSmulator(id);
	}

	public void freeSimulator(int id) {
		if (issuers.containsKey(id)) {
			issuers.get(id).free();
		}
		if (acquirers.containsKey(id)) {
			acquirers.get(id).free();
		}
	}
	@Override
	public void free() {
		issuers.clear();
		acquirers.clear();
	}
	public Simulator getEntity(String name) {
		return SimulatorDAO.getInstance().findByName(name);
	}
	@Override
	public void saveState() {}
	@Override
	public void freeSimulators(List<Integer> ids) {
		for (Integer id : ids) {
			freeSimulator(id);
		}
	}
	public  Simulator getEntity(Integer id) {
		return SimulatorDAO.getInstance().findById(id);
	}
	public Simulator saveSimulator(Simulator simulator) {
		SimulatorDAO.getInstance().insertOrUpdate(simulator);
		if (simulator.getId() == null) {
			Simulator last = SimulatorDAO.getInstance().findLast();
			if (last != null)
				simulator.setId(last.getId());
		}
		return simulator;
	}
	public Simulator deleteSimulator(Simulator simulator) {
		SimulatorDAO.getInstance().delete(simulator);
		SimulatorMessageDAO.getInstance().deleteByIdSimulator(simulator.getId());
		return simulator;
	}
	public SimulatorMessage saveSimulatorMessage(SimulatorMessage message) {
		SimulatorMessageDAO.getInstance().insertOrUpdate(message);
		if (message.getId() == null) {
			SimulatorMessage last = SimulatorMessageDAO.getInstance().findLast();
			if (last != null)
				message.setId(last.getId());
		}
		return message;
	}
	public SimulatorMessage deleteSimulatorMessage(SimulatorMessage message) {
		SimulatorMessageDAO.getInstance().delete(message);
		return message;
	}
	public Integer getNextIdSimulator() {
		return SimulatorDAO.getInstance().getNextSequenceId();
	}
	public Integer getPrevIdSimulator() {
		return SimulatorDAO.getInstance().getPrevSequenceId();
	}
	@Override
	public List<Simulator> getAcquirerEntities() {
		return SimulatorDAO.getInstance().findAllAcquirers();
	}
	@Override
	public List<Simulator> getIssuerEntities() {
		return SimulatorDAO.getInstance().findAllIssuers();
	}
	@Override
	public Object getBind(Integer id) {
		return id != null ? getSimulator(id) : null;
	}
	@Override
	public ISimulator getSimulator(Integer id) {
		if (issuers.containsKey(id)) {
			return issuers.get(id);
		}
		if (acquirers.containsKey(id)) {
			return acquirers.get(id);
		}
		Simulator entity = getEntity(id);
		if (entity.getType() == Simulator.ISSUER) {
			issuers.put(id, new SimulatorIssuer(entity.getId(), entity.getName()));
			return issuers.get(id);
		} else {
			acquirers.put(id, entity != null ? new SimulatorAcquirer(entity.getId(), entity.getName()) : NONE);
			return acquirers.get(id);
		}
	}
	@Override
	public ISimulator getSimulator(Simulator entity) {
		if (issuers.containsKey(entity.getId())) {
			return issuers.get(entity.getId());
		}
		if (acquirers.containsKey(entity.getId())) {
			return acquirers.get(entity.getId());
		}
		if (entity.getType() == Simulator.ISSUER) {
			issuers.put(entity.getId(), new SimulatorIssuer(entity.getId(), entity.getName()));
			return issuers.get(entity.getId());
		} else {
			acquirers.put(entity.getId(), entity != null ? new SimulatorAcquirer(entity.getId(), entity.getName()) : NONE);
			return acquirers.get(entity.getId());
		}
	}
	@Override
	public List<IRuleValue> getValues() {
		List<Simulator> simulators = SimulatorDAO.getInstance().findAll();
		if (simulators != null && !simulators.isEmpty()) {
			List<IRuleValue> values = new ArrayList<IRuleValue>(simulators.size());
			for (Simulator simulator : simulators) {
				values.add((IRuleValue)simulator);
			}
			return values;
		}
		return null;
	}
}
