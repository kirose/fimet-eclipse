package com.fimet.editor.usecase.section;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.net.ISocket;
import com.fimet.editor.usecase.UseCaseEditor;

public class SectionAcquirer extends SectionPart implements ISectionEditor {

	private UseCaseEditor editor;
	private AcquirerCombo cvAcquirer;
	
	public SectionAcquirer(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
		hookComponentsListeners();
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Acquirer*");
		section.setDescription("The acquirer");

		Composite composite = new Composite(section, SWT.NULL);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setBackground(section.getBackground());
		
		Label lbl;
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Acquirer");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		cvAcquirer = new AcquirerCombo(composite);
		cvAcquirer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));

		section.setClient(composite);
	}
	public ISocket getConnection() {
		return cvAcquirer.getSelected(); 
	}
	private void hookComponentsListeners() {
		cvAcquirer.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionAcquirer.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
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
		cvAcquirer.select(editor.getModifier().getAcqSrcConn());		
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		editor.getModifier().modifyAcqSrcConn(cvAcquirer.getSelected());
	}
}
