package com.fimet.cfg.commands.messageiso;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;

import com.fimet.cfg.view.messageiso.MessageIsoView;
import com.fimet.commons.exception.UseCaseException;
import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.entity.sqlite.MessageIso;

import org.eclipse.core.commands.AbstractHandler;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageIsoClipboardCmd extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IViewPart viewPart = ViewUtils.getViewAndShowById(ViewUtils.MESSAGE_ISO_ID);
			if (viewPart != null) {
				MessageIsoView view = (MessageIsoView) viewPart;
				List<MessageIso> msgs = view.getTable().getSelectedMessages();
				StringBuilder s = new StringBuilder();
				for (MessageIso msg : msgs) {
					s.append(msg.getMessage()).append("0A");
				}
				setToClipboard(s.toString());
			}
		} catch (UseCaseException e) {
			 MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Message Iso Exception",e.getMessage());
		}
		return null;
	}
    protected void setToClipboard(String value) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(value);
        clipboard.setContents(selection, selection);
   }
}