package com.fimet.editor.stress.section.acquirers;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
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
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.impl.swt.SocketCombo;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.impl.swt.VText;
import com.fimet.core.stress.StressAcquirer;
import com.fimet.core.usecase.Field;
import com.fimet.commons.Color;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class AcquirersDialog extends TrayDialog {

	
	public AcquirersDialog(StressAcquirer file, Shell shell, FormToolkit toolkit, int style) {
		super(shell);
        this.setShellStyle(style);
        this.toolkit = toolkit;
        this.input = file;
	}
	public AcquirersDialog(Shell shell, FormToolkit toolkit, int style) {
		this(null, shell, toolkit, style);
	}
	private FormToolkit toolkit;
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private VText txtFile;
	private StressAcquirer input;
	private StressAcquirer output;
	private AcquirerCombo cboAcquirer;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Field");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 120, w = 300;
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
		Label lbl;
		
		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		lbl.setText("Acquirer:");
		lbl.setToolTipText("The acquirer");
		
		cboAcquirer = new AcquirerCombo(composite);
		cboAcquirer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboAcquirer.getCombo().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {}
		});

		lbl = new Label(composite, SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		lbl.setBackground(Color.WHITE);
		lbl.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		lbl.setText("File:");
		lbl.setToolTipText("The file");
		
		txtFile = new TextDecorate(composite, SWT.BORDER);
		txtFile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtFile.setBackground(Color.WHITE);
		txtFile.setValidator((String t)->{return t != null && t.length() > 0;});
		txtFile.valid();
    	

		
		if (input != null) {
			//txtFile.setText(StringUtils.escapeNull(input.getFile()));
			cboAcquirer.select(input.getConnection());
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
				if (input == null) {
					output = new StressAcquirer(cboAcquirer.getSelected());
				} else {
					//input.setFile(txtFile.getText());
					input.setConnection(cboAcquirer.getSelected());
					output = input;
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
    public StressAcquirer getStressField() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
