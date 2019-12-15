package com.fimet.core;

import java.io.File;

import com.fimet.commons.exception.ClassLoaderException;
import com.fimet.commons.utils.FileUtils;
import com.fimet.commons.utils.RuteUtils;

public class ClassLoaderBin extends ClassLoader {
	private String BIN_PATH = RuteUtils.getBinPath();
	private String SRC_PATH = RuteUtils.getSrcPath();
	public ClassLoaderBin() {
		super();
	}
	public ClassLoaderBin(ClassLoader parent) {
		super(parent);
	}
	@Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
		File path = new File(BIN_PATH + className.replace('.', File.separatorChar) + ".class");
		if (path.exists()) {
			byte[] contents = FileUtils.readBytesContents(path);
			return defineClass(className, contents, 0, contents.length);
		} else {
			return Class.forName(className);
		}
	}
	private boolean wasInstalled(String className) {
		return new File(BIN_PATH + className.replace('.', File.separatorChar) + ".class").exists();
	}
	public void installClass(String className, byte[] clazz, boolean override) throws ClassLoaderException {
		if (override) {
			File file = new File(BIN_PATH + className.replace('.', File.separatorChar) + ".class");
			FileUtils.createSubdirectories(file);
			FileUtils.writeContents(file, clazz);
		} else {
			if (wasInstalled(className)) {
				throw new ClassLoaderException("The class "+className+" was installed previusly");
			} else {
				File file = new File(BIN_PATH + className.replace('.', File.separatorChar) + ".class");
				FileUtils.createSubdirectories(file);
				FileUtils.writeContents(file, clazz);
			}
		}
	}
	public void installClass(String className, byte[] clazz) {
		installClass(className, clazz, true);
	}
	public void uninstallClasses() {
		System.out.println("deleting..."+BIN_PATH);
		delete(new File(BIN_PATH));
		System.out.println("deleting..."+SRC_PATH);
		delete(new File(SRC_PATH));
		System.out.println("unistalled classes"+BIN_PATH);
	}
	private void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			File[] childs = file.listFiles();
			if (childs != null && childs.length > 0) {
				for (File f : childs) {
					delete(f);
				}
			}
		}
	}
}
