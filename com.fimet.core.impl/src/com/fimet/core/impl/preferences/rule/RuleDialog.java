package com.fimet.core.impl.preferences.rule;

import java.util.List;

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
import com.fimet.core.IRuleManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.EnviromentType;
import com.fimet.core.entity.sqlite.FieldMapper;
import com.fimet.core.entity.sqlite.IRuleValue;
import com.fimet.core.entity.sqlite.Rule;
import com.fimet.core.impl.swt.KeyValueCombo;
import com.fimet.core.impl.swt.RuleValueCombo;
import com.fimet.core.impl.swt.TextDecorate;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class RuleDialog extends TrayDialog implements DisposeListener {

	public RuleDialog(Shell shell, EnviromentType envType, FieldMapper ruleType, String order,  List<IRuleValue> values, int style) {
		this(null, envType, ruleType, order, shell, values, style);
	}
	public RuleDialog(Rule input, EnviromentType envType, FieldMapper ruleType, String order, Shell shell, List<IRuleValue> values, int style) {
		super(shell);
        this.setShellStyle(style);
		this.input = input;
		this.envType = envType;
		this.ruleType = ruleType;
		this.order = order;
		this.values = values;
	}
	private List<IRuleValue> values;
	private String order;
	private EnviromentType envType;
	private FieldMapper ruleType;
	private TextDecorate txtId;
	private TextDecorate txtProperty;
	private KeyValueCombo<Character> cboOperator;
	private TextDecorate txtPattern;
	private RuleValueCombo cboValues;
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private Rule input;
	private Rule output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.addDisposeListener(this);
        shell.setText("Rule");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 220, w = 420;
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
		label.setText("Rule id*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(Color.WHITE);
		label.setToolTipText("The rule id");
		
		txtId = new TextDecorate(composite, SWT.BORDER);
		txtId.setBackground(Color.WHITE);
		txtId.setEnabled(false);
		txtId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Property*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The input name. It will be included in the FIMET report.");
		
		txtProperty = new TextDecorate(composite, SWT.BORDER);
		txtProperty.setBackground(Color.WHITE);
		txtProperty.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtProperty.setValidator((String text)->{return text.trim().length() > 0;});
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Operator*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The field format group");
		
		cboOperator = new KeyValueCombo<Character>(composite);
		cboOperator.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cboOperator.addOption("CONTAINS", Rule.CONTAINS);
		cboOperator.addOption("EQUALS", Rule.EQUALS);
		cboOperator.addOption("MATCHES", Rule.MATCHES);
		cboOperator.refreshOptions();
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Pattern*:");
		label.setBackground(Color.WHITE);
		label.setToolTipText("The pattern expected");
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		txtPattern = new TextDecorate(composite, SWT.BORDER);
		txtPattern.setBackground(Color.WHITE);
		txtPattern.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtPattern.setValidator((String text)->{
			if (cboOperator.getSelected() == Rule.MATCHES) {
				try {
					"".matches(text);
					return true;
				} catch (Exception e) {
					return false;
				}
			} else {
				return true;
			}
		});
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Value*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The value");
		
		cboValues = new RuleValueCombo(composite);
		cboValues.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	
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
				
				output = input == null ? new Rule() : input;
				output.setId(Integer.parseInt(txtId.getText()));
				output.setIdTypeEnviroment(envType.getId());
				output.setIdField(ruleType.getId());
				output.setOrder(order);
				output.setProperty(txtProperty.getText());
				output.setOperator(cboOperator.getSelected());
				output.setPattern(txtPattern.getText());
				if (cboValues.getSelected() != null) {
					output.setIdResult(cboValues.getSelected().getId());
				}
				
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
    	txtProperty.addModifyListener((ModifyEvent e)->{
    		txtProperty.validate();
    		validate();
		});
    	cboOperator.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboOperator.getSelected() == null) {
    			cboOperator.invalid();
    		} else {
    			cboOperator.valid();
    		}
    		validate();
		});
    	txtPattern.addModifyListener((ModifyEvent e)->{
    		txtPattern.validate();
    		validate();
		});
	}
    private void fillFormData() {
    	cboValues.setInput(values);
    	if (input != null) {
    		txtId.setText(input.getId()+"");
    		txtProperty.setText(StringUtils.escapeNull(input.getProperty()));
    		txtProperty.valid();
			cboOperator.select(input.getOperator());
			cboOperator.valid();
			txtPattern.setText(input.getPattern());
			txtPattern.valid();
			cboValues.select(input.getIdResult());
			cboValues.valid();
			validate();
    	} else {
    		txtId.setText(Manager.get(IRuleManager.class).getNextIdRule()+"");
    		txtProperty.setText("");
    		txtProperty.invalid();
    		cboOperator.valid();
    		txtPattern.valid();
    		btnOk.setEnabled(false);
    	}		
	}
    private boolean isValid() {
    	return txtPattern.isValid() && cboOperator.isValid() && txtProperty.isValid();
    }
    private void validate() {
    	btnOk.setEnabled(isValid());
    }
	public Rule getRule() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (input == null && output == null)
			Manager.get(IRuleManager.class).getPrevIdRule();		
	}
}
