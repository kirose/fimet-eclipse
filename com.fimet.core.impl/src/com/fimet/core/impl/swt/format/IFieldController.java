package com.fimet.core.impl.swt.format;

import com.fimet.core.entity.sqlite.FieldFormatGroup;

public interface IFieldController {
	public void onNewField();
	public void onEditField();
	public void onDeleteField();

	public void onNewGroup();
	public void onEditGroup();
	public void onDeleteGroup();
	public FieldFormatGroup getSelectedGroup();
	public FieldNode getNode(String idField);
}
