package com.fimet.editor.usecase.section;

import java.util.List;

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

import com.fimet.core.impl.swt.VText;
import com.fimet.core.usecase.Field;
import com.fimet.editor.usecase.UseCaseEditor;
import com.fimet.editor.usecase.section.swt.mgrfields.ManagerFieldsViewer;

public class SectionIssuerResponse extends SectionPart implements ISectionEditor {

	private UseCaseEditor editor;
	private Button btnTimeout;
	private VText txtDelay;
	private ManagerFieldsViewer managerFieldsViewer;
	
	public SectionIssuerResponse(UseCaseEditor editor, Composite parent) {
		super(parent, editor.getToolkit(), ExpandableComposite.TITLE_BAR | /*ExpandableComposite.TWISTIE |*/ Section.DESCRIPTION);
		this.editor = editor;
		createPart(getSection(), editor.getToolkit());
		
	}
	private void createPart(Section section, FormToolkit toolkit) {
		
		section.setLayout(new GridLayout(1, true));
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		section.setText("Issuer Response (Simulator)");
		section.setDescription("The issuer response configuration");

		Composite composite = new Composite(section, SWT.NULL);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		composite.setBackground(section.getBackground());

		Label lbl;
		GridLayout layout;
		
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Timeout");
		lbl.setToolTipText("If timeout is active and delay is not specified the simulator will not respond");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));

		btnTimeout = new Button(composite, SWT.CHECK);
		btnTimeout.setSelection(false);
		btnTimeout.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnTimeout.setBackground(section.getParent().getBackground());
		
		new Label(composite,SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Delay");
		lbl.setToolTipText("The simulator will wait for respond the number of seconds indicate in delay");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(section.getParent().getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		txtDelay = new VText(composite, SWT.BORDER);
		txtDelay.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		txtDelay.setEnabled(false);
		txtDelay.setValidator((String v)->{return (v == null || v.length() == 0) || (v != null && v.length() > 0 && v.matches("^[0-9]+$"));});
		txtDelay.valid();

		Composite cIncExcFields = new Composite(composite, SWT.NONE);
		cIncExcFields.setBackground(composite.getBackground());
		layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cIncExcFields.setLayout(layout);
		cIncExcFields.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,4,1));
		
		managerFieldsViewer = new ManagerFieldsViewer(this, cIncExcFields, toolkit, SWT.NONE);
		
		section.setClient(composite);
		hookComponentsListeners();
	}
	private void hookComponentsListeners() {
		btnTimeout.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtDelay.setEnabled(btnTimeout.getSelection());
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionIssuerResponse.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		txtDelay.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!editor.getModifier().isApplingSourceChanges()) {
					SectionIssuerResponse.this.markDirty();
					editor.getModifier().markAsDirtyPagesGui();
				}
			}
		});
	}
	public Integer getTimeout() {
		Integer timeout = null;
		if (btnTimeout.getSelection() && txtDelay.getText().length() == 0) {
			timeout = -1;
		} else {
			if (txtDelay.isValid()) {
				String offset = txtDelay.getText();
				timeout = offset != null && offset.length() > 0 ? Integer.valueOf(txtDelay.getText()) : null;
			}
		}
		return timeout;
	}
	protected void expansionStateChanged(boolean expanded) {
		super.expansionStateChanged(expanded);
		//getSection().getParent().
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
		Integer timeout = editor.getModifier().getIssRespTimeout();
		if (timeout == null || timeout == 0) {
			btnTimeout.setSelection(false);
			txtDelay.setEnabled(false);
			txtDelay.setText("");
		} else {
			btnTimeout.setSelection(true);
			txtDelay.setEnabled(true);
			if (timeout < 0) {
				txtDelay.setText("");				
			} else {
				txtDelay.setText(""+timeout);
			}
		}
		managerFieldsViewer.setExcludeFields(editor.getModifier().getIssRespExcflds());
		managerFieldsViewer.setIncludeFields(editor.getModifier().getIssRespIncFlds());
	}
	@Override
	public void commit(boolean onSave) {
		super.commit(onSave);
		if (btnTimeout.getSelection()) {
			Integer timeout = getTimeout();
			editor.getModifier().modifyIssRespTimeout(timeout);
		} else {
			editor.getModifier().modifyIssRespTimeout(null);
		}
		List<Field> includeFields = managerFieldsViewer.getIncludeFields();
		List<String> excludeFields = managerFieldsViewer.getExcludeFields();
		editor.getModifier().modifyIssRespIncFlds(includeFields);
		editor.getModifier().modifyIssRespExcFlds(excludeFields);
	}
}
