package com.fimet.editor.usecase.section;

import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.fimet.core.impl.swt.msg.IMessageMonitor;
import com.fimet.core.impl.swt.msg.MessageViewer;
import com.fimet.core.net.ISocket;
import com.fimet.editor.usecase.UseCaseEditor;

public class SectionAcquirerMessage extends SectionPart implements IMessageMonitor {

	private UseCaseEditor editor;
	private MessageViewer messageViewer;
	
	public SectionAcquirerMessage(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		section.setText("Request Message*");
		section.setDescription("The acquirer request message");
		
		Composite composite = new Composite(section, SWT.NONE);
		composite.setLayout(new GridLayout(1,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBackground(section.getBackground());

		messageViewer = new MessageViewer(this, true, composite, SWT.NONE);
		messageViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		section.setClient(composite);
	}
	public UseCaseEditor getEditor() {
		return editor;
	}
	public FormToolkit getToolkit() {
		return editor.getToolkit();
	}
	public void update() {
		messageViewer.setMessage(editor.getModifier().getAcqReqMsg());
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		editor.getModifier().modifyAcqReqMsg(messageViewer.getMessage());
	}
	@Override
	public void onModifyMessage() {
		if (!editor.getModifier().isApplingSourceChanges()) {
			SectionAcquirerMessage.this.markDirty();
			editor.getModifier().markAsDirtyPagesGui();
		}		
	}
	@Override
	public ISocket getConnection() {
		return getEditor().getUseCase() != null && getEditor().getUseCase().getAcquirer() != null ? getEditor().getUseCase().getAcquirer().getConnection() : null;
	}
	@Override
	public ISelectionProvider getSelectionProvider() {
		return editor.getSite().getSelectionProvider();
	}
	@Override
	public void dispose() {
		super.dispose();
		messageViewer.dispose();
	}
}
