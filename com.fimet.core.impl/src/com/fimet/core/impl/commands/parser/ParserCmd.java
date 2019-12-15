package com.fimet.core.impl.commands.parser;

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

import com.fimet.core.ISO8583.parser.IMessage;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.impl.Activator;
import com.fimet.parser.util.ParserUtils;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ParserCmd extends AbstractHandler {
	private IParser parser;
	public ParserCmd(IParser parser, boolean withComments) {
		this.parser = parser;
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
									IMessage msg = ParserUtils.parseMessage(selection.getText(), parser);
									String msgParsed = msg.toJson();
									document.replace(selection.getOffset(), selection.getLength(), msgParsed);
									selectionProvider.setSelection(new TextSelection(document,selection.getOffset(),msgParsed.length()));
								} catch (Exception e) {
									Activator.getInstance().warning("Error parsing "+parser+" message", e);
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
    public boolean isHandled() {
    	return true;
    }
    public static void main(String[] args) {
		System.out.println(Integer.parseInt("9D", 16));
		System.out.println((char)Integer.parseInt("9D", 16));
		String c = ""+((char)Integer.parseInt("9D", 16));
		System.out.println(Integer.toHexString((byte)c.charAt(0)));
		System.out.println(String.format("%02X", (byte)c.charAt(0)));
	}
}