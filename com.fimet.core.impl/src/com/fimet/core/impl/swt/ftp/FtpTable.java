package com.fimet.core.impl.swt.ftp;

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
import org.eclipse.jface.viewers.IStructuredSelection;
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
import com.fimet.core.entity.sqlite.Ftp;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FtpTable extends TableViewer {
	private FtpPanel view;
	public FtpTable(FtpPanel view, Composite parent, int style) {
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
        col.getColumn().setWidth(40);
        col.getColumn().setText("Id");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Ftp) {
            		return ((Ftp)element).getId()+"";
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(180);
        col.getColumn().setText("Name");
        col.setLabelProvider(new ColumnLabelProvider() {
            private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
            @Override
            public Image getImage(Object element) {
            	if (element instanceof Ftp) {
            		Ftp sc = (Ftp)element;
	            	if (sc.isConnected()) {
	            		return resourceManager.createImage(Images.CONNECTED_ICON);	
	            	} else if (sc.isConnecting()) {
	            		return resourceManager.createImage(Images.CONNECTING_ICON);	
	            	} else {
	            		return resourceManager.createImage(Images.DISCONNECTED_ICON);
	            	}
            	} else {
            		return resourceManager.createImage(Images.DISCONNECTED_ICON);
            	}
            }
            @Override
            public String getText(Object element) {
            	if (element instanceof Ftp) {
            		return ((Ftp)element).getName();
            	}
                return super.getText(element);
            }
            @Override
            public void dispose() {
                super.dispose();
                resourceManager.dispose();
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(100);
        col.getColumn().setText("Address");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Ftp) {
            		return ((Ftp)element).getAddress();
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(60);
        col.getColumn().setText("Port");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Ftp) {
            		return ((Ftp)element).getPort();
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(100);
        col.getColumn().setText("User");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Ftp) {
            		return ((Ftp)element).getUser();
            	}
                return super.getText(element);
            }
        });
	}
	public Ftp getConnectionSelected() {
    	IStructuredSelection selection = (IStructuredSelection) getSelection();
    	if (selection.getFirstElement() != null) {
    		return (Ftp)selection.getFirstElement();
    	}
    	return null;
	}
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#FTPConnectionTableViewerMenu",null); //$NON-NLS-1$
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
        contextMenu.add(new Action("Connect") {
            @Override
            public void run() {
            	view.onConnect();
            }
        });
        contextMenu.add(new Action("Disconnect") {
            @Override
            public void run() {
            	view.onDiscconect();
            }
        });
        contextMenu.add(new Action("New") {
            @Override
            public void run() {
            	view.onNew();
            }
        });
        contextMenu.add(new Action("Edit") {
            @Override
            public void run() {
            	view.onEdit();
            }
        });
        contextMenu.add(new Action("Delete") {
            @Override
            public void run() {
            	view.onDelete();
            }
        });
    }
}
