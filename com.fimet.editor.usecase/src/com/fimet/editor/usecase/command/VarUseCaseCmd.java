package com.fimet.editor.usecase.command;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.fimet.commons.utils.ViewUtils;
import com.fimet.editor.usecase.wizard.vary.VaryUseCaseWizard;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class VarUseCaseCmd extends AbstractHandler {
    public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = ViewUtils.getSelection();
		if (selection != null && selection instanceof IStructuredSelection) {
			ViewUtils.openWizard(VaryUseCaseWizard.ID, (IStructuredSelection)selection);
		}
		return null;
    }
}