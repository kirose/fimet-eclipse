package com.fimet.cfg.eglobal;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

import com.fimet.commons.Color;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.net.ISocketConnectionListener;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.impl.swt.IssuerCombo;
import com.fimet.core.impl.swt.msgiso.MessageIsoFilteredCombo;
import com.fimet.core.net.ISocket;
import com.fimet.net.SocketConnection;
import com.fimet.net.SocketConnectionClient;
import com.fimet.net.SocketConnectionServer;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SwitchView extends ViewPart {
	public static final String ID = "com.fimet.view.SwitchView";

	private IssuerCombo cvIssuer;
	private AcquirerCombo cvAcquirer;
	private MessageIsoFilteredCombo filteredRequest;
	private MessageIso msgRequest;
	private MessageIso msgResponse;
	private MessageIsoFilteredCombo filteredResponse;
	private SocketConnection socketIssuer;
	private SocketConnection socketAcquirer;
	private ISocketConnectionListener issuerListener;
	private ISocketConnectionListener acquirerListener;
	private Label lblIssuer;
	private Label lblAcquirer;
	private Image imgDisconnected = SwitchImages.DISCONNECTED_ICON.createImage();
	private Image imgConnected = SwitchImages.CONNECTED_ICON.createImage();
	private Image imgConnecting = SwitchImages.CONNECTING_ICON.createImage();
	
	private Button btnConnect;
	private Button btnDisconnect;
	private ISocket acquirer;
	private ISocket issuer;
	
	public SwitchView() {
		super();
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.cvAcquirer.getControl().setFocus();
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1,true));
		parent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		Label lbl;
		ScrolledComposite scrolled = new ScrolledComposite(parent, SWT.V_SCROLL|SWT.BORDER);
		scrolled.setExpandVertical(true);
		scrolled.setExpandHorizontal(true);
		scrolled.setLayout(new GridLayout(1,true));
		scrolled.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolled.setFont(parent.getFont());
		scrolled.setBackground(Color.WHITE);
		
		Composite body = new Composite(scrolled, SWT.NONE);
		scrolled.setContent(body);
		body.setLayout(new GridLayout(2,true));
		body.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		body.setFont(parent.getFont());
		body.setBackground(Color.WHITE);
		
		Composite left = new Composite(body, SWT.NONE);
		left.setLayout(new GridLayout(4,true));
		left.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		left.setFont(parent.getFont());
		left.setBackground(Color.WHITE);

		Composite right = new Composite(body, SWT.NONE);
		right.setLayout(new GridLayout(4,true));
		right.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		right.setFont(parent.getFont());
		right.setBackground(Color.WHITE);

		Composite bottom = new Composite(body, SWT.NONE);
		bottom.setLayout(new GridLayout(4,true));
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		bottom.setFont(parent.getFont());
		bottom.setBackground(Color.WHITE);
		
		Composite clbl;
		clbl = new Composite(right, SWT.NONE);
		clbl.setLayout(new GridLayout(4,true));
		clbl.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		clbl.setFont(parent.getFont());
		clbl.setBackground(Color.WHITE);
		
		lbl = new Label(clbl,SWT.NONE);
		lbl.setText("Issuer:");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		lbl.setBackground(Color.WHITE);
		
		lblIssuer = new Label(clbl,SWT.NONE);
		lblIssuer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lblIssuer.setBackground(Color.WHITE);
		lblIssuer.setImage(imgDisconnected);
		
		cvIssuer = new IssuerCombo(right);
		cvIssuer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
        
		filteredRequest = new MessageIsoFilteredCombo(right, "IssuerRequest", SWT.NONE);
		filteredRequest.createPartControl();
		filteredRequest.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		clbl = new Composite(left, SWT.NONE);
		clbl.setLayout(new GridLayout(4,true));
		clbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		clbl.setFont(parent.getFont());
		clbl.setBackground(Color.WHITE);
		
		lbl = new Label(clbl,SWT.NONE);
		lbl.setText("Acquirer");
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		lbl.setBackground(Color.WHITE);
		
		lblAcquirer = new Label(clbl,SWT.NONE);
		lblAcquirer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
		lblAcquirer.setBackground(Color.WHITE);
		lblAcquirer.setImage(imgDisconnected);
		
		cvAcquirer = new AcquirerCombo(left);
		cvAcquirer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 3, 1));
		
		filteredResponse = new MessageIsoFilteredCombo(left, "IssuerResponse", SWT.NONE);
		filteredResponse.createPartControl();
		filteredResponse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		lbl = new Label(bottom,SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 2, 1));
		lbl.setBackground(Color.WHITE);
		
		btnConnect = new Button(bottom, SWT.NONE);
		btnConnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnConnect.setText("Connect");
		btnDisconnect = new Button(bottom, SWT.NONE);
		btnDisconnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDisconnect.setText("Disconnect");
		
		hookListeners();
	}
	
	private void hookListeners() {
		btnDisconnect.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				disconnectSockets();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnConnect.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				connectSockets();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		filteredResponse.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				msgResponse = filteredResponse.getMessageSelected();
			}
		});
		filteredRequest.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				msgRequest = filteredRequest.getMessageSelected();
			}
		});
	}
	private void disconnectSockets() {
		if (socketIssuer != null) {
			ThreadUtils.runAcync(()->{
				socketIssuer.disconnect();
				socketIssuer = null;
			});
		}
		if (socketAcquirer != null) {
			ThreadUtils.runAcync(()->{
				socketAcquirer.disconnect();
				socketAcquirer = null;
			});
		}		
	}
	private void connectSockets() {
			disconnectSockets();
			if (cvAcquirer.getSelected() != null) {
				acquirer = cvAcquirer.getSelected();
				socketAcquirer = acquirer.isServer() ? new SocketConnectionServer(acquirer, getAcquirerListener()) : new SocketConnectionClient(acquirer, getAcquirerListener());
				ThreadUtils.runAcync(()->{
					try {
						socketAcquirer.connect();
					} catch (Exception e) {
						if (socketAcquirer != null) {
							ThreadUtils.runAcync(()->{
								try{socketAcquirer.disconnect();}catch(Exception ex) {}
								socketAcquirer = null;
							});
						}
					}
				});
			}
			if (cvIssuer.getSelected() != null) {
				issuer = cvIssuer.getSelected();
				socketIssuer = issuer.isServer() ? new SocketConnectionServer(issuer, getIssuerListener()) : new SocketConnectionClient(issuer, getIssuerListener());
				ThreadUtils.runAcync(()->{
					try {
						socketIssuer.connect();
					} catch (Exception e) {
						if (socketIssuer != null) {
							ThreadUtils.runAcync(()->{
								try{socketIssuer.disconnect();}catch(Exception ex) {}
								socketIssuer = null;
							});
						}
					}
				});
			}
	}
	public ISocketConnectionListener getIssuerListener() {
		if (issuerListener == null) {
			issuerListener = new ISocketConnectionListener() {
				@Override
				public void onSocketRead(byte[] bytes) {
					if (msgResponse != null && socketAcquirer != null) {
						socketAcquirer.writeMessage(ByteUtils.hexToAscii(msgResponse.getMessage()));
					}
				}
				@Override
				public void onSocketDisconnected() {
					ThreadUtils.runOnMainThread(()->{lblIssuer.setImage(imgDisconnected);});
				}
				@Override
				public void onSocketConnecting() {
					ThreadUtils.runOnMainThread(()->{lblIssuer.setImage(imgConnecting);});
				}
				@Override
				public void onSocketConnected() {
					ThreadUtils.runOnMainThread(()->{lblIssuer.setImage(imgConnected);});
				}
				@Override
				public boolean getSocketReconnect() {
					return true;
				}
			};
		}
		return issuerListener;
	}
	public ISocketConnectionListener getAcquirerListener() {
		if (acquirerListener == null) {
			acquirerListener = new ISocketConnectionListener() {
				@Override
				public void onSocketRead(byte[] bytes) {
					if (msgRequest != null && socketIssuer != null) {
						socketIssuer.writeMessage(ByteUtils.hexToAscii(msgRequest.getMessage()));
					}
				}
				@Override
				public void onSocketDisconnected() {
					ThreadUtils.runOnMainThread(()->{lblAcquirer.setImage(imgDisconnected);});
				}
				@Override
				public void onSocketConnecting() {
					ThreadUtils.runOnMainThread(()->{lblAcquirer.setImage(imgConnecting);});
				}
				@Override
				public void onSocketConnected() {
					ThreadUtils.runOnMainThread(()->{lblAcquirer.setImage(imgConnected);});
				}
				@Override
				public boolean getSocketReconnect() {
					return true;
				}
			};
		}
		return acquirerListener;
	}
}
