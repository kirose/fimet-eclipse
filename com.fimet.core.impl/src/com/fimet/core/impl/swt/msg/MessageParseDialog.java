package com.fimet.core.impl.swt.msg;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
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
import com.fimet.commons.converter.Converter;
import com.fimet.core.IParserManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.impl.swt.ParserCombo;
import com.fimet.core.impl.swt.TextDecorate;
import com.fimet.parser.util.ParserUtils;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageParseDialog extends TrayDialog {

	public MessageParseDialog(Shell shell, IParser parser, int style) {
		super(shell);
        this.setShellStyle(style);
        this.parser = parser;
	}
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;

	private IParser parser;
	private TextDecorate txtMessageToParse;
	private TextDecorate txtMessages;
	private Message message;
	private ParserCombo cboParser;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Parse a Message");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = parser == null ? 240 : 220;
        int w = 400;
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

		GridLayout layout;
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
		composite.setLayout(new GridLayout(2,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label label;

		if (parser == null) {
			label = new Label(composite,SWT.NONE);
			label.setText("Parser:");
			label.setForeground(Color.TITLE_FORM);
			label.setBackground(composite.getBackground());
			label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
			
			cboParser = new ParserCombo(composite, IParser.ISO8583);
			cboParser.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setBackground(composite.getBackground());
		label.setForeground(Color.TITLE_FORM);
		label.setText("Message");
		label.setToolTipText("Enter a message ISO8583 to parse");

		
		Composite compositeTextArea = new Composite(composite, SWT.NONE);
		FillLayout layoutTextArea = new FillLayout();
		compositeTextArea.setLayout(layoutTextArea);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		//gridData.heightHint = 80;
		compositeTextArea.setLayoutData(gridData);
		
		txtMessageToParse = new TextDecorate(compositeTextArea, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtMessageToParse.setBackground(composite.getBackground());
		txtMessageToParse.setValidator((String text)->{
			return text.length() > 0 ;
		});
    	
		txtMessages = new TextDecorate(composite, SWT.NONE);
		txtMessages.setBackground(composite.getBackground());
		txtMessages.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		layout = new GridLayout(2,true);
		layout.marginWidth = 0;
        
        Composite compositeBtns = new Composite(composite, SWT.NONE);
        compositeBtns.setBackground(composite.getBackground());
        compositeBtns.setLayout(layout);
        compositeBtns.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
    	
		btnOk = new Button(compositeBtns, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnOk.setText("Parse");
		
		btnCancel = new Button(compositeBtns, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnCancel.setText("Cancel");

		
		txtMessageToParse.invalid();
		btnOk.setEnabled(false);
		shell.setDefaultButton(btnOk);
		
    	hookListeners();
    }
    private void hookListeners() {
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				message = parse(txtMessageToParse.getText());
				if (message != null) {
					shell.close();
					shell.dispose();
				}
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
    	if (cboParser != null) {
    		cboParser.addSelectionChangedListener((SelectionChangedEvent event)->{
				parser = cboParser.getSelected() != null ? Manager.get(IParserManager.class).getParser(cboParser.getSelected().getId()) : null;
				validate();
			});
    	}
    	txtMessageToParse.addModifyListener((ModifyEvent e)->{
    		txtMessageToParse.validate();
    		validate();
		});		
	}
    private Message parse(String m) {
    	try {
			byte[] bytes;
			if (m.matches("[0-9A-Fa-f]+")) {
				bytes = Converter.hexToAscii(m.getBytes());
			} else {
				bytes = m.getBytes();
			}
			return (Message)ParserUtils.parseMessage(bytes, parser);
		} catch (Exception ex) {
			txtMessages.setText("Error Parser: "+ex.getMessage());
			return null;
		}
    }
	private boolean isValid() {
    	return txtMessageToParse.isValid() && parser != null;
    }
    private void validate() {
		btnOk.setEnabled(isValid());
	}

	public Message getMessage() {
		return message;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
