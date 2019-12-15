package com.fimet.editor.usecase.section.swt.mgrfields;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class IncFieldsButtons extends Composite {
	private ManagerFieldsViewer viewer;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	public IncFieldsButtons(ManagerFieldsViewer viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
		setBackground(parent.getBackground());
		setLayout(new GridLayout(1,true));
		setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true,1,1));
	}
	public void createContents(FormToolkit toolkit) {
		Composite composite = this;

		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAdd.setText("Add");
		
		btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit");
		
		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setText("Delete");
		
		hookListeners();
	}
	private void hookListeners() {
		btnAdd.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onAdd();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnEdit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onEdit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onDelete();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		btnAdd.setEnabled(enabled);
		btnEdit.setEnabled(enabled);
		btnDelete.setEnabled(enabled);
	}
}
