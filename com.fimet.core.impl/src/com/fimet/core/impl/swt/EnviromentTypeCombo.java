package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.EnviromentType;

public class EnviromentTypeCombo extends VCombo {

	private List<EnviromentType> enviroments;  
	public EnviromentTypeCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public EnviromentTypeCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select EnviromentType");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((EnviromentType)element).toString();
			}
		});
		enviroments = Manager.get(IEnviromentManager.class).getEnviromentsTypes();
		setInput(enviroments);
	}
	public EnviromentType getSelected() {
		if (getStructuredSelection() != null) {
			return (EnviromentType)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Integer id) {
		if (id != null && id >= 0) {
			int i = 0;
			for (EnviromentType e: enviroments) {
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
