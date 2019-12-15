package com.fimet.editor.usecase.section;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.fimet.core.usecase.Validation;
import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.section.swt.validation.IValidationListener;
import com.fimet.editor.usecase.section.swt.validation.ValidationViewer;

public class SectionAcquirerResponse extends SectionPart implements ISectionEditor,IValidationListener {

	private UseCaseEditor editor;
	private ValidationViewer validationViewer;
	
	public SectionAcquirerResponse(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | /*ExpandableComposite.TWISTIE |*/ Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
		hookComponentsListeners();
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Acquirer Response");
		section.setDescription("Acquirer response validations");
		
		Composite composite = new Composite(section, SWT.NULL);
		GridLayout layout = new GridLayout(1,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setBackground(section.getBackground());
		
		Label lbl;
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Validations");
		lbl.setToolTipText("These validations will be executed on the acquirer response message");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(composite.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		validationViewer = new ValidationViewer(composite, this, toolkit, SWT.NONE);
		validationViewer.setDefault("Acq Response Code Approval", "00", "msg.getValue(39)");
		
		if (editor.getUseCase() != null && editor.getUseCase().getAcquirer() != null && editor.getUseCase().getAcquirer().getResponse() != null) {
			validationViewer.setValidations(editor.getModifier().getAcqResVals());
		}
		
		section.setClient(composite);
	}
	protected void expansionStateChanging(boolean expanding) {
		super.expansionStateChanging(expanding);
	}
	protected void expansionStateChanged(boolean expanded) {
		super.expansionStateChanged(expanded);
	}
	private void hookComponentsListeners() {
	}
	@Override
	public UseCaseEditor getEditor() {
		return editor;
	}
	@Override
	public FormToolkit getToolkit() {
		return editor.getToolkit();
	}
	public void update() {
		validationViewer.setValidations(editor.getModifier().getAcqResVals());
	}
	@Override
	public void onAddValidation(Validation v) {
		SectionAcquirerResponse.this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}

	@Override
	public void onEditValidation(Validation v) {
		SectionAcquirerResponse.this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}

	@Override
	public void onDeleteValidation(Validation v) {
		SectionAcquirerResponse.this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}

	@Override
	public void onSwiftValidation(int i, int j) {
		SectionAcquirerResponse.this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		editor.getModifier().modifyAcqResVals(validationViewer.getValidations());
	}
	public void setEnabled(boolean enabled) {
		validationViewer.setEnabled(enabled);		
	}
	public void clean() {
		validationViewer.deleteAll();
	}
}
