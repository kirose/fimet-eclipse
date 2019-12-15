package com.fimet.core.impl.commands.sim_queue;

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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.impl.view.sim_queue.SimQueueView;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class RunSimQueueCmd extends AbstractHandler {
	private static String SIM_QUEUE_EXTENSION = Messages.NewTransactionWizard_SimQueueFileExtension; 
    public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = ViewUtils.getSelection();
		if (selection != null && selection instanceof org.eclipse.jface.viewers.ITreeSelection) {
			org.eclipse.jface.viewers.ITreeSelection sel = (org.eclipse.jface.viewers.ITreeSelection)selection;
			List<?> list = sel.toList();
			if (list != null && !list.isEmpty()) {
				List<IResource> resources = new ArrayList<>();
				for(Object o : list) {
					if (o instanceof IResource) {
						if (SIM_QUEUE_EXTENSION.equals(((IResource) o).getLocation().getFileExtension())) {
							resources.add((IResource) o);
						}
					}
				}
				run(resources);
			}
		} else {
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editorPart != null && editorPart.getEditorInput() != null && editorPart.getEditorInput() instanceof IFileEditorInput) {
				List<IResource> resources = new ArrayList<>();
				if (SIM_QUEUE_EXTENSION.equals(((IFileEditorInput)editorPart.getEditorInput()).getFile().getFileExtension())) {
					resources.add(((IFileEditorInput)editorPart.getEditorInput()).getFile());
				}
				run(resources);
			}
		}
		return null;
    }
    private void run(List<IResource> resources) {
		if (!resources.isEmpty()) {
			try {
				IViewPart viewPart = ViewUtils.getViewAndShowById(ViewUtils.SIM_QUEUE_ID);
				if (viewPart != null) {
					SimQueueView view = (SimQueueView) viewPart;
					view.onAdd(resources);
				}
			} catch (UseCaseException e) {
				 MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Sim Queue Exception",e.getMessage());
			}
		}
    }
}