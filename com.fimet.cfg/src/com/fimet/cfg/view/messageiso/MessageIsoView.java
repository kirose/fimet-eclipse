package com.fimet.cfg.view.messageiso;

import static com.fimet.core.entity.sqlite.pojo.MessageIsoType.ACQ_REQ;
import static com.fimet.core.entity.sqlite.pojo.MessageIsoType.ACQ_RES;
import static com.fimet.core.entity.sqlite.pojo.MessageIsoType.ISS_REQ;
import static com.fimet.core.entity.sqlite.pojo.MessageIsoType.ISS_RES;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.part.ViewPart;

import com.fimet.cfg.CFGImages;
import com.fimet.commons.Color;
import com.fimet.commons.console.Console;
import com.fimet.commons.converter.Converter;
import com.fimet.commons.io.FileUtils;
import com.fimet.commons.messages.Messages;
import com.fimet.commons.utils.ByteUtils;
import com.fimet.commons.utils.StringUtils;
import com.fimet.core.IEnviromentManager;
import com.fimet.core.IParserManager;
import com.fimet.core.IUseCaseManager;
import com.fimet.core.Manager;
import com.fimet.core.ISO8583.parser.IParser;
import com.fimet.core.ISO8583.parser.Message;
import com.fimet.core.entity.sqlite.MessageIso;
import com.fimet.core.entity.sqlite.pojo.MessageIsoParameters;
import com.fimet.core.impl.swt.EnviromentTypeCombo;
import com.fimet.core.impl.swt.ParserCombo;
import com.fimet.core.impl.swt.msgiso.MessageIsoTypeCombo;
import com.fimet.core.usecase.UseCase;
import com.fimet.parser.Activator;
import com.fimet.parser.util.ParserUtils;
import com.fimet.persistence.sqlite.dao.MessageIsoDAO;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class MessageIsoView extends ViewPart {
	public static final String ID = "com.fimet.view.MessageIsoView";
	private Button btnSave;
	private Button btnSearch;
	private Button btnDelete;
	private MessageIsoTypeCombo cboType;
	private EnviromentTypeCombo cboTypeEnviroment;
	private ParserCombo cboParser;
	private Button btnAsc;
	private Text txtName;
	private Text txtMerchant;
	private Text txtMti;
	private Text txtProcessingCode;
	private MessageIsoParameters params = new MessageIsoParameters();
	private MessageIsoTableViewer table;
	private Action actSave;
	private Action actselectTwo;
	private Action actSelectFour;
	private Action actSearch;
	private List<MessageIso> messages = new ArrayList<>();
	private Text txtNameEditor;
	private Text txtMerchantEditor;
	private IContextService contextService;
	private IContextActivation activationContextTbl;
	private IEnviromentManager enviromentManager = Manager.get(IEnviromentManager.class);
	public MessageIsoView() {
		super();
	}
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(1,true));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        composite.setBackground(Color.WHITE);
        
        IActionBars aBars = getViewSite().getActionBars();
        IMenuManager menu = aBars.getMenuManager();
        IToolBarManager toolBar = aBars.getToolBarManager();
        
		createToolbar(menu, toolBar);
        createTop(composite);
        createBody(composite);
        createBottom(composite);

		contextService = (IContextService)getSite().getService(IContextService.class);
		contextService.activateContext("com.fimet.context.MessageIsoView");
		table.getTable().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (activationContextTbl != null) {
					contextService.deactivateContext(activationContextTbl);
					activationContextTbl = null;
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (activationContextTbl == null) {
					activationContextTbl = contextService.activateContext("com.fimet.context.MessageIsoTableView");
				}
			}
		});
        
		hookListeners();
		
	}
	protected void createTop(Composite composite) {
		
		GridLayout layout = new GridLayout(4, true);
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        Group groupSearch = new Group(composite, SWT.NONE);
        groupSearch.setText("Search");
        groupSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        groupSearch.setLayout(layout);
		
		Label lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("EnviromentType:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboTypeEnviroment = new EnviromentTypeCombo(groupSearch);
		if (enviromentManager.getActive() != null) {
			cboTypeEnviroment.select(enviromentManager.getActive().getIdType());
		}
		
		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Parser:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboParser = new ParserCombo(groupSearch, IParser.ISO8583);

		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Name:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtName = new Text(groupSearch, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtName.setBackground(Color.WHITE);

		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Merchant:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtMerchant = new Text(groupSearch, SWT.BORDER);
		txtMerchant.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtMerchant.setBackground(Color.WHITE);
		
		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("MTI:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtMti = new Text(groupSearch, SWT.BORDER);
		txtMti.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtMti.setBackground(Color.WHITE);

		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Proc Code:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtProcessingCode = new Text(groupSearch, SWT.BORDER);
		txtProcessingCode.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtProcessingCode.setBackground(Color.WHITE);
		
		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("Type:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		cboType = new MessageIsoTypeCombo(groupSearch);
		
		
		lbl = new Label(groupSearch,SWT.NONE);
		lbl.setText("ASC:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));
		
		btnAsc = new Button(groupSearch, SWT.CHECK);
		btnAsc.setText("Asc");
		btnAsc.setSelection(false);
		btnAsc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		layout = new GridLayout(4, true);
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        Group groupEditor = new Group(composite, SWT.NONE);
        groupEditor.setText("Edition");
        groupEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        groupEditor.setLayout(layout);
        
		lbl = new Label(groupEditor,SWT.NONE);
		lbl.setText("Name:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtNameEditor = new Text(groupEditor, SWT.BORDER);
		txtNameEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtNameEditor.setBackground(Color.WHITE);

		lbl = new Label(groupEditor,SWT.NONE);
		lbl.setText("Merchant:");
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, false, false, 1, 1));

		txtMerchantEditor = new Text(groupEditor, SWT.BORDER);
		txtMerchantEditor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtMerchantEditor.setBackground(Color.WHITE);
	}
	protected void createBody(Composite composite) {
		
        Composite compositeTree = new Composite(composite, SWT.NONE);
        compositeTree.setBackground(Color.WHITE);
        GridLayout layout = new GridLayout(1,false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        compositeTree.setLayout(layout);
        compositeTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));

        table = new MessageIsoTableViewer(compositeTree, SWT.MULTI | SWT.H_SCROLL | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.BORDER);		
	}
	protected void createBottom(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setBackground(Color.WHITE);
        composite.setLayout(new GridLayout(6,true));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.WRAP, true, false,1,1));
		
        Label lbl = new Label(composite,SWT.NONE);
		lbl.setBackground(Color.WHITE);
		lbl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        
		btnSave = new Button(composite, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Save");
		btnSave.setImage(CFGImages.RUN_IMG.createImage());
		
		btnDelete = new Button(composite, SWT.NONE);
		btnDelete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnDelete.setText("Delete");
		btnDelete.setImage(CFGImages.REMOVE_ICON.createImage());
		
		btnSearch = new Button(composite, SWT.NONE);
		btnSearch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSearch.setText("Search");
		btnSearch.setImage(CFGImages.FILTER_ICON.createImage());
		btnSearch.setFocus();
	}
	private void fillParameters() {
		params.setName(StringUtils.isBlank(txtName.getText()) ? null : txtName.getText());
		params.setMerchant(StringUtils.isBlank(txtMerchant.getText()) ? null : txtMerchant.getText());
		params.setMti(StringUtils.isBlank(txtMti.getText()) ? null : txtMti.getText());
		params.setProcessingCode(StringUtils.isBlank(txtProcessingCode.getText()) ? null : txtProcessingCode.getText());
		params.setType(cboType.getSelected() == null ? null : cboType.getSelected().getId());
		params.setIdTypeEnviroment(cboTypeEnviroment.getSelected() == null ? null : cboTypeEnviroment.getSelected().getId());
		params.setIdParser(cboParser.getSelected() == null ? null : cboParser.getSelected().getId());
		params.setAsc(btnAsc.getSelection());
	}
	protected void createToolbar(IMenuManager menu,IToolBarManager toolBar) {
        actSave = new Action("Save") {
        	@Override
        	public void run() {
        		doSave();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return CFGImages.IAP_CONNECT_ICON;
        	}
        };
        actSearch = new Action("Search") {
        	@Override
        	public void run() {
        		doSearch();
        	}
			@Override
        	public ImageDescriptor getImageDescriptor() {
        		return CFGImages.FILTER_ICON;
        	}
        };
        actSelectFour = new Action("Select Four") {
        	private int index = 0;
        	@Override
        	public void run() {
        		table.getTable().deselectAll();
        		int rows = table.getTable().getItemCount();
        		if (index >= rows) {
        			index = 0;
        		}
        		int i = 0;
        		while (index < rows && i < 4) {
        			if (index < rows) {
        				table.getTable().select(index+i);
	        		}i++;
	        	}
        		index+=i;
        		onSelecteFour();
        	}
			@Override
        	public ImageDescriptor getImageDescriptor() {
        		return CFGImages.TYPES_IMG;
        	}
        };
        actselectTwo = new Action("Select Two") {
        	private int index = 0;
        	@Override
        	public void run() {
        		table.getTable().deselectAll();
        		int rows = table.getTable().getItemCount();
        		if (index >= rows) {
        			index = 0;
        		}
        		table.getTable().select(index++);
        		if (index < rows) {
        			table.getTable().select(index++);
        		}
        		onSelecteTwo();
        	}
			@Override
        	public ImageDescriptor getImageDescriptor() {
        		return CFGImages.REFRESH_ICON;
        	}
        };
        Action actClear = new Action("Clear search fields") {
        	@Override
        	public void run() {
        		doClear();
        	}
			@Override
        	public ImageDescriptor getImageDescriptor() {
        		return CFGImages.CANCEL_ICON;
        	}
        };
        Action cancelAction = new Action("Clear table") {
        	@Override
        	public void run() {
        		onRemove();
        	}
			@Override
        	public ImageDescriptor getImageDescriptor() {
        		return CFGImages.IAP_DISCONNECT_ICON;
        	}
        };
        menu.add(actSave);
        toolBar.add(actSave);
        menu.add(actSearch);
        toolBar.add(actSearch);
        menu.add(actSelectFour);
        toolBar.add(actSelectFour);
        menu.add(actselectTwo);
        toolBar.add(actselectTwo);
        menu.add(actClear);
        toolBar.add(actClear);
        menu.add(cancelAction);
        toolBar.add(cancelAction);
	}
	private void doClear() {
		txtName.setText("");
		txtMerchant.setText("");
		txtMti.setText("");
		txtProcessingCode.setText("");
		cboTypeEnviroment.getCombo().deselectAll();
		cboParser.getCombo().deselectAll();
		cboType.getCombo().deselectAll();
		btnAsc.setSelection(true);
	}
	public void doSearch() {
		fillParameters();
		messages = MessageIsoDAO.getInstance().findByParameters(params);
		getTable().setInput(messages);
	}
	private void doDelete() {
		List<MessageIso> msgs = getTable().getSelectedMessages();
		if (msgs != null && !msgs.isEmpty()) {
			boolean ok = MessageDialog.openQuestion(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Delete Message", "Delete "+msgs.size()+" message(s)?");
			if (ok) {
				for (MessageIso msg : msgs) {
					MessageIsoDAO.getInstance().delete(msg);
				}
			}
		}
	}
	protected void hookListeners() {
		btnSave.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doSave();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		txtNameEditor.addModifyListener((ModifyEvent e)->{
			List<MessageIso> msgs = table.getSelectedMessages();
			String txt = txtNameEditor.getText().toUpperCase();
			for (MessageIso msg : msgs) {
				msg.setName(txt);
			}
			table.refresh();
		});
		txtMerchantEditor.addModifyListener((ModifyEvent e)->{
			List<MessageIso> msgs = table.getSelectedMessages();
			String txt = txtMerchantEditor.getText().toUpperCase();
			for (MessageIso msg : msgs) {
				msg.setMerchant(txt);
			}
			table.refresh();
		});
		btnSearch.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doSearch();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDelete.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doDelete();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}
	protected void onSelecteFour() {
		txtNameEditor.setFocus();
		txtNameEditor.selectAll();
	}
	protected void onSelecteTwo() {
		txtMerchantEditor.setFocus();
		txtMerchantEditor.selectAll();
	}
	public void doSave() {
		btnSave.setEnabled(false);
		actSave.setEnabled(false);
		if (messages != null && !messages.isEmpty()) {
			for (MessageIso msg : messages) {
				MessageIsoDAO.getInstance().insertOrUpdate(msg);
			}
			Console.getInstance().debug(MessageIsoView.class, "Messages ("+messages.size()+") Saved!");
			messages.clear();
			table.setInput(messages);
		}
		btnSave.setEnabled(true);
		actSave.setEnabled(true);
	}
	public void addMessages(List<IResource> resources) {
		if (resources != null && !resources.isEmpty()) {
			Integer idTypeEnviroment = enviromentManager.getActive().getIdType();
			for (IResource r : resources) {
				addMessages(idTypeEnviroment, r);
			}
			table.setInput(messages);
			if (!messages.isEmpty()) {
				table.getTable().select(0);
			}
		}
	}
	private void addMessages(Integer idTypeEnviroment, IResource r) {
		File file = r.getLocation().toFile();
		UseCase uc = Manager.get(IUseCaseManager.class).parse(r);
		String dir = file.getAbsolutePath();
		dir = dir.substring(0,dir.lastIndexOf('.'));
		File dirUseCase = new File(dir);
		if (dirUseCase.exists()) {
			IParser parserAcq;
			IParser parserIss;
			parserAcq = uc.getAcquirer().getConnection().getParser();
			parserIss = uc.getIssuer() != null && uc.getIssuer().getConnection() != null ? uc.getIssuer().getConnection().getParser() : null;
			String fileName = file.getName().substring(0,file.getName().lastIndexOf('.')).toUpperCase() + " "+parserAcq.toString();
			if (parserIss != null)
				fileName = fileName +"->"+parserIss.toString();
			addMessage(idTypeEnviroment, parserAcq.getId(), ACQ_REQ.getId(), fileName, new File(dir+"/"+Messages.UseCase_AcquirerRequest+"."+Messages.NewTransactionWizard_SimQueueFileExtension));
			addMessage(idTypeEnviroment, parserAcq.getId(), ACQ_RES.getId(), fileName, new File(dir+"/"+Messages.UseCase_AcquirerResponse+"."+Messages.NewTransactionWizard_SimQueueFileExtension));
			if (parserIss != null) {
				addMessage(idTypeEnviroment, parserIss.getId(), ISS_REQ.getId(), fileName, new File(dir+"/"+Messages.UseCase_IssuerRequest+"."+Messages.NewTransactionWizard_SimQueueFileExtension));
				addMessage(idTypeEnviroment, parserIss.getId(), ISS_RES.getId(), fileName, new File(dir+"/"+Messages.UseCase_IssuerResponse+"."+Messages.NewTransactionWizard_SimQueueFileExtension));
			}
		}
	}
	private void addMessage(Integer idTypeEnviroment, Integer idParser, short type, String name, File file) {
		if (file.exists()) {
			try {
				byte[] bytes = ByteUtils.removeEndNewLines(FileUtils.readBytesContents(file));
				String hex = new String(Converter.asciiToHex(bytes));
				Message msg = (Message)ParserUtils.parseMessage(bytes, Manager.get(IParserManager.class).getParser(idParser));
				String processingCode = msg.hasField("3") ? msg.getValue("3") : "";
				String mti = msg.getMti();
				messages.add(new MessageIso(idTypeEnviroment, idParser, type, "", processingCode, mti, name, hex));
			} catch (Exception e) {
				Activator.getInstance().warning("Error creating MessageIso", e);
			}
		}
	}
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.btnSave.setFocus();
	}
	public void onRemove() {
		messages.clear();
		table.setMessagesIso(messages);
		table.refresh();
	}
	public MessageIsoTableViewer getTable() {
		return table;
	}
	public List<MessageIso> getMessages() {
		return messages;
	}
	public void doSelectTwo() {
		actselectTwo.run();
	}
	public void doSelectFour() {
		actSelectFour.run();
	}
}