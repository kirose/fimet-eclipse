package com.fimet.editor.usecase.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.IUseCaseExecutorManager;
import com.fimet.core.Manager;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class RunUseCaseCmd extends AbstractHandler {
	private static String USE_CASE_EXTENSION = Messages.NewTransactionWizard_UseCaseFileExtension; 
	private static IUseCaseExecutorManager useCaseManager = Manager.get(IUseCaseExecutorManager.class);
    public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = ViewUtils.getSelection();
		if (selection != null && selection instanceof org.eclipse.jface.viewers.ITreeSelection) {
			org.eclipse.jface.viewers.ITreeSelection sel = (org.eclipse.jface.viewers.ITreeSelection)selection;
			List<?> list = sel.toList();
			if (list != null && !list.isEmpty()) {
				List<IResource> resources = new ArrayList<>();
				for(Object o : list) {
					if (o instanceof IResource) {
						if (USE_CASE_EXTENSION.equals(((IResource) o).getLocation().getFileExtension())) {
							resources.add((IResource) o);
						}
					}
				}
				if (!resources.isEmpty()) {
					try {
						useCaseManager.run(resources);
					} catch (UseCaseException e) {
						 MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Use Case Exception",e.getMessage());
					}
				}
			}
		} else {
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editorPart != null && editorPart.getEditorInput() != null && editorPart.getEditorInput() instanceof IFileEditorInput) {
				List<IResource> resources = new ArrayList<>();
				if (USE_CASE_EXTENSION.equals(((IFileEditorInput)editorPart.getEditorInput()).getFile().getFileExtension())) {
					resources.add(((IFileEditorInput)editorPart.getEditorInput()).getFile());
				}
				if (!resources.isEmpty()) {
					try {
						useCaseManager.run(resources);
					} catch (UseCaseException e) {
						 MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Use Case Exception",e.getMessage());
					}
				}
			}
		}
		return null;
    }
}