package com.fimet.core.impl.swt.msg.cmd;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionService;

import com.fimet.commons.utils.ViewUtils;
import com.fimet.core.impl.commands.ClipboardAbstractCmd;
import com.fimet.core.impl.swt.msg.FieldNode;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldNodeClipboardCmd extends ClipboardAbstractCmd {
    public Object execute(ExecutionEvent event) throws ExecutionException {
    	ISelectionService selectionService = ViewUtils.getSelectionService();
    	if (selectionService != null && selectionService.getSelection() instanceof StructuredSelection) {
    		Object element = ((StructuredSelection)selectionService.getSelection()).getFirstElement();
    		if (element instanceof FieldNode) {
    			FieldNode f = (FieldNode)element;
    			setToClipboard(f.getValue());
    		}
    	}
        return null;
    }
}