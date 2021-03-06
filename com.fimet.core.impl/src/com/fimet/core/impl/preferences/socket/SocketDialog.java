package com.fimet.core.impl.preferences.socket;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IParserManager;
import com.fimet.core.ISimulatorManager;
import com.fimet.core.ISocketManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.entity.sqlite.Socket;
import com.fimet.core.impl.swt.AdapterStreamCombo;
import com.fimet.core.impl.swt.EnviromentCombo;
import com.fimet.core.impl.swt.ParserCombo;
import com.fimet.core.impl.swt.SimulatorCombo;
import com.fimet.core.impl.swt.TextDecorate;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class SocketDialog extends TrayDialog implements DisposeListener {

	public SocketDialog(Shell shell,  int style) {
		this(null, shell, style);
	}
	public SocketDialog(Socket input, Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
		this.input = input;
	}
	private TextDecorate txtId;
	private EnviromentCombo cboEnviroment;
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
	
	private Socket input;
	private Socket output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.addDisposeListener(this);
        shell.setText("Socket");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 390, w = 320;
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
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Id*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(Color.WHITE);
		label.setToolTipText("The socket id");
		
		txtId = new TextDecorate(composite, SWT.BORDER);
		txtId.setBackground(Color.WHITE);
		txtId.setEnabled(false);
		txtId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Enviroment*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(Color.WHITE);
		label.setToolTipText("The enviroment owner");
		
		cboEnviroment = new EnviromentCombo(composite);
		cboEnviroment.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Name*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket name.");
		
		txtName = new TextDecorate(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtName.setValidator((String text)->{return text.trim().length() > 0;});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Address*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket address.");
		
		txtAddress = new TextDecorate(composite, SWT.BORDER);
		txtAddress.setBackground(Color.WHITE);
		txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtAddress.setValidator((String text)->{return text.trim().length() > 0;});
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Port*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket port.");
		
		txtPort = new TextDecorate(composite, SWT.BORDER);
		txtPort.setBackground(Color.WHITE);
		txtPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtPort.setValidator((String text)->{return text.trim().length() > 0;});
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Process*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The socket process.");
		
		txtProcess = new TextDecorate(composite, SWT.BORDER);
		txtProcess.setBackground(Color.WHITE);
		txtProcess.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtProcess.setValidator((String text)->{return text.trim().length() > 0;});

		label = new Label(composite,SWT.NONE);
		label.setText("Adapter:");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboAdapter = new AdapterStreamCombo(composite);
		cboAdapter.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setText("Parser:");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboParser = new ParserCombo(composite, IParser.ISO8583);
		cboParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setText("Simulator:");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
        Composite cmpSim = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout(9,true);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
		cmpSim.setLayout(layout);
		cmpSim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));
		cmpSim.setBackground(Color.WHITE);
		
		btnIsAcquirer = new Button(cmpSim, SWT.CHECK);
		btnIsAcquirer.setText("Acquirer");
		btnIsAcquirer.setSelection(false);
		btnIsAcquirer.setBackground(Color.WHITE);
		btnIsAcquirer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		cboSimulator = new SimulatorCombo(cmpSim);
		cboSimulator.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setText("");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		btnIsActive = new Button(composite, SWT.CHECK);
		btnIsActive.setText("Is Active?");
		btnIsActive.setSelection(false);
		btnIsActive.setBackground(Color.WHITE);
		btnIsActive.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		label = new Label(composite,SWT.NONE);
		label.setText("");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		btnIsServer = new Button(composite, SWT.CHECK);
		btnIsServer.setText("Is Server?");
		btnIsServer.setSelection(false);
		btnIsServer.setBackground(Color.WHITE);
		btnIsServer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setText("");
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 4, 1));
		
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
				output = input == null ? new Socket() : input;
				output.setId(Integer.parseInt(txtId.getText()));
				output.setEnviroment(cboEnviroment.getSelected());
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
    	cboEnviroment.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboEnviroment.getSelected() == null) {
    			cboEnviroment.invalid();
    		} else {
    			cboEnviroment.valid();
    		}
    		validate();
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
    	if (input != null) {
    		txtId.setText(input.getId()+"");
    		cboEnviroment.select(input.getEnviroment());
    		txtName.setText(StringUtils.escapeNull(input.getName()));
    		txtName.valid();
    		txtAddress.setText(input.getAddress());
    		txtPort.setText(input.getPort()+"");
    		txtProcess.setText(input.getProcess());
    		btnIsActive.setSelection(input.isActive());
    		btnIsServer.setSelection(input.isServer());
    		btnIsAcquirer.setSelection(input.isAcquirer());
    		cboAdapter.select(input.getAdapter().getId());
    		cboParser.select(input.getParser().getId());
    		if (input.isAcquirer()) {
    			cboSimulator.loadAcquirers();
    		} else {
    			cboSimulator.loadIssuers();
    		}
    		cboSimulator.select(input.getSimulator().getId());
			validate();
    	} else {
    		txtId.setText(Manager.get(ISocketManager.class).getNextIdSocket()+"");
    		cboEnviroment.invalid();
    		txtName.setText("Socket Name");
    		txtName.valid();
    		txtAddress.invalid();
    		txtPort.invalid();
    		txtProcess.valid();
    		cboAdapter.invalid();
    		cboParser.invalid();
    		cboSimulator.invalid();
    		btnOk.setEnabled(false);
    	}
	}
    private boolean isValid() {
    	return  txtId.isValid() &&
    			cboEnviroment.isValid() &&
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
	public Socket getSocket() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (input == null && output == null)
			Manager.get(ISocketManager.class).getPrevIdSocket();		
	}
}
