package com.fimet.commons.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.fimet.commons.exception.FimetException;


public class RuteUtils {
	private static String pluginsInstall = null;
	private static String ruteEclipsePlugins = null;
	private static String ruteEclipse = null;
	private static String ruteLogs = null;
	private static String ruteExtract = null;
	private static String ruteCommands = null;
	private static String ruteBin = null;
	private static String ruteLib = null;
	private static String ruteSrc = null;
	private static String rutePeristence = null;
	private static Boolean isProuction = false;
	private RuteUtils() {}

	/**
	 * La ruta en la que se encuentran las clases compiladas 
	 * Si se encuentran el eclipse/plugins entonces coincide con getEclipsePlugins
	 * En otro caso se encuentran compilados  en un worspace posiblemente fuera de la carpeta de eclipse 
	 * @return
	 */
	public static String getPluginsInstallation() {
		if (RuteUtils.pluginsInstall != null) {
			return RuteUtils.pluginsInstall;
		}
		/* get bundle with the specified id */
		Bundle bundle = Platform.getBundle(PluginUtils.CORE_PLUGIN_ID);//FrameworkUtil.getBundle(RuteUtils.class);
		if( bundle == null )
			throw new RuntimeException("Could not resolve plugins rute: \r\n" +
					"Probably the plugin has not been correctly installed.\r\n" +
					"Running eclipse from shell with -clean option may rectify installation.");
		
		/* resolve Bundle::getEntry to local URL */
		URL pluginURL = null;
		try {
			pluginURL = Platform.resolve(bundle.getEntry("/"));
		} catch (IOException e) {
			throw new RuntimeException("Could not get installation directory of the plugins rute");
		}
		String pluginsInstall = pluginURL.getPath().trim();
		if( pluginsInstall.length() == 0 ) {
			throw new RuntimeException("Could not get installation directory of the plugins rute ");
		}

		if(pluginsInstall.startsWith("/") || pluginsInstall.startsWith("\\") ) {
			pluginsInstall = pluginsInstall.substring(1);
		}
		if (pluginsInstall.startsWith("file:")) {
			pluginsInstall = pluginsInstall.substring(6);
		}
		pluginsInstall = pluginsInstall.substring(0, pluginsInstall.indexOf(PluginUtils.CORE_PLUGIN_ID));
		try {
			pluginsInstall = new File(pluginsInstall).getCanonicalPath();
		} catch (IOException e) {
			throw new FimetException("Unable to find path to plugins installation");
		}
		char last = pluginsInstall.charAt(pluginsInstall.length()-1);
		if (last != '/' && last != '\\'){
			pluginsInstall = pluginsInstall + "/";
		}
		return RuteUtils.pluginsInstall = pluginsInstall;
	}
	public static String getEclipsePlugins(){
		if (ruteEclipsePlugins != null) {
			return ruteEclipsePlugins;
		}
		return ruteEclipsePlugins = getEclipse()+"plugins/";
	}
	public static String getEclipse() {
		if (ruteEclipse != null) {
			return ruteEclipse;
		}
		/* get bundle with the specified id */
		Bundle bundle = Platform.getBundle(PluginUtils.ECLIPSE_UI_PLUGIN_ID);//FrameworkUtil.getBundle(RuteUtils.class);
		if( bundle == null )
			throw new RuntimeException("Could not resolve plugins rute: \r\n" +
					"Probably the plugin has not been correctly installed.\r\n" +
					"Running eclipse from shell with -clean option may rectify installation.");
		
		/* resolve Bundle::getEntry to local URL */
		URL pluginURL = null;
		try {
			pluginURL = Platform.resolve(bundle.getEntry("/"));
		} catch (IOException e) {
			throw new RuntimeException("Could not get installation directory of the plugins rute");
		}
		String pluginsInstall = pluginURL.getPath().trim();
		if( pluginsInstall.length() == 0 )
			throw new RuntimeException("Could not get installation directory of the plugins rute ");
		
		pluginsInstall = pluginsInstall.substring(0,pluginsInstall.indexOf("plugins/"+PluginUtils.ECLIPSE_UI_PLUGIN_ID));
		if(pluginsInstall.startsWith("/") || pluginsInstall.startsWith("\\") ) {
			pluginsInstall = pluginsInstall.substring(1);
		}
		if (pluginsInstall.startsWith("file:")) {
			pluginsInstall = pluginsInstall.substring(6);
		}
		char last = pluginsInstall.charAt(pluginsInstall.length()-1);
		if (last != '/' && last != '\\'){
			pluginsInstall = pluginsInstall + "/";
		}
		return ruteEclipse = pluginsInstall;

	}
	public static String getSrcPath() {
		if (ruteSrc == null) {
			ruteSrc = getEclipsePlugins() + "com.fimet/"+"src/";
		}
		return ruteSrc;
	}
	public static String getBinPath() {
		if (ruteBin == null) {
			ruteBin = getEclipsePlugins() + "com.fimet/"+"bin/";
		}
		return ruteBin;
	}
	public static String getLibPath() {
		if (ruteLib == null) {
			ruteLib = getEclipsePlugins() + "com.fimet/"+"lib/";
		}
		return ruteLib;
	}
	public static String getLogsPath() {
		if (ruteLogs == null) {
			ruteLogs = getEclipsePlugins() + "com.fimet/"+"logs/";
		}
		return ruteLogs;
	}
	public static String getExtractPath() {
		if (ruteExtract == null) {
			ruteExtract = getEclipsePlugins() + "com.fimet/"+"extract/";
		}
		return ruteExtract;
	}
	public static String getCommandsPath() {
		if (ruteCommands == null) {
			ruteCommands = getEclipsePlugins() + "com.fimet/"+"commands/";
		}
		return ruteCommands;
	}
	public static String getPersistencePath() {
		if (rutePeristence == null) {
			rutePeristence = getEclipsePlugins() + "com.fimet/"+"persistence/";
		}
		return rutePeristence;
	}
	public static String getPathToPluginJarNameOrProjectBinFolder(String idPlugin) {
		String path = RuteUtils.getEclipsePlugins() + PluginUtils.getPluginJarName(idPlugin);
		if (new File(path).exists()) {// Validamos que este en eclipse/pligins/...
			return path;
		} else {//entonces esta en el workspace/..
			path = RuteUtils.getPluginsInstallation() + idPlugin + "/bin/";
			if (new File(path).exists()) {
				return path;
			} else {
				throw new FimetException("Unable to find plugin installation "+idPlugin);
			}
		}
	}
	public static String getPathToPluginSourcesNameOrProjectSrcFolder(String idPlugin) {
		String path = RuteUtils.getEclipsePlugins() + PluginUtils.getPluginSourcesJarName(idPlugin);
		if (new File(path).exists()) {// Validamos que este en eclipse/pligins/...
			return path;
		} else {//entonces esta en el workspace/..
			path = RuteUtils.getPluginsInstallation() + idPlugin + "/src/";
			if (new File(path).exists()) {
				return path;
			} else {
				return null;
			}
		}
	}
}
