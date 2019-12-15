package com.fimet.core.impl.swt;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

public class ClassCombo extends VCombo {

	private List<Class<?>> classParsers;  
	public ClassCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public ClassCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Class");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Class<?>)element).getSimpleName();
			}
		});
	}
	public void setClasses(Class<?>[] classes) {
		classParsers = Arrays.asList(classes);
		setInput(classParsers);
	}
	public Class<?> getSelected() {
		if (getStructuredSelection() != null) {
			return (Class<?>)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Class<?> clazz) {
		if (clazz != null && classParsers != null) {
			int i = 0;
			for (Class<?> c: classParsers) {
				if (clazz == c) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		} else {
			getCombo().deselectAll();
		}
	}
	public void select(String clazz) {
		if (clazz != null && classParsers != null) {
			int i = 0;
			for (Class<?> c: classParsers) {
				if (c.getName().equals(clazz)) {
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
