package com.fimet.persistence;

import java.io.File;
import java.util.List;

import org.osgi.framework.BundleContext;

import com.fimet.commons.AbstractActivator;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.UseCaseReport;
import com.fimet.persistence.sqlite.ISQLiteManager;
import com.fimet.persistence.sqlite.dao.UseCaseReportDAO;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivator {
	// The plug-in ID
	public static final String PLUGIN_ID = PluginUtils.PERSISTENCE_PLUGIN_ID;
	
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
		deleteUnusedUseCases();
		if (Manager.get(ISQLiteManager.class) != null)
			Manager.get(ISQLiteManager.class).disconnect();
	}

	private void deleteUnusedUseCases() {
		UseCaseReportDAO dao = UseCaseReportDAO.getInstance();
		List<String> projects = dao.findProjects();
		if (projects != null && !projects.isEmpty()) {
			File file;
			List<UseCaseReport> tasks;
			for (String project : projects) {
				tasks = UseCaseReportDAO.getInstance().findByProject(project);
				for (UseCaseReport task : tasks) {
					file = new File(task.getPath());
					if (!file.exists()) {
						dao.deleteSync(task);
					}
				}
			}
		}
	}
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	public static Activator getInstance() {
		return plugin;
	}

	@Override
	public String getPluginId() {
		return PLUGIN_ID;
	}
}