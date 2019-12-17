package com.fimet.evaluator;



import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.fimet.evaluator";

	// The shared instance
	private static Activator plugin;
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
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
	public static void info(String message) {
		Status status= new Status(IStatus.INFO, PLUGIN_ID, message);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public static void cancel(String message) {
		Status status= new Status(IStatus.CANCEL, PLUGIN_ID, message);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public static void ok(String message) {
		Status status= new Status(IStatus.OK, PLUGIN_ID, message);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public static void error(String message) {
		Status status= new Status(IStatus.ERROR, PLUGIN_ID, message);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public static void error(String message, Throwable e) {
		Status status= new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, message, e);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public static void warning(String message) {
		Status status= new Status(IStatus.WARNING, PLUGIN_ID, message);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
	public static void warning(String message, Throwable e) {
		Status status= new Status(IStatus.WARNING, PLUGIN_ID, IStatus.OK, message, e);
		StatusManager.getManager().handle(status, StatusManager.LOG);
	}
}