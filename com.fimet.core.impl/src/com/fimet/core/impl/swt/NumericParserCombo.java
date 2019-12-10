package com.fimet.core.impl.swt;


import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.fimet.commons.numericparser.INumericParser;
import com.fimet.commons.numericparser.NumericParser;

public class NumericParserCombo extends VCombo {

	private List<INumericParser> encodess;  
	public NumericParserCombo(Composite parent, int style) {
		super(parent, style);
		init();
	}
	public NumericParserCombo(Composite parent) {
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
		setInput(encodess = NumericParser.getParsers());
	}
	public INumericParser getSelected() {
		if (getStructuredSelection() != null) {
			return (INumericParser)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Integer id) {
		if (id != null && encodess != null) {
			int i = 0;
			for (INumericParser encodes : encodess) {
				if (encodes.getId() == id) {
					getCombo().select(i);
					break;
				}
				i++;
			}
		} else {
			getCombo().deselectAll();
		}
	}
	public void select(INumericParser select) {
		if (select != null && encodess != null) {
			int i = 0;
			for (INumericParser encodes : encodess) {
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
}
