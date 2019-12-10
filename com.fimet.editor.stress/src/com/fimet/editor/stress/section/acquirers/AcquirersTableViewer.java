package com.fimet.editor.stress.section.acquirers;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.fimet.commons.utils.StringUtils;
import com.fimet.core.net.ISocket;
import com.fimet.core.stress.StressAcquirer;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */

public class AcquirersTableViewer extends TableViewer {
	private AcquirersViewer viewer;
	private List<StressAcquirer> stressFiles;
	public AcquirersTableViewer(AcquirersViewer viewer, Composite parent, int style) {
		super(parent, style);
		this.viewer = viewer;
		stressFiles = new java.util.ArrayList<>();
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
        col.getColumn().setWidth(250);
        col.getColumn().setText("Acquirer");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element != null && ((StressAcquirer)element).getConnection() != null) {
            		return StringUtils.escapeNull(((StressAcquirer)element).getConnection().getName());	
            	}
            	return super.getText(element);
        		
            }
        });
        col.setEditingSupport(null);
        
        /*col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(300);
        col.getColumn().setText("File");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
        		return StringUtils.escapeNull(((StressAcquirer)element).getFile());
            }
        });
        col.setEditingSupport(new StressFileEditor());*/
        
        
		setInput(stressFiles);
	}
	public void add(ISocket acquirer,String file) {
		//this.add(new StressAcquirer(acquirer, file));
	}
	public List<StressAcquirer> getStressFiles() {
		return stressFiles;
	}
	/*class StressFileEditor extends EditingSupport {

	    private final CellEditor editor;

	    public StressFileEditor() {
	        super(AcquirersTableViewer.this);
	        this.editor = new TextCellEditor(AcquirersTableViewer.this.getTable());
	    }

	    @Override
	    protected CellEditor getCellEditor(Object element) {
	    	editor.setValue(((StressAcquirer)element).getFile());
	        return editor;
	    }

	    @Override
	    protected boolean canEdit(Object element) {
	        return true;
	    }

	    @Override
	    protected Object getValue(Object element) {
	        return ((StressAcquirer) element).getFile();
	    }

	    @Override
	    protected void setValue(Object element, Object userInputValue) {
	        ((StressAcquirer) element).setFile(String.valueOf(userInputValue));
	        AcquirersTableViewer.this.update(element, null);
	    	AcquirersTableViewer.this.refresh();
	    }
	}*/
	public StressAcquirer getSelected() {
		if (getSelection() != null) {
			IStructuredSelection sel = (IStructuredSelection)getSelection();
			return (StressAcquirer)sel.getFirstElement();
		}
		return null;
	}

	public void setStressFiles(List<StressAcquirer> list) {
		stressFiles = list;
		setInput(stressFiles);
	}

	public void addStressFile(StressAcquirer field) {
		if (stressFiles == null) {
			stressFiles = new java.util.ArrayList<>();
			setInput(stressFiles);
		}
		stressFiles.add(field);
	}
}
