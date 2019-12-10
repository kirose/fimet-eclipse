package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;

public class EnviromentCombo extends VCombo {

	private List<Enviroment> items;  
	public EnviromentCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public EnviromentCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Data Base");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
		    public String getText(Object element) {
		        if (element instanceof Enviroment) {
		        	Enviroment iap = (Enviroment) element;
		            return iap.getName();
		        }
		        return super.getText(element);
		    }
		});
		items = findItems();
		setInput(items);
	}
	protected List<Enviroment> findItems(){
		return Manager.get(IEnviromentManager.class).getEnviroments();
	}
	public Enviroment getSelected() {
		if (getStructuredSelection() != null) {
			return (Enviroment)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Enviroment select) {
		if (select != null) {
			int i = items.indexOf(select);
			if (i != -1)
					getCombo().select(i);
		}
	}
}
