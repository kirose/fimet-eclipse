package com.fimet.core.impl.popup.action.converter;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

import com.fimet.core.impl.Activator;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public abstract class ConvertAbstractAction implements IObjectActionDelegate {
	@Override
	public void run(IAction action) {
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
									//IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
									document.replace(selection.getOffset(), selection.getLength(), convert);
									//org.eclipse.jface.text.TextSelection
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
	}
	abstract protected String convert(String text);
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
