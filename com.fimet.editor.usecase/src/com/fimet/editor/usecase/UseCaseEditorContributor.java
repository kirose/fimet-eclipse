package com.fimet.editor.usecase;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;

import com.fimet.editor.usecase.page.SourcePage;

public class UseCaseEditorContributor extends MultiPageEditorActionBarContributor {
	
	public static final int DEFAULT_WIDTH_IN_CHARS= 14;
	private TextEditorActionContributor sourceEditorContributor;
	public UseCaseEditorContributor () {
		super();
		sourceEditorContributor = new TextEditorActionContributor();
	}
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
		sourceEditorContributor.init(bars, page);
	}
	@Override
    public void contributeToMenu(IMenuManager menuManager) {
    }
	@Override
    public void contributeToToolBar(IToolBarManager toolBarManager) {
    }
	@Override
    public void contributeToCoolBar(ICoolBarManager coolBarManager) {
    }
	@Override
	public void contributeToStatusLine(IStatusLineManager statusLineManager) {
		super.contributeToStatusLine(statusLineManager);
	}
	@Override
	public void setActiveEditor(IEditorPart editor) {
		super.setActiveEditor(editor);
	} 
	protected final IAction getAction(ITextEditor editor, String actionId) {
		return (editor == null || actionId == null ? null : editor.getAction(actionId));
	}
	@Override
	public void setActivePage(IEditorPart activeEditor) {
		if (activeEditor != null && activeEditor instanceof SourcePage) {
			sourceEditorContributor.setActiveEditor(activeEditor);
		}
	}
}
