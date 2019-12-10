package com.fimet.editor.usecase.section;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.impl.swt.VText;
import com.fimet.core.usecase.Authorization;
import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.page.AuthorizationPage;
import com.fimet.editor.usecase.section.swt.mgrfields.ManagerFieldsViewer;

public class SectionAcquirerAuthorizationManageFields extends SectionPart implements ISectionEditor {

	private UseCaseEditor editor;
	private AuthorizationPage authorizationPage;
	private VText txtMti;
	private VText txtHeader;
	private ManagerFieldsViewer managerFieldsViewer;
	private Button btnUseDefaults;
	private Button btnCreateAuthorization;
	private Button btnUseMessage;
	
	public SectionAcquirerAuthorizationManageFields(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | /*ExpandableComposite.TWISTIE |*/ Section.DESCRIPTION);
		this.editor = editor;
		this.authorizationPage = editor.getAuthorizationPage();
		createPart(getSection(), editor.getToolkit());
		hookComponentsListeners();
		enableAuthorizationComponents(false);
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Manage Fields");
		section.setDescription("An authorization will be send before the original message");
		
		Composite composite = new Composite(section, SWT.NONE);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setBackground(section.getBackground());
		
		Label lbl;
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Create authorization");
		lbl.setToolTipText("Indicate if will generate a authorization previus to the message.");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		btnCreateAuthorization = new Button(composite, SWT.CHECK);
		btnCreateAuthorization.setSelection(true);
		btnCreateAuthorization.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCreateAuthorization.setBackground(section.getParent().getBackground());
		btnCreateAuthorization.setSelection(false);
		
