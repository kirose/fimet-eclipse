package com.fimet.net;

import org.osgi.framework.BundleContext;

import com.fimet.commons.AbstractActivator;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.core.Manager;
import com.fimet.core.net.IFtpManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivator {
	// The plug-in ID
	public static final String PLUGIN_ID = PluginUtils.NET_PLUGIN_ID;
	
	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		Manager.get(IFtpManager.class).disconnect();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	public static Activator getInstance() {
		return plugin;
	}

	@Override
	public String getPluginId() {
		return PLUGIN_ID;
	}
}