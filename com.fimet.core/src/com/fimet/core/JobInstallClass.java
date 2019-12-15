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
import com.fimet.commons.utils.FileUtils;
import com.fimet.core.IClassLoaderManager;
import com.fimet.core.Manager;

public class JobInstallClass extends Job {
	private IClassLoaderManager classLoaderManager = Manager.get(IClassLoaderManager.class);
	private IProject project;
	private int index;
	private String className;
	public JobInstallClass(IProject project, String className) {
		super("Install class from Project "+project+ " '"+className+"'");
		this.project = project;
		this.className = className;
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
			Console.getInstance().debug(JobInstallClass.class, "Installing class from bin folder");
			index = ((IFolder)bin).getLocation().toString().length();
			IResource resource = ((IFolder)bin).findMember(className.replace('.', '/')+".class");
			if (resource != null) {
				String path = resource.getLocation().toString();
				String className = path.substring(index+1,path.lastIndexOf('.')).replace('\\', '/').replace('/', '.');
				byte[] contents = FileUtils.readBytesContents(new File(path));
				Console.getInstance().debug(JobInstallClass.class, "Installing class "+className);
				classLoaderManager.installClassBin(className, contents);
			} else {
				Console.getInstance().error(JobInstallClass.class,"Cannot found class in bin folder "+className);
			}
		} else {
			Console.getInstance().debug(JobInstallClass.class, "bin folder is null");
		}
	}
}
