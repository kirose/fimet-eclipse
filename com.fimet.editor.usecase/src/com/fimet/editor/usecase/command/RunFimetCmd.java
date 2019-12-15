package com.fimet.editor.usecase.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.utils.PluginUtils;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.usecase.FimetReport;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class RunFimetCmd extends AbstractHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = ViewUtils.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection)selection;
			if (ss != null && ss.getFirstElement() instanceof IProject) {
				try {
					IProject project = (IProject) ss.getFirstElement();
					if (project.hasNature(PluginUtils.FIMET_NATURE)) {
						new FimetReport(project, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()).run();
					}
				} catch (Exception e) {}
			}
		}
		return null;
    }
}