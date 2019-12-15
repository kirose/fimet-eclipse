package com.fimet.core.impl;

import org.osgi.framework.BundleContext;

import com.fimet.commons.AbstractActivator;
import com.fimet.commons.console.Console;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = PluginUtils.CORE_PLUGIN_ID; //$NON-NLS-1$

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
		int level = Manager.get(IPreferencesManager.class).getInt(IPreferencesManager.CONSOLE_LEVEL, Console.INFO | Console.WARNING | Console.ERROR);
		Console.setLevel(level);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
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
