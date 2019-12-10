package com.fimet.core.usecase;

import java.util.List;

import org.eclipse.core.resources.IResource;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * Job para ejecucion de casos de uso
 */
public class UseCaseJob extends UseCaseJobAbstract {

	public UseCaseJob(String name, UseCaseExecutorManager manager, List<IResource> resources) {
		super(name, manager, resources);
	}

}
