package com.fimet.editor.usecase.wizard.newuc;



import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Label;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.ide.IIDEHelpContextIds;

import com.fimet.commons.converter.Converter;
import com.fimet.commons.exception.FimetException;
import com.fimet.commons.exception.ParserException;
import com.fimet.commons.messages.Messages;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.adapter.Adapter;
import com.fimet.core.ISO8583.adapter.IByteArrayAdapter;
import com.fimet.core.ISO8583.adapter.IStreamAdapter;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.fimet.core.entity.sqlite.pojo.MessageIsoType;
import com.fimet.core.impl.Activator;
import com.fimet.core.impl.swt.VText;
import com.fimet.core.impl.swt.VTextDocument;
import com.fimet.core.impl.swt.msg.MessageParseDialog;
import com.fimet.core.impl.swt.msgiso.MessageIsoSearchDialog;
import com.fimet.core.net.ISocket;
import com.fimet.core.usecase.UseCase;
import com.fimet.editor.json.utils.JsonUtils;
import com.fimet.parser.util.ParserUtils;

import org.eclipse.jdt.internal.ui.wizards.dialogfields.Separator;
import org.eclipse.jdt.internal.ui.dialogs.StatusInfo;
import org.eclipse.jdt.internal.ui.dialogs.StatusUtil;

public class NewUseCaseWizardMsgPage extends WizardPage implements Listener {
	
	public static String DEFAULT_MTI = "0200";
	private UseCase useCase;
	private VTextDocument txtMsg;
	private Button btnSearch;
	private Button btnParse;
	private NewUseCaseWizard wizard;
	
	public NewUseCaseWizardMsgPage(String pageName, NewUseCaseWizard wizard) {
		super(pageName);
		setPageComplete(true);
		this.wizard = wizard;
		this.useCase = wizard.getUseCase();
	}

	@Override
	public void createControl(Composite parent) {
		
	    initializeDialogUnits(parent);
	    
	    Composite composite = new Composite(parent, SWT.NONE);
	    composite.setFont(parent.getFont());
	    int nColumns = 1;
	    GridLayout layout = new GridLayout();
	    layout.numColumns = nColumns;
	    composite.setLayout(layout);
	    composite.setFont(parent.getFont());
	    composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IIDEHelpContextIds.NEW_FILE_WIZARD_PAGE);	    
	    //createContainerControls(composite, nColumns);
		createIsoMsgControls(composite, nColumns);
		createButtonsControls(composite, nColumns);
		createSeparator(composite, nColumns);
	    setControl(composite);

	    hookListeners();
	    
