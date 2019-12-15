package com.fimet.core.impl.swt.enviroment;

public abstract class EnviromentAction extends org.eclipse.jface.action.Action {
	private EnviromentPanel view;
	public EnviromentAction(String name, EnviromentPanel view){
		super(name);
		this.view = view;
	}
	public EnviromentPanel getEnviromentPanel() {
		return view;
	}
}
