package com.fimet.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.fimet.commons.utils.RuteUtils;

public class ClassLoaderLib extends ClassLoader {
	private String LIB_PATH = RuteUtils.getLibPath();
	private URLClassLoader urlClassLoader;
	public ClassLoaderLib() {
		super(ClassLoaderLib.getSystemClassLoader());
		newURLClassLoader();
	}
	public ClassLoaderLib(ClassLoader parent) {
		super(ClassLoaderLib.getSystemClassLoader());
		newURLClassLoader();
	}
	private URLClassLoader newURLClassLoader() {
		URL[] urls = getUrlLibraries();
		if (urls != null && urls.length > 0) {
			urlClassLoader = new URLClassLoader(
				urls,
				ClassLoaderLib.getSystemClassLoader()//class.getClassLoader()//Class.class.getClassLoader()
			);
			return urlClassLoader;
		} else {
			return null;
		}
	} 
	private URL[] getUrlLibraries() {
		File lib = new File(LIB_PATH);
		if (lib.exists() && lib.isDirectory()) {
			File[] jars = lib.listFiles();
			if (jars != null && jars.length > 0) {
				List<URL> urls = new ArrayList<URL>(jars.length);
				for (File file : jars) {
					try {
						urls.add(file.toURI().toURL());
					} catch (MalformedURLException e) {
						if (Activator.getInstance() != null) {
							Activator.getInstance().warning("Malformed URL",e);
						} else {
							e.printStackTrace();
						}
					}
				}
				if (urls.isEmpty()) {
					return null;
				} else {
					return urls.toArray(new URL[urls.size()]);
				}
			}
		}
		return null;
	}
//	@Override
//    public Class<?> findClass(String className) throws ClassNotFoundException {
//		if (urlClassLoader != null) {
//			return urlClassLoader.loadClass(className);
//		} else {
//			return Class.forName(className);
//		}
//	}
	@Override
	public Class<?> loadClass(String className) throws ClassNotFoundException {
		if (urlClassLoader != null) {
			return urlClassLoader.loadClass(className);
		} else {
			return Class.forName(className);
		}
	}
            
}
