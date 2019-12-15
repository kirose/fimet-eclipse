package com.fimet.editor.usecase.section.swt.validation;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.core.usecase.Validation;

public class ValidationViewer extends Composite {

	private ValidationTreeViewer tree;
	private ValidationButtons buttons;
	private FormToolkit toolkit;
	private IValidationListener listener;
	private String nameDefault;
	private Object expectedDefault;
	private String expressionDefault;
	
	public ValidationViewer(Composite parent, IValidationListener listener, FormToolkit toolkit, int style) {
		super(parent, style);
		this.listener = listener;
		this.toolkit = toolkit;
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		setBackground(parent.getBackground());
		createContents(parent, toolkit);
	}
	private void createContents(Composite parent, FormToolkit toolkit) {

		Composite compositeTree = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		compositeTree.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gridData.heightHint = 200;
		compositeTree.setLayoutData(gridData);
		compositeTree.setBackground(parent.getBackground());

		Composite compositeButtons = new Composite(this, SWT.NONE);
		compositeButtons.setBackground(this.getBackground());
		compositeButtons.setLayout(new GridLayout(1,true));
		compositeButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true,1,1));
		
		Composite compositeEditor = new Composite(this, SWT.NONE);
		compositeEditor.setBackground(this.getBackground());
		layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		compositeEditor.setLayout(layout);
		compositeEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,2,1));

		tree = new ValidationTreeViewer(this, compositeTree, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		buttons = new ValidationButtons(this, compositeButtons, SWT.NONE);

		tree.createContents(toolkit);
		buttons.createContents(toolkit);
		
		hookListeners();
		
	}
	private void hookListeners() {

	}
	public ValidationTreeViewer getTree() {
		return tree;
	}
	public ValidationButtons getButtons() {
		return buttons;
	}
	public void onAdd() {
		ValidationDialog dialog = new ValidationDialog(this, getShell(), toolkit, SWT.NONE);
		dialog.open();
		Validation v = dialog.getValidation();
		if (v != null) {
			tree.doAdd(v);
			listener.onAddValidation(v);
		}
	}
	public void onEdit() {
		Validation v = tree.getSelected();
		if (v != null) {
			ValidationDialog dialog = new ValidationDialog(this,v, getShell(), toolkit, SWT.NONE);
			dialog.open();
			if (dialog.getValidation() != null) {
				tree.refresh();
				//tree.refresh(v);
				listener.onEditValidation(v);
			}
		}
	}
	public void onDelete() {
    	Validation node = tree.getSelected();
		tree.doDelete(node);
		listener.onDeleteValidation(node);
	}
	public void onUp() {
		int i = tree.getSelectedIndex();
		tree.doUp(i);
		listener.onSwiftValidation(i, i-1);
	}
	public void onDown() {
		int i = tree.getSelectedIndex();
		tree.doDown(i);
		listener.onSwiftValidation(i, i+1);
	}
	public List<Validation> getValidations(){
		return tree.getValidations();
	}
	public void setValidations(List<Validation> validations) {
		tree.setValidations(validations);
		//tree.refresh();
	}
	public void deleteAll() {
		tree.setValidations(null);
		//tree.getTree().removeAll();
	}
	@Override
	public void setEnabled(boolean enabled) {
		buttons.setEnabled(enabled);
		tree.getTree().setEnabled(enabled);
		super.setEnabled(enabled);
	}
	public void setDefault(String name, Object expected,String expression) {
		this.nameDefault = name;
		this.expectedDefault = expected;
		this.expressionDefault = expression;
	}
	public String getNameDefault() {
		return nameDefault;
	}
	public Object getExpectedDefault() {
		return expectedDefault;
	}
	public String getExpressionDefault() {
		return expressionDefault;
	}
	
}
