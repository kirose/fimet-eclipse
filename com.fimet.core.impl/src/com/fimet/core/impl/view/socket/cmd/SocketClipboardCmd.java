package com.fimet.core.impl.view.socket.cmd;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;

import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.impl.commands.ClipboardAbstractCmd;
import com.fimet.core.impl.view.socket.SocketView;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class SocketClipboardCmd extends ClipboardAbstractCmd {
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	System.out.println("Contexts:"+PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(IContextService.class).getActiveContextIds());
    	SocketView view = (SocketView)ViewUtils.getViewById(SocketView.ID);
    	if (view != null) {
    		setToClipboard(view.clipboard());
    	}
        return null;
    }
}