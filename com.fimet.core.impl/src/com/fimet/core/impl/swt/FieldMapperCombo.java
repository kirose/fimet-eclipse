package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.IRuleManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldMapper;

public class FieldMapperCombo extends VCombo {

	private List<FieldMapper> types;  
	public FieldMapperCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public FieldMapperCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select RuleType");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((FieldMapper)element).toString();
			}
		});
		types = Manager.get(IRuleManager.class).getFieldMappers();
		setInput(types);
	}
	public FieldMapper getSelected() {
		if (getStructuredSelection() != null) {
			return (FieldMapper)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Integer id) {
		if (id != null && id >= 0) {
			int i = 0;
			for (FieldMapper e: types) {
				if (e.getId() == id) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		} else {
			getCombo().deselectAll();
		}
	}
}
