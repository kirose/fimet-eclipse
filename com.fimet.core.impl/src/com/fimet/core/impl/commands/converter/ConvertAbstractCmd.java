package com.fimet.core.impl.commands.converter;

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

import com.fimet.core.impl.Activator;

import org.eclipse.ui.texteditor.ITextEditor;



public abstract class ConvertAbstractCmd extends AbstractHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			//get editor
			IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			IDocument document = null;
			if (editorPart instanceof ITextEditor) {
				document = ((ITextEditor) editorPart).getDocumentProvider().getDocument(editorPart.getEditorInput());
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
								String convert = convert(selection.getText());
								if (convert != null) {
									document.replace(selection.getOffset(), selection.getLength(), convert);
									selectionProvider.setSelection(new TextSelection(document,selection.getOffset(),convert.length()));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Activator.getInstance().warning("Error in Convertion", e);
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Convertion Error",e.getMessage());
		}
		return null;
	}
    abstract protected String convert(String text);
}