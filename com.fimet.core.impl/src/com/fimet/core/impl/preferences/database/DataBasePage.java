package com.fimet.core.impl.preferences.database;


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

import com.fimet.core.impl.swt.database.DataBasePanel;

public class DataBasePage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "com.fimet.preferences.DataBasePage";
	DataBasePanel panel;
	Button btnApply;
    public DataBasePage() {
        noDefaultAndApplyButton();
    }
    @Override
    protected final Control createContents(Composite container) {
        GridData gd;
        GridLayout layout;
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);
        container.setLayoutData(gd);
		
        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite composite = new Composite(container, SWT.NONE);
        composite.setLayout(layout);
        composite.setLayoutData(gd);

        panel = new DataBasePanel(composite, SWT.NONE, false);
        
    	Composite cmpButtons = new Composite(composite, SWT.NONE);
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
        
        return composite;
    }
	@Override
    public void init(IWorkbench workbench) {
    }
    @Override
    public boolean performOk() {
    	panel.commit();
    	panel.dispose();
    	return super.performOk();
    }
    @Override
    public void dispose() {
    	panel.dispose();
    }

}
