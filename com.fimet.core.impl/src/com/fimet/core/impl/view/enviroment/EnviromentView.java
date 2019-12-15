package com.fimet.core.impl.view.enviroment;


import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.impl.swt.enviroment.EnviromentPanel;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class EnviromentView extends ViewPart {

	public static final String ID = "com.fimet.view.EnviromentsView";

	EnviromentPanel panel;
	public EnviromentView() {
		super();
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		panel = new EnviromentPanel(parent);
		
		List<Enviroment> enviroments = Manager.get(IEnviromentManager.class).getEnviroments();
		panel.setInput(enviroments);
		if (enviroments != null && !enviroments.isEmpty()) {
			panel.select(0);
		}
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.panel.setFocus();
	}

	public Enviroment getSelected() {
		return panel.getSelected();
	}
	@Override
	public void dispose() {
		panel.dispose();
	}
}