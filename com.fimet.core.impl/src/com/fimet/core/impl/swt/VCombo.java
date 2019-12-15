package com.fimet.core.impl.swt;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class VCombo extends ComboViewer implements DisposeListener {

	protected boolean isValid;
	public VCombo(Composite parent) {
		super(parent);
		Combo combo = getCombo();
		if (combo != null)
			combo.addDisposeListener(this);
	}
	public VCombo(Composite parent, int style) {
		super(parent, style);
		Combo combo = getCombo();
		if (combo != null)
			combo.addDisposeListener(this);
	}
	public void valid() {
		isValid = true;
	}
	public void invalid() {
		isValid = false;
	}
	public boolean isValid() {
    	return isValid;
    }
	public void validate() {
		isValid = hasSelected();
	}
	public boolean hasSelected() {
		return getStructuredSelection() != null;
	}
	public void dispose() {
		Combo combo = getCombo();
		if (combo != null)
			combo.removeDisposeListener(this);
	}
	public void widgetDisposed(DisposeEvent e) {
		dispose();
	}
}
