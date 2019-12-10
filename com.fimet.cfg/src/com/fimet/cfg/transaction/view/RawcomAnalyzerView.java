package com.fimet.cfg.transaction.view;

import java.io.File;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.fimet.cfg.enums.Parser;
import com.fimet.cfg.transaction.RawcomAnalyzer;
import com.fimet.cfg.transaction.TransactionAnalyzerManager;
import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.preference.IPreference;
import com.fimet.commons.utils.ThreadUtils;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class RawcomAnalyzerView extends ViewPart {
	public static final String ID = "com.fimet.view.RawcomAnalyzerView";
	
	private Text txtFileInput;
	private Text txtFileOutput;
	private Button btnAnalyze;
	private Button btnStop;
	private RawcomAnalyzer analyzer;
	private TransactionAnalyzerManager manager = new TransactionAnalyzerManager();
	private IParser PARSER_NACIONAL = Manager.get(IParserManager.class).getParser(Parser.POS_NACIONAL.getId());
	
	public RawcomAnalyzerView() {
		super();
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

		Label lbl;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(4,true));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        composite.setBackground(Color.WHITE);
        
		createToolbar();
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Input:");
		
		txtFileInput = new Text(composite, SWT.BORDER);
		txtFileInput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtFileInput.setBackground(Color.WHITE);
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Output:");
		
		txtFileOutput = new Text(composite, SWT.BORDER);
		txtFileOutput.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtFileOutput.setBackground(Color.WHITE);
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 2, 1));

		btnStop = new Button(composite, SWT.NONE);
		btnStop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnStop.setText("Stop");

		btnAnalyze = new Button(composite, SWT.NONE);
		btnAnalyze.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnAnalyze.setText("Analyze");
	
		hookListeners();
		setSavedPreferences();
	}
	private void hookListeners() {
		btnStop.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onStop();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnAnalyze.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onAnalyze();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});		
	}
	private void createToolbar() {
        Action connectAction = new Action("Analyze") {
        	@Override
        	public void run() {
        		onAnalyze();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.TYPES_IMG;
        	}
        };
        IActionBars aBars = getViewSite().getActionBars();
        IMenuManager menu = aBars.getMenuManager();
        IToolBarManager toolBar = aBars.getToolBarManager();
        
        menu.add(connectAction);
        toolBar.add(connectAction);
	}
	private void onStop() {
		if (analyzer != null) {
			analyzer.stop();
		}
	}
	private void onAnalyze() {
		savePrefereces();
		File in = new File(txtFileInput.getText());
		File out = new File(txtFileOutput.getText());
		manager.initialize(0);
		analyzer = new RawcomAnalyzer(in, out, PARSER_NACIONAL, manager);
		ThreadUtils.runAcync(()->{
			analyzer.analyze();
		});
	}
	private void setSavedPreferences() {
		txtFileInput.setText(PlatformUI.getPreferenceStore().getString(IPreference.PATH_INPUT_RAWCOM));
		txtFileOutput.setText(PlatformUI.getPreferenceStore().getString(IPreference.PATH_OUTPUT_RAWCOM));
	}
	private void savePrefereces() {
		PlatformUI.getPreferenceStore().setValue(IPreference.PATH_INPUT_RAWCOM, txtFileInput.getText());
		PlatformUI.getPreferenceStore().setValue(IPreference.PATH_OUTPUT_RAWCOM,txtFileOutput.getText() );
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.txtFileInput.setFocus();
	}
}