package com.fimet.core;

import java.util.List;

import org.eclipse.core.resources.IResource;

public interface IUseCaseExecutorManager extends IManager {
	void run(List<IResource> resources);
	void runF8(IFimetReport f8Report);
	void onFinish(IUseCaseJob job);
	void stop();
}
