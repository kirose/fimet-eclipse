package com.fimet.core.impl.view.sim_queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class SimQueueButtons extends Composite {
	private SimQueueView view;
	private Button btnEdit;
	private Button btnDelete;
	private Button btnUp;
	private Button btnDown;
	private Button btnInject;
	private Button btnInjectAll;
	public SimQueueButtons(SimQueueView view, Composite parent, int style) {
		super(parent, style);
		this.view = view;
		setBackground(parent.getBackground());
		setLayout(new GridLayout(1,true));
		setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true,1,1));
		createContents();
	}
	public void createContents() {
		Composite parent = this;

		btnInject = new Button(parent, SWT.NONE);
		btnInject.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnInject.setText("Inject");
		//btnInject.setImage(Images.RUN_IMG.createImage());
		btnInject.setFocus();
		
		btnInjectAll = new Button(parent, SWT.NONE);
		btnInjectAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnInjectAll.setText("Inject All");
		//btnInjectAll.setImage(Images.RUN_IMG.createImage());
		btnInjectAll.setFocus();
		
		btnEdit = new Button(parent, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnEdit.setText("Edit");
		
		btnDelete = new Button(parent, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelete.setText("Remove");

		btnUp = new Button(parent, SWT.NONE);
		btnUp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnUp.setText("Up");
		
		btnDown = new Button(parent, SWT.NONE);
		btnDown.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDown.setText("Down");

		hookListeners();
	}
	private void hookListeners() {
		btnEdit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.onEdit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.onRemove();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnUp.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.onUp();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDown.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.onDown();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnInject.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.injectSelection();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnInjectAll.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.injectAll();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	@Override
	public boolean setFocus() {
		return btnInject.setFocus();
	}
}
