package com.fimet.editor.stress.view.vary;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.pde.internal.ui.PDEUIMessages;
import org.eclipse.pde.internal.ui.SWTFactory;
import org.eclipse.pde.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.fimet.commons.Images;
import com.google.gson.reflect.TypeToken;
import com.fimet.commons.messages.Messages;
import com.fimet.parser.util.ParserUtils;
import com.fimet.commons.Color;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class Panel extends Composite {

	private Text txtField;
	private Text txtFieldsJson;
	private Text txtNumberOfCases;
	protected Combo cboDestination;
	protected Button btnDestination;
	protected Combo cboSource;
	protected Button btnSource;
	
	private Button btnSave;
	private Button btnAddField;
	private Button btnAddFieldsJson;
	private Button btnRemoveIdField;
	private FiledsVariationView view;
	public Panel(FiledsVariationView view, Composite parent, int style) {
		super(parent, style);
		this.view = view;
		setFont(parent.getFont());
        setBackground(Color.WHITE);
        
        createContents(this);
        
		hookListeners();
	}

	public void createContents(Composite parent) {
		
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));
        createTabVariation(tabFolder);
        createTabDestination(tabFolder);
		
	}
	
	private void createTabVariation(TabFolder tabFolder) {
        Label label;
		
        Composite composite = new Composite(tabFolder, SWT.NONE);
        composite.setBackground(tabFolder.getBackground());
		composite.setLayout(new GridLayout(1,true));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));
        composite.setFont(tabFolder.getFont());
        composite.setBackground(Color.WHITE);
        TabItem tab = new TabItem(tabFolder, SWT.NONE);
        tab.setControl(composite);
        tab.setText("MessageFields");
        
        Group groupVariation = new Group(composite, SWT.NONE);
        groupVariation.setText("MessageFields");
        groupVariation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        groupVariation.setLayout(new GridLayout(4,true));
        groupVariation.setBackground(Color.WHITE);
		
		label = new Label(groupVariation, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setToolTipText("The idField to vary");
		label.setText("Id Field:");
		
		txtField = new Text(groupVariation, SWT.BORDER);
		txtField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		txtField.setBackground(Color.WHITE);

		btnAddField = new Button(groupVariation, SWT.NONE);
		btnAddField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnAddField.setText("Add");
		
		label = new Label(groupVariation, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 3, 1));

		btnRemoveIdField = new Button(groupVariation, SWT.NONE);
		btnRemoveIdField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnRemoveIdField.setToolTipText("Remove the selected parameter");
		btnRemoveIdField.setText("Delete");
		
		label = new Label(groupVariation, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 4, 1));
		label.setBackground(Color.WHITE);
		label.setToolTipText("Use the format:\n\"3\":[\"000000\",\"010000\"],\n" + 
				"\"22\":[\"010\",\"040\",\"050\",\"070\"],\n" + 
				"\"35\":[\"4101773000653801=2210\",\"4101773000653802=2210\"]");
		label.setText("Parameters (JSON):");
		
		txtFieldsJson = new Text(groupVariation, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 2);
		gridData.heightHint = 70;
		txtFieldsJson.setLayoutData(gridData);
		txtFieldsJson.setBackground(Color.WHITE);

		btnAddFieldsJson = new Button(groupVariation, SWT.NONE);
		btnAddFieldsJson.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnAddFieldsJson.setText("Add");
		
	}
	private void createTabDestination(TabFolder tabFolder) {
		
		Label label;
		
        Composite composite = new Composite(tabFolder, SWT.NONE);
        composite.setBackground(tabFolder.getBackground());
		composite.setLayout(new GridLayout(1,false));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,1,1));
        composite.setFont(tabFolder.getFont());
        composite.setBackground(Color.WHITE);
        TabItem tab = new TabItem(tabFolder, SWT.NONE);
        tab.setControl(composite);
        tab.setText("Destination");
        
        Group groupDestination = new Group(composite, SWT.NONE);
        groupDestination.setText("Destination");
        groupDestination.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        groupDestination.setLayout(new GridLayout(4,true));
        groupDestination.setBackground(Color.WHITE);
        
		label = new Label(groupDestination, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Source:");
		
		cboSource = SWTFactory.createCombo(groupDestination, SWT.BORDER, 2, null);

		btnSource = SWTFactory.createPushButton(groupDestination, PDEUIMessages.ExportWizard_browse, null);
		SWTUtil.setButtonDimensionHint(btnSource);
        
		label = new Label(groupDestination, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Output:");
		
		cboDestination = SWTFactory.createCombo(groupDestination, SWT.BORDER, 2, null);

		btnDestination = SWTFactory.createPushButton(groupDestination, PDEUIMessages.ExportWizard_browse, null);
		SWTUtil.setButtonDimensionHint(btnDestination);

		label = new Label(groupDestination, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("Number of Cases:");
		
		txtNumberOfCases = new Text(groupDestination, SWT.NONE);
		txtNumberOfCases.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtNumberOfCases.setBackground(Color.WHITE);
		txtNumberOfCases.setText("0");
		
		label = new Label(groupDestination, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 3, 1));
		
		btnSave = new Button(groupDestination, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnSave.setText("Save");
		btnSave.setImage(Images.RUN_IMG.createImage());
	}
	private void hookListeners() {
		btnAddField.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.onAddVariationParameter(txtField.getText());
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnAddFieldsJson.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!"".equals(txtFieldsJson.getText())){
					String json = txtFieldsJson.getText().trim();
					if (!json.startsWith("{")) {
						json = "{" +json; 
					}
					if (!json.endsWith("}")) {
						json = json + "}"; 
					}
					try {
						Map<String, List<String>> map = (Map<String, List<String>>)ParserUtils.parseJson(json,new TypeToken<Map<String, List<String>>>() {}.getType());
						for (Map.Entry<String, List<String>> entry : map.entrySet()) {
							view.onAddVariationParameter(entry.getKey(), (List<String>)entry.getValue());
						}
					} catch (Exception ex) {
						MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),"Parse Json Error",ex.getMessage());
					}
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnRemoveIdField.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				view.onDeleteVariationParameter();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnSave.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnSave.setEnabled(false);
				view.onSave();
				btnSave.setEnabled(true);
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		btnDestination.addSelectionListener(widgetSelectedAdapter(e -> chooseFile(cboDestination, new String[] {"*." + Messages.NewTransactionWizard_SimQueueFileExtension})));
		btnSource.addSelectionListener(widgetSelectedAdapter(e -> chooseFile(cboSource, new String[] {"*." + Messages.NewTransactionWizard_UseCaseFileExtension})));
		btnSource.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (cboSource.getText().trim().length() > 0) {
					view.setUseCaseResource(new File(cboSource.getText()));
				}
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
	}
	public String getIdFieldVariation() {
		return txtField.getText();
	}
	public String getDestination() {
		return cboDestination.getText();
	}
	public void setSource(String resource) {
		if (File.separatorChar == '\\') {
			cboSource.setText(resource.replace('/', '\\'));
		} else {
			cboSource.setText(resource);
		}
	}
	protected void chooseFile(Combo combo, String[] filters) {
		FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
		String path = combo.getText();
		if (path.trim().length() == 0)
			path = PDEPlugin.getWorkspace().getRoot().getLocation().toString();
		dialog.setFileName(path);
		dialog.setFilterExtensions(filters);
		String res = dialog.open();
		if (res != null) {
			if (combo.indexOf(res) == -1)
				combo.add(res, 0);
			combo.setText(res);
		}
	}

	public void setNumberOfCases(long numberOfCases) {
		txtNumberOfCases.setText(""+numberOfCases);
	}
}