		new Label(composite,SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Use defaults");
		lbl.setToolTipText("MessageFields 38 and 39 will be replaced to the original message, ahotorization copy the original message and remove the fields 15, 38, 39 and 90 and assign an mti");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		btnUseDefaults = new Button(composite, SWT.CHECK);
		btnUseDefaults.setSelection(true);
		btnUseDefaults.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnUseDefaults.setBackground(section.getParent().getBackground());
		btnUseDefaults.setSelection(true);
		
		new Label(composite,SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Use message");
		lbl.setToolTipText("The authorization will be take from a message");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		btnUseMessage = new Button(composite, SWT.CHECK);
		btnUseMessage.setSelection(true);
		btnUseMessage.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnUseMessage.setBackground(section.getParent().getBackground());
		btnUseMessage.setSelection(false);
		
		new Label(composite,SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("MTI");
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		
		txtMti = new VText(composite, SWT.BORDER);
		txtMti.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		txtMti.valid();

		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Header");
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		
		txtHeader = new VText(composite, SWT.BORDER);
		txtHeader.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		txtHeader.valid();		
		
		Composite cIEFields = new Composite(composite, SWT.NONE);
		cIEFields.setBackground(composite.getBackground());
		GridLayout layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cIEFields.setLayout(layout);
		cIEFields.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,4,1));
		
		managerFieldsViewer = new ManagerFieldsViewer(this, cIEFields, toolkit, true, SWT.NONE);
		
		section.setClient(composite);
		
	}
	private void enableAuthorizationComponents(boolean enabled) {
		btnUseDefaults.setEnabled(enabled);
		btnUseMessage.setEnabled(enabled);
		enableDefaultComponents(enabled);

	}
	private void enableDefaultComponents(boolean enabled) {
		managerFieldsViewer.setEnabled(enabled);
		txtMti.setEnabled(enabled);
		txtHeader.setEnabled(enabled);
		authorizationPage.getSectionAcquirerAuthorizationMessage().setEnabled(enabled);
	}
	private void enableMessageComponents(boolean enabled) {
		managerFieldsViewer.setEnabled(!enabled);
		managerFieldsViewer.setEnabledReplaceFields(true);
		txtMti.setEnabled(!enabled);
		txtHeader.setEnabled(!enabled);
		authorizationPage.getSectionAcquirerAuthorizationMessage().setEnabled(enabled);
	}
	private void hookComponentsListeners() {
		btnCreateAuthorization.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnCreateAuthorization.getSelection()) {
					btnUseDefaults.setEnabled(true);
					if (!btnUseDefaults.getSelection()) {
						btnUseMessage.setEnabled(true);
						enableMessageComponents(btnUseMessage.getSelection());
					}
				} else {
					enableAuthorizationComponents(false);
				}
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionAcquirerAuthorizationManageFields.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnUseDefaults.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnUseDefaults.getSelection()) {
					enableDefaultComponents(false);
				} else {
					btnUseMessage.setEnabled(true);
					enableMessageComponents(btnUseMessage.getSelection());
				}
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionAcquirerAuthorizationManageFields.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnUseMessage.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableMessageComponents(btnUseMessage.getSelection());
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionAcquirerAuthorizationManageFields.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		txtMti.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionAcquirerAuthorizationManageFields.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
		});
		txtHeader.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionAcquirerAuthorizationManageFields.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
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
		Authorization aut = editor.getModifier().getAcqReqAut();
		if (aut != null) {
			btnCreateAuthorization.setSelection(true);
			btnUseDefaults.setEnabled(true);
			if (aut.getMti() == null && 
				aut.getHeader() == null && 
				aut.getExcludeFields() == null && 
				aut.getIncludeFields() == null && 
				aut.getReplaceFields() == null && 
				aut.getMessage() == null) 
			{
				btnUseDefaults.setSelection(true);
				btnUseMessage.setEnabled(false);
				enableDefaultComponents(false);
				cleanAllContents();
				authorizationPage.getSectionAcquirerAuthorizationMessage().cleanAllContents();
			} else {
				btnUseDefaults.setSelection(false);
				btnUseMessage.setEnabled(true);
				if (aut.getMessage() != null) {
					btnUseMessage.setSelection(true);
					enableMessageComponents(true);
					txtMti.setText("");
					txtHeader.setText("");
					//messageFieldsViewer.setFields(msg !=null ? msg.getFields() : null);				
				} else {
					btnUseMessage.setSelection(false);
					enableMessageComponents(false);
					txtMti.setText(StringUtils.escapeNull(editor.getModifier().getAcqReqAutMti()));
					txtHeader.setText(StringUtils.escapeNull(editor.getModifier().getAcqReqAutHeader()));					
				}
				managerFieldsViewer.update();
			}
		} else {
			btnCreateAuthorization.setSelection(false);
			enableAuthorizationComponents(false);
			cleanAllContents();
			authorizationPage.getSectionAcquirerAuthorizationMessage().cleanAllContents();
		}
	}
	private void cleanAllContents() {
		txtMti.setText("");
		txtHeader.setText("");
		managerFieldsViewer.setIncludeFields(null);
		managerFieldsViewer.setExcludeFields(null);
		managerFieldsViewer.setReplaceFields(null);
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		if (btnCreateAuthorization.getSelection()) {
			if (btnUseDefaults.getSelection()) {
				editor.getModifier().modifyAcqReqAut(new Authorization());
			} else {
				String mti = txtMti.getText();
				String header = txtHeader.getText();
				if (btnUseMessage.getSelection()) {
					editor.getModifier().modifyAcqReqAutMti(null);
					editor.getModifier().modifyAcqReqAutHeader(null);
					editor.getModifier().modifyAcqReqAutIncFlds(null);
					editor.getModifier().modifyAcqReqAutExcFlds(null);
					editor.getModifier().modifyAcqReqAutRepFlds(managerFieldsViewer.getReplaceFields());
				} else {
					editor.getModifier().modifyAcqReqAutMti("".equals(mti) ? null : mti);
					editor.getModifier().modifyAcqReqAutHeader("".equals(header) ? null : header);
					editor.getModifier().modifyAcqReqAutIncFlds(managerFieldsViewer.getIncludeFields());
					editor.getModifier().modifyAcqReqAutExcFlds(managerFieldsViewer.getExcludeFields());
					editor.getModifier().modifyAcqReqAutRepFlds(managerFieldsViewer.getReplaceFields());
				}
			}
		} else {
			editor.getModifier().modifyAcqReqAut(null);
		}
	}
	public void setEnabled(boolean enabled) {
		
	}
	public boolean useMessage() {
		return btnCreateAuthorization.getSelection() && !btnUseDefaults.getSelection() && btnUseMessage.getSelection();
	}
}
