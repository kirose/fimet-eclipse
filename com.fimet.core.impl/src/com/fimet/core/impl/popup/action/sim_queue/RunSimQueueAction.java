package com.fimet.core.impl.popup.action.sim_queue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
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
public class RunSimQueueAction implements IObjectActionDelegate {

	private static String SIM_QUEUE_EXTENSION = Messages.NewTransactionWizard_SimQueueFileExtension;
	private List<IResource> resources;
	private Shell shell;
	@Override
	public void run(IAction action) {
		if (resources == null || resources.isEmpty()) {
			return;
		}
		run(resources);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection != null && selection instanceof org.eclipse.jface.viewers.ITreeSelection) {
			org.eclipse.jface.viewers.ITreeSelection sel = (org.eclipse.jface.viewers.ITreeSelection)selection;
			List<?> list = sel.toList();
			if (list != null && !list.isEmpty()) {
				resources = new ArrayList<>();
				for(Object o : list) {
					if (o instanceof IResource) {
						if (SIM_QUEUE_EXTENSION.equals(((IResource) o).getLocation().getFileExtension())) {
							resources.add((IResource) o);
						}
					}
				}
			}
		} else {
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editorPart != null && editorPart.getEditorInput() != null && editorPart.getEditorInput() instanceof IFileEditorInput) {
				resources = new ArrayList<>();
				if (SIM_QUEUE_EXTENSION.equals(((IFileEditorInput)editorPart.getEditorInput()).getFile().getLocation().getFileExtension())) {
					resources.add((IResource)((IFileEditorInput)editorPart.getEditorInput()).getFile());
				}
			}
		}
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
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
}
