package com.fimet.editor.usecase.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.section.SectionAcquirerResponse;
import com.fimet.editor.usecase.section.SectionClassValidator;
import com.fimet.editor.usecase.section.SectionExtract;
import com.fimet.editor.usecase.section.SectionIssuerRequest;

public class ValidationsPage extends GuiPage {
	private SectionClassValidator sectionClassValidator;
	private SectionAcquirerResponse sectionAcquirerResponse;
	private SectionIssuerRequest sectionIssuerRequest;
	private SectionExtract sectionExtract;

	public ValidationsPage(UseCaseEditor editor, String id, String title) {
		super(editor, id, title, "Validations");
	}
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout(3,true));
		body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite left = toolkit.createComposite(body);
		left.setLayout(new GridLayout(1,true));
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		Composite right = toolkit.createComposite(body);
		right.setLayout(new GridLayout(1,true));
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		// Sections

        managedForm.addPart(sectionAcquirerResponse = new SectionAcquirerResponse(editor, left));
        managedForm.addPart(sectionIssuerRequest = new SectionIssuerRequest(editor, left));
        managedForm.addPart(sectionExtract = new SectionExtract(editor, left));
        managedForm.addPart(sectionClassValidator = new SectionClassValidator(editor, right));
	}
	public SectionAcquirerResponse getSectionAcquirerResponse() {
		return sectionAcquirerResponse;
	}
	public SectionIssuerRequest getSectionIssuerRequest() {
		return sectionIssuerRequest;
	}
	public SectionClassValidator getSectionClassValidator() {
		return sectionClassValidator;
	}
	public SectionExtract getSectionExtract() {
		return sectionExtract;
	}
	public void update() {
		if (sectionClassValidator != null)
			sectionClassValidator.update();
		if (sectionAcquirerResponse != null)
			sectionAcquirerResponse.update();
		if (sectionExtract != null)
			sectionExtract.update();
		if (sectionIssuerRequest != null)
			sectionIssuerRequest.update();
	}
}
