package com.fimet.core.impl.swt.msg;

import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.Form;

import com.fimet.commons.Images;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IFieldParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.commons.Color;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldDialog extends Dialog {

	
	public FieldDialog(Integer idGroup, FieldNode field, String idFieldSuggest, Shell shell, String title, int style) {
		super(shell);
        this.setShellStyle(style);
        this.inputField = field;
        this.title = title == null ? "Edit Field" : title;
        this.idGroup = idGroup;
        this.idFieldSuggest = idFieldSuggest;
	}
	private static IFieldParserManager fieldParserManager = Manager.get(IFieldParserManager.class);
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	private String title;
	private Integer idGroup;
	private String idFieldSuggest;
	
	private Label lblKey;
	private Label lblValue;
	private TextDecorate txtFieldId;
	private TextDecorate txtFieldValue;
	private FieldNode inputField;
	private FieldNode outputField;
	private boolean invalidLength;
	private boolean invalidType;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText(title);
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 150, w = 400;
        shell.setBounds(Math.max(bounds.x + bounds.width/2 - w/2,0), Math.max(bounds.y + bounds.height/2 - h/2,0), w, h);
        shell.setImage(Images.TYPES_IMG.createImage());
        
        createControls(shell);
        //shell.setBounds(Math.max(bounds.x + bounds.width/2 - size.x/2,0), Math.max(bounds.y + bounds.height/2 - size.y/2,0), size.y, size.x);
        //shell.setTabList(new Control[] {ctrl});
        shell.open();
        shell.layout();

        Display display = getParentShell().getDisplay();
        while (!shell.isDisposed()) {
                if (!display.readAndDispatch()) display.sleep();
        }
        return 0;
	}
	
    private Composite createControls(Composite parent) {
    	Form form = new Form(parent, SWT.NONE);
    	form.setBackground(Color.WHITE);
    	form.setLayout(new GridLayout(1,true));
    	form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    	GridLayout layout = new GridLayout(4,true);
    	layout.horizontalSpacing = 10;
        Composite composite = form.getBody();//new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        
		lblKey = new Label(composite, SWT.NONE);
		lblKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblKey.setBackground(Color.WHITE);
		lblKey.setForeground(Color.TITLE_FORM);
		lblKey.setText("Id Field:");
		lblKey.setToolTipText("The id field");
		
		txtFieldId = new TextDecorate(composite, SWT.BORDER);
		txtFieldId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtFieldId.setBackground(Color.WHITE);
		txtFieldId.setValidator((String t)->{return t != null && t.length() > 0;});
		txtFieldId.valid();
    	
		lblValue = new Label(composite, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lblValue.setBackground(Color.WHITE);
		lblValue.setForeground(Color.TITLE_FORM);
		lblValue.setText("Value:");
		lblValue.setToolTipText("The value of the field");
		
		txtFieldValue = new TextDecorate(composite, SWT.BORDER);
		txtFieldValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtFieldValue.setBackground(Color.WHITE);
		txtFieldValue.setValidator((String t)->{return t != null && t.length() > 0;});
		txtFieldValue.valid();
		
        Composite compositeBtns = new Composite(composite, SWT.NONE);
        compositeBtns.setBackground(Color.WHITE);
        compositeBtns.setLayout(new GridLayout(2,true));
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
		btnOk = new Button(compositeBtns, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnOk.setText("OK");
		
		btnCancel = new Button(compositeBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onOkClick();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		//composite.setTabList(new Control [] {txtFieldId, txtFieldValue, compositeBtns});
		
    	btnCancel.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
				shell.dispose();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	txtFieldId.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validateIdField();
			}
		});

    	txtFieldValue.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				lblValue.setText("Value ("+txtFieldValue.getText().length()+"):");
				validateValueField();
			}
		});
    	
		if (inputField != null) {
			lblValue.setText("Value ("+(inputField.getValue() != null ? inputField.getValue() : "").length()+"):");
			txtFieldId.setText(inputField.getIdField() != null ? inputField.getIdField() : "");
			txtFieldValue.setText(inputField.getValue() != null ? inputField.getValue() : "");
			txtFieldValue.setFocus();
			txtFieldValue.selectAll();
			validateIdField();
			validateValueField();
		} else {
			btnOk.setEnabled(false);
		}
    	if (idFieldSuggest != null) {
    		txtFieldId.setText(idFieldSuggest);
    	}
    	return form;
    }
    private void validateIdField() {
		if (
			"header".equals(txtFieldId.getText()) ||
			"mti".equals(txtFieldId.getText()) ||
			fieldParserManager.getFieldParser(idGroup, txtFieldId.getText()) != null
		) {
			txtFieldId.markValid();
			txtFieldValue.setEnabled(true);
			btnOk.setEnabled(true);
		} else {
			txtFieldId.markInvalid("Unknow field "+txtFieldId.getText());
			txtFieldValue.setEnabled(false);
			btnOk.setEnabled(false);
		}
    }
    private void validateValueField() {
		IFieldParser fieldParser = fieldParserManager.getFieldParser(idGroup, txtFieldId.getText());
		if (fieldParser != null) {
			if (isValidLength(fieldParser, txtFieldValue.getText()) & isValidValue(fieldParser, txtFieldValue.getText())) {
				txtFieldValue.markValid();
			}
		}
    }
    private boolean isValidValue(IFieldParser fieldParser, String value) {
		if (!fieldParser.isValidValue(txtFieldValue.getText())) {
			invalidType = true;
			txtFieldValue.markInvalid("Expected format "+fieldParser.getType());
			return false;
		} else {
			invalidType = false;
			return true;
		}
    }
    private boolean isValidLength(IFieldParser fieldParser, String value) {
		if (!fieldParser.isValidLength(txtFieldValue.getText())) {
			invalidLength = true;
			if (fieldParser.isFixed()) {
				txtFieldValue.markInvalid("Expected length "+fieldParser.getLength());
			} else {
				txtFieldValue.markInvalid("Expected max length "+((VarFieldParser)fieldParser).getMaxLength());
			}	
			return false;
		} else {
			invalidLength = false;
			return true;
		}
    }
    private void onOkClick() {
		if (inputField == null) {
			outputField = new FieldNode(txtFieldId.getText(), txtFieldValue.getText());
		} else {
			inputField.setKey(txtFieldId.getText());
			inputField.setValue(txtFieldValue.getText());
			outputField = inputField;
		}
		outputField.invalidType = invalidType;
		outputField.invalidLength = invalidLength;
		shell.close();
		shell.dispose();    	
    }
    public FieldNode getField() {
		return outputField;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
