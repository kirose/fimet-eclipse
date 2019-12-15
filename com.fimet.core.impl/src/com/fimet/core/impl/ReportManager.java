package com.fimet.core.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import com.fimet.commons.console.Console;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.core.Activator;
import com.fimet.core.IReport;
import com.fimet.core.IReportManager;
import com.fimet.core.IUseCaseReportManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.UseCaseReport;
import com.fimet.core.usecase.IReportDataMapper;
import com.fimet.core.usecase.NullReportDataMapper;

public class ReportManager implements IReportManager {

	public static final String EXTENSION_ID = "com.fimet.report.UseCase";
	private HashMap<String, Class<? extends IReport>> reports = new HashMap<>();
	private IUseCaseReportManager useCaseReportManager = Manager.get(IUseCaseReportManager.class);
	private IReportDataMapper reportDataMapper;
	@SuppressWarnings("unchecked")
	public ReportManager() {
		IConfigurationElement[] reports = getReportsConfigurationElement();
		if (reports != null && reports.length > 0) {
			for (IConfigurationElement e : reports) {
				try {
					if ("mapper".equals(e.getName())) {
						Bundle plugin = PluginUtils.startPlugin(e.getContributor().getName());
						Class<?> clazz = plugin.loadClass(e.getAttribute("class"));
						reportDataMapper = (IReportDataMapper)clazz.newInstance(); 
					} else if ("report".equals(e.getName())) {
						Bundle plugin = PluginUtils.startPlugin(e.getContributor().getName());
						String type = e.getAttribute("type");
						Class<?> clazz = plugin.loadClass(e.getAttribute("class"));
						this.reports.put(type.toUpperCase(), (Class<? extends IReport>)clazz);
					}
				} catch (Exception ex) {
					Activator.getInstance().warning("Extension "+EXTENSION_ID,ex);
				}
			}
		}
		if (!this.reports.containsKey(XLSX)){
			this.reports.put(XLSX, com.fimet.core.impl.report.ReportXlsx.class);
		}
		if (reportDataMapper == null) {
			reportDataMapper = new NullReportDataMapper();
		}
	}
	private IConfigurationElement[] getReportsConfigurationElement() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(EXTENSION_ID);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				if ("reports".equals(e.getName())) {
					return e.getChildren();
				}
			}
		}
		return null;
	}
	@Override
	public void free() {}

	@Override
	public void report(String extenssion, IProject project) {
		if (reports.containsKey(extenssion)) {
			try {
				String path = project.getLocation().toString();
				path = path + "/FIMET."+extenssion.toLowerCase();
				List<UseCaseReport> tasks = useCaseReportManager.findByProject(project.getName());
				Console.getInstance().info(ReportManager.class,"Creating FIMET."+extenssion.toLowerCase()+" with "+tasks.size()+" use cases for project "+project.getName());
				IReport report = reports.get(extenssion).newInstance();
				report.report(project, new File(path), tasks);
				project.refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			} catch (Exception e) {
				Activator.getInstance().error("Error creating FIMET.xlsx for project "+project.getName(), e);
			}
		}
	}

	@Override
	public void saveState() {
		
	}
	@Override
	public IReportDataMapper getReportDataMapper() {
		return reportDataMapper;
	}

}
