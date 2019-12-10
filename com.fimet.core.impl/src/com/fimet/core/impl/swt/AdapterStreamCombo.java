package com.fimet.core.impl.swt;


import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.Adapter;
import com.fimet.core.ISO8583.adapter.IAdapter;
import com.fimet.core.ISO8583.adapter.IAdapterManager;
import com.fimet.core.ISO8583.adapter.IStreamAdapter;


public class AdapterStreamCombo extends VCombo {

	private List<IStreamAdapter> adapters;
	public AdapterStreamCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public AdapterStreamCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Adapter");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Adapter)element).toString();
			}
		});
		adapters = Manager.get(IAdapterManager.class).getStreamAdapters();
		setInput(adapters);
	}
	public Adapter getSelected() {
		if (getStructuredSelection() != null) {
			return (Adapter)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Integer id) {
		if (id != null && id >= 0) {
			int i = 0;
			for (IAdapter e: adapters) {
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
