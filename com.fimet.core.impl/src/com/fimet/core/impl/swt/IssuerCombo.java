package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.swt.widgets.Composite;

import com.fimet.core.IEnviromentManager;
import com.fimet.core.ISocketManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.net.ISocket;

public class IssuerCombo extends SocketCombo {

	public IssuerCombo(Composite parent, int style) {
		super(parent, style);
	}
	public IssuerCombo(Composite parent) {
		super(parent);
	}
	@Override
	protected List<ISocket> findSockets(){
		Enviroment active = Manager.get(IEnviromentManager.class).getActive();
		if (active != null) {
			return Manager.get(ISocketManager.class).findIssuers(active.getAddress());
		} else {
			return null;
		}
	}
}
