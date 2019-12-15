package com.fimet.core.impl.swt;


import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.entity.sqlite.Parser;
import com.fimet.persistence.sqlite.dao.ParserDAO;

public class ParserCombo extends VCombo {

	private List<Parser> parsers;
	public ParserCombo(Composite parent, int type, int style) {
		super(parent, style);
		init(type);
	}
	public ParserCombo(Composite parent, int type) {
		super(parent);
		init(type);
	}
	private void init(int type) {
		getCombo().setText("Select Parser");
		setContentProvider(ArrayContentProvider.getInstance());
		setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Parser)element).toString();
			}
		});
		parsers = ParserDAO.getInstance().findByType(type);
		setInput(parsers);
	}
	public Parser getSelected() {
		if (getStructuredSelection() != null) {
			return (Parser)getStructuredSelection().getFirstElement();
		} else {
			return null;
		}
	}
	public void select(Integer id) {
		if (id != null && id >= 0) {
			int i = 0;
			for (Parser e: parsers) {
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
