package com.fimet.core.impl.view.database;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.fimet.core.impl.swt.database.DataBasePanel;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class DataBaseView extends ViewPart {
	
	public static final String ID = "com.fimet.view.DataBaseView";
	private DataBasePanel panel;
	public DataBaseView() {
		super();
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		panel = new DataBasePanel(parent, SWT.NONE, true);
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.panel.setFocus();
	}
	@Override
	public void dispose() {
		panel.dispose();
	}
}