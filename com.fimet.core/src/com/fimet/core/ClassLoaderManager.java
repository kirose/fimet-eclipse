package com.fimet.core;

public class ClassLoaderManager implements IClassLoaderManager {
	private ClassLoaderBin classLoaderBin = new ClassLoaderBin();
	private ClassLoaderLib classLoaderLib = new ClassLoaderLib();
	
	@Override
	public Class<?> loadClassBin(String className) throws ClassNotFoundException {
		return classLoaderBin.loadClass(className);
	}
	@Override
	public Class<?> loadClassLib(String className) throws ClassNotFoundException {
		return classLoaderLib.loadClass(className);
	}
	@Override
	public ClassLoader getClassLoaderBin() {
		return classLoaderBin;
	}
	@Override
	public ClassLoader getClassLoaderLib() {
		return classLoaderLib;
	}
	public void installClassBin(String className, byte[] contents) {
		classLoaderBin.installClass(className, contents);
	}
	public void installClassBin(String className, byte[] clazz, boolean override) {
		classLoaderBin.installClass(className, clazz, override);
	}
	/**
	 * If you want reload a class you to must do:
	 * IClassLoaderManager.reloadClases().loadClass(className);
	 * this code will affect all classes loaded for the previus ClassLoader
	 */
	@Override
	public void reloadClasesBin() {
		classLoaderBin = new ClassLoaderBin();
		//classLoaderLib = new ClassLoaderLib();
	}
	@Override
	public void uninstallClasses() {
		classLoaderBin.uninstallClasses();
	}
	@Override
	public void free() {
		classLoaderBin.uninstallClasses();
	}
	@Override
	public void saveState() {
		
	}
}
