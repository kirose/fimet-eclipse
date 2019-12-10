package com.fimet.editor.usecase.section.swt.validation;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.impl.swt.ClassCombo;
import com.fimet.core.impl.swt.VText;
import com.fimet.core.usecase.Validation;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class ValidationDialog extends TrayDialog {

	public ValidationDialog(ValidationViewer viewer, Shell shell, FormToolkit toolkit, int style) {
		this(viewer, null, shell, toolkit, style);
	}
	public ValidationDialog(ValidationViewer viewer,Validation input, Shell shell, FormToolkit toolkit, int style) {
		super(shell);
        this.setShellStyle(style);
        this.viewer = viewer;
        this.toolkit = toolkit;
		this.input = input;
	}
	private ValidationViewer viewer;
	private VText txtValidationName;
	private VText txtValidationExpected;
	private VText txtValidationExpression;
	private ClassCombo cboType;
	
	private FormToolkit toolkit;
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private Validation input;
	private Validation output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Parse a Message");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 270, w = 420;
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
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBackground(Color.WHITE);
		
		Label label;

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Validation Name*:");
		label.setBackground(Color.WHITE);
		label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The input name. It will be included in the FIMET report.");
		
		txtValidationName = new VText(composite, SWT.BORDER);
		txtValidationName.setBackground(Color.WHITE);
		txtValidationName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Expected Value*:");
		label.setBackground(Color.WHITE);
		label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The expected value, this value will be compared with the result of the expression evaluation.");
		
		txtValidationExpected = new VText(composite, SWT.BORDER);
		txtValidationExpected.setBackground(Color.WHITE);
		txtValidationExpected.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		cboType = new ClassCombo(composite);
		cboType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cboType.setClasses(new Class[] {String.class,Double.class,Boolean.class});
		cboType.select(String.class);
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		label.setText("Expression (Java)*:");
		label.setBackground(Color.WHITE);
		label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("A Java expression for get the value. Use the 'msg' or 'extract' variable.");
		
		Composite compositeExpression = new Composite(composite, SWT.NONE);
		FillLayout layoutTextArea = new FillLayout();
		compositeExpression.setLayout(layoutTextArea);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, false, true, 4, 1);
		gridData.heightHint = 50;
		compositeExpression.setLayoutData(gridData);
		
		txtValidationExpression = new VText(compositeExpression, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtValidationExpression.setBackground(Color.WHITE);
		txtValidationExpression.valid();
    	
    	
		btnOk = new Button(composite, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		btnOk.setLayoutData(gridData);
		btnOk.setText("OK");
		btnOk.setFocus();
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (input == null) {
					output = new Validation(txtValidationName.getText(), txtValidationExpression.getText(), getExpected());
				} else {
					output = input;
					output.setName(txtValidationName.getText());
					output.setExpected(getExpected());
					output.setExpression(txtValidationExpression.getText());
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
				input = null;
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	if (input != null) {
    		txtValidationName.setText(StringUtils.escapeNull(input.getName()));
    		setExpected(input.getExpected());
    		txtValidationExpression.setText(input.getExpression() != null ? input.getExpression()+"" : viewer.getExpressionDefault());
    	} else {
    		txtValidationName.setText(viewer.getNameDefault());
    		setExpected(viewer.getExpectedDefault());
    		txtValidationExpression.setText(viewer.getExpressionDefault());
    	}
    }
    private Object getExpected() {
		if (cboType.getSelected() == Double.class) {
			try {
				return Double.parseDouble(txtValidationExpected.getText());
			} catch (Exception e) {
				return 0D;
			}
		} else if (cboType.getSelected() == Boolean.class) {
			return "true".equals(txtValidationExpected.getText());
		} else {
			return txtValidationExpected.getText();
		}
    }
    private void setExpected(Object expected) {
    	if ((expected instanceof Double) || (expected instanceof Integer)) {
    		cboType.select(Double.class);
    	} else if (expected instanceof Boolean) {
    		cboType.select(Boolean.class);
    	} else {
    		cboType.select(String.class);
    	}
    	txtValidationExpected.setText(expected+"");
    }
    public Validation getValidation() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
