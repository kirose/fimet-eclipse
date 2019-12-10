package com.fimet.commons;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;

import com.fimet.commons.console.Console;

public abstract class AbstractActivator extends AbstractUIPlugin {
	
	abstract public String getPluginId();
	public void info(String message) {
		if (Console.isEnabledInfo()) {
			Status status= new Status(IStatus.INFO, getPluginId(), message);
			StatusManager.getManager().handle(status, StatusManager.LOG);
		}
	}
	public void cancel(String message) {
		Status status= new Status(IStatus.CANCEL, getPluginId(), message);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public void ok(String message) {
		Status status= new Status(IStatus.OK, getPluginId(), message);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public void error(String message) {
		if (Console.isEnabledError()) {
			Status status= new Status(IStatus.ERROR, getPluginId(), message);
			StatusManager.getManager().handle(status, StatusManager.LOG);
		}
	}
	public void error(String message, Throwable e) {
		if (Console.isEnabledError()) {
			Status status= new Status(IStatus.ERROR, getPluginId(), IStatus.OK, message, e);
			StatusManager.getManager().handle(status, StatusManager.LOG);
		}
	}
	public void warning(String message) {
		if (Console.isEnabledWarning()) {
			Status status= new Status(IStatus.WARNING, getPluginId(), message);
			StatusManager.getManager().handle(status, StatusManager.LOG);
		}
	}
	public void warning(String message, Throwable e) {
		if (Console.isEnabledWarning()) {
			Status status= new Status(IStatus.WARNING, getPluginId(), IStatus.OK, message, e);
			StatusManager.getManager().handle(status, StatusManager.LOG);
		}
	}
	public static boolean isEnabledInfo() {
		return Console.isEnabledInfo();
	}
	public static boolean isEnabledDebug() {
		return Console.isEnabledDebug();
	}
	public static boolean isEnabledWarning() {
		return Console.isEnabledWarning();
	}
	public static boolean isEnabledError() {
		return Console.isEnabledError();
	}
}
