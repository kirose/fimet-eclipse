package com.fimet.core.impl.commands.format;


import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.Activator;
import com.fimet.parser.util.ParserUtils;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FormatJsonToIsoCmd extends AbstractHandler {
	public FormatJsonToIsoCmd() {
		super();
	}
    public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			//get editor
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			IDocument document = null;
			if (editorPart instanceof org.eclipse.ui.texteditor.ITextEditor) {
				document = ((org.eclipse.ui.texteditor.ITextEditor) editorPart).getDocumentProvider().getDocument(editorPart.getEditorInput());
			}
			if (editorPart != null && document != null) {
				IEditorSite iEditorSite = editorPart.getEditorSite();
				if (iEditorSite != null) {
					ISelectionProvider selectionProvider = iEditorSite.getSelectionProvider();
					if (selectionProvider != null) {
						ISelection iSelection = selectionProvider.getSelection();
						if (iSelection instanceof ITextSelection) {
							ITextSelection selection = (ITextSelection) iSelection;
							if (selection.getText() != null && selection.getText().length() > 0) {
								try {
									Message msg = ParserUtils.parseJsonMessage(selection.getText());
									String msgFormated = new String(ParserUtils.formatMessage(msg));
									document.replace(selection.getOffset(), selection.getLength(), msgFormated);
									selectionProvider.setSelection(new TextSelection(document,selection.getOffset(),msgFormated.length()));									
								} catch (Exception e) {
									Activator.getInstance().warning("Error formating message", e);
									MessageDialog.openError(
										 PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
								         "Parser Message",
								         e.getMessage());
								}

							}
						}
					}
				}
			}
		} catch (Exception e) {}
        return null;
    }

}