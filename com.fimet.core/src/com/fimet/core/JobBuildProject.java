package com.fimet.core;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.fimet.commons.console.Console;
import com.fimet.commons.io.FileUtils;
import com.fimet.core.IClassLoaderManager;
import com.fimet.core.Manager;

public class JobBuildProject extends Job {
	private IClassLoaderManager classLoaderManager = Manager.get(IClassLoaderManager.class);
	private IProject project;
	private int index;
	public JobBuildProject(IProject project) {
		super("Build Project");
		this.project = project;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			project.build(IncrementalProjectBuilder.FULL_BUILD, new DoneProgressMonitor(monitor, ()->{
				installClasses();
			}));
		} catch (CoreException e) {
			return new Status(Status.ERROR, Activator.PLUGIN_ID, 1, "Cannot build project "+project, e);
		}
		return Status.OK_STATUS;
	}
	private void installClasses() {
		classLoaderManager.reloadClasesBin();
		IResource bin = project.findMember("bin");
		if (bin != null && bin instanceof IFolder) {
			Console.getInstance().debug(JobBuildProject.class, "Installing classes from bin folder");
			index = ((IFolder)bin).getLocation().toString().length();
			JobBuildProject.this.install(bin);
		} else {
			Console.getInstance().debug(JobBuildProject.class, "bin folder is null");
		}
	}
	private void install(IResource resource) {
		if (resource instanceof IFolder) {
			IResource[] members;
			try {
				members = ((IFolder)resource).members();
				for (IResource iResource : members) {
					install(iResource);
				}
			} catch (CoreException e) {
				Activator.getInstance().error(" Error Building Project Job",e);
			}
		}else {
			String path = resource.getLocation().toString();
			String className = path.substring(index+1,path.lastIndexOf('.')).replace('\\', '/').replace('/', '.');
			byte[] contents = FileUtils.readBytesContents(new File(path));
			Console.getInstance().debug(JobBuildProject.class, "Installing class "+className);
			classLoaderManager.installClassBin(className, contents);
		}
	}
}
