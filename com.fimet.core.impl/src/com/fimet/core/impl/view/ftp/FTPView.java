package com.fimet.core.impl.view.ftp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.fimet.core.impl.swt.ftp.FtpPanel;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FTPView extends ViewPart {
	
	public static final String ID = "com.fimet.ts.view.FTPConnectionsView";

	FtpPanel panel;
	public FTPView() {
		super();
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		panel = new FtpPanel(parent, SWT.NONE, true);
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