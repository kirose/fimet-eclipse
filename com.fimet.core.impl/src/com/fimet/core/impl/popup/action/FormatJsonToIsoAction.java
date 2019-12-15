package com.fimet.core.impl.popup.action;


import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.AbstractTextEditor;

import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.Activator;
import com.fimet.parser.util.ParserUtils;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FormatJsonToIsoAction implements IObjectActionDelegate {
	private Shell shell;
	@Override
	public void run(IAction action) {
		try {
			//get editor
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editorPart != null && editorPart instanceof AbstractTextEditor) {
				AbstractTextEditor editor = (AbstractTextEditor) editorPart;
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
									IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
									document.replace(selection.getOffset(), selection.getLength(), new String(ParserUtils.formatMessage(msg)));
								} catch (Exception e) {
									Activator.getInstance().warning("Error formating message", e);
									 MessageDialog.openInformation(
								         shell,
								         "Parser Message",
								         e.getMessage());
								}

							}
						}
					}
				}
			}
		} catch (Exception e) {}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}


}
