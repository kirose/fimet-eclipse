package com.fimet.editor.usecase.popup.action;


import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.fimet.core.impl.swt.msgiso.MessageIsoSaveDialog;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SaveUseCaseAction implements IObjectActionDelegate {

	private IResource resource;
	private Shell shell;
	@Override
	public void run(IAction action) {
		if (resource == null) {
			return;
		}
    	if (resource != null && PlatformUI.getWorkbench() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
			MessageIsoSaveDialog dialog = new MessageIsoSaveDialog(
				resource,
				shell,
				SWT.NONE
			);
			dialog.open();
    	}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		resource = getSelection(selection);

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
	private IResource getSelection(ISelection selection) {
		if (selection != null && selection instanceof org.eclipse.jface.viewers.ITreeSelection) {
			Object obj = ((org.eclipse.jface.viewers.ITreeSelection)selection).getFirstElement();
			if (obj instanceof IResource)
				return (IResource)obj;
		} else {
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editorPart != null && editorPart.getEditorInput() != null && editorPart.getEditorInput() instanceof IFileEditorInput) {
				return (IResource)((IFileEditorInput)editorPart.getEditorInput()).getFile();
			}
		}
		return null;
	}
}
