package com.fimet.core.impl.swt.enviroment;

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

import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IPreferencesManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Enviroment;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.impl.swt.DataBaseCombo;
import com.fimet.core.impl.swt.EnviromentTypeCombo;
import com.fimet.core.impl.swt.FTPCombo;
import com.fimet.core.impl.swt.TextDecorate;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class EnviromentDialog extends Dialog implements DisposeListener {

	private Shell shell;
	private Button btnSave;
	private Button btnCancel;
	private Button btnAutoconnect;
	private TextDecorate txtId;
	private TextDecorate txtName;
	private EnviromentTypeCombo cboType;
	private DataBaseCombo cboDataBase;
	private FTPCombo cboFtp;
	private TextDecorate txtPath;
	private Button btnIsLocal;
	
	private boolean autoconnect;
	private Enviroment input;
	private Enviroment output;
	private EnviromentType type;
	protected EnviromentDialog(EnviromentType type, Enviroment connection, Shell shell, int style) {
        super(shell);
        this.type = type;
        this.input = connection;
        this.setShellStyle(style);
	}
	protected EnviromentDialog(EnviromentType type, Shell shell, int style) {
        this(type, null, shell, style);
	}
    public EnviromentDialog (EnviromentType type, Shell shell) {
        this(type, null, shell, SWT.NONE);
    }
	
	public int open () {
        Shell parent = getParentShell();
		
        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.addDisposeListener(this);
        shell.setText("Enviroment");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(getParentShell().getBackground());
        int h = 300, w = 350;
        shell.setBounds(Math.max(bounds.x + bounds.width/2 - w/2,0), Math.max(bounds.y + bounds.height/2 - h/2,0), w, h);
        shell.setImage(Images.TYPES_IMG.createImage());
        createControls(shell);
        //shell.setBounds(Math.max(bounds.x + bounds.width/2 - size.x/2,0), Math.max(bounds.y + bounds.height/2 - size.y/2,0), size.y, size.x);

        shell.open();
        shell.layout();

    	txtName.validate();
    	txtPath.validate();
        handleButtons();

        Display display = getParentShell().getDisplay();
        while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) display.sleep();
        }
        return 0;
}
	
    private void createControls(Composite parent) {

		GridLayout layout = new GridLayout(3,true);
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        
        Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label label;

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Id:");

		txtId = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtId.setEnabled(false);
		txtId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	txtId.setMarkDescription("Field can not be empty");

		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Name:");

		txtName = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	txtName.setMarkDescription("Field can not be empty");
    	txtName.setValidator((String value)->{return value != null && !"".equals(value.trim());});
    	txtName.addModifyListener((ModifyEvent e) -> {
    		txtName.validateAndMark();
			handleButtons();
		});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Enviroment Type:");
		label.setToolTipText("Select the enviroment, POS, ATM, ...");
		
    	cboType = new EnviromentTypeCombo(composite);
    	if (type != null)
    		cboType.getCombo().setEnabled(false);
    	cboType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	cboType.getCombo().addModifyListener((ModifyEvent e) -> {
			handleButtons();
		});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Data Base:");
		label.setToolTipText("Select the database");
		
    	cboDataBase = new DataBaseCombo(composite);
    	cboDataBase.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	cboDataBase.getCombo().addModifyListener((ModifyEvent e) -> {
			handleButtons();
		});
    	
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Application Path:");

		txtPath = new TextDecorate(composite, SWT.NONE | SWT.BORDER);
		txtPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	txtPath.setMarkDescription("Field can not be empty");
    	txtPath.setValidator((String value)->{return value != null && !"".equals(value.trim());});
    	txtPath.addModifyListener((ModifyEvent e) -> {
    		txtPath.validateAndMark();
			handleButtons();
		});
    	
    	layout = new GridLayout(6,true);
    	layout.marginWidth = 0;
        Composite cmpFtp = new Composite(composite, SWT.NONE);
		cmpFtp.setLayout(layout);
		cmpFtp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
    	
		label = new Label(cmpFtp, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("FTP:");
		label.setToolTipText("Select the FTP connection");
		
    	btnIsLocal = new Button(cmpFtp, SWT.CHECK);
    	btnIsLocal.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	btnIsLocal.setText("Local");
    	btnIsLocal.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onChangeIsLocal(btnIsLocal.getSelection());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
    	cboFtp = new FTPCombo(cmpFtp);
    	cboFtp.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    	cboFtp.getCombo().addModifyListener((ModifyEvent e) -> {
			handleButtons();
		});
    	
    	btnAutoconnect = new Button(composite, SWT.CHECK);
    	btnAutoconnect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    	btnAutoconnect.setText("Autostart");
    	btnAutoconnect.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				autoconnect = btnAutoconnect.getSelection();
				if (!btnAutoconnect.getSelection()){
					Manager.get(IPreferencesManager.class).remove(IPreferencesManager.ENVIROMENT_AUTOSTART);
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	
		layout = new GridLayout();
        layout.numColumns = 2;
        layout.horizontalSpacing = 10;
        layout.marginWidth = 10;
        
        Composite compositeBtns = new Composite(composite, SWT.NONE);
        compositeBtns.setLayout(layout);
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
    	
		btnSave = new Button(compositeBtns, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Save");
		
		btnCancel = new Button(compositeBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnSave);

		fillDataForm();
		hookListeners();
    }
    private void fillDataForm() {
    	if (input != null) {
			txtId.setText(input.getId()+"");
			cboType.select(input.getIdType());
			txtName.setText(StringUtils.escapeNull(input.getName()));
			cboDataBase.select(input.getDataBase());
			txtPath.setText(StringUtils.escapeNull(input.getPath()));
			if (input.getIsLocal()) {
				cboFtp.getCombo().deselectAll();
			} else {
				cboFtp.select(input.getFtp());
			}
			btnIsLocal.setSelection(input.getIsLocal());
			btnAutoconnect.setSelection(input != null ? (input.getId().equals(Manager.get(IPreferencesManager.class).getInt(IPreferencesManager.ENVIROMENT_AUTOSTART))) : false);
			onChangeIsLocal(input.getIsLocal());
		} else {
			txtId.setText(Manager.get(IEnviromentManager.class).getNextIdEnviroment()+"");
			cboFtp.getCombo().setEnabled(false);
			btnIsLocal.setSelection(true);
			if (type != null)
				cboType.select(type.getId());
			onChangeIsLocal(true);
		}
	}
	private void hookListeners() {
    	btnSave.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSave.setEnabled(false);
				output = createConnection(input);
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
	private void onChangeIsLocal(boolean selected) {
    	if (btnIsLocal.getSelection()){
			cboFtp.getCombo().setEnabled(false);
			cboFtp.getCombo().deselectAll();
		} else {
			cboFtp.getCombo().setEnabled(true);
		}
    }
    public boolean isValid() {
    	return txtName.isValid() && 
    			!"".equals(txtPath.getText()) && 
    			(!btnIsLocal.getSelection() ? cboFtp.getSelected() != null : true)&&
    			cboType.getSelected() != null;
    }
    private void handleButtons() {
		btnSave.setEnabled(isValid());
    }
    private Enviroment createConnection(Enviroment e) {
    	if (e == null) {
    		e = new Enviroment();
    	}
    	e.setId(Integer.parseInt(txtId.getText()));
		e.setName(txtName.getText());
		e.setPath(validateRute(txtPath.getText()));
		if (btnIsLocal.getSelection()) {
			e.setFtp(null);
			e.setIdFtp(null);
		} else {
			if (cboFtp.getSelected() != null) {
				e.setFtp(cboFtp.getSelected());
				e.setIdFtp(cboFtp.getSelected().getId());
			}
		}
		e.setIsLocal(btnIsLocal.getSelection());
		e.setType(cboType.getSelected());
		e.setIdType(cboType.getSelected().getId());
		if (cboDataBase.getSelected() != null) {
			e.setDataBase(cboDataBase.getSelected());
			e.setIdDataBase(cboDataBase.getSelected().getId());
		} else {
			e.setDataBase(null);
			e.setIdDataBase(null);
		}
    	return e;
    }
    private String validateRute(String rute) {
    	if (rute == null || rute.trim().length() == 0) {
    		return "";
    	}
    	rute = rute.replace('\\', '/');
    	if (rute.endsWith("/")) {
    		return rute;
    	} else {
    		return rute + '/';
    	}
    }
    public Enviroment getEnviroment() {
    	return output;
    }
    public boolean getAuthoconnect() {
    	return autoconnect;
    }
    @Override
    protected boolean isResizable() {
    	return true;
    }
	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (input == null && output == null)
			Manager.get(IEnviromentManager.class).getPrevIdEnviroment();		
	}
}
