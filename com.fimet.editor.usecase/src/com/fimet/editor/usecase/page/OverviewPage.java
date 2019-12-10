package com.fimet.editor.usecase.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.section.SectionAcquirer;
import com.fimet.editor.usecase.section.SectionAcquirerMessage;
import com.fimet.editor.usecase.section.SectionExecution;
import com.fimet.editor.usecase.section.SectionIssuer;

public class OverviewPage extends GuiPage {

	private SectionAcquirerMessage sectionAcquirerMessage;
	private SectionAcquirer sectionAcquirer;
	private SectionIssuer sectionIssuer;
	private SectionExecution sectionExecution;
	
	
	public OverviewPage(UseCaseEditor editor, String id, String title) {
		super(editor, id, title, "Overview");
	}
	protected void fillBody(IManagedForm managedForm, FormToolkit toolkit) {
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout(6,true));
		body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Composite left = toolkit.createComposite(body);
		left.setLayout(new GridLayout(1,true));
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		Composite right = toolkit.createComposite(body);
		right.setLayout(new GridLayout(1,true));
		right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		// Sections
        managedForm.addPart(sectionAcquirerMessage = new SectionAcquirerMessage(editor, left));
        managedForm.addPart(sectionAcquirer = new SectionAcquirer(editor, right));
        managedForm.addPart(sectionIssuer = new SectionIssuer(editor, right));
        managedForm.addPart(sectionExecution = new SectionExecution(editor, right));

	}
	public SectionAcquirer getSectionAcquirer() {
		return sectionAcquirer;
	}
	public SectionAcquirerMessage getSectionAcquirerMessage() {
		return sectionAcquirerMessage;
	}
	public SectionIssuer getSectionIssuer() {
		return sectionIssuer;
	}
	public SectionExecution getSectionExecution() {
		return sectionExecution;
	}
	public void update() {
		if (sectionAcquirer != null)
			sectionAcquirer.update();
		if (sectionAcquirerMessage != null)
			sectionAcquirerMessage.update();
		if (sectionIssuer != null)
			sectionIssuer.update();
	}
}
