package com.fimet.core.impl.swt.format;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import com.fimet.core.IFieldFormatManager;
import com.fimet.core.Manager;
import com.fimet.core.entity.sqlite.FieldFormatGroup;
import com.fimet.core.impl.swt.FieldFormatGroupCombo;
import com.fimet.core.impl.swt.VText;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class GroupDialog extends TrayDialog implements DisposeListener {

	public GroupDialog(Shell shell,  int style) {
		this(null, null, shell, style);
	}
	public GroupDialog(FieldFormatGroup parent, FieldFormatGroup input, Shell shell, int style) {
		super(shell);
		this.parent = parent;
        this.setShellStyle(style);
		this.input = input;
	}
	private VText txtId;
	private VText txtName;
	private FieldFormatGroupCombo cboFieldGroup;
	private IFieldFormatManager fieldFormatGroupManager = Manager.get(IFieldFormatManager.class);
	
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private FieldFormatGroup parent;
	private FieldFormatGroup input;
	private FieldFormatGroup output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.addDisposeListener(this);
        shell.setText("Field Format Group");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 170, w = 420;
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
		label.setText("Group id*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(Color.WHITE);
		label.setToolTipText("The parser id");
		
		txtId = new VText(composite, SWT.BORDER);
		txtId.setBackground(Color.WHITE);
		txtId.setEnabled(false);
		txtId.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Group Name*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The field group name");
		
		txtName = new VText(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		label.setText("Parent Group*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("The parent field group");
		
		cboFieldGroup = new FieldFormatGroupCombo(composite, true);
		cboFieldGroup.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
   	
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnOk.setText("OK");
		btnOk.setFocus();
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		
		fillFormData();
		hookListeners();
    }
    private void fillFormData() {
    	if (input != null) {
    		txtId.setText(input.getId()+"");
    		txtName.setText(StringUtils.escapeNull(input.getName()));
			cboFieldGroup.select(input.getIdParent());
    	} else {
    		txtId.setText(fieldFormatGroupManager.getNextIdGroup()+"");
    		txtName.setText("FieldFormatGroup Name");
    		if (this.parent != null)
    			cboFieldGroup.select(this.parent.getId());
    	}
	}
	private void hookListeners() {
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				output = input == null ? new FieldFormatGroup() : input;
				output.setId(Integer.parseInt(txtId.getText()));
				output.setName(txtName.getText());
				output.setId(Integer.valueOf(txtId.getText()));
				if (cboFieldGroup.getSelected() != null) {
					output.setIdParent(cboFieldGroup.getSelected().getId());
				} else {
					output.setIdParent(null);
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
	}
	public FieldFormatGroup getFieldFormatGroup() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (input == null && output == null)
			fieldFormatGroupManager.getPrevIdGroup();
	}
}
