package com.fimet.core;

import java.util.List;

import org.eclipse.core.resources.IResource;


public interface IFimetReport {
	List<IResource> getResources();
	/**
	 * Se invoca cuando se ternima de ejecurar el task, siempre se invoca
	 * @param task
	 */
	//void onFinishTask(UseCaseExecutor task);
	/**
	 * Se invoca cuando se finaliza la ejecucion del ultimo caso de uso, siempre se invoca
	 */
	void onFinishExecution();
	/**
	 * The project of th use cases 
	 * @return
	 */
	//IProject getProject();
}
