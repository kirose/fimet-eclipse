package com.fimet.core.impl.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

public class KeyValueCombo<T> extends VCombo {

	private List<Option> options = new ArrayList<Option>();  
	public KeyValueCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public KeyValueCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Option");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@SuppressWarnings("unchecked")
			@Override
			public String getText(Object element) {
				return ((KeyValueCombo<T>.Option)element).key;
			}
		});
	}
	public void addOption(String name, T value) {
		this.options.add(new Option(name, value));
	}
	public void refreshOptions() {
		setInput(this.options);
	}
	@SuppressWarnings("unchecked")
	public T getSelected() {
		if (getStructuredSelection() != null) {
			return ((Option)getStructuredSelection().getFirstElement()).value;
		} else {
			return null;
		}
	}
	public void select(String key) {
		int i = 0;
		for (Option o: options) {
			if (key.equals(o.key)) {
				getCombo().select(i);
				break;
			}
			i++;
		}
	}
	public void select(T value) {
		int i = 0;
		for (Option o: options) {
			if (o.value.equals(value)) {
				getCombo().select(i);
				break;
			}
			i++;
		}
	}
	public void select(Option option) {
		getCombo().select(options.indexOf(option));
	}
	 class Option {
		String key;
		T value;
		public Option(String key, T value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
}
