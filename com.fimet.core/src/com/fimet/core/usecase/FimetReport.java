package com.fimet.core.usecase;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.fimet.commons.exception.ReportException;
import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.messages.Messages;
import com.fimet.core.IFimetReport;
import com.fimet.core.IReportManager;
import com.fimet.core.IUseCaseExecutorManager;
import com.fimet.core.Manager;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * Esta clase administra la generacion de reporte de F8
 */
public class FimetReport implements IFimetReport {

	private static String USE_CASE_EXTENSION = Messages.NewTransactionWizard_UseCaseFileExtension;
	private IProject project;
	private List<IResource> resources;
	private Shell shell;
	private static IUseCaseExecutorManager useCaseManager = Manager.get(IUseCaseExecutorManager.class);

	public FimetReport(IProject project, Shell shell) {
		super();
		if (project == null) {
			throw new NullPointerException();
		}
		this.project = project;
		this.resources = new ArrayList<>();
		this.shell = shell;//PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		if (!Manager.isManaged(IReportManager.class)) {
			throw new ReportException("There is not configured ReportManager, you create an implementation of "+IReportManager.class.getName());
		}
	}

	public void run() {
		 
		 IContainer useCasesContainer = (IContainer) project.findMember("useCases");
		 if (useCasesContainer != null) {
			 resources = getUseCases(useCasesContainer);
			 if (resources.isEmpty()) {
				 MessageDialog.openInformation(shell, "Error", "There not exists use cases inside \""+project.getName()+"/useCases\"");
			 } else {
				try {
					useCaseManager.runF8(this);
				} catch (UseCaseException e) {
					MessageDialog.openError(shell,"F8 Exception",e.getMessage());
				}
			 }
		 } else {
			MessageDialog.openError(shell, "Error", "Cannot found folder \""+project.getName()+"/useCases\"");
		 }
	}
	@Override
	public void onFinishExecution() {
		Manager.get(IReportManager.class).report(IReportManager.XLSX, project);
	}
	private List<IResource> getUseCases(IContainer parent) {
		
		IResource[] members = null;
		try {
			members = parent.members();
		} catch (CoreException e) {}
		if (members != null) {
			for (IResource member : members) {
				if (member instanceof IContainer) {
					getUseCases((IContainer)member);
				} else if (member instanceof IFile) {
					String fileExtension = ((IFile) member).getFileExtension();
					if (fileExtension != null && fileExtension.toLowerCase().equals(USE_CASE_EXTENSION)) {
						resources.add((IFile) member);
					}
		      	}
	    	}
		}
		
		return resources;
	}

	@Override
	public List<IResource> getResources() {
		return resources;
	}
}
