package com.fimet.editor.stress;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.part.MultiPageEditorSite;

public class StressEditorSite extends MultiPageEditorSite {

	public StressEditorSite(MultiPageEditorPart multiPageEditor, IEditorPart editor) {
		super(multiPageEditor, editor);
	}
	@Override
	public IKeyBindingService getKeyBindingService() {
		return super.getKeyBindingService();
	}

}
