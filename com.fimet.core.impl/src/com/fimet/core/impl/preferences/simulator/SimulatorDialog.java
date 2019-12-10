package com.fimet.core.impl.preferences.simulator;

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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.ISimulatorManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.impl.swt.KeyValueCombo;
import com.fimet.core.impl.swt.VText;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class SimulatorDialog extends TrayDialog implements DisposeListener {

	public SimulatorDialog(Shell shell,  int style) {
		this(null, shell, style);
	}
	public SimulatorDialog(Simulator input, Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
		this.input = input;
	}
	private ISimulatorManager simulatorManager = Manager.get(ISimulatorManager.class);
	private VText txtId;
	private KeyValueCombo<Character> cboType;
	private VText txtName;
	
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private Simulator input;
	private Simulator output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.addDisposeListener(this);
        shell.setText("Simulator");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 170, w = 350;
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
		label.setText("Simulator id:");
		label.setBackground(Color.WHITE);
		//label.setForeground(Color.WHITE);
		label.setToolTipText("The parser id");
		
		txtId = new VText(composite, SWT.BORDER);
		txtId.setBackground(Color.WHITE);
		txtId.setEnabled(false);
		txtId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Simulator Type:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("the simulator type");
		
		cboType = new KeyValueCombo<Character>(composite);
		cboType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cboType.addOption("Acquirer", Simulator.ACQUIRER);
		cboType.addOption("Issuer", Simulator.ISSUER);
		cboType.refreshOptions();
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Simulator Name:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The input name. It will be included in the FIMET report.");
		
		txtName = new VText(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtName.setValidator((String text)->{return text.trim().length() > 0;});

		btnOk = new Button(composite, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnOk.setText("OK");
		btnOk.setFocus();
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);

		fillDataForm();
    	hookListeners();
    }
    private void fillDataForm() {
    	if (input != null) {
    		txtId.setText(input.getId()+"");
    		cboType.select(input.getType());
    		txtName.setText(StringUtils.escapeNull(input.getName()));
    	} else {
    		txtId.setText(simulatorManager.getNextIdSimulator()+"");
    		cboType.select(Simulator.ISSUER);
    		txtName.setText("Simulator Name");
    		//txtKeySequence.setText(input.getKeySequence());
    	}
    }
    private void hookListeners() {
    	btnOk.addSelectionListener(new SelectionListener() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			
    			output = input == null ? new Simulator() : input;
    			output.setId(Integer.parseInt(txtId.getText()));
    			output.setName(txtName.getText());
    			output.setType(cboType.getSelected());
    			
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
    	txtName.addModifyListener((ModifyEvent e)->{
    		txtName.validate();
    		validate();
    	});
    }
    public Simulator getSimulator() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
	public boolean isValid() {
		return true;
	}
	public void validate() {
		btnOk.setEnabled(isValid());
	}
	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (input == null && output == null)
			simulatorManager.getPrevIdSimulator();
	}
}
