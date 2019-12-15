package com.fimet.core.startup;


import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.IBindingManager;
import com.fimet.core.Manager;

public class FimetStartup implements org.eclipse.ui.IStartup {
	@Override
	public void earlyStartup() {
		ThreadUtils.runOnMainThread(()->{
			Manager.get(IBindingManager.class).installCommands();
		});
	}
}
