package com.fimet.core.impl.swt;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.converter.IConverter;


public class ConverterCombo extends VCombo {

	private List<IConverter> encodess;  
	public ConverterCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public ConverterCombo(Composite parent) {
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
		setInput(encodess = Converter.getConverters());
	}
	public IConverter getSelected() {
		if (getStructuredSelection() != null) {
			return (IConverter)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(IConverter select) {
		if (select != null && encodess != null) {
			int i = 0;
			for (IConverter encodes : encodess) {
				if (encodes.equals(select)) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		} else {
			getCombo().deselectAll();
		}
	}
	public void select(Integer idConverter) {
		if (idConverter != null && encodess != null) {
			int i = 0;
			for (IConverter encodes : encodess) {
				if (encodes.getId() == idConverter) {
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
