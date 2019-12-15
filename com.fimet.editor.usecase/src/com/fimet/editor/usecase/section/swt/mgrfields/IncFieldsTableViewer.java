package com.fimet.editor.usecase.section.swt.mgrfields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ICellEditorListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.usecase.Field;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

public class IncFieldsTableViewer extends TableViewer {
	private ManagerFieldsViewer viewer;
	private List<Field> includeFields;
	public IncFieldsTableViewer(ManagerFieldsViewer viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
		includeFields = new java.util.ArrayList<>();
	}

	public void createContents() {

        Table table = this.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        //stable.
        this.setContentProvider(ArrayContentProvider.getInstance());
        
        TableViewerColumn col;

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(50);
        col.getColumn().setText("Length");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((Field)element).getValue()).length()+"";
        		
            }
        });
        col.setEditingSupport(null);
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(150);
        col.getColumn().setText("IdField");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return StringUtils.escapeNull(((Field)element).getKey());
            }
        });
        col.setEditingSupport(new FieldKeyEditor());
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(200);
        col.getColumn().setText("Value");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	return StringUtils.escapeNull(((Field)element).getValue());
            }
        });
        col.setEditingSupport(new FieldValueEditor());
        
		setInput(includeFields);
	}
	public void add(String idField,String value) {
		this.add(new Field(idField, value));
	}
	public Map<String,String> getFields() {
		Map<String, String> map = new HashMap<>();
		Item[] items = getTable().getItems();
		Field field;
		for (Item item : items) {
			field = (Field)item.getData();
			map.put(field.getKey(), field.getValue());
		}
		return map;
	}
	class FieldKeyEditor extends EditingSupport {

	    private final CellEditor editor;

	    public FieldKeyEditor() {
	        super(IncFieldsTableViewer.this);
	        this.editor = new TextCellEditor(IncFieldsTableViewer.this.getTable());
	    }

	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	editor.setValue(((Field)element).getKey());
	        return editor;
	    }

	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }

	    @Override
	    protected Object getValue(Object element) {
	        return ((Field) element).getKey();
	    }

	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	        ((Field) element).setKey(String.valueOf(userInputValue));
	        IncFieldsTableViewer.this.update(element, null);
	    	IncFieldsTableViewer.this.refresh();
	    }
	}
	class FieldValueEditor extends EditingSupport {

	    private final CellEditor editor;

	    public FieldValueEditor() {
	        super(IncFieldsTableViewer.this);
	        this.editor = new TextCellEditor(IncFieldsTableViewer.this.getTable());
	        editor.addListener(new ICellEditorListener() {
				@Override
				public void editorValueChanged(boolean oldValidState, boolean newValidState) {
					Field field = IncFieldsTableViewer.this.getSelected();
					field.setValue(""+editor.getValue());
					IncFieldsTableViewer.this.update(field, null);
				}
				@Override
				public void cancelEditor() {}
				@Override
				public void applyEditorValue() {}
			});
	    }

	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	editor.setValue(((Field)element).getValue());
	        return editor;
	    }

	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }

	    @Override
	    protected Object getValue(Object element) {
	        return ((Field) element).getValue();
	    }

	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	        ((Field) element).setValue(String.valueOf(userInputValue));
	        IncFieldsTableViewer.this.update(element, null);
	    }

	}
	public Field getSelected() {
		if (getSelection() != null) {
			IStructuredSelection sel = (IStructuredSelection)getSelection();
			return (Field)sel.getFirstElement();
		}
		return null;
	}

	public void setIncludeFields(List<Field> list) {
		includeFields = list;
		setInput(includeFields);
	}

	public List<Field> getIncludeFields() {

		return includeFields;
	}
	public void addField(Field field) {
		if (includeFields == null) {
			includeFields = new java.util.ArrayList<>();
			setInput(includeFields);
		}
		includeFields.add(field);
	}
}
