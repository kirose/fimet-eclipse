package com.fimet.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.fimet.commons.console.Console;

public class JobRefreshProject extends Job {
	private IProject project;
	private String folder;
	
	public JobRefreshProject(IProject project, String folder) {
		super("Refresh Project "+project);
		this.project = project;
		this.folder = folder;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			if (folder != null) {
				IResource resource = project.findMember(folder);
				if (resource != null) {
					resource.refreshLocal(IResource.DEPTH_INFINITE, monitor);
				} else {
					Console.getInstance().error(JobRefreshProject.class,"There is not exists " +project.getName() + "/" + folder);
				}
			} else {
				project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			}
		} catch (CoreException e) {
			return new Status(Status.ERROR, Activator.PLUGIN_ID, 1, "Cannot refresh project "+project, e);
		}
		return Status.OK_STATUS;
	}
}
