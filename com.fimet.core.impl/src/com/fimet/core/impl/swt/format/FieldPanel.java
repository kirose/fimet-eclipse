package com.fimet.core.impl.swt.format;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.fimet.commons.Color;
import com.fimet.commons.console.Console;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IFieldParserManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldFormat;
import com.fimet.core.impl.swt.ClassCombo;
import com.fimet.core.impl.swt.ConverterCombo;
import com.fimet.core.impl.swt.FieldFormatGroupCombo;
import com.fimet.core.impl.swt.NumericParserCombo;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.impl.swt.VText;

public class FieldPanel extends ScrolledComposite {

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
	
	public FieldPanel(Composite parent, int style) {
		super(parent, style);
		setFont(parent.getFont());
		setLayout(new GridLayout(1,true));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        setExpandHorizontal(true);
        setExpandVertical(true);
        setBackground(Color.WHITE);
		setContent(createContents(this));
		setMinSize(300, Console.isEnabledDebug() ? 400 : 300);
	}

    private Composite createContents(ScrolledComposite parent) {
    
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

		cboFieldFormatGroup = new FieldFormatGroupCombo(composite, false);
		cboFieldFormatGroup.getCombo().setBackground(Color.WHITE);
		cboFieldFormatGroup.getCombo().setEnabled(false);
		cboFieldFormatGroup.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		if (Console.isEnabledDebug()) {
		
			label = new Label(composite, SWT.NONE);
			label.setBackground(Color.WHITE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			label.setText("Id:");
			label.setToolTipText("The field format id");
	
			txtIdFieldFormat = new VText(composite, SWT.BORDER);
			txtIdFieldFormat.setBackground(Color.WHITE);
			txtIdFieldFormat.setEditable(false);
			txtIdFieldFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
			txtIdFieldFormat.valid();
			
			label = new Label(composite, SWT.NONE);
			label.setBackground(Color.WHITE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			label.setText("Parent Field Id:");
	

			txtIdFieldParent = new TextDecorate(composite, SWT.BORDER);
			txtIdFieldParent.setEditable(false);
			txtIdFieldParent.setBackground(Color.WHITE);
			txtIdFieldParent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
			
			label = new Label(composite, SWT.NONE);
			label.setBackground(Color.WHITE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			label.setText("Address:");
	
			txtIdOrder = new TextDecorate(composite, SWT.BORDER);
			txtIdOrder.setBackground(Color.WHITE);
			txtIdOrder.setEditable(false);
			txtIdOrder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
			
			label = new Label(composite, SWT.NONE);
			label.setBackground(Color.WHITE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			label.setText("Children:");
	
			txtChildren = new Text(composite, SWT.BORDER);
			txtChildren.setEditable(false);
			txtChildren.setBackground(Color.WHITE);
			txtChildren.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
			
			label = new Label(composite, SWT.NONE);
			label.setBackground(Color.WHITE);
			label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			label.setText("Key:");
	
			txtKey = new TextDecorate(composite, SWT.BORDER);
			txtKey.setBackground(Color.WHITE);
			txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
			txtKey.setEditable(false);
		}
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Field Id:");
		
		txtIdField = new TextDecorate(composite, SWT.BORDER);
		txtIdField.setBackground(Color.WHITE);
		txtIdField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtIdField.setEditable(false);
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Class Parser:");
		
		cboClassParser = new ClassCombo(composite);
		cboClassParser.getCombo().setBackground(Color.WHITE);
		cboClassParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboClassParser.setClasses(Manager.get(IFieldParserManager.class).getParserClasses());
		cboClassParser.getCombo().setEnabled(false);
    
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Name:");

		txtName = new TextDecorate(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtName.setEditable(false);
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Value Length:");
		label.setToolTipText("Fixed Length or Variable Length (number of positions)");

		txtLength = new TextDecorate(composite, SWT.BORDER);
		txtLength.setBackground(Color.WHITE);
		txtLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtLength.setEditable(false);
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Max Length:");
		label.setToolTipText("Only for Variable Length");
		
		txtMaxLength = new TextDecorate(composite, SWT.BORDER);
		txtMaxLength.setBackground(Color.WHITE);
		txtMaxLength.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtMaxLength.setEditable(false);
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Value Converter:");
		
		cboValueConverter = new ConverterCombo(composite);
		cboValueConverter.getCombo().setBackground(Color.WHITE);
		cboValueConverter.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboValueConverter.getCombo().setEnabled(false);
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Length Converter:");
		
		cboLengthConverter = new ConverterCombo(composite);
		cboLengthConverter.getCombo().setBackground(Color.WHITE);
		cboLengthConverter.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboLengthConverter.getCombo().setEnabled(false);
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Length Parser:");
		
		cboLengthParser = new NumericParserCombo(composite);
		cboLengthParser.getCombo().setBackground(Color.WHITE);
		cboLengthParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboLengthParser.getCombo().setEnabled(false);
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Value Regexp:");

		txtType = new TextDecorate(composite, SWT.BORDER);
		txtType.setBackground(Color.WHITE);
		txtType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtType.setEditable(false);
		
		return composite;
    }
	public void setField(FieldFormat field) {
    	if (field != null) {
    		if (txtIdFieldFormat != null) {
    			txtIdFieldFormat.setText(StringUtils.escapeNull(field.getIdFieldFormat()));
    			txtIdFieldParent.setText(StringUtils.escapeNull(field.getIdFieldParent()));
    			txtIdOrder.setText(StringUtils.escapeNull(field.getIdOrder()));
    			txtKey.setText(StringUtils.escapeNull(field.getKey()));
    			txtChildren.setText(StringUtils.escapeNull(field.getChilds()));
    		}
    		txtIdField.setText(StringUtils.escapeNull(field.getIdField()));
    		txtName.setText(StringUtils.escapeNull(field.getName()));
    		txtType.setText(StringUtils.escapeNull(field.getType()));
    		txtLength.setText(StringUtils.escapeNull(field.getLength()));
    		txtMaxLength.setText(StringUtils.escapeNull(field.getMaxLength()));
			cboValueConverter.select(field.getIdConverterValue());
			cboLengthConverter.select(field.getIdConverterLength());
			cboLengthParser.select(field.getIdParserLength());
    		cboClassParser.select(field.getClassParser());
    		cboFieldFormatGroup.select(field.getIdGroup());
    	} else {
    		if (txtIdFieldFormat != null) {
	    		txtIdFieldFormat.setText("");
	    		txtIdFieldParent.setText("");
	    		txtIdOrder.setText("");
	    		txtKey.setText("");
	    		txtChildren.setText("");
    		}
    		txtIdField.setText("");
    		txtName.setText("");
    		txtType.setText("");
    		txtLength.setText("");
    		txtMaxLength.setText("");
			cboValueConverter.getCombo().deselectAll();
			cboLengthConverter.getCombo().deselectAll();
			cboLengthParser.getCombo().deselectAll();
    		cboClassParser.getCombo().deselectAll();
    		cboFieldFormatGroup.getCombo().deselectAll();
    	}
	}
	

}
