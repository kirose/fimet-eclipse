package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.IFieldFormatManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldFormatGroup;


public class FieldFormatGroupCombo extends VCombo {

	private List<FieldFormatGroup> groups;  
	public FieldFormatGroupCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public FieldFormatGroupCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Group");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				return super.getText(element);
			}
		});
		getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		groups = Manager.get(IFieldFormatManager.class).getGroups();
		setInput(groups);
	}
	public FieldFormatGroup getSelected() {
		if (getStructuredSelection() != null) {
			return (FieldFormatGroup)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(FieldFormatGroup select) {
		if (select != null && groups != null) {
			int i = 0;
			for (FieldFormatGroup group : groups) {
				if (group.equals(select)) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		}
	}
	public void select(Integer idGroup) {
		if (idGroup != null && groups != null) {
			int i = 0;
			for (FieldFormatGroup group : groups) {
				if (group.getId().equals(idGroup)) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		}
	}
}
