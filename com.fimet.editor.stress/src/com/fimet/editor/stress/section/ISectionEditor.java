package com.fimet.editor.stress.section;

import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.editor.stress.StressEditor;

public interface ISectionEditor {
	StressEditor getEditor();
	FormToolkit getToolkit();
	void markDirty();
	boolean isDirty();
}
