package com.fimet.core.impl.preferences.field;


import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
//import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.fimet.core.impl.swt.field.FieldDefinitionPanel;

public class FieldDefinitionPage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "com.fimet.preferences.FieldDefinitionPage";
	
	private FieldDefinitionPanel panel;
	Button btnApply;
	public FieldDefinitionPage() {
        noDefaultAndApplyButton();
    }
	
    @Override
    protected final Control createContents(Composite container) {
    	GridLayout layout = new GridLayout(1,false);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	
    	panel = new FieldDefinitionPanel(container);
    	panel.setLayout(layout);
    	
    	Composite cmpButtons = new Composite(container, SWT.NONE);
    	layout = new GridLayout(2,false);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	cmpButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1,1));
    	cmpButtons.setLayout(layout);
    	new Label(cmpButtons, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnApply = new Button(cmpButtons, SWT.NONE);
		btnApply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnApply.setText("    Apply    ");
		btnApply.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				panel.commit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	return panel;
    }
	@Override
    public void init(IWorkbench workbench) {
    }
    @Override
    public boolean performOk() {
    	panel.commit();
    	return super.performOk();
    }
}
