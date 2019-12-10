package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.IDataBaseManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.DataBase;

public class DataBaseCombo extends VCombo {
	private static final DataBase NONE = new DataBase(-1,"None");
	private List<DataBase> items;  
	public DataBaseCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public DataBaseCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Data Base");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
		    public String getText(Object element) {
		    	if (element == NONE) {
		    		return "None";
		    	} else { 
		        	DataBase iap = (DataBase) element;
		            return iap.getName() + " / " + iap.getAddress();
		        }
		    }
		});
		items = findItems();
		if (items == null) {
			items = new java.util.ArrayList<>();
		}
		items.add(0,NONE);
		setInput(items);
	}
	protected List<DataBase> findItems(){
		return Manager.isManaged(IDataBaseManager.class) ? Manager.get(IDataBaseManager.class).getDataBases() : null;
	}
	public DataBase getSelected() {
		if (getStructuredSelection() != null && getStructuredSelection().getFirstElement() != NONE) {
			return (DataBase)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(DataBase select) {
		if (select != null) {
			int i = items.indexOf(select);
			if (i != -1)
					getCombo().select(i);
		}
	}
}
