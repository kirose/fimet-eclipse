package com.fimet.core.impl.swt.msgiso;

import org.eclipse.core.resources.IResource;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.fimet.commons.Activator;
import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.converter.Converter;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IMessageIsoManager;
import com.fimet.core.IUseCaseManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.IByteArrayAdapter;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.usecase.UseCase;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageIsoSaveDialog extends TrayDialog {
	public MessageIsoSaveDialog(IResource resource, Shell shell,  int style) {
		super(shell);
        this.setShellStyle(style);
        this.resource = resource;
        try {
        	useCase = Manager.get(IUseCaseManager.class).parseForExecution(resource);
        } catch (Exception e) {
        	Activator.getInstance().warning("Invalid use case "+resource,e);
        }
	}
	UseCase useCase;
	IResource resource;
	private TextDecorate txtName;
	private TextDecorate txtMerchant;
	private Shell shell;
	private Button btnSave;
	private Button btnCancel;
	
	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Save Message ISO8583");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 140, w = 380;
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
        GridLayout layout = new GridLayout(3,true);
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
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtName.setValidator((String text)->{return text.trim().length() > 0;});
		txtName.valid();
		
		label = new Label(composite, SWT.NONE);
		label.setBackground(Color.WHITE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Merchant:");

		txtMerchant = new TextDecorate(composite, SWT.BORDER);
		txtMerchant.setBackground(Color.WHITE);
		txtMerchant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtMerchant.setValidator((String text)->{return text.trim().length() > 0;});
		txtMerchant.valid();
		
		
        Composite cmpBtns = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
        layout = new GridLayout(2,true);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
		cmpBtns.setLayout(layout);
		cmpBtns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		cmpBtns.setBackground(Color.WHITE);
		
		btnSave = new Button(cmpBtns, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Save");
		btnSave.setFocus();
		
		btnCancel = new Button(cmpBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnSave);
		
		btnSave.setEnabled(false);
		
    	hookListeners();
    	fillDataForm();
    }
	private void fillDataForm() {
		if (useCase != null) {
			txtName.setText(useCase.getName());
			txtName.setFocus();
			txtName.selectAll();
			txtMerchant.setText(useCase.getAcquirer().getConnection().getName());
		} else {
			txtName.invalid();
			txtMerchant.invalid();
			txtName.setEnabled(false);
			txtMerchant.setEnabled(false);
			btnSave.setEnabled(false);
		}
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
    	txtMerchant.addModifyListener((ModifyEvent e)->{
    		txtMerchant.validateAndMark();
    		validate();
    	});
	}
	private void doSave() {
		try {
			MessageIso msg = new MessageIso();
			msg.setName(txtName.getText());
			msg.setMerchant(txtMerchant.getText());
			IByteArrayAdapter adapter = (IByteArrayAdapter)useCase.getAcquirer().getConnection().getAdapter();
			IParser parser = useCase.getAcquirer().getConnection().getParser();
			Message message = useCase.getAcquirer().getRequest().getMessage();
			String hex = new String(Converter.asciiToHex(adapter.writeByteArray(parser.formatMessage(message))));
			msg.setType(MessageIsoType.ACQ_REQ.getId());
			msg.setMessage(hex);
			msg.setMti(message.getMti());
			msg.setProcessingCode(message.getValue(3));
			msg.setIdParser(parser.getId());
			msg.setIdTypeEnviroment(Manager.get(IEnviromentManager.class).getActive().getIdType());
			Manager.get(IMessageIsoManager.class).insert(msg);
		} catch (Exception e) {
			Activator.getInstance().warning("Error saving "+txtName.getText(),e);
		}
	}
	private void validate() {
		btnSave.setEnabled(isValid());
	}
	private boolean isValid() {
		return txtName.isValid() && txtMerchant.isValid();
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
