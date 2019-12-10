package com.fimet.editor.json;

import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.StatusLineContributionItem;


public class JsonEditorContributor extends TextEditorActionContributor {
	public static final int DEFAULT_WIDTH_IN_CHARS= 14;
	
	public JsonEditorContributor () {
		super();
	}
	@Override
	public void init(IActionBars bars, IWorkbenchPage page) {
		super.init(bars, page);
	}
	@Override
	public void contributeToStatusLine(IStatusLineManager statusLineManager) {
		if (statusLineManager instanceof org.eclipse.jface.action.SubStatusLineManager)
			((org.eclipse.jface.action.SubStatusLineManager)statusLineManager).setVisible(true);
		super.contributeToStatusLine(statusLineManager);
		StatusLineContributionItem sl = new StatusLineContributionItem(ITextEditorActionConstants.STATUS_CATEGORY_INPUT_POSITION, true, DEFAULT_WIDTH_IN_CHARS);
		statusLineManager.add(sl);
	}
}
