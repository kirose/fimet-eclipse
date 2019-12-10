package com.fimet.editor.usecase.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.impl.swt.msgiso.MessageIsoSaveDialog;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SaveUseCaseCmd extends AbstractHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	IResource selection = getSelection();
    	if (selection != null && PlatformUI.getWorkbench() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
			MessageIsoSaveDialog dialog = new MessageIsoSaveDialog(
				selection,
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				SWT.NONE
			);
			dialog.open();
    	}
		return null;
    }
    private IResource getSelection() {
		ISelection selection = ViewUtils.getSelection();
		if (selection != null && selection instanceof org.eclipse.jface.viewers.ITreeSelection) {
			Object sel = ((org.eclipse.jface.viewers.ITreeSelection)selection).getFirstElement();
			if (sel instanceof IResource) {
				return (IResource)sel;
			}
		} else {
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editorPart != null && editorPart.getEditorInput() != null && editorPart.getEditorInput() instanceof IFileEditorInput) {
				return((IFileEditorInput)editorPart.getEditorInput()).getFile();
			}
		}
		return null;
    } 
}