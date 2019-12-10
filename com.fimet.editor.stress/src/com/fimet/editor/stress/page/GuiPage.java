package com.fimet.editor.stress.page;


import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.fimet.commons.Images;
import com.fimet.editor.stress.StressEditor;

public abstract class GuiPage extends FormPage {
	protected StressEditor editor;
	private String formTitle;
	public GuiPage(StressEditor editor, String id, String title, String formTitle) {
		super(editor, id, title);
		this.editor = editor;
		this.formTitle = formTitle;
	}
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
		ScrolledForm form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();
		form.setImage(Images.USE_CASE_ICON.createImage());
		form.setText(formTitle);
		fillBody(managedForm, toolkit);
		editor.getModifier().startApplingSourceChanges();
		update();
		editor.getModifier().finishApplingSourceChanges();
		IContextService contextService = (IContextService)getSite().getService(IContextService.class);
		contextService.activateContext("com.fimet.context.stressEditorPageGui");
	}

	abstract public void update();
	abstract protected void fillBody(IManagedForm managedForm, FormToolkit toolkit);
	
	@Override
	public void setActive(boolean active) {
		if (active) {
			if (editor.getSourcePage().isDirty()) {
				editor.applyChangesToPagesGui();
				editor.getModifier().cleanDirtyPagesGui();
			}
		}
	}
	public void commit() {
		if (getManagedForm() != null) {
			getManagedForm().commit(true);
		}
	}
    @Override
    public void doSave(IProgressMonitor monitor) {
    	super.doSave(monitor);
    }
}
