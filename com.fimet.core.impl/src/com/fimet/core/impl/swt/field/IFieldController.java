package com.fimet.core.impl.swt.field;

import com.fimet.core.entity.sqlite.FieldFormatGroup;

public interface IFieldController {
	public void onNewField();
	public void onAddField();
	public void onEditField();
	public void onDeleteField();

	public void onNewGroup();
	public void onAddGroup();
	public void onEditGroup();
	public void onDeleteGroup();
	public FieldFormatGroup getSelectedGroup();
}
