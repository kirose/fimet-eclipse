package com.fimet.core.impl.preferences.simulator;

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
import com.fimet.commons.exception.FimetException;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.entity.sqlite.Simulator;
import com.fimet.core.entity.sqlite.SimulatorMessage;
import com.fimet.core.entity.sqlite.pojo.SimulatorField;
import com.fimet.core.impl.swt.TextDecorate;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class MessageDialog extends TrayDialog {

	
	public MessageDialog(Simulator simulator, SimulatorMessage sm, Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
        this.simulator = simulator;
        if (sm != null) {
	        try {
				this.input = sm.clone();
			} catch (CloneNotSupportedException e) {
				throw new FimetException(e);
			}
        }
	}
	public MessageDialog(Simulator simulator, Shell shell, int style) {
		this(simulator,null, shell, style);
	}
	private Simulator simulator;
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private TextDecorate txtHeader;
	private TextDecorate txtMti;
	private TextDecorate txtRequiredFields;
	private TextDecorate txtExcludeFields;
	private FieldTable table;
	private SimulatorMessage input;
	private SimulatorMessage output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Simulator Message");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 380, w = 450;
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

    	Label lbl;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Header:");
		lbl.setToolTipText("This header will be included into the simulated message");
		
		txtHeader = new TextDecorate(composite, SWT.BORDER);
		txtHeader.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtHeader.setBackground(Color.WHITE);
		txtHeader.setValidator((String t)->{return t != null && (t.length() == 0 || t.matches("[A-Za-z0-9]+"));});
		txtHeader.valid();
		txtHeader.addModifyListener((ModifyEvent e)->{
			txtHeader.validateAndMark();
		});
    	
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Mti:");
		lbl.setToolTipText("The message (mti) to simulate");
		
		txtMti = new TextDecorate(composite, SWT.BORDER);
		txtMti.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtMti.setBackground(Color.WHITE);
		txtMti.setValidator((String t)->{return t != null && (t.length() == 0 || t.matches("[0-9]+"));});
		txtMti.addModifyListener((ModifyEvent e)->{
			txtMti.validateAndMark();
			validate();
		});
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Required Fields:");
		lbl.setToolTipText("The required field\nExample:3,22,63.EZ");
		
		txtRequiredFields = new TextDecorate(composite, SWT.BORDER);
		txtRequiredFields.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtRequiredFields.setBackground(Color.WHITE);
		txtRequiredFields.setValidator((String t)->{return t != null && (t.length() == 0 || t.matches("[0-9A-Za-z._]+(,[0-9A-Za-z._]+)*"));});
		txtRequiredFields.valid();
		txtRequiredFields.addModifyListener((ModifyEvent e)->{
			txtRequiredFields.validateAndMark();
			validate();
		});
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Exclude Fields:");
		lbl.setToolTipText("The fields to be excluded\nExample:3,22,63.EZ");
		
		txtExcludeFields = new TextDecorate(composite, SWT.BORDER);
		txtExcludeFields.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtExcludeFields.setBackground(Color.WHITE);
		txtExcludeFields.setValidator((String t)->{return t != null && (t.length() == 0 || t.matches("[0-9A-Za-z._]+(,[0-9A-Za-z._]+)*"));});
		txtExcludeFields.valid();
		txtRequiredFields.addModifyListener((ModifyEvent e)->{
			txtRequiredFields.validateAndMark();
			validate();
		});
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setText("Include Fields:");
		lbl.setToolTipText("This fields will be include into the simulate message");
		
		Composite cTable = new Composite(composite, SWT.NONE);
		cTable.setBackground(Color.WHITE);
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		cTable.setLayout(layout);
		cTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

		table = new FieldTable(this, cTable);
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lbl.setBackground(Color.WHITE);
		
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnOk.setText("OK");
		btnOk.setFocus();
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		
    	hookListeners();
    	setInputValues();
    }
    private void setInputValues() {
    	if (input != null) {
    		txtHeader.setText(StringUtils.escapeNull(input.getHeader()));
    		txtMti.setText(StringUtils.escapeNull(input.getMti()));
    		txtRequiredFields.setText(StringUtils.join(input.getRequiredFields()));
    		txtExcludeFields.setText(StringUtils.join(input.getExcludeFields()));
    		table.setInput(input.getIncludeFields());
    	}
    }
    private void setOutputValues() {
    	if (input == null) {
    		input = new SimulatorMessage();
    	}
    	input.setIdSimulator(simulator.getId());
    	input.setHeader("".equals(txtHeader.getText().trim()) ? null : txtHeader.getText());
    	input.setMti("".equals(txtMti.getText().trim()) ? null : txtMti.getText());
    	input.setRequiredFields(StringUtils.expand(txtRequiredFields.getText()));
    	input.setExcludeFields(StringUtils.expand(txtExcludeFields.getText()));
    	input.setIncludeFields(table.getEntities());
    	output = input;
    }
    private void hookListeners() {
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setOutputValues();
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
	public SimulatorMessage getSimulatorMessage() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
	public boolean isValid() {
		return txtMti.isValid() && txtRequiredFields.isValid() && txtExcludeFields.isValid();
	}
	public void validate() {
		btnOk.setEnabled(isValid());
	}
	public void onNewField() {
		FieldDialog dialog = new FieldDialog(shell, SWT.NONE);
		dialog.open();
		if (dialog.getSimulatorField() != null) {
			SimulatorField field = dialog.getSimulatorField();
			table.add(field);
		}
	}
	public void onEditField() {
		if (table.getSelected() != null) {
			FieldDialog dialog = new FieldDialog(table.getSelected(), shell, SWT.NONE);
			dialog.open();
			if (dialog.getSimulatorField() != null) {
				SimulatorField field = dialog.getSimulatorField();
				table.update(field);
			}
		}
	}
	public void onDeleteField() {
		if (table.getSelected() != null) {
			SimulatorField field = table.getSelected();
			table.delete(field);
		}
	}
}
