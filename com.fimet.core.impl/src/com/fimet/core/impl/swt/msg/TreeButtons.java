package com.fimet.core.impl.swt.msg;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class TreeButtons extends Composite {
	private MessageViewer viewer;
	private Button btnAdd;
	private Button btnEdit;
	private Button btnDelete;
	private Button btnParse;
	private Button btnSearch;
	public TreeButtons(MessageViewer viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
		setBackground(parent.getBackground());
		GridLayout layout = new GridLayout(1,true);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, true,1,1));
		createContents();
	}
	public void createContents() {
		Composite composite = this;

		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnAdd.setText("New");
		//btnAdd.setTextDirection(SWT.LEFT_TO_RIGHT);
		//btnAdd.setImage(UCImages.ADD.createImage());
		
		btnEdit = new Button(composite, SWT.NONE);
		btnEdit.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnEdit.setText("Edit");
		//btnEdit.setTextDirection(SWT.LEFT_TO_RIGHT);
		//btnEdit.setImage(UCImages.EDIT.createImage());
		
		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDelete.setText("Delete");
		//btnDelete.setTextDirection(SWT.LEFT_TO_RIGHT);
		//btnDelete.setImage(UCImages.DELETE.createImage());
		
		btnParse = new Button(composite, SWT.NONE);
		btnParse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnParse.setText("Parse");
		//btnParse.setTextDirection(SWT.LEFT_TO_RIGHT);
		//btnParse.setImage(UCImages.PARSE.createImage());

		btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSearch.setText("Search");
		
		hookListeners();
	}
	private void hookListeners() {
		btnAdd.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onNewField();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnEdit.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onEditField();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onDeleteField();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnParse.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onParse();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				viewer.onSearch();
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
		btnParse.setEnabled(enabled);	
		btnSearch.setEnabled(enabled);
	}
}
