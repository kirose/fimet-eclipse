package com.fimet.core.impl.preferences.parser;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
//import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.fimet.commons.exception.FieldFormatException;
import com.fimet.commons.history.History;
import com.fimet.commons.preference.IPreference;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Parser;
import com.fimet.core.impl.Activator;

public class ParserPage extends PreferencePage implements IWorkbenchPreferencePage {
	public static final String ID = "com.fimet.preferences.ParsersPage";
	private ParserTable table;
	private IParserManager parserManager = Manager.get(IParserManager.class);
	private History<Parser> history;
	Button btnApply;
    public ParserPage() {
        noDefaultAndApplyButton();
        history = new History<Parser>();
    }

    @Override
    protected final Control createContents(Composite parent) {
        Color widgetBackground = parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
        parent.setBackground(widgetBackground);
		GridLayout layout = new GridLayout(1,true);
        layout.horizontalSpacing = 1;
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        gd.heightHint = 400;
        parent.setLayout(layout);
		parent.setLayoutData(gd);
		
		
        gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
        layout = new GridLayout(1,true);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
        
		gd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd.heightHint = 200;
		table = new ParserTable(this, composite);
		table.getTable().getTable().setLayoutData(gd);
		table.setInput(parserManager.getEntities());

    	Composite cmpButtons = new Composite(parent, SWT.NONE);
    	layout = new GridLayout(2,false);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	cmpButtons.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,1,1));
    	cmpButtons.setLayout(layout);
    	new Label(cmpButtons, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnApply = new Button(cmpButtons, SWT.NONE);
		btnApply.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnApply.setText("    Apply    ");
		btnApply.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
        return composite;
    }
	public void onNew() {
		ParserDialog dialog = new ParserDialog(getShell(), SWT.NONE);
		dialog.open();
		Parser sm = dialog.getParser();
		if (sm != null) {
			table.add(sm);
			history.insert(sm);
		}
	}
	public void onEdit() {
		if (table.getSelected() != null) {
			ParserDialog dialog = new ParserDialog(table.getSelected(), getShell(), SWT.NONE);
			dialog.open();
			Parser sm = dialog.getParser();
			if (sm != null) {
				table.update(sm);
				history.update(sm);
			}
		} else {
			onNew();
		}
	}
	public void onDelete() {
		if (table.getSelected() != null) {
			Parser p = table.getSelected();
			if (askDeleteParser(p)) {
				table.delete(p);
				history.delete(p);
			}
		}
	}
	@Override
    public void init(IWorkbench workbench) {
    }
	@Override
    public boolean performOk() {
		try {
			commit();
			return super.performOk();
		} catch (FieldFormatException e) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Field Format", e.getMessage());
			Activator.getInstance().warning(e.getMessage(), e);
			return false;
		}
    }
	public void commit() {
		parserManager.commit(history);
		history = new History<Parser>();
	}
	private boolean askDeleteParser(Parser node) {
		//IPreferenceStore store = IDEWorkbenchPlugin.getDefault().getPreferenceStore();
		IPreferenceStore store = Activator.getInstance().getPreferenceStore();
		boolean delete = store.getBoolean(IPreference.DELETE_PARSER);
		if (delete) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			getShell(), 
			"Delete Parser",
			"Do you want to delete the message simulator \""+node.getName()+"\"?",
			"Don't ask again.",
			false,
			store,
			IPreference.DELETE_PARSER
		);
		delete = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && delete) {
			store.setValue(IPreference.DELETE_PARSER, delete);
		}
		return delete;
	}
}