		setErrorMessage(null);
		setMessage(null);
	    validateControls();
	    Dialog.applyDialogFont(composite);
	    PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, "org.eclipse.jdt.ui.new_class_wizard_page_context");
	}

	protected void createIsoMsgControls(Composite parent, int nColumns) {
		
		GridLayout layout = new GridLayout(4,true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.numColumns = nColumns;
		
		// top level group
		Composite composite = new Composite(parent, SWT.NONE);
		
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));//topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());
		
		
		Label lbl = new Label(composite,SWT.NONE);
		lbl.setText(Messages.NewUseCaseWizard_JsonMsg);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, nColumns, 1));
		
		
		Composite compositeTextArea = new Composite(composite, SWT.NONE);
		FillLayout layoutTextArea = new FillLayout();
		compositeTextArea.setLayout(layoutTextArea);
		compositeTextArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, nColumns, 1));
		
		txtMsg = new VTextDocument(compositeTextArea, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		txtMsg.valid();
		JsonUtils.reconcilerJson(txtMsg, null);
		
		/**/
		//txtMsg.setLayoutData(new GridData(GridData.FILL_BOTH));

	}
	protected void createButtonsControls(Composite parent, int nColumns) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(5,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, nColumns, 1));
		composite.setFont(parent.getFont());
		
		Label lbl = new Label(composite,SWT.NONE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));

		btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSearch.setText("Search");
		
		btnParse = new Button(composite, SWT.NONE);
		btnParse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnParse.setToolTipText("Hex, Rawcom, LogHex");
		btnParse.setText("Parse");
	}
	private void hookListeners() {
		btnSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onSearch();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnParse.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onParse();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		ITextListener listenerMsg = (TextEvent e)->{
			String json = txtMsg.getDocument().get();
			if (json != null && json.trim().length() > 0) {
				try {
					Message msg = ParserUtils.parseJsonMessage(json);
					useCase.getAcquirer().getRequest().setMessage(msg);
					txtMsg.valid();
				} catch (Exception ex) {
					txtMsg.invalid();
				}
				validateControls();
			} else {
				txtMsg.invalid();
			}
		};

		txtMsg.addTextListener(listenerMsg);
	}
	private void error(String msg) {
		StatusInfo status = new StatusInfo();
		status.setError(msg);
		setPageComplete(!status.matches(IStatus.ERROR));
		StatusUtil.applyToStatusLine(this, status);
	}
	@Override
	public boolean isPageComplete() {
		return txtMsg.isValid();
	}
	/**
	 * Creates the widget for advanced options.
	 *
	 * @param parent
	 *            the parent composite
	 */
	protected void createSeparator(Composite composite, int nColumns) {
	  new Separator(258).doFillIntoGrid(composite, nColumns, convertHeightInCharsToPixels(1));
  	}

	/**
	 * The <code>WizardNewFileCreationPage</code> implementation of this
	 * <code>Listener</code> method handles all events and enablements for
	 * controls on this page. Subclasses may extend.
	 */
	@Override
	public void handleEvent(Event event) {
		setPageComplete(validateControls());
	}

	/**
	 * Returns whether this page's controls currently all contain valid values.
	 *
	 * @return <code>true</code> if all controls are valid, and
	 *         <code>false</code> if at least one is invalid
	 */
	protected boolean validateControls() {
		if (txtMsg.isValid()) {
			setPageComplete(true);
			return true;
		}
		return false;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			txtMsg.getControl().setFocus();
		}
	}
	public void setMsgTemplate(String template) {
		txtMsg.setDocument(new Document(template));
	}
	public void onParse() {
		if (wizard.getMainPage().getAcquirer() == null || wizard.getMainPage().getAcquirer().getParser() == null) {
			throw new FimetException("Select an Acquirer");
		}
		MessageParseDialog dialog = new MessageParseDialog(getShell(),wizard.getMainPage().getAcquirer().getParser(), SWT.NONE);
		dialog.open();
		Message message = dialog.getMessage();
		if (message != null) {
			if (wizard.getMainPage().getAcquirer() != null) {
				try {
					useCase.getAcquirer().getRequest().setMessage(message);
					txtMsg.getDocument().set(message.toJson());
				} catch (Exception e) {
					Activator.getInstance().warning("Error parsing message",e);
					StatusInfo status = new StatusInfo();
					status.setError(e.getMessage());
					StatusUtil.applyToStatusLine(this, status);
				}
			} else {
				StatusInfo status = new StatusInfo();
				status.setError("Must select the acquirer");
				StatusUtil.applyToStatusLine(this, status);
			}
		}
	}
	public void onSearch() {
		try {
			MessageIsoParameters params = new MessageIsoParameters();
			if (Manager.get(IEnviromentManager.class).getActive() != null) {
				params.setIdTypeEnviroment(Manager.get(IEnviromentManager.class).getActive().getIdType());
			}
			if (wizard.getMainPage().getAcquirer() != null) {
				ISocket iap = wizard.getMainPage().getAcquirer();
				if (iap != null && iap.getParser() != null) {
					params.setIdParser(((IParser)iap.getParser()).getId());
				}
			}
			params.setType(MessageIsoType.ACQ_REQ.getId());
			MessageIsoSearchDialog dialog = new MessageIsoSearchDialog(params, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.NONE);
			dialog.open();
			if (dialog.getMessage() != null) {
				try {
					byte[] isoAndMli = Converter.hexToAscii(dialog.getMessage().getMessage().getBytes());
					Message msg = (Message)ParserUtils.parseMessage(isoAndMli, dialog.getMessage().getIdParser());
					useCase.getAcquirer().getRequest().setMessage(msg);
					txtMsg.getDocument().set(msg.toJson());
				} catch (Exception e) {
					Activator.getInstance().warning("Error parsing message",e);
					StatusInfo status = new StatusInfo();
					status.setError(e.getMessage());
					StatusUtil.applyToStatusLine(this, status);
				}
			}
		} catch (Exception e) {
			Activator.getInstance().error("Error searching message", e);
		}
	}
}
