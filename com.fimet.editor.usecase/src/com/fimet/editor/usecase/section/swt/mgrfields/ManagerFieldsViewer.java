package com.fimet.editor.usecase.section.swt.mgrfields;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.core.impl.swt.VText;
import com.fimet.core.usecase.Field;
import com.fimet.editor.usecase.section.ISectionEditor;

public class ManagerFieldsViewer extends Composite {

	private IncFieldsTableViewer table;
	private IncFieldsButtons buttons;
	
	private FormToolkit toolkit;
	private ISectionEditor section;
	private VText txtExcludeFields;
	private VText txtReplaceFields;
	
	public ManagerFieldsViewer(ISectionEditor section, Composite parent, FormToolkit toolkit, boolean showReplaceField, int style) {
		super(parent, style);
		this.section = section;
		this.toolkit = toolkit;
		GridLayout layout = new GridLayout(4,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		setBackground(parent.getBackground());
		createContents(this, toolkit, showReplaceField);
		hookComponentsListeners();
	}
	
	private void hookComponentsListeners() {
	}

	public ManagerFieldsViewer(ISectionEditor section, Composite parent, FormToolkit toolkit, int style) {
		this(section, parent, toolkit, false, style);
	}
	private void createContents(Composite parent, FormToolkit toolkit, boolean showReplaceField) {
		
		GridData gridData;
		GridLayout layout;

		Label lbl;

		if (showReplaceField) {
			lbl = new Label(parent,SWT.NONE);
			lbl.setText("Replace fields");
			lbl.setToolTipText("These fields will be replaced in the message from the message authorization, use idField separate by comma (Ex. 38,63.C4,55.9F34).");
			lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			lbl.setBackground(parent.getBackground());
			lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
			
			txtReplaceFields = new VText(parent, SWT.BORDER);
			txtReplaceFields.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
			txtReplaceFields.valid();
		}
		
		lbl = new Label(parent,SWT.NONE);
		lbl.setText("Exclude fields");
		lbl.setToolTipText("These fields will be exclude in the message, use idField separate by comma (Ex. 38,63.C4,55.9F34).");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lbl.setBackground(parent.getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		txtExcludeFields = new VText(parent, SWT.BORDER);
		txtExcludeFields.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		txtExcludeFields.setEditable(true);
		txtExcludeFields.valid();
		
		lbl = new Label(parent,SWT.NONE);
		lbl.setText("Include fields");
		lbl.setToolTipText("These fields will be included in the message");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 4, 1));
		lbl.setBackground(parent.getBackground());
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		Composite cInclude = new Composite(parent, SWT.NONE);
		cInclude.setBackground(this.getBackground());
		layout = new GridLayout(2,false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cInclude.setLayout(layout);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false,4,1);
		gridData.heightHint = 170;
		cInclude.setLayoutData(gridData);
		
		Composite cTable = new Composite(cInclude, SWT.NONE);
		cTable.setBackground(this.getBackground());
		layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cTable.setLayout(layout);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false,1,1);
		gridData.widthHint = 350;
		gridData.heightHint = 170;
		cTable.setLayoutData(gridData);
		
