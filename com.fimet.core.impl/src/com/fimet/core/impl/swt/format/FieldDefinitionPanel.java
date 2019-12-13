package com.fimet.core.impl.swt.format;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.fimet.commons.Color;
import com.fimet.commons.history.HistoryGroup;
import com.fimet.commons.preference.IPreference;
import com.fimet.core.IFieldFormatManager;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.core.impl.Activator;

public class FieldDefinitionPanel extends Composite implements IFieldController {

	
	private GroupTree treeGroups;
	private FieldTree treeFields;
	private FieldPanel fieldPanel;
	IFieldFormatManager fieldFormatGroupManager = Manager.get(IFieldFormatManager.class);
	IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	HistoryGroup<FieldFormatGroup> historyGroups;
	HistoryGroup<FieldFormat> historyFields;
	public static final int NONE = 0;
	public static final int SHOW_FIELD_DETAILS = 1;
	
	public FieldDefinitionPanel(Composite parent) {
		this(parent, NONE);
	}
	public FieldDefinitionPanel(Composite parent, int panelStyle) {
		super(parent, SWT.NONE);
		historyGroups = new HistoryGroup<FieldFormatGroup>();
		historyFields = new HistoryGroup<FieldFormat>();
		createContents(this, panelStyle);
	}
    protected final Control createContents(Composite container, int stylePanel) {
    	
    	boolean addFieldPanel = (stylePanel & SHOW_FIELD_DETAILS) > 0;
    	
        container.setBackground(container.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
        GridData gd;
        GridLayout layout;
        gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        container.setLayout(layout);
        container.setLayoutData(gd);
		
	    gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        gd.heightHint = 300;
        gd.widthHint = 650;
        layout = new GridLayout(addFieldPanel ? 2 : 3,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        
        SashForm parent = new SashForm(container, SWT.HORIZONTAL);
        parent.setLayout(layout);
        parent.setLayoutData(gd);
        parent.setFont(container.getFont());
        parent.setBackground(Color.WHITE);

        
        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite cmpGroups = new Composite(parent, SWT.NONE);
        cmpGroups.setLayout(layout);
        cmpGroups.setLayoutData(gd);

        gd = new GridData(SWT.FILL, SWT.FILL, true, true,1,1);
        layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite cmpFields = new Composite(parent, SWT.NONE);
        cmpFields.setLayout(layout);
        cmpFields.setLayoutData(gd);
        
        if (addFieldPanel) {
            gd = new GridData(SWT.FILL, SWT.FILL, true, true ,1,1);
            layout = new GridLayout(1,false);
            layout.marginWidth = 0;
            layout.marginHeight = 0;
            Composite cmpDetailsField = new Composite(parent, SWT.NONE);
            cmpDetailsField.setLayout(layout);
            cmpDetailsField.setLayoutData(gd);
            
            fieldPanel = new FieldPanel(cmpDetailsField, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        }
        
        treeGroups = new GroupTree(this, cmpGroups, SWT.SINGLE | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
        treeGroups.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        treeFields = new FieldTree(this, cmpFields, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		
        if (addFieldPanel) {
        	parent.setWeights(new int[] {3,5,3});
        } else {
        	parent.setWeights(new int[] {3,5});
        }
		
		treeGroups.setFieldFormatGroups(fieldFormatGroupManager.getRootGroups());
    	treeGroups.addDoubleClickListener((DoubleClickEvent event)->{
			this.onEditGroup();
		});
    	treeGroups.getTree().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadFieldsIntoTable(treeGroups.getSelectedNode() != null ? treeGroups.getSelectedNode().group : null);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	treeFields.addDoubleClickListener((DoubleClickEvent event)->{
			this.onEditField();
		});
    	if (addFieldPanel) {
	    	treeFields.getTree().addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					fieldPanel.setField(treeFields.getSelected());
				}
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {}
			});
    	}
    	return parent;
    }
    private void loadFieldsIntoTable(FieldFormatGroup group) {
		if (group != null && !group.equals(treeFields.getGroupLoaded())) {
			List<FieldFormat> fields = fieldFormatGroupManager.getFieldsFormat(group.getId());
			if (fields != null && !fields.isEmpty() && !historyFields.isEmpty(group.getId())) {
				List<FieldFormat> inserts = historyFields.getInserts();
				List<FieldFormat> updates = historyFields.getUpdates();
				List<FieldFormat> deletes = historyFields.getDeletes();
				fields.removeAll(deletes);
				fields.removeAll(updates);
				fields.addAll(updates);
				fields.addAll(inserts);
				Collections.sort(fields, (FieldFormat f1, FieldFormat f2) ->{return f1.getIdOrder().compareTo(f2.getIdOrder());});
			}
			treeFields.setFieldFormats(fields);
			treeFields.setGroupLoaded(group);
		}    	
    }
	public void onNewGroup() {
		GroupDialog dialog = new GroupDialog(treeGroups.getSelected(), null, getShell(), SWT.NONE);
		dialog.open();
		FieldFormatGroup g = dialog.getFieldFormatGroup();
		if (g != null) {
			treeGroups.add(treeGroups.getNode(g.getIdParent()), g);
			historyGroups.insert(g.getId(), g);
			loadFieldsIntoTable(g);
		}
	}
	public void onEditGroup() {
		if (treeGroups.getSelected() != null) {
			GroupDialog dialog = new GroupDialog(treeGroups.getSelectedParent(), treeGroups.getSelected(), getShell(), SWT.NONE);
			dialog.open();
			FieldFormatGroup sm = dialog.getFieldFormatGroup();
			if (sm != null) {
				treeGroups.update(treeGroups.getSelectedParentNode(),  sm);
				historyGroups.update(sm.getId(), sm);
			}
		}
	}
	public void onDeleteGroup() {
		if (treeGroups.getSelected() != null) {
			GroupNode node = treeGroups.getSelectedNode();
			if (askDeleteGroup(node)) {
				deleteGroup(node);
				treeGroups.delete(treeGroups.getSelectedParentNode(), node);
			}
		}
	}
    public void onNewField() {
		FieldDialog dialog = new FieldDialog(this, treeFields.getSelected(), null, getShell(), SWT.NONE);
		dialog.open();
		FieldFormat f = dialog.getFieldFormat();
		if (f != null) {
			FieldNode node = treeFields.getTreeNode(f.getIdField());
			if (node == null) {
				treeFields.add(f);
				historyFields.insert(f.getIdGroup(), f);
				if (f.getIdFieldParent() != null && f.getIdFieldParent().length() > 0) {
					FieldNode parent = treeFields.getTreeNode(f.getIdFieldParent());
					historyFields.update(f.getIdGroup(), parent.field);
				}
			}
		}
    }
    public void onEditField() {
		FieldDialog dialog = new FieldDialog(this, null, treeFields.getSelected(), getShell(), SWT.NONE);
		dialog.open();
		FieldFormat f = dialog.getFieldFormat();
		if (f != null) {
			FieldNode node = treeFields.getTreeNode(f.getIdField());
			if (node != null) {
				node.field = f;
				treeFields.update(node);
				historyFields.update(f.getIdGroup(), f);
			}
		}
	}
    public void onDeleteField() {
		if (treeFields.getSelected() != null) {
			FieldNode node = treeFields.getSelectedNode();
			if (askDeleteField(node)) {
				historyFields.delete(this.getSelectedGroup().getId(), node.getField());
				deleteChildren(node);
				treeFields.delete(treeFields.getSelectedParentNode(), node);
			}
		}
	}	
	private void deleteChildren(FieldNode node) {
		if (node.hasChildren()) {
			for (FieldNode field : node.getChildren()) {
				historyFields.delete(this.getSelectedGroup().getId(), field.getField());
				if (field.hasChildren()) {
					deleteChildren(field);
				}
			}
		}
	}
	private void deleteGroup(GroupNode node) {
		historyGroups.delete(this.getSelectedGroup().getId(), node.getGroup());
		if (node.hasChildren()) {
			for (GroupNode group : node.getChildren()) {
				deleteGroup(group);
			}
		}
	}
	public FieldFormatGroup getSelectedGroup() {
		return treeGroups.getSelected();
	}
	public FieldFormatGroup getSelectedGroupParent() {
		return treeGroups.getSelectedParent();
	}
	public void commit() {
		List<FieldFormat> deletesFields = historyFields.getDeletes();
		for (FieldFormat field : deletesFields) {
			fieldFormatGroupManager.deleteField(field);
		}
		List<FieldFormatGroup> deletesGroups = historyGroups.getDeletes();
		for (FieldFormatGroup field : deletesGroups) {
			fieldFormatGroupManager.deleteGroup(field);
		}
		List<FieldFormatGroup> savesGroups = historyGroups.getSaves();
		for (FieldFormatGroup field : savesGroups) {
			fieldFormatGroupManager.saveGroup(field);
		}
		List<FieldFormat> savesField = historyFields.getSaves();
		for (FieldFormat field : savesField) {
			fieldFormatGroupManager.saveField(field);
		}
		List<Integer> groups = historyGroups.getIds();
		groups.addAll(historyFields.getIds());
		fieldFormatGroupManager.free(groups);
		
		historyGroups = new HistoryGroup<FieldFormatGroup>();
		historyFields = new HistoryGroup<FieldFormat>();
		
	}
	public void cancel() {
		historyGroups = new HistoryGroup<FieldFormatGroup>();
		historyFields = new HistoryGroup<FieldFormat>();		
	}
	private boolean askDeleteField(FieldNode node) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_FIELD_AND_SUBFIELDS);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			getShell(), 
			"Delete Field",
			"Do you want to delete the field \""+node.getField().getName()+(node.hasChildren() ? "\" and his children ("+node.getChildren().length+") recursively?" : "\"?"),
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_FIELD_AND_SUBFIELDS
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_FIELD_AND_SUBFIELDS, delete);
		}
		return delete;
	}
	private boolean askDeleteGroup(GroupNode node) {
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_FIELD_GROUP_AND_SUBGROUPS);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			getShell(), 
			"Delete Field Group and Fields",
			"Do you want to delete the group \""+node.getGroup().getName()+(node.hasChildren() ? "\" and his children ("+node.getChildren().length+") recursively?" : "\"?"),
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_FIELD_GROUP_AND_SUBGROUPS
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_FIELD_GROUP_AND_SUBGROUPS, delete);
		}
		return delete;
	}
	@Override
	public FieldNode getNode(String idField) {
		return treeFields.getTreeNode(idField);
	}
}
