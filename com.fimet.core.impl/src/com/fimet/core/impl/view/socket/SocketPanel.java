package com.fimet.core.impl.view.socket;


import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.osgi.framework.Bundle;

import com.fimet.commons.Color;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.core.impl.Activator;
import com.fimet.core.net.ISocket;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SocketPanel extends ScrolledComposite {

	private ISocket socket;
	private TabFolder tabFolder;
	
	
	public SocketPanel(SocketView view, Composite parent, int style) {
		super(parent, style);
		setFont(parent.getFont());
		setLayout(new GridLayout(1,true));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
        setExpandHorizontal(true);
        setExpandVertical(true);
        setBackground(Color.WHITE);
        tabFolder = new TabFolder(this, SWT.NONE);
        createTabs(tabFolder);
        setContent(tabFolder);
        setMinSize(300, 420);//this.setMinSize(tabFolder.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		hookListeners();
	}
	
	private void createTabs(TabFolder tabFolder) {
		IConfigurationElement[] tabs = getTabsConfigurationElement();
		if (tabs != null && tabs.length > 0) {
			for (IConfigurationElement e : tabs) {
				Class<?> clazz;
				try {
					Bundle plugin = PluginUtils.startPlugin(e.getContributor().getName());
					clazz = plugin.loadClass(e.getAttribute("class"));
					if (SocketTabItem.class.isAssignableFrom(clazz)) {
						clazz.getConstructor(TabFolder.class).newInstance(tabFolder);
					}
				} catch (Exception ex) {
					Activator.getInstance().warning("Extension "+SocketView.EXTENSION_ID,ex);
				}
			}
		}
		if (tabFolder.getItemCount() == 0) {
			new SocketTabOverview(tabFolder);
		}
	}
	private IConfigurationElement[] getTabsConfigurationElement() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(SocketView.EXTENSION_ID);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				if ("tabs".equals(e.getName())) {
					return e.getChildren();
				}
			}
		}
		return null;
	}
	private void hookListeners() {
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				onChangeSocket(socket);
			}
	    });
	}

	public void setSocket(ISocket socket) {
		this.socket = socket;
		for (TabItem tab : tabFolder.getItems()) {
			((SocketTabItem)tab).setIsLoaded(false);
		}
		onChangeSocket(socket);
	}
	private void onChangeSocket(ISocket socket) {
		if (tabFolder.getSelection() != null && tabFolder.getSelection().length > 0) {
			SocketTabItem tab = (SocketTabItem)tabFolder.getSelection()[0];
			if (!tab.isLoaded()) {
				tab.setSocket(socket);
				tab.setIsLoaded(true);
			}
		}
	}
}
