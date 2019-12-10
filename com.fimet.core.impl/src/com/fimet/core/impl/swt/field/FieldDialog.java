package com.fimet.core.impl.swt.field;

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
//import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.console.Console;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.FixedFieldParser;
import com.fimet.core.ISO8583.parser.VarFieldParser;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.impl.swt.ClassCombo;
import com.fimet.core.impl.swt.ConverterCombo;
import com.fimet.core.impl.swt.FieldFormatGroupCombo;
import com.fimet.core.impl.swt.NumericParserCombo;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.impl.swt.VText;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldDialog extends TrayDialog {

	public FieldDialog(IFieldController controller, Shell shell,  int style) {
		this(controller, null, null, shell, style);
	}
	public FieldDialog(IFieldController controller, FieldNode parent, FieldFormat input, Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
        this.controller = controller;
		this.input = input;
		this.parent = parent;
	}
	private IFieldController controller;

	private VText txtIdFieldFormat;
	private TextDecorate txtIdFieldParent;
	private TextDecorate txtIdField;
	private TextDecorate txtIdOrder;
	private TextDecorate txtName;
	private TextDecorate txtType;
	private TextDecorate txtLength;
	private TextDecorate txtMaxLength;
	private TextDecorate txtKey;
	private Text txtChildren;
	private ConverterCombo cboValueConverter;
	private ConverterCombo cboLengthConverter;
	private NumericParserCombo cboLengthParser;
	private ClassCombo cboClassParser;
	private FieldFormatGroupCombo cboFieldFormatGroup;

	
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private FieldNode parent;
	private FieldFormat input;
	private FieldFormat output;
	private Label lblLength;
	
	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Field Format");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 490, w = 380;
        shell.setBounds(Math.max(bounds.x + bounds.width/2 - w/2,0), Math.max(bounds.y + bounds.height/2 - h/2,0), w, h);
        shell.setImage(Images.TYPES_IMG.createImage());
        createControls(shell);
        //shell.setBounds(Math.max(bounds.x + bounds.width/2 - size.x/2,0), Math.max(bounds.y + bounds.height/2 - size.y/2,0), size.y, size.x);

        shell.open();
        shell.layout();

        Display display = getParentShell().getDisplay();
        while (!shell.isDisposed()) {
                if (display != null && !display.readAndDispatch()) display.sleep();
        }
        return 0;
	}
	
    private void createControls(Composite parent) {

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
        GridLayout layout = new GridLayout(5,true);
        layout.horizontalSpacing = 10;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBackground(Color.WHITE);
		
		Label label;
		
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Fields Group:");

		cboFieldFormatGroup = new FieldFormatGroupCombo(composite);
		cboFieldFormatGroup.getCombo().setBackground(Color.WHITE);
		cboFieldFormatGroup.getCombo().setEnabled(false);
		cboFieldFormatGroup.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Id:");
		label.setToolTipText("The field format id");

		txtIdFieldFormat = new VText(composite, SWT.BORDER);
		txtIdFieldFormat.setBackground(Color.WHITE);
		txtIdFieldFormat.setEnabled(false);
		txtIdFieldFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtIdFieldFormat.valid();
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Parent Field Id:");

		txtIdFieldParent = new TextDecorate(composite, SWT.BORDER);
		txtIdFieldParent.setEnabled(false);
		txtIdFieldParent.setBackground(Color.WHITE);
		txtIdFieldParent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Field Id:");

		txtIdField = new TextDecorate(composite, SWT.BORDER);
		txtIdField.setBackground(Color.WHITE);
		txtIdField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtIdField.setEnabled(false);
		txtIdField.valid();
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Address:");

		txtIdOrder = new TextDecorate(composite, SWT.BORDER);
		txtIdOrder.setBackground(Color.WHITE);
		txtIdOrder.setEnabled(false);
		txtIdOrder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Children:");

		txtChildren = new Text(composite, SWT.BORDER);
		txtChildren.setEnabled(false);
		txtChildren.setBackground(Color.WHITE);
		txtChildren.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Key:");

		txtKey = new TextDecorate(composite, SWT.BORDER);
		txtKey.setBackground(Color.WHITE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtKey.setValidator((String text)->{
			if (this.parent == null) {
				return text != null && text.matches("^[0-9]+$");
			} else { 
				return text != null && text.matches("^[A-Za-z0-9_$]+$");
			}
		});
		txtKey.valid();
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Class Parser:");
		
		cboClassParser = new ClassCombo(composite);
		cboClassParser.getCombo().setBackground(Color.WHITE);
		cboClassParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboClassParser.setClasses(Manager.get(IFieldParserManager.class).getParserClasses());

		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Name:");

		txtName = new TextDecorate(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtName.setValidator((String text)->{
			return text != null && text.length() > 0;
		});
		txtName.valid();
		
		lblLength = new Label(composite, SWT.NONE);
		lblLength.setBackground(Color.WHITE);
		lblLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		lblLength.setText("Value Length:");
		lblLength.setToolTipText("Fixed Length or Variable Length (number of positions)");

		txtLength = new TextDecorate(composite, SWT.BORDER);
		txtLength.setBackground(Color.WHITE);
		txtLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtLength.setValidator((String text)->{return text!= null && text.length() > 0 && text.matches("[0-9]+");});
		txtLength.valid();
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Max Length:");
		label.setToolTipText("Only for Variable Length");
		
		txtMaxLength = new TextDecorate(composite, SWT.BORDER);
		txtMaxLength.setBackground(Color.WHITE);
		txtMaxLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtMaxLength.setValidator((String text)->{return text!= null && (text.length() == 0 || (text.length() > 0 && text.matches("[0-9]+")));});
		txtMaxLength.valid();		
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Value Converter:");
		
		cboValueConverter = new ConverterCombo(composite);
		cboValueConverter.getCombo().setBackground(Color.WHITE);
		cboValueConverter.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboValueConverter.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(cboValueConverter.getStructuredSelection() != null && cboValueConverter.getStructuredSelection().getFirstElement() != null) {
					cboValueConverter.valid();
				} else {
					cboValueConverter.invalid();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Length Converter:");
		
		cboLengthConverter = new ConverterCombo(composite);
		cboLengthConverter.getCombo().setBackground(Color.WHITE);
		cboLengthConverter.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboLengthConverter.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(cboLengthConverter.getStructuredSelection() != null && cboLengthConverter.getStructuredSelection().getFirstElement() != null) {
					cboLengthConverter.valid();
				} else {
					cboLengthConverter.invalid();
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Length Parser:");
		
		cboLengthParser = new NumericParserCombo(composite);
		cboLengthParser.getCombo().setBackground(Color.WHITE);
		cboLengthParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Value Regexp:");

		txtType = new TextDecorate(composite, SWT.BORDER);
		txtType.setBackground(Color.WHITE);
		txtType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtType.setValidator((String txt)->{
			try {
				"".matches(txtType.getText());
				return true;
			} catch (Exception e) {
				return false;
			}
		});
		txtType.valid();
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnOk.setText("OK");
		btnOk.setFocus();
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		
		fillFormData();
		validateParserOptions();
    	hookListeners();
    }
	private void hookListeners() {
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				output = input == null ? new FieldFormat() : input;
				output.setName(txtName.getText());
				if (txtIdFieldFormat.getText().length()>0) {
					output.setIdFieldFormat(Integer.valueOf(txtIdFieldFormat.getText()));
				}
				output.setIdGroup(controller.getSelectedGroup().getId());
				output.setIdOrder(txtIdOrder.getText());
				output.setIdField(txtIdField.getText());
				output.setKey(txtKey.getText());
				output.setIdConverterValue(cboValueConverter.getSelected().getId());
				output.setIdConverterLength(cboLengthConverter.getSelected().getId());
				output.setIdParserLength(cboLengthParser.getSelected().getId());
				output.setType(txtType.getText());
				output.setLength(Integer.valueOf(txtLength.getText()));
				output.setMaxLength(txtMaxLength.getText().trim().length() > 0 ? Integer.valueOf(txtMaxLength.getText()) : null);
				output.setName(txtName.getText());
				output.setClassParser(cboClassParser.getSelected().getName());
				output.setIdFieldParent(FieldDialog.this.parent != null ? FieldDialog.this.parent.field.getIdField() : null);
				
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
		cboClassParser.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validateParserOptions();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	txtName.addModifyListener((ModifyEvent e)->{
    		txtName.validateAndMark();
    		validate();
    	});
    	txtKey.addModifyListener((ModifyEvent e)->{
    		txtKey.validateAndMark();
    		validate();
    		if (txtKey.isValid()) {
	    		if (this.parent != null) {
	    			int nextId = 1;
	    			if (this.parent.hasChildren()) {
	    				String idOrder = this.parent.getLastChild().field.getIdOrder();
	    				nextId = Integer.parseInt(idOrder.substring(this.parent.field.getIdOrder().length()+1))+1;
	    			}
	    			//(this.parent.hasChildren() ? StringUtils.leftPad((this.parent.getChildren().length+1)+"", 3,'0') : "001")
	    			txtIdOrder.setText(this.parent.field.getIdOrder()+"."+StringUtils.leftPad(nextId+"", 3,'0'));
	    			txtIdField.setText(this.parent.field.getIdField()+"."+txtKey.getText());
	    		} else {
	    			txtIdOrder.setText(StringUtils.leftPad(txtKey.getText(), 3,'0'));
	    			txtIdField.setText(txtKey.getText());
	    		}
    		}
    	});
    	txtLength.addModifyListener((ModifyEvent e)->{
    		txtLength.validateAndMark();
    		validate();
    	});
    	txtType.addModifyListener((ModifyEvent e)->{
    		txtType.validateAndMark();
    		validate();
    	});
	}
	private void validateParserOptions() {
		boolean enabled = cboClassParser.getSelected() != null && VarFieldParser.class.isAssignableFrom(cboClassParser.getSelected());
		txtMaxLength.setEnabled(enabled);
		cboLengthConverter.getCombo().setEnabled(enabled);
		cboLengthParser.getCombo().setEnabled(enabled);
		if (enabled) {
			lblLength.setText("Length of length:");
		} else {
			lblLength.setText("Value Length:");
		}
	}
	private void fillFormData() {
    	if (input != null) {
    		txtIdFieldFormat.setText(StringUtils.escapeNull(input.getIdFieldFormat()));
    		txtIdFieldParent.setText(this.parent != null ? this.parent.field.getIdField() : "");
    		txtIdField.setText(StringUtils.escapeNull(input.getIdField()));
    		txtIdOrder.setText(StringUtils.escapeNull(input.getIdOrder()));
    		txtName.setText(StringUtils.escapeNull(input.getName()));
    		txtType.setText(validateType(StringUtils.escapeNull(input.getType())));
    		txtLength.setText(StringUtils.escapeNull(input.getLength()));
    		txtMaxLength.setText(StringUtils.escapeNull(input.getMaxLength()));
    		txtKey.setText(StringUtils.escapeNull(input.getKey()));
    		txtChildren.setText(StringUtils.escapeNull(input.getChilds()));
			cboValueConverter.select(input.getIdConverterValue());
			cboLengthConverter.select(input.getIdConverterLength());
			cboLengthParser.select(input.getIdParserLength());
    		cboClassParser.select(input.getClassParser());
    		cboFieldFormatGroup.select(input.getIdGroup());
    	} else {
    		if (this.parent != null) {
    			FieldFormat last = this.parent.hasChildren() ? this.parent.getLastChild().field : null;
        		txtIdFieldParent.setText(this.parent.field.getIdField());
        		txtName.setText("Field Format Name");
        		txtType.setText(last != null && last.getType() != null ? last.getType() : "(?s).*");
        		txtLength.setText(last != null && last.getLength() != null ? last.getLength()+"" : "");
        		txtMaxLength.setText(last != null && last.getMaxLength() != null ? last.getMaxLength()+"" : "");
        		txtKey.setText("");
        		txtChildren.setText("");
    			cboValueConverter.select(last != null && last.getIdConverterValue() != null ? last.getIdConverterValue() : Integer.valueOf(0));
    			cboLengthConverter.select(last != null && last.getIdConverterLength() != null ? last.getIdConverterLength() : Integer.valueOf(0));
    			cboLengthParser.select(last != null && last.getIdParserLength() != null? last.getIdParserLength() : Integer.valueOf(0));
        		cboClassParser.select(last != null && last.getClassParser() != null ? last.getClassParser() : FixedFieldParser.class.getName());
        		cboFieldFormatGroup.select(controller.getSelectedGroup().getId());
    		} else {
        		txtIdFieldParent.setText("");
        		txtName.setText("Field Format Name");
        		txtType.setText("(?s).*");
        		txtLength.setText("2");
        		txtMaxLength.setText("999");
        		txtKey.setText("");
        		txtChildren.setText("");
    			cboValueConverter.select(0);
    			cboLengthConverter.select(0);
    			cboLengthParser.select(0);
        		cboClassParser.select(FixedFieldParser.class);
        		cboFieldFormatGroup.select(controller.getSelectedGroup().getId());
    		}
    		btnOk.setEnabled(false);
    	}
	}
	private String validateType(String type) {
		try {
			"".matches(type);
			return type;
		} catch (Exception e) {
			Console.getInstance().warning(FieldDialog.class, "Invalida regexp for field format type "+type);
			return "(?s).*";
		}
	}
	private void validate() {
		btnOk.setEnabled(isValid());
	}
	private boolean isValid() {
		return txtKey.isValid() && txtName.isValid() && txtIdField.isValid() && txtType.isValid() && txtLength.isValid();
	}
    public FieldFormat getFieldFormat() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
