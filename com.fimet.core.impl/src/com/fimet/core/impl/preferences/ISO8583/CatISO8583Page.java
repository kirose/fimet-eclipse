package com.fimet.core.impl.preferences.ISO8583;


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

import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
import com.fimet.core.impl.swt.msgiso.MessageIsoViewer;

public class CatISO8583Page extends PreferencePage implements IWorkbenchPreferencePage {
	MessageIsoViewer viewer;
	Button btnApply;
    public CatISO8583Page() {
        noDefaultAndApplyButton();
    }
	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected Control createContents(Composite parent) {
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    	GridLayout layout = new GridLayout(1,false);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	parent.setLayout(layout);
    	
    	Composite composite = new Composite(parent, SWT.NONE);
    	layout = new GridLayout(1,true);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    	composite.setLayout(layout);
    	
    	MessageIsoParameters params = new MessageIsoParameters();
    	params.setType(MessageIsoType.ACQ_REQ.getId());
    	params.setAsc(Boolean.TRUE);
    	
    	viewer = new MessageIsoViewer(params, true, composite, SWT.NONE);
    	viewer.setLayout(layout);
    	
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
				viewer.commit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	return composite;
	}
    @Override
    public boolean performOk() {
    	viewer.commit();
    	return super.performOk();
    }
}