		Composite cButtons = new Composite(cInclude, SWT.NONE);
		cButtons.setBackground(this.getBackground());
		layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cButtons.setLayout(layout);
		cButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true,1,1));
		
		table = new IncFieldsTableViewer(this, cTable, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		buttons = new IncFieldsButtons(this, cButtons, SWT.NONE);

		table.createContents();
		buttons.createContents(toolkit);
		
		hookListeners();
		
	}
	private void hookListeners() {
		if (txtReplaceFields != null) {
			txtReplaceFields.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					if (!section.getEditor().getModifier().isApplingSourceChanges()) {
						ManagerFieldsViewer.this.section.markDirty();
						ManagerFieldsViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
					}
				}
			});
		}
		txtExcludeFields.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				if (!section.getEditor().getModifier().isApplingSourceChanges()) {
					ManagerFieldsViewer.this.section.markDirty();
					ManagerFieldsViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
				}
			}
		});
	}
	

	public void onAdd() {
		IncFieldDialog dialog = new IncFieldDialog(getShell(),getToolkit(), SWT.NONE);
		dialog.open();
		Field field = dialog.getField();
		if (field != null) {
			table.addField(field);
			table.refresh();
			table.getTable().deselectAll();
			table.getTable().select(table.getIncludeFields().indexOf(field));
			ManagerFieldsViewer.this.section.markDirty();
			ManagerFieldsViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
		}
	}
	public void onEdit() {
		if (table.getSelected() == null) {
			return;
		}
		IncFieldDialog dialog = new IncFieldDialog(table.getSelected(), getShell(),getToolkit(), SWT.NONE);
		dialog.open();
		Field field = dialog.getField();
		if (field != null) {
			table.refresh();
			ManagerFieldsViewer.this.section.markDirty();
			ManagerFieldsViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
		}
	}
	public void onDelete() {
		if (table.getSelected() == null) {
			return;
		}
		Field field = table.getSelected();
		int i = table.getIncludeFields().indexOf(field);
		table.getIncludeFields().remove(field);
		if (i-1 >=0) {
			table.getTable().select(i-1);
		}
		table.refresh();
		ManagerFieldsViewer.this.section.markDirty();
		ManagerFieldsViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
	}

	public FormToolkit getToolkit() {
		return toolkit;
	}
	public ISectionEditor getSection() {
		return section;
	}
	public IncFieldsTableViewer getTable() {
		return table;
	}
	public IncFieldsButtons getButtons() {
		return buttons;
	}
	public void setExcludeFields(List<String> fields) {
		if (fields != null && !fields.isEmpty()) {
			StringBuilder s = new StringBuilder();
			for (String f : fields) {
				s.append(f).append(',');
			}
			s.delete(s.length()-1, s.length());
			txtExcludeFields.setText(s.toString());
		} else {
			txtExcludeFields.setText("");
		}
	}
	public void setReplaceFields(List<String> fields) {
		if (txtReplaceFields != null) {
			if (fields != null && !fields.isEmpty()) {
				StringBuilder s = new StringBuilder();
				for (String f : fields) {
					s.append(f).append(',');
				}
				s.delete(s.length()-1, s.length());
				txtReplaceFields.setText(s.toString());
			} else {
				txtReplaceFields.setText("");
			}
		}
	}
	public void setIncludeFields(List<Field> fields) {
		table.setIncludeFields(fields);
	}
	public List<String> getExcludeFields() {
		if (txtExcludeFields.getText() == null || txtExcludeFields.getText().trim().length() == 0) {
			return null;
		}
		return Arrays.asList(txtExcludeFields.getText().split(","));
	}
	public List<String> getReplaceFields() {
		if (txtReplaceFields == null || txtReplaceFields.getText() == null || txtReplaceFields.getText().trim().length() == 0) {
			return null;
		}
		return Arrays.asList(txtReplaceFields.getText().split(","));
	}
	public List<Field> getIncludeFields() {
		return table.getIncludeFields();
	}
	public void update() {
		setReplaceFields(section.getEditor().getModifier().getAcqReqAutRepFlds());
		setIncludeFields(section.getEditor().getModifier().getAcqReqAutIncFlds());
		setExcludeFields(section.getEditor().getModifier().getAcqReqAutExcFlds());
	}
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		table.getTable().setEnabled(enabled);
		buttons.setEnabled(enabled);
		txtExcludeFields.setEnabled(enabled);
		if (txtReplaceFields != null) {
			txtReplaceFields.setEnabled(enabled);
		}
	}
	public void setEnabledReplaceFields(boolean enabled) {
		if (txtReplaceFields != null) { 
			txtReplaceFields.setEnabled(enabled);
		}
	}
}
