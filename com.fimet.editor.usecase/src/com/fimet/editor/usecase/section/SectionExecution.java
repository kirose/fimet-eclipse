package com.fimet.editor.usecase.section;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.fimet.commons.Images;
import com.fimet.commons.preference.IPreference;
import com.fimet.core.IUseCaseExecutorManager;
import com.fimet.core.Manager;
import com.fimet.editor.usecase.UseCaseEditor;

public class SectionExecution extends SectionPart implements ISectionEditor {

	private UseCaseEditor editor;
	private Button btnRun;
	private static IUseCaseExecutorManager useCaseManager = Manager.get(IUseCaseExecutorManager.class);
	
	public SectionExecution(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
		hookComponentsListeners();
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Execution");
		section.setDescription("");

		Composite composite = new Composite(section, SWT.NULL);
		composite.setLayout(new GridLayout(6,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setBackground(section.getBackground());
		
		new Label(composite,SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		
		btnRun = new Button(composite, SWT.NONE);
		btnRun.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		btnRun.setText("Run Use Case");
		btnRun.setImage(Images.RUN_USECASE.createImage());

		section.setClient(composite);
	}
	private void hookComponentsListeners() {
		btnRun.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (editor.getModifier().areDirtyPagesGui() || editor.isDirty()) {
					if (askSave(editor.getEditorInput().getName())) {
						editor.doSave(new NullProgressMonitor());
						runUseCase();
					}
				} else {
					runUseCase();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	private boolean askSave(String name) {
		boolean save = PlatformUI.getPreferenceStore().getBoolean(IPreference.SAVE_USECASE_BEFORE_RUN);
		if (save) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
			"Save Use Case",
			"In order to run the use case you must save the changes.\n"+
			"Do you want save the changes on  "+name+"?",
			"Don't ask again.",
			false,
			PlatformUI.getPreferenceStore(),
			IPreference.SAVE_USECASE_BEFORE_RUN
		);
		save = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && save) {
			PlatformUI.getPreferenceStore().setValue(IPreference.RUN_USECASE_OVERRIDE_FOLDER, save);
		}
		return save;
	}

	@Override
	public UseCaseEditor getEditor() {
		return editor;
	}
	@Override
	public FormToolkit getToolkit() {
		return editor.getToolkit();
	}
	public void update() {}
	private void runUseCase() {
		IEditorInput input = editor.getSourcePage().getEditorInput();
		if (input instanceof org.eclipse.ui.part.FileEditorInput) {
			List<IResource> useCases = new ArrayList<>();
			org.eclipse.ui.part.FileEditorInput fileEditorInput = (org.eclipse.ui.part.FileEditorInput) input;
			useCases.add((IResource)fileEditorInput.getFile());
			useCaseManager.run(useCases);
		}
	}
}
