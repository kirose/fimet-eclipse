package com.fimet.editor.usecase.section.swt.validation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class ValidationButtons extends Composite {
	private ValidationViewer viewer;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	private Button btnUp;
	private Button btnDown;
	public ValidationButtons(ValidationViewer viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
		setBackground(parent.getBackground());
		setLayout(new GridLayout(1,true));
		setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true,1,1));
	}
	public void createContents(FormToolkit toolkit) {
		Composite parent = this;
		btnAdd = new Button(parent, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAdd.setText("Add");

		btnEdit = new Button(parent, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit");
		
		btnDelete = new Button(parent, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setText("Delete");

		btnUp = new Button(parent, SWT.NONE);
		btnUp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnUp.setText("Up");
		
		btnDown = new Button(parent, SWT.NONE);
		btnDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDown.setText("Down");
		
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
		btnUp.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onUp();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDown.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onDown();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
}
