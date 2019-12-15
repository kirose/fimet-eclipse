package com.fimet.core;

public interface IClassLoaderManager extends IManager {
	public Class<?> loadClassBin(String className) throws ClassNotFoundException;
	public Class<?> loadClassLib(String className) throws ClassNotFoundException;
	public ClassLoader getClassLoaderBin();
	public ClassLoader getClassLoaderLib();
	public void installClassBin(String className, byte[] clazz, boolean override);
	public void installClassBin(String className, byte[] contents);
	public void reloadClasesBin();
	public void uninstallClasses();
}
