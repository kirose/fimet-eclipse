package com.fimet.editor.usecase.section.swt.mgrfields;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.impl.swt.VText;
import com.fimet.core.usecase.Field;
import com.fimet.commons.Color;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class IncFieldDialog extends TrayDialog {

	
	public IncFieldDialog(Field field, Shell shell, FormToolkit toolkit, int style) {
		super(shell);
        this.setShellStyle(style);
        this.toolkit = toolkit;
        this.inputField = field;
	}
	public IncFieldDialog(Shell shell, FormToolkit toolkit, int style) {
		this(null, shell, toolkit, style);
	}
	private FormToolkit toolkit;
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private Label lblKey;
	private Label lblValue;
	private VText txtFieldKey;
	private VText txtFieldValue;
	private Field inputField;
	private Field outputField;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Field");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 150, w = 400;
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

		GridLayout layout = new GridLayout(4,true);
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		lblKey = new Label(composite, SWT.NONE);
		lblKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblKey.setBackground(Color.WHITE);
		lblKey.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		lblKey.setText("Id Field:");
		lblKey.setToolTipText("The id field");
		
		txtFieldKey = new TextDecorate(composite, SWT.BORDER);
		txtFieldKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtFieldKey.setBackground(Color.WHITE);
		txtFieldKey.setValidator((String t)->{return t != null && t.length() > 0;});
		txtFieldKey.valid();
    	
		lblValue = new Label(composite, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblValue.setBackground(Color.WHITE);
		lblValue.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		lblValue.setText("Value:");
		lblValue.setToolTipText("The value of the field");
		
		txtFieldValue = new TextDecorate(composite, SWT.BORDER);
		txtFieldValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtFieldValue.setBackground(Color.WHITE);
		txtFieldValue.setValidator((String t)->{return t != null && t.length() > 0;});
		txtFieldValue.valid();
		
		if (inputField != null) {
			lblValue.setText("Value ("+(inputField.getValue() != null ? inputField.getValue() : "").length()+"):");
			txtFieldKey.setText(StringUtils.escapeNull(inputField.getKey()));
			txtFieldValue.setText(StringUtils.escapeNull(inputField.getValue()));
		}
		
        Composite compositeBtns = new Composite(composite, SWT.NONE);
        compositeBtns.setBackground(Color.WHITE);
        compositeBtns.setLayout(new GridLayout(2,true));
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
		btnOk = new Button(compositeBtns, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnOk.setText("OK");
		btnOk.setFocus();
		
		btnCancel = new Button(compositeBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (inputField == null) {
					outputField = new Field(txtFieldKey.getText(), txtFieldValue.getText());
				} else {
					inputField.setKey(txtFieldKey.getText());
					inputField.setValue(txtFieldValue.getText());
					outputField = inputField;
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
    	txtFieldValue.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				lblValue.setText("Value ("+txtFieldValue.getText().length()+"):");
			}
		});
    }
    public Field getField() {
		return outputField;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
