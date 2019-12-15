package com.fimet.core.impl.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.entity.sqlite.IRuleValue;

public class RuleValueCombo extends VCombo {

	private List<IRuleValue> values;  
	public RuleValueCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public RuleValueCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Value");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IRuleValue)element).getName();
			}
		});
	}
	public void setInput(List<?> values) {
		if (values == null || values.isEmpty()) {
			setInput(this.values = null);	
		} else {
			this.values = new ArrayList<>(values.size());
			for (Object o : values) {
				this.values.add((IRuleValue)o);	
			}
			super.setInput(this.values);
		}
	}
	public IRuleValue getSelected() {
		if (getStructuredSelection() != null) {
			return (IRuleValue)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Integer id) {
		if (id != null && id >= 0 && values != null) {
			int i = 0;
			for (IRuleValue e: values) {
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
