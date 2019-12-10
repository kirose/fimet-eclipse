package com.fimet.core.impl.view.socket;

public abstract class SocketAction extends org.eclipse.jface.action.Action {
	private SocketView view;
	public SocketAction(String name, SocketView view){
		super(name);
		this.view = view;
	}
	public SocketView getSocketView() {
		return view;
	}
}
