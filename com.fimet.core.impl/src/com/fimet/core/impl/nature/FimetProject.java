package com.fimet.core.impl.nature;

import org.eclipse.core.runtime.CoreException;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FimetProject extends BaseProject {
	
	public static final String NATURE_ID = "com.fimet.nature.FimetProject";
	
	/**
	 * PluginProject constructor comment.
	 */
	public FimetProject() {
		super();
	}

	@Override
	public void configure() throws CoreException {
		this.addNature(NATURE_ID);
	}

	@Override
	public void deconfigure() throws CoreException {
		this.removeNature(NATURE_ID);
	}
}
