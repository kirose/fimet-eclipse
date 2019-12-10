package com.fimet.core.impl.swt;

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

import com.fimet.commons.Color;
import com.fimet.commons.Images;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageDialog extends TrayDialog {

	public MessageDialog(Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
	}
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private TextDecorate txtMsg;
	private String message;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Parse a Message");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 200, w = 400;
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

		GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label label;

		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.TITLE_FORM);
		label.setText("Message");
		label.setToolTipText("Enter a message in hexadecimal (log hex, sim_queue)");

		
		Composite compositeTextArea = new Composite(composite, SWT.NONE);
		FillLayout layoutTextArea = new FillLayout();
		compositeTextArea.setLayout(layoutTextArea);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		//gridData.heightHint = 80;
		compositeTextArea.setLayoutData(gridData);
		
		txtMsg = new TextDecorate(compositeTextArea, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtMsg.setBackground(Color.WHITE);
		txtMsg.valid();
    	
		layout = new GridLayout(2,true);
		layout.marginWidth = 0;
        
        Composite compositeBtns = new Composite(composite, SWT.NONE);
        compositeBtns.setBackground(Color.WHITE);
        compositeBtns.setLayout(layout);
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	
		btnOk = new Button(compositeBtns, SWT.NONE);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		btnOk.setLayoutData(gridData);
		btnOk.setText("OK");
		
		btnCancel = new Button(compositeBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				message = txtMsg.getText();
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
    public String getMessage() {
		return message;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
