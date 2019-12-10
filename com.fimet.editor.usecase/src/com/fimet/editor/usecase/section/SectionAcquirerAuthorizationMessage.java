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

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.swt.msg.IMessageMonitor;
import com.fimet.core.impl.swt.msg.MessageViewer;
import com.fimet.core.net.ISocket;
import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.page.AuthorizationPage;

public class SectionAcquirerAuthorizationMessage extends SectionPart implements IMessageMonitor {

	private UseCaseEditor editor;
	private MessageViewer messageFieldsViewer;
	private AuthorizationPage authorizationPage;
	
	public SectionAcquirerAuthorizationMessage(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | /*ExpandableComposite.TWISTIE |*/ Section.DESCRIPTION);
		this.editor = editor;
		this.authorizationPage = editor.getAuthorizationPage();
		createPart(getSection(), editor.getToolkit());
		setEnabled(false);
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		section.setText("Authorization Message");
		section.setDescription("Use an specify message as Authorization");
		
		Composite composite = new Composite(section, SWT.NONE);
		composite.setLayout(new GridLayout(1,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBackground(section.getBackground());
		

		messageFieldsViewer = new MessageViewer(this, true, composite, SWT.NONE);
		messageFieldsViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		section.setClient(composite);
		
	}
	public UseCaseEditor getEditor() {
		return editor;
	}
	public FormToolkit getToolkit() {
		return editor.getToolkit();
	}
	public void update() {
		Message msg = editor.getModifier().getAcqReqAutMsg();
		if (msg != null) {
			messageFieldsViewer.setMessage(msg);
			editor.getModifier().modifyAcqReqAutMsgParser(editor.getModifier().getOverviewAcqSrcConn().getParser().toString());
			editor.getModifier().modifyAcqReqAutMsgAdapter(editor.getModifier().getOverviewAcqSrcConn().getAdapter().toString());
		} else {
			cleanAllContents();
		}
	}
	public void cleanAllContents() {
		messageFieldsViewer.setMessage(null);
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		if (authorizationPage.getSectionAcquirerAuthorizationManageFields().useMessage()) {
			Message message = messageFieldsViewer.getMessage();
			editor.getModifier().modifyAcqReqAutMsg(message);
			editor.getModifier().modifyAcqReqAutMsgParser(editor.getModifier().getOverviewAcqSrcConn().getParser().toString());
			editor.getModifier().modifyAcqReqAutMsgAdapter(editor.getModifier().getOverviewAcqSrcConn().getAdapter().toString());
		} else {
			editor.getModifier().modifyAcqReqAutMsg(null);
		}
	}
	public void setEnabled(boolean enabled) {
		messageFieldsViewer.setEnabled(enabled);
	}
	@Override
	public void onModifyMessage() {
		if (!editor.getModifier().isApplingSourceChanges()) {
			this.markDirty();
			editor.getModifier().markAsDirtyPagesGui();
		}
	}
	@Override
	public ISocket getConnection() {
		return getEditor().getUseCase() != null && getEditor().getUseCase().getAcquirer() != null ? getEditor().getUseCase().getAcquirer().getConnection() : null;
	}
	@Override
	public ISelectionProvider getSelectionProvider() {
		return editor.getSelectionProvider();
	}
}
