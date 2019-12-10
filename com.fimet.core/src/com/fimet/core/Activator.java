package com.fimet.core;

import org.osgi.framework.BundleContext;

import com.fimet.commons.AbstractActivator;
import com.fimet.commons.utils.PluginUtils;

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
//		Manager.manage(IFieldValidatorManager.class, FieldValidatorManager.class);
//		Manager.manage(IClassLoaderManager.class, ClassLoaderManager.class);
//		Manager.manage(IValidatorManager.class, ValidatorManager.class);
//		Manager.manage(IUseCaseExecutorManager.class, UseCaseExecutorManager.class);
//		Manager.manage(IAdapterManager.class, AdapterManager.class);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		PluginUtils.forceStartPlugin(PluginUtils.CORE_IMPL_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.PARSER_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.SIMULATOR_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.NET_PLUGIN_ID);
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		if (Manager.isManaged(IDataBaseManager.class)) {
			Manager.get(IDataBaseManager.class).disconnect();
		}
		Manager.saveAll();
		Manager.freeAll();
		if (Manager.isLoaded(IPreferencesManager.class)) {
			Manager.get(IPreferencesManager.class).saveState();
			Manager.get(IPreferencesManager.class).free();
		}
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
