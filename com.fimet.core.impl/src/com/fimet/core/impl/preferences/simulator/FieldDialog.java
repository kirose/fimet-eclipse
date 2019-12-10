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
import com.fimet.core.ISimulatorFieldManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.pojo.SimulatorField;
import com.fimet.core.impl.swt.ClassCombo;
import com.fimet.core.impl.swt.KeyValueCombo;
import com.fimet.core.impl.swt.TextDecorate;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class FieldDialog extends TrayDialog {

	public FieldDialog(Shell shell, int style) {
		this(null, shell, style);
	}

	public FieldDialog(SimulatorField input, Shell shell, int style) {
		super(shell);
		this.setShellStyle(style);
		this.input = input;
	}

	private TextDecorate txtIdField;
	private KeyValueCombo<Character> cboType;
	private Composite cmpValue;
	private TextDecorate txtFixedValue;
	private ClassCombo cboCustomValue;

	private Shell shell;
	private Button btnOk;
	private Button btnCancel;

	private SimulatorField input;
	private SimulatorField output;

	public int open() {
		Shell parent = getParentShell();

		Rectangle bounds = getParentShell().getBounds();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE | getDefaultOrientation());// |
																													// SWT.MAX
		shell.setText("Field");
		shell.setLayout(new GridLayout(1, true));
		shell.setBackground(Color.WHITE);// getParentShell().getBackground());
		int h = 140, w = 340;
		shell.setBounds(Math.max(bounds.x + bounds.width / 2 - w / 2, 0),
				Math.max(bounds.y + bounds.height / 2 - h / 2, 0), w, h);
		shell.setImage(Images.TYPES_IMG.createImage());
		createControls(shell);
		// shell.setBounds(Math.max(bounds.x + bounds.width/2 - size.x/2,0),
		// Math.max(bounds.y + bounds.height/2 - size.y/2,0), size.y, size.x);

		shell.open();
		shell.layout();

		Display display = getParentShell().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return 0;
	}

	private void createControls(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(Color.WHITE);
		composite.setLayout(new GridLayout(4, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBackground(Color.WHITE);

		Label label;

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Field id*:");
		label.setBackground(Color.WHITE);
		// label.setForeground(Color.WHITE);
		label.setToolTipText("The parser id");

		txtIdField = new TextDecorate(composite, SWT.BORDER);
		txtIdField.setBackground(Color.WHITE);
		//txtIdField.setEnabled(false);
		txtIdField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,3, 1));
		txtIdField.setValidator((String text)->{return text.matches("^[0-9]+(\\.[0-9A-Za-z_$]+)*");});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Value*:");
		label.setBackground(Color.WHITE);
		// label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The input name. It will be included in the FIMET report.");

		cboType = new KeyValueCombo<Character>(composite);
		cboType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cboType.addOption("FIXED", SimulatorField.FIXED);
		cboType.addOption("CUSTOM", SimulatorField.CUSTOM);
		cboType.refreshOptions();
		cboType.select("FIXED");
		cboType.valid();
		
		cmpValue = new Composite(composite, SWT.NONE);
		cmpValue.setBackground(Color.WHITE);
		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		cmpValue.setLayout(gridLayout);
		cmpValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cmpValue.setBackground(Color.WHITE);
		
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnOk.setText("OK");
		btnOk.setFocus();
		shell.setDefaultButton(btnOk);

		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);

		fillFormData();
		hookListeners();
	}

	private void hookListeners() {
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (input == null) {
					output = new SimulatorField(
						txtIdField.getText(),
						cboType.getSelected(),
						cboType.getSelected() == SimulatorField.FIXED ? txtFixedValue.getText() : cboCustomValue.getSelected().getName()
					);
				} else {
					output = input;
					output.setIdField(txtIdField.getText());
					output.setType(cboType.getSelected());
					output.setValue(cboType.getSelected() == SimulatorField.FIXED ? txtFixedValue.getText() : cboCustomValue.getSelected().getName());
				}
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		btnCancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		txtIdField.addModifyListener((ModifyEvent e)->{
			txtIdField.validate();
			validate();
		});
		cboType.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onChangeType();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	private void fillFormData() {
		if (input != null) {
			txtIdField.setText(input.getIdField() + "");
			txtIdField.validate();
			cboType.select(input.getType());
			onChangeType();
			if (input.getType() == SimulatorField.CUSTOM) {
				cboCustomValue.select(input.getValue());
				cboCustomValue.valid();
			} else {
				txtFixedValue.setText(input.getValue());
				txtFixedValue.valid();
			}
		} else {
			txtIdField.setText("");
			txtIdField.validate();
			cboType.select(SimulatorField.FIXED);
			onChangeType();
			txtFixedValue.setText("");
			txtFixedValue.invalid();
		}	
		validate();
	}
	public SimulatorField getSimulatorField() {
		return output;
	}
	private void onChangeType() {
		if (cboType.getSelected() == SimulatorField.FIXED) {
			if (cboCustomValue != null) cboCustomValue.getCombo().dispose();
			if (txtFixedValue == null || txtFixedValue.isDisposed()) {
				txtFixedValue = new TextDecorate(cmpValue, SWT.BORDER);
				txtFixedValue.setBackground(Color.WHITE);
				txtFixedValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				txtFixedValue.setValidator((String text)->{return text.matches("^[0-9]+(.[A-Za-z0-9_$]+)*$");});
				txtFixedValue.addModifyListener((ModifyEvent e)->{
					txtFixedValue.validate();
					validate();
				});
				txtFixedValue.valid();
			}
		} else {
			if (txtFixedValue != null) txtFixedValue.dispose();
			if (cboCustomValue == null || cboCustomValue.getCombo().isDisposed()) {
				cboCustomValue = new ClassCombo(cmpValue);
				cboCustomValue.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				cboCustomValue.setClasses(Manager.get(ISimulatorFieldManager.class).getSimulatorClasses());
				cboCustomValue.invalid();
				cboCustomValue.getCombo().addModifyListener((ModifyEvent e)->{
					cboCustomValue.validate();
					validate();
				});
			}
		}
		cmpValue.redraw();
		cmpValue.requestLayout();
		validate();
	}
	private boolean isValid() {
		return txtIdField.isValid() && (cboType.getSelected() == SimulatorField.FIXED ? txtFixedValue.isValid() : cboCustomValue.isValid());
	}
	private void validate() {
		btnOk.setEnabled(isValid());
	}
	@Override
	protected boolean isResizable() {
		return true;
	}
}
