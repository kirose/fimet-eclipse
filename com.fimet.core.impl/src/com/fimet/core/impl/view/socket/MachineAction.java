package com.fimet.core.impl.view.socket;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.fimet.commons.Images;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IMachine;
import com.fimet.core.IMachineManager;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.listener.IEnviromentConnected;
import com.fimet.core.listener.IEnviromentDisconnected;

public class MachineAction extends Action implements IEnviromentConnected, IEnviromentDisconnected {
	
	Menu menu;
	String machine;
	List<IMachine> machines;
	IPreferencesManager preferencesManager = Manager.get(IPreferencesManager.class);
	IEnviromentManager enviromentManager = Manager.get(IEnviromentManager.class);
	SelectionListener listener = new SelectionListener() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			IMachine machine = (IMachine)((MenuItem)e.widget).getData();
			select(machine.getAddress());
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {}
	};
	SocketView view;
	MachineAction(SocketView view) {
		super("Machine",Action.AS_DROP_DOWN_MENU);
		this.view = view;
		if (enviromentManager.getActive() != null) {
			onChangeEnviroment(enviromentManager.getActive());
		}
		enviromentManager.addFirstListener(ON_DISCONNECTED, this);
		enviromentManager.addFirstListener(ON_CONNECTED, this);
	}
	private void onChangeEnviroment(Enviroment e) {
		ThreadUtils.runOnMainThread(()->{
			List<IMachine> machines = Manager.get(IMachineManager.class).findMachines();
			setMachines(machines);
			select(e.getAddress());
		});
	}
	public void setMachines(List<IMachine> machines) {
		IMenuCreator creator = crateMenu(this.machines = machines);
		setMenuCreator(creator);
	}
	public void select(String address) {
		setText(createTitle(getMachine(address)));
		if (enviromentManager.getActive() != null) {
			enviromentManager.getActive().setAddress(address);
			enviromentManager.update(enviromentManager.getActive());
		}
		if (this.machine == null || !this.machine.equals(address)) {
			view.onChangedMachine(this.machine = address);
		}
	}
	private IMachine getMachine(String address) {
		if (address == null || machines == null) {
			return null;
		} else {
			for (IMachine machine : machines) {
				if (machine.getAddress().equals(address)) return machine;
			}
			return null;
		}
	}
	private IMenuCreator crateMenu(List<IMachine> machines) {
		IMenuCreator menu = new IMenuCreator() {
			@Override
			public void dispose() {
			}

			@Override
			public Menu getMenu(Control parent) {
				MachineAction.this.menu = new Menu(parent);
				MenuItem item;
				if (machines != null && !machines.isEmpty()) {
					for (IMachine machine : machines) {
						item = new MenuItem(MachineAction.this.menu, SWT.PUSH);
						item.setText(createTitle(machine));
						item.setData(machine);
						item.addSelectionListener(listener);
					}
				}
				return MachineAction.this.menu;
			}

			@Override
			public Menu getMenu(Menu parent) {
				return parent;
			}
		};
		return menu;
	}
	private String createTitle(IMachine machine) {
		return machine != null ? (machine.getName() + " / " + machine.getAddress()) : "Select Machine";
	}
	@Override
	public void run() {
		
	}
	@Override
	public ImageDescriptor getImageDescriptor() {
		return Images.IAP_MACHINE_ICON;
	}
	public String getSelected() {
		return machine;
	}
	public void dispose() {
		enviromentManager.removeListener(ON_DISCONNECTED, this);
		enviromentManager.removeListener(ON_CONNECTED, this);
	}
	@Override
	public void onEnviromentDisconnected(Enviroment e) {
		setText(null);
		setMachines(null);
		view.onChangedMachine(MachineAction.this.machine = null);		
	}
	@Override
	public void onEnviromentConnected(Enviroment e) {
		onChangeEnviroment(e);		
	}
}