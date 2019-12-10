package com.fimet.editor.stress.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.editor.stress.Activator;
import com.fimet.editor.stress.view.vary.FiledsVariationView;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class VariateParametersCmd extends AbstractHandler {
	private static String USE_CASE_EXTENSION = Messages.NewTransactionWizard_UseCaseFileExtension; 
    public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = ViewUtils.getSelection();
		if (selection != null && selection instanceof StructuredSelection) {
			FiledsVariationView view = (FiledsVariationView)ViewUtils.getViewAndShowById(FiledsVariationView.ID);
	    	if (view != null) {
	    		try {
	    			view.setUseCaseResource(((IResource)((StructuredSelection)selection).getFirstElement()));
	    		} catch (UseCaseException e) {
	    			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Error in variation of parameters",e.getMessage());
	    			Activator.getInstance().warning("Error in variation of parameters", e);
	    		}
	    	}
		} else {
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editorPart != null && editorPart.getEditorInput() != null && editorPart.getEditorInput() instanceof IFileEditorInput) {
				List<IResource> resources = new ArrayList<>();
				if (USE_CASE_EXTENSION.equals(((IFileEditorInput)editorPart.getEditorInput()).getFile().getFileExtension())) {
					FiledsVariationView view = (FiledsVariationView)ViewUtils.getViewAndShowById(FiledsVariationView.ID);
			    	if (view != null) {
			    		try {
			    			view.setUseCaseResource(((IFileEditorInput)editorPart.getEditorInput()).getFile());
			    		} catch (UseCaseException e) {
			    			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Error in variation of parameters",e.getMessage());
			    			Activator.getInstance().warning("Error in variation of parameters", e);
			    		}
			    	}
				}
				if (!resources.isEmpty()) {
				}
			}
		}
        return null;
    }

}