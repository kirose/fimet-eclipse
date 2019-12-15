package com.fimet.core.impl.view.sim_queue;

import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;

import com.fimet.commons.utils.ByteUtils;
import com.fimet.commons.utils.FileUtils;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.net.IMessenger;
import com.fimet.core.net.IMessengerManager;
import com.fimet.core.net.ISocketConnection;
import com.fimet.core.net.listeners.IMessengerConnected;
import com.fimet.core.net.listeners.IMessengerDisconnected;
import com.fimet.core.net.listeners.IMessengerListener;
import com.fimet.core.net.listeners.IMessengerReadAcquirerResponse;
import com.fimet.commons.Images;
import com.fimet.commons.console.Console;
import com.fimet.commons.Color;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SimQueueView extends ViewPart implements IMessengerConnected, IMessengerDisconnected, IMessengerReadAcquirerResponse {
	public static final String ID = "com.fimet.view.SimQueueView";
	
	private SimQueueTreeViewer tree;
	private SimQueueButtons buttons;
	private AcquirerCombo cboAcquirer;
	private static IMessengerManager connectionManager = Manager.get(IMessengerManager.class);
	
	public SimQueueView() {
		super();
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		Label lbl;
		GridLayout layout;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(4,false));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        composite.setBackground(Color.WHITE);
        
		createToolbar();
		
		lbl = new Label(composite,SWT.NONE);
		lbl.setText("Acquirer");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		
        cboAcquirer = new AcquirerCombo(composite);
        cboAcquirer.getCombo().setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 2, 1));

        Composite compositeTree = new Composite(composite, SWT.NONE);
        compositeTree.setBackground(Color.WHITE);
        layout = new GridLayout(2,false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        compositeTree.setLayout(layout);
        compositeTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,4,1));

        tree = new SimQueueTreeViewer(this, compositeTree, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        buttons = new SimQueueButtons(this, compositeTree, SWT.NONE);
	}
	private void createToolbar() {
		Action injectAction = new Action("Inject") {
        	@Override
        	public void run() {
        		injectSelection();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_CONNECT_ICON;
        	}
        };
        Action injectAllAction = new Action("Inject All") {
        	@Override
        	public void run() {
        		injectAll();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_CONNECT_ICON;
        	}
        };
        Action disconnectAction = new Action("Remove selection") {
        	@Override
        	public void run() {
        		onRemove();
        	}
			@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_DISCONNECT_ICON;
        	}
        };
        IActionBars aBars = getViewSite().getActionBars();
        IMenuManager menu = aBars.getMenuManager();
        IToolBarManager toolBar = aBars.getToolBarManager();
        
        menu.add(injectAction);
        toolBar.add(injectAction);
        menu.add(injectAllAction);
        toolBar.add(injectAllAction);
        menu.add(disconnectAction);
        toolBar.add(disconnectAction);
	}
	public void injectSelection() {
		List<IResource> resources = tree.getSelectedSimQueues();
		if (resources != null && !resources.isEmpty() && cboAcquirer.getSelected() != null) {
			IMessenger acquirerConnection = connectionManager.getConnection(cboAcquirer.getSelected());
			acquirerConnection.addListener(IMessengerListener.ON_DISCONNECTED, this);
			acquirerConnection.addListener(IMessengerListener.ON_READ_ACQ_RESPONSE, this);
			if (!acquirerConnection.isConnected()) {
				Console.getInstance().info(SimQueueView.class, "Connecting to "+cboAcquirer.getSelected());
				acquirerConnection.addListener(IMessengerListener.ON_CONNECTED, this);
				acquirerConnection.connect();
			} else {
				writeMessages(acquirerConnection);
			}
		} else {
			Console.getInstance().info(SimQueueView.class, "There are not sim_queue to inject to "+cboAcquirer.getSelected());
		}
	}
	public void injectAll() {
		List<IResource> resources = tree.getIResources();
		if (resources != null && !resources.isEmpty() && cboAcquirer.getSelected() != null) {
			IMessenger acquirerConnection = connectionManager.getConnection(cboAcquirer.getSelected());
			acquirerConnection.addListener(IMessengerListener.ON_DISCONNECTED, this);
			acquirerConnection.addListener(IMessengerListener.ON_READ_ACQ_RESPONSE, this);
			if (!acquirerConnection.isConnected()) {
				Console.getInstance().info(SimQueueView.class, "Connecting to "+cboAcquirer.getSelected());
				acquirerConnection.addListener(IMessengerListener.ON_CONNECTED, this);
				acquirerConnection.connect();
			} else {
				writeMessages(acquirerConnection);
			}
		} else {
			Console.getInstance().info(SimQueueView.class, "There are not sim_queue to inject to "+cboAcquirer.getSelected());
		}
	}
	@Override
	public void onMessangerConnected(IMessenger conn) {
		writeMessages(conn);
	}
	@Override
	public void onMessangerDisconnected(IMessenger conn) {
		conn.removeListener(IMessengerListener.ON_CONNECTED, this);
		conn.removeListener(IMessengerListener.ON_DISCONNECTED, this);
		conn.removeListener(IMessengerListener.ON_READ_ACQ_RESPONSE, this);
	}
	@Override
	public void onMessangerReadAcquirerResponse(IMessenger conn, byte[] bytes) {
		try {
			IMessage message = conn.getConnection().getParser().parseMessage(bytes);
			message.setAdapter(conn.getConnection().getAdapter());
			Console.getInstance().info(SimQueueView.class, conn+", read message: "+message.toJson());
		} catch (Exception e) {
			Console.getInstance().warning(SimQueueView.class, "Error parsing response from "+conn+", "+e.getMessage());
		}
	}
	private void writeMessages(IMessenger conn) {
		List<IResource> resources = tree.getIResources();
		ISocketConnection sm = conn.getSocket();
		byte[] bytes;
		for (IResource r : resources) {
			bytes = FileUtils.readBytesContents(r.getLocation().toFile());
			sm.writeMessage(ByteUtils.removeEndNewLines(bytes), false);
		}
		Console.getInstance().info(SimQueueView.class, "Writen "+resources.size()+" sim_queue(s) to "+conn);
		conn.removeListener(IMessengerListener.ON_CONNECTED, this);
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.buttons.setFocus();
	}
	public void onRemove() {
    	List<IResource> nodes = tree.getSelectedSimQueues();
    	for (IResource r : nodes) {
    		tree.doRemove(r);
		}
	}
	public void onUp() {
		int i = tree.getSelectedIndex();
		tree.doUp(i);
	}
	public void onDown() {
		int i = tree.getSelectedIndex();
		tree.doDown(i);
	}
	public void onEdit() {
		List<IResource> resources = tree.getSelectedSimQueues();
		if (resources != null && !resources.isEmpty()) {
	        final IWorkbenchWindow dw = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	        try {
	            if (dw != null) {
	                IWorkbenchPage page = dw.getActivePage();
	                for (IResource r : resources) {
	                	IFileStore fileStore = EFS.getLocalFileSystem().getStore(r.getLocationURI());
	                	IDE.openEditorOnFileStore(page, fileStore);
	                }
	            }
	        } catch (Exception e) {}
		}
	}
	public void onAdd(List<IResource> resources) {
    	for (IResource r : resources) {
    		tree.doAdd(r);
		}
	}
	@Override
	public void dispose() {
		super.dispose();
		connectionManager.removeListener(IMessengerListener.ON_CONNECTED, this);
		connectionManager.removeListener(IMessengerListener.ON_DISCONNECTED, this);
		connectionManager.removeListener(IMessengerListener.ON_READ_ACQ_RESPONSE, this);
	}
}