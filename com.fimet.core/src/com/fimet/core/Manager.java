package com.fimet.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.FimetLogger;
import com.fimet.commons.console.Console;
import com.fimet.commons.exception.FimetException;
import com.fimet.commons.utils.PluginUtils;

public class Manager {
	public static final String EXTENSION_ID = "com.fimet.manager";
	private Map<Class<?>, Class<?>> managers = new HashMap<>();
	private Map<Class<?>, Object> instances = new HashMap<>();
	private static Manager instance;
	public static Manager getInstance() {
		if (instance == null)
			instance = new Manager();
		return instance;
	}
	public Manager() {
		PluginUtils.forceStartPlugin(PluginUtils.COMMONS_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.CORE_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.CORE_IMPL_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.PARSER_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.SIMULATOR_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.NET_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.PERSISTENCE_PLUGIN_ID);
		loadExtensions();
	}
	void loadExtensions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(EXTENSION_ID);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				loadExtension(e);
			}
		} else {
			warning("No manager extensions to registre");
			throw new RuntimeException("No manager extensions to registre");
			// Manager extensions are required to works
		}
	}
	private void loadExtension(IConfigurationElement e) {
		try {
			if ("manager".equals(e.getName())) {
				String pluginId = e.getContributor().getName();
				PluginUtils.startPlugin(pluginId);
				Class<?> extension = PluginUtils.loadClass(pluginId, e.getAttribute("extension"));
				Class<?> clazz = PluginUtils.loadClass(pluginId, e.getAttribute("class"));
				if (extension == null) {
					warning("Cannot found extension "+e.getAttribute("extension"));
				} else if (clazz == null) {
					warning("Cannot found class "+e.getAttribute("class"));
				} else if (!IManager.class.isAssignableFrom(extension)) {
					warning("Interface "+extension.getName()+" is not assignable from "+IManager.class.getName());
				} else if (!extension.isAssignableFrom(clazz)) {
					warning("Class "+clazz.getName()+" is not assignable from "+extension.getName());
				} else {
					//warning("Registred manager "+clazz.getName());
					Boolean replace = e.getAttribute("replace") != null ? Boolean.valueOf(e.getAttribute("replace")) : true;
					if (!managers.containsKey(extension) || replace) {
						managers.put(extension, clazz);
					}
				}
			}
		} catch (Throwable ex) {
			warning("Error Manager load extensions", ex);
		}
	}
	private void warning(String message) {
		if (!PlatformUI.isWorkbenchRunning()) {
			FimetLogger.warning(message);
		} else if (Activator.getInstance() != null ) {
			Activator.getInstance().warning(message);
		} else { 
			Console.getInstance().warning(Manager.class, message);
		}
	}
	private void warning(String message, Throwable e) {
		if (!PlatformUI.isWorkbenchRunning()) {
			FimetLogger.warning(message, e);
		} else if (Activator.getInstance() != null ) {
			Activator.getInstance().warning(message, e);
		} else { 
			Console.getInstance().warning(Manager.class, message, e);
		}
	}
	public static <I extends IManager,C> void manage(Class<I> i, Class<C> c) {
		getInstance().managers.put(i,c);
	}
	public static <I extends IManager,C> void manageIfNotExists(Class<I> i, Class<C> c) {
		if (!getInstance().managers.containsKey(i))
			getInstance().managers.put(i,c);
	}
	public static boolean isLoaded(Class<?> clazz) {
		return getInstance().instances.containsKey(clazz);
	}
	public static boolean isManaged(Class<?> clazz) {
		return getInstance().managers.containsKey(clazz);
	}
	public static <T> T get(Class<T> clazz) {
		if (getInstance().instances.containsKey(clazz)) {
			return clazz.cast(getInstance().instances.get(clazz));
		} else {
			if (!getInstance().managers.containsKey(clazz)) {
				//Activator.getInstance().error("Unregistred Manager "+clazz.getName());
				//System.err.println("Unregistred Manager "+clazz.getName());
				return null;
			} else {
				try {
					Object newInstance = getInstance().managers.get(clazz).newInstance();
					getInstance().instances.put(clazz, newInstance);
					return clazz.cast(newInstance);
				} catch (InstantiationException e) {
					throw new FimetException(e);
				} catch (IllegalAccessException e) {
					throw new FimetException(e);
				}
			}
		}
	}
	public static void saveAll() {
		for(Map.Entry<Class<?>, Object> e : getInstance().instances.entrySet()) {
			try {
				((IManager)e.getValue()).saveState();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void freeAll() {
		for(Map.Entry<Class<?>, Object> e : getInstance().instances.entrySet()) {
			try {
				((IManager)e.getValue()).free();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
