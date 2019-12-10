package com.fimet.editor.stress.section;

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

import com.fimet.editor.stress.StressEditor;
import com.fimet.editor.stress.section.acquirers.AcquirersViewer;

public class SectionAcquirers extends SectionPart implements ISectionEditor {

	private StressEditor editor;
	private AcquirersViewer acquirersViewer;
	
	public SectionAcquirers(StressEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
		hookCompositeListeners();
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("IssuerRequest Message*");
		section.setDescription("The acquirer request message");
		
		Composite composite = new Composite(section, SWT.NONE);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setBackground(section.getBackground());

		Label lbl;
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Files");
		lbl.setToolTipText("The Stress files");
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		lbl.setBackground(section.getParent().getBackground());
		
		acquirersViewer = new AcquirersViewer(this, composite, toolkit,SWT.NONE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		acquirersViewer.setLayout(layout);
		acquirersViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		acquirersViewer.setBackground(section.getBackground());
		
		
		section.setClient(composite);
	}
	private void hookCompositeListeners() {
	}
	public StressEditor getEditor() {
		return editor;
	}
	public FormToolkit getToolkit() {
		return editor.getToolkit();
	}
	public void update() {
		//txtMti.setText(StringUtils.escapeNull(editor.getModifier().getAcqReqMsgMti()));
		//txtHeader.setText(StringUtils.escapeNull(editor.getModifier().getAcqReqMsgHeader()));
		//acquirersViewer.setMessage(editor.getModifier().getAcqReqMsg());
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		//editor.getModifier().modifyAcqReqMsgMti(txtMti.getText());
		//editor.getModifier().modifyAcqReqMsgHeader(txtHeader.getText());
		//editor.getModifier().modifyAcqReqMsgFlds(acquirersViewer.getFields());
	}
}
