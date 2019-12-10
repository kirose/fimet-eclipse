package com.fimet.editor.stress.view.vary;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IWorkbenchActionConstants;

import com.fimet.commons.Images;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldsTable extends TableViewer {
	private FiledsVariationView view;
	private List<String> fields = new ArrayList<>();
	public FieldsTable(FiledsVariationView view, Composite parent, int style) {
		super(parent, style);
		this.view = view;
		createTable();
		createContextMenu();
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
        col.getColumn().setText("MessageFields to vary");
        col.getColumn().setMoveable(true);
        col.setLabelProvider(new ColumnLabelProvider() {
        	//private Color RED = new Color(Display.getDefault(), 255, 0, 0);
            private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
            @Override
            public Image getImage(Object element) {
	            return resourceManager.createImage(Images.CONNECTED_ICON);	
            }
            @Override
            public String getText(Object element) {
                return super.getText(element);
            }
            @Override
            public void dispose() {
                super.dispose();
                resourceManager.dispose();
            }
        });
	}
	public String getSelected(){
		if (getSelection() != null) {
			return (String)((StructuredSelection)getSelection()).getFirstElement();
		}
		return null;
	}
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#IapConnectionTableViewerMenu",null); //$NON-NLS-1$
        contextMenu.setRemoveAllWhenShown(true);
        contextMenu.addMenuListener(new IMenuListener() {
            @Override
            public void menuAboutToShow(IMenuManager mgr) {
                fillContextMenu(mgr);
            }
        });
        Menu menu = contextMenu.createContextMenu(this.getControl());
        this.getControl().setMenu(menu);
    }
    private void fillContextMenu(IMenuManager contextMenu) {
        contextMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
        contextMenu.add(new Action("Delete") {
            @Override
            public void run() {
            	view.onDeleteVariationParameter();
            }
        });

    }
	public boolean contains(String idField) {
		return fields.contains(idField);
	}
	public void addField(String idField) {
		fields.add(idField);
		setInput(fields);
	}
	public void selectField(String idField) {
		getTable().select(fields.indexOf(idField));
	}
	public int removeField(String idField) {
		int index = fields.indexOf(idField);
		if (index != -1) {
			fields.remove(index);
			setInput(fields);
		}
		return index;
	}
}
