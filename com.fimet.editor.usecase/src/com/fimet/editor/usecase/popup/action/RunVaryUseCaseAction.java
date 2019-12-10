package com.fimet.editor.usecase.popup.action;


import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.fimet.commons.utils.ViewUtils;
import com.fimet.editor.usecase.wizard.vary.VaryUseCaseWizard;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class RunVaryUseCaseAction implements IObjectActionDelegate {

	private IResource resource;
	private Shell shell;
	@Override
	public void run(IAction action) {
		if (resource == null) {
			return;
		}
		ISelection selection = ViewUtils.getSelection();
		if (selection instanceof IStructuredSelection) {
			ViewUtils.openWizard(VaryUseCaseWizard.ID, (IStructuredSelection)selection);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection)selection;
			if (ss != null) {
				if (ss instanceof org.eclipse.jface.viewers.ITreeSelection) {
					org.eclipse.jface.viewers.ITreeSelection sel = (org.eclipse.jface.viewers.ITreeSelection)selection;
					if (sel.getFirstElement() instanceof IResource) {
						resource = (IResource) sel.getFirstElement();
					}
				} else if (ss.getFirstElement() instanceof IResource) {
					resource = (IResource) ss.getFirstElement();
				} else if (ss.getFirstElement() instanceof org.eclipse.ui.part.FileEditorInput) {
					resource = (IResource)((org.eclipse.ui.part.FileEditorInput)ss.getFirstElement()).getFile();
				}
			}
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}


}
