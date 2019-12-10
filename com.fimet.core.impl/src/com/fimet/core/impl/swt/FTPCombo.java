package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Ftp;
import com.fimet.core.net.IFtpManager;

public class FTPCombo extends VCombo {

	private List<Ftp> items;  
	public FTPCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public FTPCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select FTP");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
		    public String getText(Object element) {
		        if (element instanceof Ftp) {
		        	Ftp iap = (Ftp) element;
		            return iap.getName() + " / " + iap.getAddress();
		        }
		        return super.getText(element);
		    }
		});
		items = findItems();
		setInput(items);
	}
	protected List<Ftp> findItems(){
		return Manager.get(IFtpManager.class).getFtps();
	}
	public Ftp getSelected() {
		if (getStructuredSelection() != null) {
			return (Ftp)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Ftp select) {
		if (select != null) {
			int i = items.indexOf(select);
			if (i != -1)
					getCombo().select(i);
		}
	}
}
