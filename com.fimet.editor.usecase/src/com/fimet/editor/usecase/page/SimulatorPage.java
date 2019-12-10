package com.fimet.editor.usecase.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.section.SectionIssuerResponse;

public class SimulatorPage extends GuiPage {
	private UseCaseEditor editor;
	private SectionIssuerResponse sectionIssuerResponse;
	public SimulatorPage(UseCaseEditor editor, String id, String title) {
		super(editor, id, title, "Simulator");
		this.editor = editor;
	}
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout(2,true));
		body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite left = toolkit.createComposite(body);
		left.setLayout(new GridLayout(1,true));
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite right = toolkit.createComposite(body);
		right.setLayout(new GridLayout(1,true));
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		// Sections
        managedForm.addPart(sectionIssuerResponse = new SectionIssuerResponse(editor, left));
	}
	public SectionIssuerResponse getSectionIssuerResponse() {
		return sectionIssuerResponse;
	}
	public void update() {
		if (sectionIssuerResponse != null)
			sectionIssuerResponse.update();
	}
}
