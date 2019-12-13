package com.fimet.core.impl.preferences.parser;

import org.eclipse.jface.bindings.keys.KeySequence;
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
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.entity.sqlite.Parser;
import com.fimet.core.impl.swt.ClassCombo;
import com.fimet.core.impl.swt.ConverterCombo;
import com.fimet.core.impl.swt.FieldFormatGroupCombo;
import com.fimet.core.impl.swt.KeyValueCombo;
import com.fimet.core.impl.swt.TextDecorate;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class ParserDialog extends TrayDialog implements DisposeListener {

	public ParserDialog(Shell shell,  int style) {
		this(null, shell, style);
	}
	public ParserDialog(Parser input, Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
		this.input = input;
	}
	private IParserManager parserManager = Manager.get(IParserManager.class);
	private TextDecorate txtId;
	private TextDecorate txtName;
	private ClassCombo cboClass;
	private ConverterCombo cboConverter;
	private FieldFormatGroupCombo cboFieldGroup;
	private KeyValueCombo<Integer> cboType;
	private TextDecorate txtKeySequence;
	
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private Parser input;
	private Parser output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.addDisposeListener(this);
        shell.setText("Parser");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 275, w = 420;
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
		label.setText("Parser id*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(Color.WHITE);
		label.setToolTipText("The parser id");
		
		txtId = new TextDecorate(composite, SWT.BORDER);
		txtId.setBackground(Color.WHITE);
		txtId.setEnabled(false);
		txtId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Parser Name*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The parser name");
		
		txtName = new TextDecorate(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtName.setValidator((String text)->{return text.trim().length() > 0;});

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Parser Class*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The parser class ");
		
		cboClass = new ClassCombo(composite);
		cboClass.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cboClass.setClasses(parserManager.getParserClasses());
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Converter*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("A global converter");
		
		cboConverter = new ConverterCombo(composite);
		cboConverter.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Field Group*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The field format group");
		
		cboFieldGroup = new FieldFormatGroupCombo(composite, false);
		cboFieldGroup.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Parser Type*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("A global converter");
		
		cboType = new KeyValueCombo<Integer>(composite);
		cboType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cboType.addOption("ISO8583", IParser.ISO8583);
		cboType.addOption("Layout", IParser.LAYOUT);
		cboType.refreshOptions();
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Key Sequence*:");
		label.setBackground(Color.WHITE);
		label.setToolTipText("Ctrl:M4, M3:Alt, Shift:M2, Ctrl:M1\nExample 1:M3+N P\nExample 2:M2+R");
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		
		txtKeySequence = new TextDecorate(composite, SWT.BORDER);
		txtKeySequence.setBackground(Color.WHITE);
		txtKeySequence.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtKeySequence.setValidator((String text)->{
			try {
				return text.trim().length() > 0 && KeySequence.getInstance(text) != null;
			} catch (Exception e) {
				return false;
			}
		});
    	
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
				
				output = input == null ? new Parser() : input;
				output.setId(Integer.parseInt(txtId.getText()));
				output.setName(txtName.getText());
				output.setIdGroup(cboFieldGroup.getSelected().getId());
				output.setParserClass(cboClass.getSelected().getName());
				output.setIdConverter(cboConverter.getSelected().getId());
				output.setType(cboType.getSelected());
				output.setKeySequence(txtKeySequence.getText());

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
    	cboClass.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboClass.getSelected() == null) {
    			cboClass.invalid();
    		} else {
    			cboClass.valid();
    		}
    		validate();
		});
    	cboConverter.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboConverter.getSelected() == null) {
    			cboConverter.invalid();
    		} else {
    			cboConverter.valid();
    		}
    		validate();
		});
    	cboFieldGroup.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboFieldGroup.getSelected() == null) {
    			cboFieldGroup.invalid();
    		} else {
    			cboFieldGroup.valid();
    		}
    		validate();
		});
    	cboType.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboType.getSelected() == null) {
    			cboType.invalid();
    		} else {
    			cboType.valid();
    		}
    		validate();
		});
    	txtKeySequence.addModifyListener((ModifyEvent e)->{
    		txtKeySequence.validate();
    		validate();
		});
	}
    private void fillFormData() {
    	if (input != null) {
    		txtId.setText(input.getId()+"");
    		txtName.setText(StringUtils.escapeNull(input.getName()));
    		txtName.valid();
			cboFieldGroup.select(input.getIdGroup());
			cboFieldGroup.valid();
			cboClass.select(input.getParserClass());
			cboClass.valid();
			cboConverter.select(input.getIdConverter());
			cboConverter.valid();
			cboType.select(input.getType());
			cboType.valid();
			txtKeySequence.setText(input.getKeySequence());
			txtKeySequence.valid();
			validate();
    	} else {
    		txtId.setText(Manager.get(IParserManager.class).getNextIdParser()+"");
    		txtName.setText("Parser Name");
    		txtName.valid();
    		cboType.select("ISO8583");
    		cboType.valid();
    		txtKeySequence.invalid();
    		btnOk.setEnabled(false);
    	}		
	}
    private boolean isValid() {
    	return txtKeySequence.isValid() && cboType.isValid() && cboFieldGroup.isValid() && cboConverter.isValid() && cboClass.isValid() && txtName.isValid();
    }
    private void validate() {
    	btnOk.setEnabled(isValid());
    }
	public Parser getParser() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (input == null && output == null)
			Manager.get(IParserManager.class).getPrevIdParser();		
	}
}
