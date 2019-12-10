package com.fimet.editor.stress.section.acquirers;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.core.stress.StressAcquirer;
import com.fimet.editor.stress.section.ISectionEditor;

public class AcquirersViewer extends Composite {

	private AcquirersTableViewer table;
	private AcquirersButtons buttons;
	
	private FormToolkit toolkit;
	private ISectionEditor section;
	
	public AcquirersViewer(ISectionEditor section, Composite parent, FormToolkit toolkit, boolean showReplaceField, int style) {
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

	public AcquirersViewer(ISectionEditor section, Composite parent, FormToolkit toolkit, int style) {
		this(section, parent, toolkit, false, style);
	}
	private void createContents(Composite parent, FormToolkit toolkit, boolean showReplaceField) {
		
		GridData gridData;
		GridLayout layout;

		//Label lbl;
		
		Composite cTable = new Composite(parent, SWT.NONE);
		cTable.setBackground(this.getBackground());
		layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cTable.setLayout(layout);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false,1,1);
		gridData.widthHint = 350;
		gridData.heightHint = 170;
		cTable.setLayoutData(gridData);
		
		Composite cButtons = new Composite(parent, SWT.NONE);
		cButtons.setBackground(this.getBackground());
		layout = new GridLayout(1,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cButtons.setLayout(layout);
		cButtons.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true,1,1));
		
		table = new AcquirersTableViewer(this, cTable, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		buttons = new AcquirersButtons(this, cButtons, SWT.NONE);

		table.createContents();
		buttons.createContents(toolkit);
		
		hookListeners();
		
	}
	private void hookListeners() {
	}
	

	public void onAdd() {
		AcquirersDialog dialog = new AcquirersDialog(getShell(),getToolkit(), SWT.NONE);
		dialog.open();
		StressAcquirer file = dialog.getStressField();
		if (file != null) {
			table.addStressFile(file);
			table.refresh();
			table.getTable().deselectAll();
			table.getTable().select(table.getStressFiles().indexOf(file));
		}
		AcquirersViewer.this.section.markDirty();
		AcquirersViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
	}
	public void onEdit() {
		if (table.getSelected() == null) {
			return;
		}
		AcquirersDialog dialog = new AcquirersDialog(table.getSelected(), getShell(),getToolkit(), SWT.NONE);
		dialog.open();
		StressAcquirer file = dialog.getStressField();
		if (file != null) {
			table.refresh();
		}
		AcquirersViewer.this.section.markDirty();
		AcquirersViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
	}
	public void onDelete() {
		if (table.getSelected() == null) {
			return;
		}
		StressAcquirer file = table.getSelected();
		int i = table.getStressFiles().indexOf(file);
		table.getStressFiles().remove(file);
		if (i-1 >=0) {
			table.getTable().select(i-1);
		}
		table.refresh();
		AcquirersViewer.this.section.markDirty();
		AcquirersViewer.this.section.getEditor().getModifier().markAsDirtyPagesGui();
	}

	public FormToolkit getToolkit() {
		return toolkit;
	}
	public ISectionEditor getSection() {
		return section;
	}
	public AcquirersTableViewer getTable() {
		return table;
	}
	public AcquirersButtons getButtons() {
		return buttons;
	}
	public void setStressFiles(List<StressAcquirer> files) {
		table.setStressFiles(files);
	}
	public List<StressAcquirer> getStressFiles() {
		return table.getStressFiles();
	}
	public void update() {
		/*setReplaceFields(section.getEditor().getModifier().getAcqReqAutRepFlds());
		setStressFiles(section.getEditor().getModifier().getAcqReqAutIncFlds());
		setExcludeFields(section.getEditor().getModifier().getAcqReqAutExcFlds());*/
	}
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		table.getTable().setEnabled(enabled);
		buttons.setEnabled(enabled);
	}
}
