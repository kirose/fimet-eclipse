package com.fimet.core.impl.swt.msgiso;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
import com.fimet.core.impl.swt.VCombo;

public class MessageIsoTypeCombo extends VCombo {

	private List<MessageIsoType> types;  
	public MessageIsoTypeCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public MessageIsoTypeCombo(Composite parent) {
		super(parent);
		init();
	}
	private void init() {
		getCombo().setText("Select Encode");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			public String getText(Object element) {
				return super.getText(element);
			}
		});
		getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		setInput(MessageIsoType.values());
	}
	public MessageIsoType getSelected() {
		if (getStructuredSelection() != null) {
			return (MessageIsoType)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(MessageIsoType select) {
		if (select != null && types != null) {
			int i = 0;
			for (MessageIsoType encodes : types) {
				if (encodes.equals(select)) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		}
	}
}
