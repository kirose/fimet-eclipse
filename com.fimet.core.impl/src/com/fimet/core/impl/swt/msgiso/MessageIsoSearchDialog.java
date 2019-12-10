package com.fimet.core.impl.swt.msgiso;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.swt.SWT;
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

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;


/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageIsoSearchDialog extends TrayDialog {

	
	public MessageIsoSearchDialog(MessageIsoParameters inputParams, Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
        this.inputParams = inputParams;
        this.params = new MessageIsoParameters();
        if (inputParams != null) {
        	if (inputParams.getIdTypeEnviroment() != null) {
        		params.setIdTypeEnviroment(inputParams.getIdTypeEnviroment());
        	}
        	if (inputParams.getIdParser() != null) {
        		params.setIdParser(inputParams.getIdParser());
        	}
        	if (inputParams.getType() != null) {
        		params.setType(inputParams.getType());
        	}
        }
	}
	
	private MessageIsoParameters inputParams;
	private MessageIsoParameters params;
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	private MessageIso output;
	private MessageIsoViewer viewer;

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Message ISO8583");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 450, w = 750;
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

		GridLayout layout = new GridLayout(1,true);
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        Label lbl;

        viewer = new MessageIsoViewer(inputParams, false, composite, SWT.NONE);
		
        Composite compositeBtns = new Composite(composite, SWT.NONE);
        compositeBtns.setBackground(Color.WHITE);
        compositeBtns.setLayout(new GridLayout(6,true));
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    	
		lbl = new Label(compositeBtns,SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		lbl.setBackground(Color.WHITE);
        
		btnOk = new Button(compositeBtns, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnOk.setText("OK");
		btnOk.setFocus();
		
		btnCancel = new Button(compositeBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				output = viewer.getSelectedMessage();
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
    	viewer.addTableDoubleClickListener((DoubleClickEvent event)->{
			output = viewer.getSelectedMessage();
			shell.close();
			shell.dispose();
		});
    }
    public MessageIso getMessage() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
