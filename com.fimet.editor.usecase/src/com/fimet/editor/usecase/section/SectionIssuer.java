package com.fimet.editor.usecase.section;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.fimet.core.impl.swt.IssuerCombo;
import com.fimet.editor.usecase.UseCaseEditor;

public class SectionIssuer extends SectionPart implements ISectionEditor {

	private UseCaseEditor editor;
	private IssuerCombo cvIssuer;
	private Button btnConnect;
	
	public SectionIssuer(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR |  Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
		hookComponentsListeners();
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Issuer*");
		section.setDescription("The issuer");

		Composite composite = new Composite(section, SWT.NULL);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setBackground(section.getBackground());

		Label lbl;
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Issuer");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		cvIssuer = new IssuerCombo(composite);
		cvIssuer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Connect?");
		lbl.setToolTipText("Indicate if the issuer will be connect.");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		btnConnect = new Button(composite, SWT.CHECK);
		btnConnect.setSelection(true);
		btnConnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnConnect.setBackground(section.getParent().getBackground());
		
		new Label(composite,SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		
		section.setClient(composite);
	}
	private void hookComponentsListeners() {
		cvIssuer.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionIssuer.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnConnect.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionIssuer.this.markDirty();
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
		cvIssuer.select(editor.getModifier().getIssSrcConn());
		Boolean connect = editor.getModifier().getIssConnect();
		btnConnect.setSelection(connect == null ? Boolean.TRUE : connect);
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		editor.getModifier().modifyIssSrcConn(cvIssuer.getSelected());
		editor.getModifier().modifyIssConnect(btnConnect.getSelection());
	}
}
