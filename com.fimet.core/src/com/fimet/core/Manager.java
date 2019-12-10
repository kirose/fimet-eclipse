package com.fimet.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.fimet.commons.exception.FimetException;
import com.fimet.commons.utils.PluginUtils;

public class Manager {
	public static final String EXTENSION_ID = "com.fimet.manager";
	private static Map<Class<?>, Class<?>> managers = new HashMap<>();
	private static Map<Class<?>, Object> instances = new HashMap<>();
	static {
		PluginUtils.forceStartPlugin(PluginUtils.COMMONS_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.CORE_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.CORE_IMPL_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.PARSER_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.SIMULATOR_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.NET_PLUGIN_ID);
		PluginUtils.forceStartPlugin(PluginUtils.PERSISTENCE_PLUGIN_ID);
		loadExtensions();
	}
	static void loadExtensions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(EXTENSION_ID);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				try {
					if ("manager".equals(e.getName())) {
						String pluginId = e.getContributor().getName();
						PluginUtils.startPlugin(pluginId);
						Class<?> extension = PluginUtils.loadClass(pluginId, e.getAttribute("extension"));
						Class<?> clazz = PluginUtils.loadClass(pluginId, e.getAttribute("class"));
						if (extension == null) {
							System.err.println("Cannot found extension "+e.getAttribute("extension"));
						} else if (clazz == null) {
							System.err.println("Cannot found extension "+e.getAttribute("class"));
						} else if (IManager.class.isAssignableFrom(extension) && extension.isAssignableFrom(clazz)) {
							Boolean replace = e.getAttribute("replace") != null ? Boolean.valueOf(e.getAttribute("replace")) : true;
							if (!managers.containsKey(extension) || replace) {
								managers.put(extension, clazz);
							}
						}
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	public static <I extends IManager,C> void manage(Class<I> i, Class<C> c) {
		managers.put(i,c);
	}
	public static <I extends IManager,C> void manageIfNotExists(Class<I> i, Class<C> c) {
		if (!managers.containsKey(i))
			managers.put(i,c);
	}
	public static boolean isLoaded(Class<?> clazz) {
		return instances.containsKey(clazz);
	}
	public static boolean isManaged(Class<?> clazz) {
		return managers.containsKey(clazz);
	}
	public static <T> T get(Class<T> clazz) {
		if (instances.containsKey(clazz)) {
			return clazz.cast(instances.get(clazz));
		} else {
			if (!managers.containsKey(clazz)) {
				//Activator.getInstance().error("Unregistred Manager "+clazz.getName());
				//System.err.println("Unregistred Manager "+clazz.getName());
				return null;
			} else {
				try {
					Object newInstance = managers.get(clazz).newInstance();
					instances.put(clazz, newInstance);
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
		for(Map.Entry<Class<?>, Object> e : instances.entrySet()) {
			try {
				((IManager)e.getValue()).saveState();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void freeAll() {
		for(Map.Entry<Class<?>, Object> e : instances.entrySet()) {
			try {
				((IManager)e.getValue()).free();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
