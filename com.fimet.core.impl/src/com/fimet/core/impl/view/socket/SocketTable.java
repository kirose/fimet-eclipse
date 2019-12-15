package com.fimet.core.impl.view.socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.impl.Activator;
import com.fimet.core.net.IMessengerManager;
import com.fimet.core.net.ISocket;
import com.fimet.commons.Images;
import com.fimet.commons.utils.PluginUtils;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SocketTable extends TableViewer {
	private SocketView view;
	private List<ISocket> sockets;
	private Image SORTABLE_IMG = Images.SORTABLE_ICON.createImage();
	private static IMessengerManager messengerManager = Manager.get(IMessengerManager.class);
	public SocketTable(SocketView view, Composite parent, int style) {
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
        col.getColumn().setWidth(180);
        col.getColumn().setText("Name");
        col.getColumn().setImage(SORTABLE_IMG);
        col.getColumn().setMoveable(true);
        col.getColumn().addSelectionListener(new SelectionListener() {
        	private Comparator<ISocket> asc  = (ISocket i1, ISocket i2)->{return i2.getName().compareTo(i1.getName());};
			private Comparator<ISocket> desc = (ISocket i1, ISocket i2)->{return i1.getName().compareTo(i2.getName());};
			private Comparator<ISocket> comparator = asc;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collections.sort(sockets, comparator);
				comparator = comparator == asc ? desc : asc;
				setInput(sockets);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
			
		});
        col.setLabelProvider(new ColumnLabelProvider() {
        	//private Color RED = new Color(Display.getDefault(), 255, 0, 0);
            private ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources());
            @Override
            public Image getImage(Object element) {
            	if (element instanceof ISocket) {
            		ISocket socket = (ISocket)element;
	            	if (messengerManager.isConnected(socket)) {
	            		return resourceManager.createImage(Images.CONNECTED_ICON);	
	            	} else if (messengerManager.isConnecting(socket)) {
	            		return resourceManager.createImage(Images.CONNECTING_ICON);	
	            	} else {
	            		return resourceManager.createImage(Images.DISCONNECTED_ICON);
	            	}
            	}
            	return resourceManager.createImage(Images.DISCONNECTED_ICON);
            }
            @Override
            public String getText(Object element) {
            	if (element instanceof ISocket) {
            		return ((ISocket)element).getName();
            	}
                return super.getText(element);
            }
            @Override
            public void dispose() {
                super.dispose();
                resourceManager.dispose();
            }
            @Override
            public Color getBackground(Object element) {
            	return ((ISocket)element).isActive() ? null : com.fimet.commons.Color.GRAY_LIGHT;
            }
        });
        
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(130);
        col.getColumn().setText("Process");
        col.getColumn().setImage(SORTABLE_IMG);
        col.getColumn().setMoveable(true);
        col.getColumn().addSelectionListener(new SelectionListener() {
        	private Comparator<ISocket> asc  = (ISocket i1, ISocket i2)->{return i2.getProcess().compareTo(i1.getProcess());};
			private Comparator<ISocket> desc = (ISocket i1, ISocket i2)->{return i1.getProcess().compareTo(i2.getProcess());};
			private Comparator<ISocket> comparator = asc;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collections.sort(sockets, comparator);
				comparator = comparator == asc ? desc : asc;
				setInput(sockets);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof ISocket) {
            		return ((ISocket)element).getProcess();
            	}
                return super.getText(element);
            }
            @Override
            public Color getBackground(Object element) {
            	return ((ISocket)element).isActive() ? null : com.fimet.commons.Color.GRAY_LIGHT;
            }
        });
        
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(70);
        col.getColumn().setText("Port");
        col.getColumn().setImage(SORTABLE_IMG);
        col.getColumn().setMoveable(true);
        col.getColumn().addSelectionListener(new SelectionListener() {
        	private Comparator<ISocket> asc  = (ISocket i1, ISocket i2)->{return i2.getPort().compareTo(i1.getPort());};
			private Comparator<ISocket> desc = (ISocket i1, ISocket i2)->{return i1.getPort().compareTo(i2.getPort());};
			private Comparator<ISocket> comparator = asc;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collections.sort(sockets, comparator);
				comparator = comparator == asc ? desc : asc;
				setInput(sockets);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof ISocket) {
            		return ((ISocket)element).getPort()+"";
            	}
                return super.getText(element);
            }
            @Override
            public Color getBackground(Object element) {
            	return ((ISocket)element).isActive() ? null : com.fimet.commons.Color.GRAY_LIGHT;
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(80);
        col.getColumn().setText("Type");
        col.getColumn().setImage(SORTABLE_IMG);
        col.getColumn().setMoveable(true);
        col.getColumn().addSelectionListener(new SelectionListener() {
        	private Comparator<ISocket> asc  = (ISocket i1, ISocket i2)->{return (i2.isAcquirer() ? "Acquirer" : "Issuer").compareTo(i1.isAcquirer() ? "Acquirer" : "Issuer");};
			private Comparator<ISocket> desc = (ISocket i1, ISocket i2)->{return (i1.isAcquirer() ? "Acquirer" : "Issuer").compareTo(i2.isAcquirer() ? "Acquirer" : "Issuer");};
			private Comparator<ISocket> comparator = asc;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collections.sort(sockets, comparator);
				comparator = comparator == asc ? desc : asc;
				setInput(sockets);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof ISocket) {
            		return ((ISocket)element).isAcquirer() ? "Acquirer" : "Issuer";
            	}
                return super.getText(element);
            }
            @Override
            public Color getBackground(Object element) {
            	return ((ISocket)element).isActive() ? null : com.fimet.commons.Color.GRAY_LIGHT;
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(100);
        col.getColumn().setText("Parser");
        col.getColumn().setImage(SORTABLE_IMG);
        col.getColumn().setMoveable(true);
        col.getColumn().addSelectionListener(new SelectionListener() {
        	private Comparator<ISocket> asc  = (ISocket i1, ISocket i2)->{ return compareSafely(i1.getParser(), i2.getParser()); };
			private Comparator<ISocket> desc = (ISocket i1, ISocket i2)->{ return compareSafely(i2.getParser(), i1.getParser()); };
			private Comparator<ISocket> comparator = asc;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collections.sort(sockets, comparator);
				comparator = comparator == asc ? desc : asc;
				setInput(sockets);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof ISocket) {
            		return ((ISocket)element).getParser() != null ? ((ISocket)element).getParser().toString() : "";
            	}
                return super.getText(element);
            }
            @Override
            public Color getBackground(Object element) {
            	return ((ISocket)element).isActive() ? null : com.fimet.commons.Color.GRAY_LIGHT;
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(90);
        col.getColumn().setText("Adapter");
        col.getColumn().setImage(SORTABLE_IMG);
        col.getColumn().setMoveable(true);
        col.getColumn().addSelectionListener(new SelectionListener() {
        	private Comparator<ISocket> asc  = (ISocket i1, ISocket i2)->{ return compareSafely(i1.getAdapter(), i2.getAdapter()); };
			private Comparator<ISocket> desc = (ISocket i1, ISocket i2)->{ return compareSafely(i2.getAdapter(), i1.getAdapter()); };
			private Comparator<ISocket> comparator = asc;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collections.sort(sockets, comparator);
				comparator = comparator == asc ? desc : asc;
				setInput(sockets);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof ISocket) {
            		return ((ISocket)element).getAdapter() != null ? ((ISocket)element).getAdapter().toString() : "";
            	}
                return super.getText(element);
            }
            @Override
            public Color getBackground(Object element) {
            	return ((ISocket)element).isActive() ? null : com.fimet.commons.Color.GRAY_LIGHT;
            }
        });

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(150);
        col.getColumn().setText("Simulator");
        col.getColumn().setImage(SORTABLE_IMG);
        col.getColumn().setMoveable(true);
        col.getColumn().addSelectionListener(new SelectionListener() {
        	private Comparator<ISocket> asc  = (ISocket i1, ISocket i2)->{ return compareSafely(i1.getSimulator(), i2.getSimulator()); };
			private Comparator<ISocket> desc = (ISocket i1, ISocket i2)->{ return compareSafely(i2.getSimulator(), i1.getSimulator()); };
			private Comparator<ISocket> comparator = asc;
			@Override
			public void widgetSelected(SelectionEvent e) {
				Collections.sort(sockets, comparator);
				comparator = comparator == asc ? desc : asc;
				setInput(sockets);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof ISocket) {
            		return ((ISocket)element).getSimulator() != null ? ((ISocket)element).getSimulator().toString() : "";
            	}
                return super.getText(element);
            }
            @Override
            public Color getBackground(Object element) {
            	return ((ISocket)element).isActive() ? null : com.fimet.commons.Color.GRAY_LIGHT;
            }
        });
        
	}
	private int compareSafely(Object o1, Object o2) {
		if (o2 == null && o1 == null) {
			return 0;
		}
		if (o2 == null) {
			return "".compareTo(o1.toString());
		}
		if (o1 == null) {
			return o2.toString().compareTo("");
		}
		return  o2.toString().compareTo(o1.toString());
	}
	public List<ISocket> getSelectedSockets(){
		TableItem[] selection = getTable().getSelection();
		if (selection != null && selection.length > 0) {
			List<ISocket> sockets = new ArrayList<>();
			for (TableItem i : selection) {
				sockets.add((ISocket)i.getData());
			}
			return sockets;
		} else {
			return null;
		}
	}
	public ISocket getSelectedSocket(){
		return getStructuredSelection() != null ? (ISocket)getStructuredSelection().getFirstElement() : null;
	}
	public void setSockets(List<ISocket> sockets) {
		this.sockets = sockets;
		if (!getTable().isDisposed()) {
			setInput(sockets);
		}
	}
    private void createContextMenu() {
        MenuManager contextMenu = new MenuManager("#SocketTableViewerMenu",null); //$NON-NLS-1$
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
        contextMenu.add(new Action("Connect\t(Alt+C)") {
            @Override
            public void run() {
            	view.connectSelection();
            }
        });
        contextMenu.add(new Action("Disconnect\t(Alt+D)") {
            @Override
            public void run() {
            	view.disconnectSelection();
            }
        });
        contextMenu.add(new Action("Disconnect All\t(Alt+A)") {
            @Override
            public void run() {
            	view.disconnectAll();
            }
        });
        contextMenu.add(new Action("Edit") {
            @Override
            public void run() {
            	view.onEditSocket();
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
						if (SocketAction.class.isAssignableFrom(clazz)) {
							contextMenu.add((Action)clazz.getConstructor(String.class, SocketView.class).newInstance(e.getAttribute("name"),view));
						}
					} catch (Exception ex) {
						Activator.getInstance().warning("Extension "+SocketView.EXTENSION_ID,ex);
					}
				}
			}
		}
    }
	private IConfigurationElement[] getTabsConfigurationElement() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(SocketView.EXTENSION_ID);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				if ("actions".equals(e.getName())) {
					return e.getChildren();
				}
			}
		}
		return null;
	}
    public void add(ISocket socket) {
    	if (sockets == null)
    		sockets = new ArrayList<ISocket>();
    	if (!sockets.contains(socket) && PlatformUI.isWorkbenchRunning()) {
    		sockets.add(0,socket);
    		setInput(sockets);
		}    	
    }
    public void remove(ISocket socket) {
    	if (sockets != null && sockets.contains(socket) && PlatformUI.isWorkbenchRunning()) {
    		sockets.remove(socket);
    		super.remove(socket);
		}    	
    }
    public void refresh(ISocket socket) {
    	if (sockets != null && sockets.contains(socket) && PlatformUI.isWorkbenchRunning()) {
    		super.refresh(socket);
		}
    }
    public boolean containsSocket(ISocket socket) {
    	return sockets != null && sockets.contains(socket);
    }
}
