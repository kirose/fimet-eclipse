package com.fimet.core.impl.swt.ftp;

import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.exception.FtpException;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Ftp;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.net.IFtpManager;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FtpDialog extends Dialog implements DisposeListener {

	private static final String REGEXP_ADDRESS = "[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}";
	private static final String ADDRESS_LOCALHOST = "localhost";
	
	public static final int OK = 0;
	public static final int CANCEL = 1;
	
	private Shell shell;
	private Button btnTest;
	private Button btnSave;
	private Button btnCancel;
	
	private TextDecorate txtId;
	private TextDecorate txtName;
	private TextDecorate txtAddress;
	private TextDecorate txtPort;
	private TextDecorate txtUser;
	private TextDecorate txtPassword;
	private TextDecorate txtMsg;
	
	private int result;
	private boolean autoconnect;
	private boolean isValid;
	private Ftp input;
	private Ftp output;
	protected FtpDialog(Shell shell, int style, Ftp input) {
        super(shell);
        this.input = input;
        this.setShellStyle(style);
	}
	protected FtpDialog(Shell shell, int style) {
        this(shell, style, null);
	}
    public FtpDialog (Shell shell) {
        this(shell, SWT.NONE,null);
    }
	
	public int open () {
        Shell parent = getParentShell();
        Rectangle bounds = parent.getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.addDisposeListener(this);
        shell.setText("FTP Connection");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(getParentShell().getBackground());
        int h = 260, w = 300;
        shell.setBounds(Math.max(bounds.x + bounds.width/2 - w/2,0), Math.max(bounds.y + bounds.height/2 - h/2,0), w, h);
        shell.setImage(Images.FTP_IMG.createImage());
        createControls(shell);
        //shell.setBounds(Math.max(bounds.x + bounds.width/2 - size.x/2,0), Math.max(bounds.y + bounds.height/2 - size.y/2,0), size.y, size.x);

        shell.open();
        shell.layout();

        validate();
        handleButtons();

        Display display = getParentShell().getDisplay();
        while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) display.sleep();
        }
        return result;
}
	
    private void createControls(Composite parent) {

    	parent.setBackground(Color.WHITE);
		GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        
        Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBackground(parent.getBackground());
		
		Label label;

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(parent.getBackground());
		label.setText("id:");

		txtId = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	txtId.setEnabled(false);
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(parent.getBackground());
		label.setText("Name:");

		txtName = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	txtName.setMarkDescription("Field can not be empty");
    	txtName.setValidator((String value)->{return value != null && !"".equals(value.trim());});
    	txtName.addModifyListener((ModifyEvent e) -> {
    		txtName.validateAndMark();
			handleButtons();
		});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(parent.getBackground());
		label.setText("Address*:");

		txtAddress = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtAddress.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	txtAddress.setMarkDescription("Invalid Field, ej. 172.29.40.10");
    	txtAddress.setValidator((String value)->{
    		return value != null && !"".equals(value.trim()) && (ADDRESS_LOCALHOST.equals(value) || value.matches(REGEXP_ADDRESS));
    	});
    	txtAddress.addModifyListener((ModifyEvent e) -> {
    		txtAddress.validateAndMark();
			handleButtons();
			isValid = false;
		});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(parent.getBackground());
		label.setText("Port*:");

		txtPort = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtPort.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	txtPort.setMarkDescription("Field must be numeric");
    	txtPort.setValidator((String value)->{return value != null && !"".equals(value.trim()) && value.matches("[0-9]+");});
    	txtPort.addModifyListener((ModifyEvent e) -> {
    		txtPort.validateAndMark();
			handleButtons();
			isValid = false;
		});
    	
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(parent.getBackground());
		label.setText("User:");

		txtUser = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtUser.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	txtUser.setMarkDescription("Field must be alphanumeric");
    	txtUser.setValidator((String value)->{return value != null && !"".equals(value.trim()) && value.matches("[a-zA-Z0-9_]+");});
    	txtUser.addModifyListener((ModifyEvent e) -> {
    		txtUser.validateAndMark();
			handleButtons();
			isValid = false;
		});
    	
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(parent.getBackground());
		label.setText("Password*:");

		txtPassword = new TextDecorate(composite, SWT.PASSWORD | SWT.BORDER);
		txtPassword.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	txtPassword.setMarkDescription("Field can not be empty");
    	txtPassword.setValidator((String value)->{return value != null && !"".equals(value.trim());});
    	txtPassword.addModifyListener((ModifyEvent e) -> {
    		txtPassword.validateAndMark();
			handleButtons();
			isValid = false;
		});
    	   	
    	txtMsg = new TextDecorate(composite, SWT.NONE);
    	txtMsg.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	txtMsg.setBackground(composite.getBackground());
    	
		layout = new GridLayout();
        layout.numColumns = 3;
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        
        Composite compositeBtns = new Composite(composite, SWT.NONE);
        compositeBtns.setLayout(layout);
        compositeBtns.setBackground(composite.getBackground());
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    	
		btnTest = new Button(compositeBtns, SWT.NONE);
		btnTest.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnTest.setText("Test");
		
		btnSave = new Button(compositeBtns, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Save");
		
		btnCancel = new Button(compositeBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnSave);
		
		if (input != null) {
			txtId.setText(""+input.getId());
			txtName.setText(StringUtils.escapeNull(input.getName()));
			txtAddress.setText(StringUtils.escapeNull(input.getAddress()));
			txtPort.setText(StringUtils.escapeNull(input.getPort()));
			txtUser.setText(StringUtils.escapeNull(input.getUser()));
			txtPassword.setText(StringUtils.escapeNull(input.getPassword()));
		} else {
			txtId.setText(""+Manager.get(IFtpManager.class).getNextIdFtp());
		}
		btnTest.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					btnTest.setEnabled(false);
					txtMsg.setText("");
					Manager.get(IFtpManager.class).testConnection(createConnection(null));
					txtMsg.setText("Successfull connection!");
					isValid = true;
				} catch (FtpException ex) {
					txtMsg.setText(ex.getMessage());
				}
				btnTest.setEnabled(true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		//size = composite.computeSize(SWT.DEFAULT, SWT.DEFAULT);
    	btnSave.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSave.setEnabled(false);
				output = createConnection(input);
				output.setIsValid(isValid);
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	btnCancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	
    }
    public boolean isValid() {
    	return txtName.isValid() && txtAddress.isValid() && txtPort.isValid() && txtUser.isValid() && txtPassword.isValid();
    }
    private void handleButtons() {
    	boolean valid = isValid();
		btnSave.setEnabled(valid);
		btnTest.setEnabled(valid);
    }
    private void validate() {
    	txtName.validate();
    	txtAddress.validate();
    	txtPort.validate();
    	txtUser.validate();
    	txtPassword.validate();
	}
    private Ftp createConnection(Ftp c) {
    	if (c == null) {
    		c = new Ftp();
    		c.setStatusConnection(Ftp.DISCONNECTED);
			c.setIsValid(false);
    	}
    	c.setId(Integer.parseInt(txtId.getText()));
		c.setName(txtName.getText());
		c.setAddress(txtAddress.getText());
		c.setPort(txtPort.getText());
		c.setUser(txtUser.getText());
		c.setPassword(txtPassword.getText());
    	return c;
    }

    public Ftp getConnection() {
    	return output;
    }
    public boolean getAuthoconnect() {
    	return autoconnect;
    }
	public void widgetDisposed(DisposeEvent e) {
		if (input == null && output == null)
			Manager.get(IFtpManager.class).getPrevIdFtp();		
	}
}
