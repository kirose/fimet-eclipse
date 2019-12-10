package com.fimet.core.impl.swt.enviroment;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
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
import org.osgi.framework.Bundle;

import com.fimet.commons.utils.PluginUtils;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.impl.Activator;
import com.fimet.commons.Images;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class EnviromentTable extends TableViewer {
	public EnviromentPanel view;
	public EnviromentTable(EnviromentPanel view, Composite parent, int style) {
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
            	if (element instanceof Enviroment) {
            		return ((Enviroment)element).getId()+"";
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(140);
        col.getColumn().setText("Name");
        col.setLabelProvider(new ColumnLabelProvider() {
            private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
            @Override
            public Image getImage(Object element) {
            	if (element instanceof Enviroment) {
            		Enviroment sc = (Enviroment)element;
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
            	if (element instanceof Enviroment) {
            		return ((Enviroment)element).getName();
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
        col.getColumn().setWidth(80);
        col.getColumn().setText("Type");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Enviroment) {
            		return ((Enviroment)element).getType() != null ? ((Enviroment)element).getType().getName() : "";
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(140);
        col.getColumn().setText("Application Path");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Enviroment) {
            		return StringUtils.escapeNull(((Enviroment)element).getPath());
            	}
                return super.getText(element);
            }
        });
        
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(120);
        col.getColumn().setText("Data Base");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Enviroment) {
            		return ((Enviroment)element).getDataBase() != null ? ((Enviroment)element).getDataBase().getName() : "";
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(80);
        col.getColumn().setText("FTP");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof Enviroment) {
            		return ((Enviroment)element).getFtp() != null ? ((Enviroment)element).getFtp().getName() : "";
            	}
                return super.getText(element);
            }
        });
        
	}
	public Enviroment getSelected() {
    	IStructuredSelection selection = (IStructuredSelection) getSelection();
    	if (selection.getFirstElement() != null) {
    		return (Enviroment)selection.getFirstElement();
    	}
    	return null;
	}
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#EnviromentTableViewerMenu",null); //$NON-NLS-1$
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
        if (Manager.get(IPreferencesManager.class).getBoolean(IPreferencesManager.KUSUNOKI_MODE, false)) {
			IConfigurationElement[] actions = getTabsConfigurationElement();
			if (actions != null && actions.length > 0) {
				for (IConfigurationElement e : actions) {
					Class<?> clazz;
					try {
						Bundle plugin = PluginUtils.startPlugin(e.getContributor().getName());
						clazz = plugin.loadClass(e.getAttribute("class"));
						if (EnviromentAction.class.isAssignableFrom(clazz)) {
							contextMenu.add((Action)clazz.getConstructor(String.class, EnviromentPanel.class).newInstance(e.getAttribute("name"),view));
						}
					} catch (Exception ex) {
						Activator.getInstance().warning("Extension "+EnviromentPanel.EXTENSION_ID,ex);
					}
				}
			}
		}
    }
	private IConfigurationElement[] getTabsConfigurationElement() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(EnviromentPanel.EXTENSION_ID);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				if ("actions".equals(e.getName())) {
					return e.getChildren();
				}
			}
		}
		return null;
	}
}
