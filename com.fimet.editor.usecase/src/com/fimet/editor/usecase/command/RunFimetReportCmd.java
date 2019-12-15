package com.fimet.editor.usecase.command;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.fimet.commons.utils.PluginUtils;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.IReportManager;
import com.fimet.core.Manager;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class RunFimetReportCmd extends AbstractHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = ViewUtils.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection)selection;
			if (ss != null && ss.getFirstElement() instanceof IProject) {
				IProject project = (IProject) ss.getFirstElement();
				try {
					if (project.hasNature(PluginUtils.FIMET_NATURE)) {
						ThreadUtils.runAcync(()->{
							createReportExcel(project);
						});
					}
				} catch (CoreException e) {
				}
			}
		}
		return null;
    }
	private void createReportExcel(IProject project) {
		Manager.get(IReportManager.class).report(IReportManager.XLSX, project);
    }
}