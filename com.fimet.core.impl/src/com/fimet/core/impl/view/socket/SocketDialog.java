package com.fimet.core.impl.view.socket;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IParserManager;
import com.fimet.core.ISimulatorManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.impl.swt.AdapterStreamCombo;
import com.fimet.core.impl.swt.ParserCombo;
import com.fimet.core.impl.swt.SimulatorCombo;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.net.ISocket;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class SocketDialog extends TrayDialog {

	public SocketDialog(ISocket input, Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
		this.input = input;
	}
	private TextDecorate txtName;
	private TextDecorate txtAddress;
	private TextDecorate txtPort;
	private TextDecorate txtProcess;
	private Button btnIsServer;
	private Button btnIsActive;
	private Button btnIsAcquirer;
	private AdapterStreamCombo cboAdapter;
	private ParserCombo cboParser;
	private SimulatorCombo cboSimulator;
	
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private ISocket input;
	private ISocket output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Socket");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 340, w = 320;
        shell.setBounds(Math.max(bounds.x + bounds.width/2 - w/2,0), Math.max(bounds.y + bounds.height/2 - h/2,0), w, h);
        shell.setImage(Images.TYPES_IMG.createImage());
        createControls(shell);
        //shell.setBounds(Math.max(bounds.x + bounds.width/2 - size.x/2,0), Math.max(bounds.y + bounds.height/2 - size.y/2,0), size.y, size.x);

        shell.open();
        shell.layout();

        Display display = getParentShell().getDisplay();
        while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) display.sleep();
        }
        return 0;
	}
	
    private void createControls(Composite parent) {

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		composite.setBackground(Color.WHITE);
		
		Label label;

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Socket Name*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket name.");
		
		txtName = new TextDecorate(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtName.setValidator((String text)->{return text.trim().length() > 0;});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Socket Address*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket address.");
		
		txtAddress = new TextDecorate(composite, SWT.BORDER);
		txtAddress.setBackground(Color.WHITE);
		txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtAddress.setValidator((String text)->{return text.trim().length() > 0;});
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Socket Port*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket port.");
		
		txtPort = new TextDecorate(composite, SWT.BORDER);
		txtPort.setBackground(Color.WHITE);
		txtPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtPort.setValidator((String text)->{return text.trim().length() > 0;});
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Socket Process*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket process.");
		
		txtProcess = new TextDecorate(composite, SWT.BORDER);
		txtProcess.setBackground(Color.WHITE);
		txtProcess.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtProcess.setValidator((String text)->{return text.trim().length() > 0;});

		label = new Label(composite,SWT.NONE);
		label.setText("Adapter:");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 2, 1));
		
		cboAdapter = new AdapterStreamCombo(composite);
		cboAdapter.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setText("Parser:");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 2, 1));
		
		cboParser = new ParserCombo(composite, IParser.ISO8583);
		cboParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setText("Simulator:");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 2, 1));
		
		cboSimulator = new SimulatorCombo(composite);
		cboSimulator.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		
		label = new Label(composite,SWT.NONE);
		label.setText("");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 2, 1));
		
		btnIsActive = new Button(composite, SWT.CHECK);
		btnIsActive.setText("Is Active?");
		btnIsActive.setSelection(false);
		btnIsActive.setBackground(Color.WHITE);
		btnIsActive.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		label = new Label(composite,SWT.NONE);
		label.setText("");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 2, 1));
		
		btnIsAcquirer = new Button(composite, SWT.CHECK);
		btnIsAcquirer.setText("Is Acquirer?");
		btnIsAcquirer.setSelection(false);
		btnIsAcquirer.setBackground(Color.WHITE);
		btnIsAcquirer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setText("");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 2, 1));
		
		btnIsServer = new Button(composite, SWT.CHECK);
		btnIsServer.setText("Is Server?");
		btnIsServer.setSelection(false);
		btnIsServer.setBackground(Color.WHITE);
		btnIsServer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnOk.setText("OK");
		btnOk.setFocus();

		shell.setDefaultButton(btnOk);

		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnCancel.setText("Cancel");
		
		hookListeners();
		fillFormData();
    }
	private void hookListeners() {
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				output = input;
				output.setName(txtName.getText());
				output.setAddress(txtAddress.getText());
				output.setPort(Integer.parseInt(txtPort.getText()));
				output.setProcess(txtProcess.getText());
				output.setIsActive(btnIsActive.getSelection());
				output.setIsServer(btnIsServer.getSelection());
				output.setIsAcquirer(btnIsAcquirer.getSelection());
				output.setAdapter(cboAdapter.getSelected());
				output.setParser(Manager.get(IParserManager.class).getParser(cboParser.getSelected()));
				output.setSimulator(Manager.get(ISimulatorManager.class).getSimulator(cboSimulator.getSelected()));
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	btnCancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				input = null;
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	txtName.addModifyListener((ModifyEvent e)->{
    		txtName.validate();
    		validate();
		});
    	txtAddress.addModifyListener((ModifyEvent e)->{
    		txtAddress.validate();
    		validate();
		});
    	txtPort.addModifyListener((ModifyEvent e)->{
    		txtPort.validate();
    		validate();
		});
    	txtProcess.addModifyListener((ModifyEvent e)->{
    		txtProcess.validate();
    		validate();
		});
    	cboAdapter.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboAdapter.getSelected() == null) {
    			cboAdapter.invalid();
    		} else {
    			cboAdapter.valid();
    		}
    		validate();
		});
    	cboParser.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboParser.getSelected() == null) {
    			cboParser.invalid();
    		} else {
    			cboParser.valid();
    		}
    		validate();
		});
    	cboSimulator.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboSimulator.getSelected() == null) {
    			cboSimulator.invalid();
    		} else {
    			cboSimulator.valid();
    		}
    		validate();
		});
	}
    private void fillFormData() {
		txtName.setText(StringUtils.escapeNull(input.getName()));
		txtAddress.setText(input.getAddress());
		txtPort.setText(input.getPort()+"");
		txtProcess.setText(input.getProcess());
		btnIsActive.setSelection(input.isActive());
		btnIsServer.setSelection(input.isServer());
		btnIsAcquirer.setSelection(input.isAcquirer());
		cboAdapter.select(input.getAdapter().getId());
		cboParser.select(input.getParser().getId());
		cboSimulator.select(input.getSimulator().getId());
		validate();
	}
    private boolean isValid() {
    	return  
        		txtName.isValid() &&
        		txtName.isValid() &&
        		txtAddress.isValid() &&
        		txtPort.isValid() &&
        		txtProcess.isValid() &&
        		cboAdapter.isValid() &&
        		cboParser.isValid() &&
        		cboSimulator.isValid();
    }
    private void validate() {
    	btnOk.setEnabled(isValid());
    }
	public ISocket getSocket() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
