package com.fimet.editor.usecase.command;


import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.IReportManager;
import com.fimet.core.Manager;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class RunFimeteReportAction implements IObjectActionDelegate {

	private IProject project;
	//private Shell shell;

	@Override
	public void run(IAction action) {
		ThreadUtils.runAcync(()->{
			createReportExcel();
		});
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection)selection;
			if (ss != null && ss.getFirstElement() instanceof IProject) {
				project = (IProject) ss.getFirstElement();
			}
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		//shell = targetPart.getSite().getShell();
	}
	private void createReportExcel() {
		Manager.get(IReportManager.class).report(IReportManager.XLSX, project);
    }
}
