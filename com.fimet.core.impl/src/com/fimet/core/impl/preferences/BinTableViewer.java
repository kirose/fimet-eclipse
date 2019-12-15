package com.fimet.core.impl.preferences;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.fimet.core.entity.sqlite.FieldFormat;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class BinTableViewer extends TableViewer {

	private List<FieldFormat> fieldFormats;

	public BinTableViewer(Composite parent, int style) {
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
        col.getColumn().setWidth(60);
        col.getColumn().setText("IdField");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof FieldFormat) {
            		FieldFormat ff = (FieldFormat)element;
            		return ff.getIdField().substring(ff.getIdField().indexOf('.')+1,ff.getIdField().length());
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(100);
        col.getColumn().setText("Name");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof FieldFormat) {
            		return ((FieldFormat)element).getName();
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(50);
        col.getColumn().setText("Type");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof FieldFormat) {
            		return ((FieldFormat)element).getType();
            	}
                return super.getText(element);
            }
        });

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(50);
        col.getColumn().setText("Length");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof FieldFormat) {
            		return ((FieldFormat)element).getLength()+"";
            	}
                return super.getText(element);
            }
        });

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(50);
        col.getColumn().setText("Encode");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof FieldFormat) {
            		return ((FieldFormat)element).getIdConverterValue()+"";
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(250);
        col.getColumn().setText("Parser");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof FieldFormat) {
            		return ((FieldFormat)element).getClassParser();
            	}
                return super.getText(element);
            }
        });

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(150);
        col.getColumn().setText("Childs");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof FieldFormat) {
            		return ((FieldFormat)element).getChilds();
            	}
                return super.getText(element);
            }
        });

        
	}
	public void setFieldFormat(List<FieldFormat> ff) {
		fieldFormats = ff;
		setInput(ff);
	}
	public void addFieldFormat(FieldFormat fieldFormat) {
		fieldFormats.add(fieldFormat);
		this.add(fieldFormat);
	}
}
