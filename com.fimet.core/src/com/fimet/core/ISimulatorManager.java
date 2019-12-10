package com.fimet.core;

import java.util.List;

import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.fimet.core.simulator.ISimulator;

public interface ISimulatorManager extends IManager, ISocketFieldMapper {
	public ISimulator getSimulator(Integer id);
	public ISimulator getSimulatorIssuer(Integer id);
	public ISimulator getSimulatorAcquirer(Integer id);
	public List<Simulator> getEntities();
	public List<Simulator> getAcquirerEntities();
	public List<Simulator> getIssuerEntities();
	public Simulator getEntity(Integer id);
	public Simulator getEntity(String name);
	public void freeSimulator(int id);
	public void freeSimulators(List<Integer> ids);
	public Simulator saveSimulator(Simulator simulator);
	public Simulator deleteSimulator(Simulator simulator);
	public SimulatorMessage saveSimulatorMessage(SimulatorMessage message);
	public SimulatorMessage deleteSimulatorMessage(SimulatorMessage message);
	public Integer getNextIdSimulator();
	public Integer getPrevIdSimulator();
	public ISimulator getSimulator(Simulator entity);
}
