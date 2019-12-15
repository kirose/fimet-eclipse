package com.fimet.editor.usecase.wizard.copy;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldsTableViewer extends TableViewer {
	public FieldsTableViewer(Composite parent, int style) {
		super(parent, style);
		createTable();
	}

	private void createTable() {

        Table table = this.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        
        //stable.
        this.setContentProvider(ArrayContentProvider.getInstance());
        
        TableViewerColumn col;
               
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(200);
        col.getColumn().setText("IdField");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return ((Field)element).getKey();
            }
        });
        col.setEditingSupport(new FieldKeyEditor());
        
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(300);
        col.getColumn().setText("Value");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return ((Field)element).getValue();
            }
        });
        col.setEditingSupport(new FieldValueEditor());
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
	        super(FieldsTableViewer.this);
	        this.editor = new TextCellEditor(FieldsTableViewer.this.getTable());
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
	        FieldsTableViewer.this.update(element, null);
	    }

	}
	class FieldValueEditor extends EditingSupport {

	    private final CellEditor editor;

	    public FieldValueEditor() {
	        super(FieldsTableViewer.this);
	        this.editor = new TextCellEditor(FieldsTableViewer.this.getTable());
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
	        FieldsTableViewer.this.update(element, null);
	    }

	}
	public static class Field {
		private String key;
		private String value;
		public Field() {
			super();
		}
		public Field(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
}
