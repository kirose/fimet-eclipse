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

public class SectionIssuerRequest extends SectionPart implements ISectionEditor, IValidationListener {

	private ValidationViewer validationViewer;
	private UseCaseEditor editor;
	
	public SectionIssuerRequest(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | /*ExpandableComposite.TWISTIE |*/ Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
		hookComponentsListeners();
	}

	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Issuer Request");
		section.setDescription("The issuer request validations");

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
		lbl.setToolTipText("These validations will be executed on the issuer request message");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		validationViewer = new ValidationViewer(composite, this, toolkit, SWT.NONE);
		validationViewer.setDefault("Iss Request Tran Type", "00", "msg.getValue(\"3.1\")");
		//validationViewer.setValidations(editor.getModifier().getIssReqVals());
		
		section.setClient(composite);
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
		validationViewer.setValidations(editor.getModifier().getIssReqVals());
	}

	@Override
	public void onAddValidation(Validation v) {
		this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}

	@Override
	public void onEditValidation(Validation v) {
		this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}

	@Override
	public void onDeleteValidation(Validation v) {
		this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}

	@Override
	public void onSwiftValidation(int i, int j) {
		this.markDirty();
		editor.getModifier().markAsDirtyPagesGui();
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		editor.getModifier().modifyIssReqVals(validationViewer.getValidations());
	}

	public void setEnabled(boolean enabled) {
		validationViewer.setEnabled(enabled);
	}

	public void clean() {
		validationViewer.deleteAll();
	}	
}
