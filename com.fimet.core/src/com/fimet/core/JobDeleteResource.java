package com.fimet.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.fimet.commons.console.Console;

public class JobDeleteResource extends Job {
	private IProject project;
	private String path;
	
	public JobDeleteResource(IProject project, String path) {
		super("Delete resource from Project "+project);
		this.project = project;
		this.path = path;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			if (path != null) {
				IResource resource = project.findMember(path);
				if (resource != null) {
					resource.delete(true, monitor);
				} else {
					Console.getInstance().info(JobDeleteResource.class,"There is not exists " +project.getName() + "/" + path+", nothing to do");
				}
			}
		} catch (CoreException e) {
			return new Status(Status.ERROR, Activator.PLUGIN_ID, 1, "Cannot delete resource from project "+project, e);
		}
		return Status.OK_STATUS;
	}
}
