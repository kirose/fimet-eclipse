package com.fimet.cfg.commands.messageiso;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.fimet.cfg.view.messageiso.MessageIsoView;
import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.utils.ViewUtils;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageIsoSearchCmd extends org.eclipse.core.commands.AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IViewPart viewPart = ViewUtils.getViewAndShowById(ViewUtils.MESSAGE_ISO_ID);
			if (viewPart != null) {
				MessageIsoView view = (MessageIsoView) viewPart;
				view.doSearch();
			}
		} catch (UseCaseException e) {
			 MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Message Iso Exception",e.getMessage());
		}
		return null;
	}
}