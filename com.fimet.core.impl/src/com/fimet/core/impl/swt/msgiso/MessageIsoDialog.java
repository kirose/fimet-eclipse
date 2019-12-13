package com.fimet.core.impl.swt.msgiso;

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
import com.fimet.commons.converter.Converter;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
import com.fimet.core.impl.swt.EnviromentTypeCombo;
import com.fimet.core.impl.swt.ParserCombo;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.core.impl.swt.msg.IMessageContainer;
import com.fimet.core.impl.swt.msg.MessageViewer;
import com.fimet.core.net.ISocket;
import com.fimet.parser.util.ParserUtils;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageIsoDialog extends TrayDialog implements IMessageContainer {
	public MessageIsoDialog(MessageIso iso, Shell shell,  int style) {
		super(shell);
		this.input = iso;
        this.setShellStyle(style);
	}
	private TextDecorate txtName;
	private EnviromentTypeCombo cboEnviromentType;
	private ParserCombo cboParser;
	private TextDecorate txtMerchant;
	private MessageViewer messageViewer;
	private MessageIso input;
	private MessageIso output;
	private Shell shell;
	private Button btnSave;
	private Button btnCancel;
	
	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Message ISO8583");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 550, w = 500;
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
		label.setText("Merchant:");
		label.setBackground(composite.getBackground());
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtMerchant = new TextDecorate(composite, SWT.BORDER);
		txtMerchant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtMerchant.setValidator((String text)->{return text.trim().length() > 0;});
		
		label = new Label(composite,SWT.NONE);
		label.setText("Enviroment Type:");
		label.setBackground(composite.getBackground());
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboEnviromentType = new EnviromentTypeCombo(composite);
		cboEnviromentType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		label = new Label(composite,SWT.NONE);
		label.setText("Parser:");
		label.setBackground(composite.getBackground());
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboParser = new ParserCombo(composite, IParser.ISO8583);
		cboParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		messageViewer = new MessageViewer(this, true, composite, SWT.NONE);
		messageViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		
		label = new Label(composite,SWT.NONE);
		label.setBackground(composite.getBackground());
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 2, 1));
		
		btnSave = new Button(composite, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Save");
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
    	txtMerchant.addModifyListener((ModifyEvent e)->{
    		txtMerchant.validateAndMark();
    		validate();
    	});
    	cboEnviromentType.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cboEnviromentType.valid();
				validate();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	cboParser.getCombo().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cboParser.valid();
				validate();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	private void fillDataForm() {
		if (input != null) {
			txtName.setText(StringUtils.escapeNull(input.getName()));
			txtMerchant.setText(StringUtils.escapeNull(input.getMerchant()));
			Message parse = parse(input);
			messageViewer.setMessage(parse);
			cboEnviromentType.select(input.getIdTypeEnviroment());
			cboEnviromentType.valid();
			cboParser.select(input.getIdParser());
			cboParser.valid();
		} else {
			txtName.invalid();
			txtMerchant.invalid();
			cboEnviromentType.invalid();
			cboParser.invalid();
			btnSave.setEnabled(false);
		}
	}
	private void doSave() {
		try {
			if (input != null) {
				output = input;
				Message message = messageViewer.getMessage();
				String hex = new String(Converter.asciiToHex(ParserUtils.formatMessage(message)));

				output.setName(txtName.getText());
				output.setMerchant(txtMerchant.getText());
				output.setType(MessageIsoType.ACQ_REQ.getId());
				output.setMessage(hex);
				output.setMti(message.getMti());
				output.setProcessingCode(message.getValue(3));
				output.setIdParser(message.getParser().getId());
				output.setIdTypeEnviroment(cboEnviromentType.getSelected().getId());
			}
		} catch (Exception e) {
			Activator.getInstance().warning("Error saving "+txtName.getText(),e);
		}
	}
	private void validate() {
		btnSave.setEnabled(isValid());
	}
	private boolean isValid() {
		return txtName.isValid() && txtMerchant.isValid() && cboEnviromentType.isValid() && cboParser.isValid() && messageViewer.getMessage()!= null;
	}
	public MessageIso getOutput() {
		return output;
	}
	private Message parse(MessageIso iso) {
		try {
			byte[] isoAndMli = Converter.hexToAscii(iso.getMessage().getBytes());
			return (Message)ParserUtils.parseMessage(isoAndMli, iso.getIdParser());
		} catch (Exception ex) {
			Activator.getInstance().warning("Error parsing message ",ex);
		}
		return null;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }

	@Override
	public void onModifyMessage() {
		
	}

	@Override
	public ISocket getConnection() {
		return null;
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return null;
	}
}
