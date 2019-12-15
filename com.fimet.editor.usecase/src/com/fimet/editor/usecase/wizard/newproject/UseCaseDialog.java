package com.fimet.editor.usecase.wizard.newproject;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
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

import com.fimet.commons.Activator;
import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.impl.swt.AcquirerCombo;
import com.fimet.core.impl.swt.IssuerCombo;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.impl.swt.msg.IMessageContainer;
import com.fimet.core.impl.swt.msg.MessageViewer;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCase;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class UseCaseDialog extends TrayDialog implements IMessageContainer {
	private ISocket acquirerSuggest;
	private ISocket issuerSuggest;
	public UseCaseDialog(UseCase useCase, ISocket acquirer, ISocket issuer, Shell shell,  int style) {
		super(shell);
		this.input = useCase;
		this.acquirerSuggest = acquirer;
		this.issuerSuggest = issuer;
        this.setShellStyle(style);
	}
	private TextDecorate txtName;
	private AcquirerCombo cboAcquirer;
	private IssuerCombo cboIssuer;
	private MessageViewer messageViewer;
	private UseCase input;
	private UseCase output;
	private Shell shell;
	private Button btnSave;
	private Button btnCancel;
	
	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Use Case");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 450, w = 500;
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

        Composite container = new Composite(parent, SWT.NONE);
        container.setBackground(Color.WHITE);
        GridLayout layout = new GridLayout(1,false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
		container.setLayout(layout);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		container.setBackground(Color.WHITE);
		
        Composite composite = new Composite(container, SWT.NONE);
        composite.setBackground(Color.WHITE);
        layout = new GridLayout(4,true);
        layout.horizontalSpacing = 10;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBackground(Color.WHITE);
		
		Label label;
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Name:");

		txtName = new TextDecorate(composite, SWT.BORDER);
		txtName.setBackground(Color.WHITE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtName.setValidator((String text)->{return text.trim().length() > 0;});
		
		label = new Label(composite,SWT.NONE);
		label.setText("Acquirer:");
		label.setBackground(composite.getBackground());
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboAcquirer = new AcquirerCombo(composite);
		cboAcquirer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		label = new Label(composite,SWT.NONE);
		label.setText("Issuer:");
		label.setBackground(composite.getBackground());
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboIssuer = new IssuerCombo(composite);
		cboIssuer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		messageViewer = new MessageViewer(this, true, composite, SWT.NONE);
		messageViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setBackground(composite.getBackground());
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 2, 1));
		
		btnSave = new Button(composite, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Ok");
		btnSave.setFocus();
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnSave);
		
		fillDataForm();
    	hookListeners();
    }
	private void hookListeners() {
		btnSave.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doSave();
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
    		txtName.validateAndMark();
    		validate();
    	});
    	cboAcquirer.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cboAcquirer.valid();
				validate();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	private void fillDataForm() {
		if (input != null) {
			txtName.setText(StringUtils.escapeNull(input.getName()));
			cboAcquirer.select(input.getAcquirer().getConnection());
			cboIssuer.select(input.getIssuer() != null ? input.getIssuer().getConnection() : null);
			messageViewer.setMessage(input.getMsgAcqReq());
		} else {
			txtName.invalid();
			cboAcquirer.select(acquirerSuggest);
			if (cboAcquirer.getSelected() != null) {
				cboAcquirer.valid();
			}
			cboIssuer.select(issuerSuggest);
			btnSave.setEnabled(false);
		}
	}
	private void doSave() {
		try {
			output = input != null ? input : new UseCase(cboAcquirer.getSelected(), cboIssuer.getSelected());
			output.setName(txtName.getText());
			output.getAcquirer().getRequest().setMessage(messageViewer.getMessage());
		} catch (Exception e) {
			Activator.getInstance().warning("Error saving "+txtName.getText(),e);
		}
	}
	private void validate() {
		btnSave.setEnabled(isValid());
	}
	private boolean isValid() {
		return txtName.isValid() && cboAcquirer.isValid() && messageViewer.getMessage()!= null;
	}
	public UseCase getOutput() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }

	@Override
	public void onModifyMessage() {
		validate();
	}

	@Override
	public ISocket getConnection() {
		return cboAcquirer.getSelected();
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return null;
	}
}
