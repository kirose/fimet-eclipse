package com.fimet.core.impl.view.socket;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ViewPart;

import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.ISocketManager;
import com.fimet.core.Manager;
import com.fimet.core.listener.ISocketDeleted;
import com.fimet.core.listener.ISocketInserted;
import com.fimet.core.listener.ISocketListener;
import com.fimet.core.listener.ISocketUpdateAll;
import com.fimet.core.listener.ISocketUpdated;
import com.fimet.core.net.IMessenger;
import com.fimet.core.net.IMessengerManager;
import com.fimet.core.net.ISocket;
import com.fimet.core.net.listeners.IMessengerMonitor;
import com.fimet.commons.Images;
import com.fimet.commons.Color;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SocketView extends ViewPart implements IMessengerMonitor, ISocketInserted, ISocketUpdated, ISocketDeleted, ISocketUpdateAll {
	public static final String EXTENSION_ID = "com.fimet.view.Socket";
	public static final String ID = "com.fimet.view.SocketView";
	public static final String CONTEXT_VIEW = "com.fimet.context.SocketView";
	public static final String CONTEXT_TABLE = "com.fimet.context.SocketTableView";
	
	private SocketTable table;
	private SocketPanel panel;
	private MachineAction machineAction;
	private IContextService contextService;
    private IContextActivation activeContext;
    private IMessengerManager messengerManager = Manager.get(IMessengerManager.class);
    
	public SocketView() {
		super();
		messengerManager.addMonitor(this);
		Manager.get(ISocketManager.class).addListener(ISocketListener.ON_INSERT, this);
		Manager.get(ISocketManager.class).addListener(ISocketListener.ON_UPDATE, this);
		Manager.get(ISocketManager.class).addListener(ISocketListener.ON_DELETE, this);
		Manager.get(ISocketManager.class).addListener(ISocketListener.ON_UPDATE_ALL, this);
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

        SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
        sashForm.setLayout(new GridLayout(2,false));
        sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        sashForm.setFont(parent.getFont());
        sashForm.setBackground(Color.WHITE);
        
        table = new SocketTable(this, sashForm, SWT.MULTI | SWT.H_SCROLL| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		panel = new SocketPanel(this, sashForm, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		createToolbar();
		
		sashForm.setWeights(new int[] {2,1});
        
		hookListeners();
	}
	private void hookListeners() {
        table.getTable().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Table table = (Table)e.widget;
				if (table.getSelection() != null && table.getSelection().length > 0) {
					panel.setSocket((ISocket)table.getSelection()[0].getData());
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        table.addDoubleClickListener((DoubleClickEvent event)->{
			onEditSocket();
		});
		contextService = (IContextService)getSite().getService(IContextService.class);
		contextService.activateContext(CONTEXT_VIEW);
		activeContext = contextService.activateContext(CONTEXT_TABLE);
		this.table.getTable().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (activeContext != null) {
					contextService.deactivateContext(activeContext);
					activeContext = null;
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (activeContext == null) {
					activeContext = contextService.activateContext(CONTEXT_TABLE);
				}
			}
		});
	}
	private void createToolbar() {
		machineAction = new MachineAction(this);

        Action connectAction = new Action("Connect (Alt+C)") {
        	@Override
        	public void run() {
        		connectSelection();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_CONNECT_ICON;
        	}
        };
        Action disconnectAction = new Action("Disconnect (Alt+D)") {
        	@Override
        	public void run() {
        		disconnectSelection();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_DISCONNECT_ICON;
        	}
        };
        Action disconnectAllAction = new Action("Disconnect All (Alt+A)") {
        	@Override
        	public void run() {
        		disconnectAll();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_DISCONNECTED_ALL_ICON;
        	}
        };
        IActionBars aBars = getViewSite().getActionBars();
        IMenuManager menu = aBars.getMenuManager();
        IToolBarManager toolBar = aBars.getToolBarManager();
        
        menu.add(machineAction);
        toolBar.add(machineAction);
        menu.add(connectAction);
        toolBar.add(connectAction);
        menu.add(disconnectAction);
        toolBar.add(disconnectAction);
        menu.add(disconnectAllAction);
        toolBar.add(disconnectAllAction);
	}
	public void connectSelection() {
		if (table.getTable().getSelectionCount() <= 0) {
			return;
		}
		TableItem[] selection = table.getTable().getSelection();
		ISocket socket;
		for (TableItem item : selection) {
			socket = (ISocket)item.getData();
			if (messengerManager.isDisconnected(socket)) {
				//Console.getInstance().info("Connecting to "+ iap.getMachineAddress()+" " +iap.getSocketPort()+ " for "+iap.getName());
				Manager.get(IMessengerManager.class).connect(socket);
			}
		}
	}
	public void disconnectSelection() {
		if (table.getTable().getSelectionCount() <= 0) {
			return;
		}
		TableItem[] selection = table.getTable().getSelection();
		ISocket socket;
		for (TableItem item : selection) {
			socket = (ISocket)item.getData();
			if (!messengerManager.isDisconnected(socket)) {
				//Console.getInstance().info("Disconnecting from "+ iap.getMachineAddress()+" " +iap.getSocketPort()+ " for "+iap.getName());
				Manager.get(IMessengerManager.class).disconnect(socket);
			}
		}
	}
	public void disconnectAll() {
		Manager.get(IMessengerManager.class).disconnectAll();
	}
	private void setConnections(List<ISocket> connections) {
		table.setSockets(connections);
		if (connections != null && !connections.isEmpty()) {
			table.getTable().setSelection(0);
			panel.setSocket(connections.get(0));
		}
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.table.getControl().setFocus();
	}
	public void onChangedMachine(String address) {
		if (address != null) {
			setConnections(Manager.get(ISocketManager.class).findByAddress(address));
		} else {
			setConnections(null);
		}
	}
	public String clipboard() {
		if (table.getStructuredSelection().getFirstElement() != null)
			return ((ISocket)table.getStructuredSelection().getFirstElement()).toString();
		return null;
	}
    protected void setToClipboard(String value) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(value);
        clipboard.setContents(selection, selection);
   }
    public void copyToClipboardSelection() {
    	setToClipboard(clipboard());
    }
	public void onEditSocket() {
		if (table.getSelectedSocket() != null) {
			SocketDialog dialog = new SocketDialog(table.getSelectedSocket(), this.getSite().getShell(), SWT.NONE);
			dialog.open();
			if (dialog.getSocket() != null) {
				Manager.get(ISocketManager.class).update(dialog.getSocket());
			}
		}
	}
	@Override
	public void onSocketInserted(ISocket socket) {
		if (machineAction != null && machineAction.getSelected() != null && machineAction.getSelected().equals(socket.getAddress())) {
			ThreadUtils.runOnMainThread(()->{table.add(socket);});
		}
	}
	@Override
	public void onSocketDeleted(ISocket socket) {
		if (machineAction != null && machineAction.getSelected() != null && machineAction.getSelected().equals(socket.getAddress())) {
			ThreadUtils.runOnMainThread(()->{table.remove(socket);});
		}
	}
    @Override
	public void onSocketUpdated(ISocket socket) {
    	if (machineAction != null && machineAction.getSelected() != null && machineAction.getSelected().equals(socket.getAddress())) {
    		ThreadUtils.runOnMainThread(()->{table.refresh(socket);});
    	} else if (table.containsSocket(socket)) {
    		onSocketDeleted(socket);
    	}
	}
	@Override
	public void onSocketUpdateAll() {
		ThreadUtils.runOnMainThread(()->{table.refresh();});
	}
	@Override
	public void dispose() {
		if (machineAction != null) machineAction.dispose();
		super.dispose();
		messengerManager.removeMonitor(this);
		Manager.get(ISocketManager.class).removeListener(ISocketListener.ON_INSERT, this);
		Manager.get(ISocketManager.class).removeListener(ISocketListener.ON_UPDATE, this);
		Manager.get(ISocketManager.class).removeListener(ISocketListener.ON_DELETE, this);
		Manager.get(ISocketManager.class).removeListener(ISocketListener.ON_UPDATE_ALL, this);
	}
	@Override
	public void onMessengerDisconnected(IMessenger conn) {
		if (PlatformUI.isWorkbenchRunning()) {
			ThreadUtils.runOnMainThread(()->{
				table.refresh(conn.getConnection());
			});
		}
	}
	@Override
	public void onMessengerConnecting(IMessenger conn) {
		if (PlatformUI.isWorkbenchRunning()) {
			ThreadUtils.runOnMainThread(()->{
				table.refresh(conn.getConnection());
			});
		}
	}
	@Override
	public void onMessengerConnected(IMessenger conn) {
		if (PlatformUI.isWorkbenchRunning()) {
			ThreadUtils.runOnMainThread(()->{
				table.refresh(conn.getConnection());
			});
		}
	}
	public List<ISocket> getTableSelection(){
		return table.getSelectedSockets();
	}
}