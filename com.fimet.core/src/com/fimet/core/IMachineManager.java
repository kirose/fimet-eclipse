package com.fimet.core;

import java.util.List;

public interface IMachineManager extends IManager {
	List<IMachine> findMachines();
}
