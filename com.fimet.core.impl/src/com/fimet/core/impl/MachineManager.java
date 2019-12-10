package com.fimet.core.impl;

import java.util.ArrayList;
import java.util.List;

import com.fimet.core.IMachine;
import com.fimet.core.IMachineManager;
import com.fimet.core.net.Machine;
import com.fimet.persistence.sqlite.dao.SocketDAO;

public class MachineManager implements IMachineManager {

	@Override
	public void free() {

	}

	@Override
	public void saveState() {

	}

	@Override
	public List<IMachine> findMachines() {
		List<Machine> machines = SocketDAO.getInstance().findMachines();
		List<IMachine> m = new ArrayList<>();
		if (machines != null) {
			for (Machine machine : machines) {
				m.add(machine);
			}
		}
		return m;
	}

}
