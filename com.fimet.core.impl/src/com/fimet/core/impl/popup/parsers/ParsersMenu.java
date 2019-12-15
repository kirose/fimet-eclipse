package com.fimet.core.impl.popup.parsers;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.Images;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.Activator;
import com.fimet.parser.util.ParserUtils;

public class ParsersMenu extends org.eclipse.jface.action.ContributionItem {
    public ParsersMenu() {
        super("com.fimet.popup.menu.parsers");
    }

    @Override
    public void fill(Menu popup, int index) {
        MenuItem parsersMenu = new MenuItem(popup, SWT.CASCADE, index);
        parsersMenu.setImage(Images.TYPES_IMG.createImage());
        parsersMenu.setText("Parse ...");
        parsersMenu.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {}
        });
        Menu menu = new Menu(popup);
        addParserMenu(menu);
        parsersMenu.setMenu(menu);
    }
    private void addParserMenu(Menu parsresMenu) {
    	List<IParser> parsers = Manager.get(IParserManager.class).getParsers();
    	MenuItem menuItem;
    	//int i = 1;
    	for (IParser p : parsers) {
    		menuItem = new MenuItem(parsresMenu, SWT.PUSH);
    		menuItem.setText(p.getName()+"\t"+p.getKeySequence().replace("M4", "Ctrl").replace("M3", "Alt").replace("M2", "Shift").replace("M1", "Ctrl"));
    		menuItem.setData(p);
    		menuItem.setImage(Images.TYPES_IMG.createImage());
    		//menuItem.setAccelerator(SWT.ALT + (i++));
    		menuItem.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
    				doParse((IParser)((MenuItem)e.getSource()).getData());
    			}
    		});
		}
    }
    private void doParse(IParser parser) {
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
								Message msg = (Message)ParserUtils.parseMessage(selection.getText(), parser);
								String msgParsed = msg.toJson();
								document.replace(selection.getOffset(), selection.getLength(), msgParsed);
								selectionProvider.setSelection(new TextSelection(document,selection.getOffset(),msgParsed.length()));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			Activator.getInstance().warning("Error parsing "+parser+" message", e);
			MessageDialog.openError(
				 PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
		         "Parser Message",
		         e.getMessage());
		}
    }
}
