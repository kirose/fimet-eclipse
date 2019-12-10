package com.fimet.core.stress;

import java.util.List;

import org.eclipse.core.resources.IResource;

import com.fimet.commons.exception.StressException;
import com.fimet.core.IStressExecutorManager;

public class StressExecutorManager implements IStressExecutorManager {

	@Override
	public void free() {

	}

	@Override
	public void saveState() {

	}

	@Override
	public void execute(Stress stress) {
		StressExecutor stressExecutor = new StressExecutor(stress); 
		createSimQueuesFile(stressExecutor);
	}

	private void createSimQueuesFile(StressExecutor executor) {
		IResource resource = executor.getStress().getResource();
		
	}

}
