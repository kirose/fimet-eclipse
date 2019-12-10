package com.fimet.editor.stress.page;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.editor.stress.StressEditor;
import com.fimet.editor.stress.section.SectionAcquirers;
import com.fimet.editor.stress.section.SectionExecution;

public class OverviewPage extends GuiPage {

	private SectionAcquirers sectionAcquirers;
	private SectionExecution sectionExecution;
	
	public OverviewPage(StressEditor editor, String id, String title) {
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
        managedForm.addPart(sectionAcquirers = new SectionAcquirers(editor, left));
        managedForm.addPart(sectionExecution = new SectionExecution(editor, right));

	}
	public SectionExecution getSectionExecution() {
		return sectionExecution;
	}
	public SectionAcquirers getSectionAcquirers() {
		return sectionAcquirers;
	}
	public void update() {
		if (sectionAcquirers != null)
			sectionAcquirers.update();
		if (sectionExecution != null) {
			sectionExecution.update();
		}
	}
}
