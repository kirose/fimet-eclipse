package com.fimet.editor.usecase;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;

public class UseCaseEditorSite extends MultiPageEditorSite {
	public UseCaseEditorSite(MultiPageEditorPart multiPageEditor, IEditorPart editor) {
		super(multiPageEditor, editor);
	}
}
