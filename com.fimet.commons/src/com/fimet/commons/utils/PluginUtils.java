package com.fimet.commons.utils;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

import com.fimet.commons.Activator;

public class PluginUtils {
	public static final String NET_PLUGIN_ID = "com.fimet.net";
	public static final String COMMONS_PLUGIN_ID = "com.fimet.commons";
	public static final String PERSISTENCE_PLUGIN_ID = "com.fimet.persistence";
	public static final String CORE_PLUGIN_ID = "com.fimet.core";
	public static final String CORE_IMPL_PLUGIN_ID = "com.fimet.core.impl";
	public static final String PARSER_PLUGIN_ID = "com.fimet.parser";
	public static final String SIMULATOR_PLUGIN_ID = "com.fimet.simulator";
	public static final String JSON_EDITOR_PLUGIN_ID = "com.fimet.editor.json";
	public static final String USE_CASE_EDITOR_PLUGIN_ID = "com.fimet.editor.usecase";
	public static final String STRESS_EDITOR_PLUGIN_ID = "com.fimet.editor.stress";

	
	public static final String ECLIPSE_UI_PLUGIN_ID = "org.eclipse.ui";
	public static final String ECLIPSE_CONSOLE_PLUGIN_ID = "org.eclipse.ui.console";

	
//	public static final String EGLOBAL_PLUGIN_ID = "com.fimet.eglobal";
	
	public static final String PREFERECES_PAGE_ID = "com.fimet.preferences.FimetPreferences";
	public static final String FIMET_NATURE = "com.fimet.nature.FimetProject";
	
	private PluginUtils() {}
	public static Bundle forceStartPlugin(String id) {
		Bundle bundle = null;
		try {
			bundle = Platform.getBundle(id);
			if (bundle.getState() != Bundle.STARTING)
				bundle.start();
			//System.out.println("bundle "+id+" state "+bundle.getState());
			return bundle;
			//Platform.getBundle(id).getBundleContext().getBundle().start();
		} catch (BundleException e) {
			e.printStackTrace();
		}
		return bundle;
	}
	public static Bundle startPlugin(String id) {
		Bundle bundle = null;
		try {
			bundle = Platform.getBundle(id);
			if (bundle.getState() == Bundle.RESOLVED || bundle.getState() == Bundle.STOPPING) {
				bundle.start();
			}
			return bundle;
			//Platform.getBundle(id).getBundleContext().getBundle().start();
		} catch (BundleException e) {
			e.printStackTrace();
		}
		return bundle;
	}
	public static Class<?> loadClass(String id, String name) {
		Class<?> clazz = null;
		if (name.startsWith("com.fimet")) {
			clazz = PluginUtils.loadFimetClass(name);
		}
		if (clazz == null) {
			try {
				//clazz = startPlugin(id).loadClass(name);
				clazz = Platform.getBundle(id).loadClass(name);
			} catch (Throwable e) {
				try {
					clazz = Class.forName(name);
				} catch(Throwable ex) {
					Activator.getInstance().warning("Class not found: "+name+" for pligin id "+id);
					return null;
				}
			}
		}
		return clazz;
	}
	public static Class<?> loadClass(String name) {
		if (name.startsWith("com.fimet")) {
			return PluginUtils.loadFimetClass(name);
		} else {
			try {
				return Class.forName(name);
			} catch(Throwable ex) {
				Activator.getInstance().warning("Class not found: "+name);
				return null;
			}
		}
	}
	public static Class<?> _loadClass(String id,String name) {
		try {
			//return startPlugin(id).loadClass(name);
			return Platform.getBundle(id).loadClass(name);
		} catch (Throwable e) {
			try {
				return Class.forName(name);
			} catch(Throwable ex) {
				Activator.getInstance().warning("Class not found: "+name);
				return null;
			}
		}
	}
	public static Class<?> loadFimetClass(String name){
		if (name.startsWith(NET_PLUGIN_ID))
			return _loadClass(NET_PLUGIN_ID, name);
		if (name.startsWith(COMMONS_PLUGIN_ID))
			return _loadClass(COMMONS_PLUGIN_ID, name);
		if (name.startsWith(PERSISTENCE_PLUGIN_ID))
			return _loadClass(PERSISTENCE_PLUGIN_ID, name);
		if (name.startsWith(CORE_IMPL_PLUGIN_ID))
			return _loadClass(CORE_IMPL_PLUGIN_ID, name);
		if (name.startsWith(CORE_PLUGIN_ID))
			return _loadClass(CORE_PLUGIN_ID, name);
		if (name.startsWith(PARSER_PLUGIN_ID))
			return _loadClass(PARSER_PLUGIN_ID, name);
		if (name.startsWith(SIMULATOR_PLUGIN_ID))
			return _loadClass(SIMULATOR_PLUGIN_ID, name);
		if (name.startsWith(JSON_EDITOR_PLUGIN_ID))
			return _loadClass(JSON_EDITOR_PLUGIN_ID, name);
		if (name.startsWith(USE_CASE_EDITOR_PLUGIN_ID))
			return _loadClass(USE_CASE_EDITOR_PLUGIN_ID, name);
		if (name.startsWith(STRESS_EDITOR_PLUGIN_ID))
			return _loadClass(STRESS_EDITOR_PLUGIN_ID, name);
		return null;
	}
	public static String getPluginVersion(String id) {
		return Platform.getBundle(id).getVersion().toString();
	}
	public static String getPluginJarName(String id) {
		return id +"_"+ getPluginVersion(id)+".jar";
	}
	public static String getPluginSourcesJarName(String id) {
		return id +".source_"+ getPluginVersion(id)+".jar";
	}
}
