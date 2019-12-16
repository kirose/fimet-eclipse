package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.IEnviromentManager;
import com.fimet.core.ISocketManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.listener.ISocketUpdateAll;
import com.fimet.core.net.ISocket;

public class SocketCombo extends VCombo implements ISocketUpdateAll {

	private List<ISocket> connections;  
	public SocketCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public SocketCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Connection");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
		    public String getText(Object element) {
		        if (element instanceof ISocket) {
		        	ISocket connection = (ISocket) element;
		            return connection.getName() + " / " + connection.getPort() + " / " + connection.getProcess();
		        }
		        return super.getText(element);
		    }
		});
		setInput(connections = findSockets());
		Manager.get(ISocketManager.class).addListener(ON_UPDATE_ALL, this);
	}
	protected List<ISocket> findSockets(){
		Enviroment active = Manager.get(IEnviromentManager.class).getActive();
		if (active != null) {
			return Manager.get(ISocketManager.class).findByAddress(active.getAddress());
		} else {
			return null;
		}
	}
	public ISocket getSelected() {
		if (getStructuredSelection() != null) {
			return (ISocket)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(ISocket select) {
		if (select != null && connections != null) {
			int i = 0;
			for (ISocket connection : connections) {
				if (connection.equals(select)) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		} else {
			getCombo().deselectAll();
		}
	}
	@Override
	public void onSocketUpdateAll() {
		setInput(connections = findSockets());
	}
	@Override
	public void dispose() {
		super.dispose();
		Manager.get(ISocketManager.class).removeListener(ON_UPDATE_ALL, this);		
	}
}
