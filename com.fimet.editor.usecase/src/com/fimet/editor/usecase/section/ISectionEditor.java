package com.fimet.editor.usecase.section;

import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.editor.usecase.UseCaseEditor;

public interface ISectionEditor {
	UseCaseEditor getEditor();
	FormToolkit getToolkit();
	void markDirty();
	boolean isDirty();
}
