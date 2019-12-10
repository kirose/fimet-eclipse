package com.fimet.core.impl.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.console.Console;
import com.fimet.core.IExtractorManager;
import com.fimet.core.ILogManager;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.impl.swt.TextDecorate;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FimetPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	private static IPreferencesManager preferencesManager = Manager.get(IPreferencesManager.class);
	public static final String ID = "com.fimet.preferences.FimetPreferences";
	private TextDecorate txtBdk;
	private Button btnLevelInfo;
	private Button btnLevelWarning;
	private Button btnLevelError;
	private Button btnLevelDebug;
	
	private Button btnSimQueueWriteAcquirer;
	private Button btnSimQueueReadAcquirer;
	private Button btnSimQueueWriteIssuer;
	private Button btnSimQueueReadIssuer;
	
	private Button btnValidateTypesField;
	private Button btnLogEnable;
	private TextDecorate txtLogGarbageCycle;
	private TextDecorate txtLogReaderCycle;
	private Button btnExtractorEnable;
	private TextDecorate txtExtractorGarbageCycle;
	private TextDecorate txtExtractorReacerCycle;
	
    public FimetPreferencePage() {
        noDefaultAndApplyButton();
    }

    @Override
    protected final Control createContents(Composite parent) {
    	
    	Label label;
    	
        Color widgetBackground = parent.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
        parent.setBackground(widgetBackground);
		GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.horizontalSpacing = 1;
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
        parent.setLayout(layout);
		parent.setLayoutData(gd);
        
		layout = new GridLayout();
        layout.numColumns = 1;
        gd = new GridData(SWT.FILL, SWT.CENTER, true, true);
        gd.heightHint = 300;
        Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(gd);
		
		composite.setFont(parent.getFont());
		
		layout = new GridLayout(3, true);
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        Group groupEncrypt = new Group(composite, SWT.NONE);
        groupEncrypt.setText("Encryption");
        groupEncrypt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        groupEncrypt.setLayout(layout);
		
		label = new Label(groupEncrypt, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("BDK:");

		txtBdk = new TextDecorate(groupEncrypt, SWT.BORDER);
		txtBdk.setText(preferencesManager.getString(IPreferencesManager.BDK));
		txtBdk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtBdk.setValidator((String value)->{return value != null && value.length() > 0;});
		txtBdk.addModifyListener((ModifyEvent e) -> {txtBdk.validateAndMark();});
		txtBdk.valid();
		
		layout = new GridLayout(3, true);
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        Group groupConsole = new Group(composite, SWT.NONE);
        groupConsole.setText("Trace Level");
        groupConsole.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        groupConsole.setLayout(layout);
        int level = preferencesManager.getInt(IPreferencesManager.CONSOLE_LEVEL);
        btnLevelInfo = new Button(groupConsole, SWT.CHECK);
        btnLevelInfo.setText("INFO");
        btnLevelInfo.setSelection(preferencesManager.getBoolean(IPreferencesManager.VALIDATE_TYPES_FIELD_FORMAT));
        btnLevelInfo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnLevelInfo.setSelection((level & Console.INFO) > 0);

        btnLevelWarning = new Button(groupConsole, SWT.CHECK);
        btnLevelWarning.setText("WARNING");
        btnLevelWarning.setSelection(preferencesManager.getBoolean(IPreferencesManager.VALIDATE_TYPES_FIELD_FORMAT));
        btnLevelWarning.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnLevelWarning.setSelection((level & Console.WARNING) > 0);
        
        btnLevelError = new Button(groupConsole, SWT.CHECK);
        btnLevelError.setText("ERROR");
        btnLevelError.setSelection(preferencesManager.getBoolean(IPreferencesManager.VALIDATE_TYPES_FIELD_FORMAT));
        btnLevelError.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnLevelError.setSelection((level & Console.ERROR) > 0);
        
        btnLevelDebug = new Button(groupConsole, SWT.CHECK);
        btnLevelDebug.setText("DEBUG");
        btnLevelDebug.setSelection(preferencesManager.getBoolean(IPreferencesManager.VALIDATE_TYPES_FIELD_FORMAT));
        btnLevelDebug.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        btnLevelDebug.setSelection((level & Console.DEBUG) > 0);
        
		new Label(groupConsole, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(groupConsole, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		layout = new GridLayout(3, true);
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        Group groupDates = new Group(composite, SWT.NONE);
        groupDates.setText("SimQueues");
        groupDates.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        groupDates.setLayout(layout);
		
		btnSimQueueWriteAcquirer = new Button(groupDates, SWT.CHECK);
		btnSimQueueWriteAcquirer.setText("Generate On Write Acqurier");
		btnSimQueueWriteAcquirer.setSelection(preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ACQUIRER));
		btnSimQueueWriteAcquirer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		new Label(groupDates, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnSimQueueReadAcquirer = new Button(groupDates, SWT.CHECK);
		btnSimQueueReadAcquirer.setText("Generate On Read Acqurier");
		btnSimQueueReadAcquirer.setSelection(preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_READ_ACQUIRER));
		btnSimQueueReadAcquirer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnSimQueueWriteIssuer = new Button(groupDates, SWT.CHECK);
		btnSimQueueWriteIssuer.setText("Generate On Write Issuer");
		btnSimQueueWriteIssuer.setSelection(preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ISSUER));
		btnSimQueueWriteIssuer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		new Label(groupDates, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnSimQueueReadIssuer = new Button(groupDates, SWT.CHECK);
		btnSimQueueReadIssuer.setText("Generate On Read Issuer");
		btnSimQueueReadIssuer.setSelection(preferencesManager.getBoolean(IPreferencesManager.CREATE_SIMQUEUE_READ_ISSUER));
		btnSimQueueReadIssuer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		layout = new GridLayout(3, true);
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        Group groupParsers = new Group(composite, SWT.NONE);
        groupParsers.setText("Parsers");
        groupParsers.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        groupParsers.setLayout(layout);
			
		
		btnValidateTypesField = new Button(groupParsers, SWT.CHECK);
		btnValidateTypesField.setText("Validate Types");
		btnValidateTypesField.setSelection(preferencesManager.getBoolean(IPreferencesManager.VALIDATE_TYPES_FIELD_FORMAT));
		btnValidateTypesField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		new Label(groupParsers, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		if (Manager.isManaged(ILogManager.class)) {
			layout = new GridLayout(4, true);
	        layout.horizontalSpacing = 10;
	        layout.marginWidth = 10;
	        Group groupLogs = new Group(composite, SWT.NONE);
	        groupLogs.setText("Logs");
	        groupLogs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	        groupLogs.setLayout(layout);
			
	        btnLogEnable = new Button(groupLogs, SWT.CHECK);
	        btnLogEnable.setText("Logs Enabled");
	        btnLogEnable.setSelection(preferencesManager.getBoolean(IPreferencesManager.LOG_ENABLE, Boolean.FALSE));
	        btnLogEnable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			new Label(groupLogs, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	        
			label = new Label(groupLogs, SWT.NONE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			label.setText("Garbage Cycle Time (sec):");
	        
			txtLogGarbageCycle = new TextDecorate(groupLogs, SWT.BORDER);
			txtLogGarbageCycle.setText(""+preferencesManager.getInt(IPreferencesManager.LOG_GARBAGE_CYCLE_TIME_SEC,14));
			txtLogGarbageCycle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			txtLogGarbageCycle.setValidator((String value)->{return value != null && value.length() > 0 && value.matches("[0-9]+");});
			txtLogGarbageCycle.addModifyListener((ModifyEvent e) -> {txtLogGarbageCycle.validateAndMark();});
			txtLogGarbageCycle.valid();
			
			label = new Label(groupLogs, SWT.NONE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			label.setText("Reader Cycle Time (sec)");
	        
			txtLogReaderCycle = new TextDecorate(groupLogs, SWT.BORDER);
			txtLogReaderCycle.setText(""+preferencesManager.getInt(IPreferencesManager.LOG_READER_CYCLE_TIME_SEC,4));
			txtLogReaderCycle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			txtLogReaderCycle.setValidator((String value)->{return value != null && value.length() > 0 && value.matches("[0-9]+");});
			txtLogReaderCycle.addModifyListener((ModifyEvent e) -> {txtLogReaderCycle.validateAndMark();});
			txtLogReaderCycle.valid();
		}
		if (Manager.isManaged(IExtractorManager.class)) {
			layout = new GridLayout(4, true);
	        layout.horizontalSpacing = 10;
	        layout.marginWidth = 10;
	        Group groupExtractor = new Group(composite, SWT.NONE);
	        groupExtractor.setText("Extractor");
	        groupExtractor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	        groupExtractor.setLayout(layout);
			
	        btnExtractorEnable = new Button(groupExtractor, SWT.CHECK);
	        btnExtractorEnable.setText("Extractor Enabled");
	        btnExtractorEnable.setSelection(preferencesManager.getBoolean(IPreferencesManager.EXTRACTOR_ENABLE, Boolean.FALSE));
	        btnExtractorEnable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			
			new Label(groupExtractor, SWT.NONE).setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));        
	        
			label = new Label(groupExtractor, SWT.NONE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			label.setText("Garbage Cycle Time (sec):");
	
			txtExtractorGarbageCycle = new TextDecorate(groupExtractor, SWT.BORDER);
			txtExtractorGarbageCycle.setText(""+preferencesManager.getInt(IPreferencesManager.EXTRACTOR_GARBAGE_CYCLE_TIME_SEC,50));
			txtExtractorGarbageCycle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			txtExtractorGarbageCycle.setValidator((String value)->{return value != null && value.matches("[0-9]+");});
			txtExtractorGarbageCycle.addModifyListener((ModifyEvent e) -> {txtExtractorGarbageCycle.validateAndMark();});
			txtExtractorGarbageCycle.valid();
	
			
			label = new Label(groupExtractor, SWT.NONE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			label.setText("Reader Cycle Time (sec)");
	
			txtExtractorReacerCycle = new TextDecorate(groupExtractor, SWT.BORDER);
			txtExtractorReacerCycle.setText(""+preferencesManager.getInt(IPreferencesManager.EXTRACTOR_READER_CYCLE_TIME_SEC,5));
			txtExtractorReacerCycle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			txtExtractorReacerCycle.setValidator((String value)->{return value != null && value.matches("[0-9]+");});
			txtExtractorReacerCycle.addModifyListener((ModifyEvent e) -> {txtExtractorGarbageCycle.validateAndMark();});
			txtExtractorReacerCycle.valid();
		}
        return composite;
    }
    private void updateLevelTrace() {
    	int level = (btnLevelInfo.getSelection() ? Console.INFO : 0)+
    			(btnLevelWarning.getSelection() ? Console.WARNING : 0)+
    			(btnLevelError.getSelection() ? Console.ERROR : 0)+
    			(btnLevelDebug.getSelection() ? Console.DEBUG : 0);
    	preferencesManager.save(IPreferencesManager.CONSOLE_LEVEL, level);
    	Console.setLevel(level);
	}
    public boolean isValid() {
    	return (
			Manager.isManaged(ILogManager.class) ?
			txtLogReaderCycle.isValid() && 
			txtLogGarbageCycle.isValid() : true
    	)
		&&
		(
			Manager.isManaged(IExtractorManager.class) ? 
			txtExtractorReacerCycle.isValid() && 
			txtExtractorGarbageCycle.isValid() : true
		);
    }
    private void updatePreferences() {
    	
    	preferencesManager.save(IPreferencesManager.BDK, txtBdk.getText());
    	preferencesManager.save(IPreferencesManager.CREATE_SIMQUEUE_READ_ACQUIRER, btnSimQueueReadAcquirer.getSelection());
    	preferencesManager.save(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ACQUIRER, btnSimQueueWriteAcquirer.getSelection());
    	preferencesManager.save(IPreferencesManager.CREATE_SIMQUEUE_READ_ISSUER, btnSimQueueReadIssuer.getSelection());
    	preferencesManager.save(IPreferencesManager.CREATE_SIMQUEUE_WRITE_ISSUER, btnSimQueueWriteIssuer.getSelection());
    	preferencesManager.save(IPreferencesManager.VALIDATE_TYPES_FIELD_FORMAT, btnValidateTypesField.getSelection());
    	preferencesManager.save(IPreferencesManager.LOG_ENABLE, btnLogEnable.getSelection());
    	preferencesManager.save(IPreferencesManager.LOG_GARBAGE_CYCLE_TIME_SEC, Integer.parseInt(txtLogGarbageCycle.getText()));
    	preferencesManager.save(IPreferencesManager.LOG_READER_CYCLE_TIME_SEC, Integer.parseInt(txtLogReaderCycle.getText()));
    	preferencesManager.save(IPreferencesManager.EXTRACTOR_ENABLE, btnExtractorEnable.getSelection());
    	preferencesManager.save(IPreferencesManager.EXTRACTOR_GARBAGE_CYCLE_TIME_SEC, Integer.parseInt(txtExtractorGarbageCycle.getText()));
    	preferencesManager.save(IPreferencesManager.EXTRACTOR_READER_CYCLE_TIME_SEC, Integer.parseInt(txtExtractorReacerCycle.getText()));

    	if (Manager.isManaged(ILogManager.class)) {
	    	Manager.get(ILogManager.class).setReaderCycleTime(Integer.parseInt(txtLogReaderCycle.getText()));
	    	Manager.get(ILogManager.class).setGarbageCycleTime(Integer.parseInt(txtLogGarbageCycle.getText()));
    	}    	
    	if (Manager.isManaged(IExtractorManager.class)) {
	    	Manager.get(IExtractorManager.class).setReaderCycleTime(Integer.parseInt(txtExtractorReacerCycle.getText()));
	    	Manager.get(IExtractorManager.class).setGarbageCycleTime(Integer.parseInt(txtExtractorGarbageCycle.getText()));
    	}
    }
	@Override
    public void init(IWorkbench workbench) {
        // Do nothing
    }
    @Override
    public boolean performOk() {
    	if (isValid()) {
			updateLevelTrace();
			updatePreferences();
	        return super.performOk();
		} else {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Invalid Parameters", "No field can be empty");
			return false;
		}
    }
}
