package com.fimet.core.impl.swt.database;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.pde.internal.ui.PDEUIMessages;
import org.eclipse.pde.internal.ui.SWTFactory;
import org.eclipse.pde.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
//import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import com.fimet.commons.Color;
import com.fimet.commons.Images;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.core.entity.sqlite.DataBase;
import com.fimet.core.impl.Activator;
import com.fimet.core.impl.swt.KeyValueCombo;
import com.fimet.core.impl.swt.TextDecorate;



/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
class DataBaseImportDialog extends TrayDialog {

	
	public static final String EXTENSION_ID = "com.fimet.import.DataBase";
	
	public DataBaseImportDialog(Shell shell, int style) {
		super(shell);
        this.setShellStyle(style);
	}
	private TextDecorate txtKey;
	private TextDecorate txtMessages;
	private KeyValueCombo<IDataBaseImporter> cboType;
	
	protected Combo cboSource;
	protected Button btnSource;
	
	private Shell shell;
	private Button btnOk;
	private Button btnCancel;
	
	private List<DataBase> output;
	

	public int open () {
        Shell parent = getParentShell();

        Rectangle bounds = getParentShell().getBounds();
        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE| getDefaultOrientation());// | SWT.MAX
        shell.setText("Import Database Connections");
        shell.setLayout(new GridLayout(1, true));
        shell.setBackground(Color.WHITE);//getParentShell().getBackground());
        int h = 190, w = 420;
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

        Composite composite = new Composite(parent, SWT.NONE);
        composite.setBackground(Color.WHITE);
		composite.setLayout(new GridLayout(4,true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		composite.setBackground(Color.WHITE);
		
		Label label;
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.WRAP, SWT.CENTER, true, false, 1, 1));
		label.setBackground(Color.WHITE);
		label.setText("File:");
		
		cboSource = SWTFactory.createCombo(composite, SWT.BORDER, 2, null);

		btnSource = SWTFactory.createPushButton(composite, PDEUIMessages.ExportWizard_browse, null);
		SWTUtil.setButtonDimensionHint(btnSource);
		
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Importer*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		label.setToolTipText("A global converter");
		
		cboType = new KeyValueCombo<IDataBaseImporter>(composite);
		cboType.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
    	
		label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		label.setText("Key*:");
		label.setBackground(Color.WHITE);
		//label.setForeground(Color.WHITE);
		label.setToolTipText("The encryption key");
		
		txtKey = new TextDecorate(composite, SWT.BORDER);
		txtKey.setBackground(Color.WHITE);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		txtKey.setValidator((String text)->{return text.length() > 0;});
		
		txtMessages = new TextDecorate(composite, SWT.NONE);
		txtMessages.setBackground(Color.WHITE);
		txtMessages.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnOk.setText("OK");
		btnOk.setFocus();

		shell.setDefaultButton(btnOk);

		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		btnCancel.setText("Cancel");
		
		cboType.valid();
		txtKey.invalid();
		btnOk.setEnabled(false);
		fillDataForm();
		hookListeners();
    }
    

	private void fillDataForm() {
		setImporterOptions();		
	}
	private void setImporterOptions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elements = registry.getConfigurationElementsFor(EXTENSION_ID);
		if (elements != null && elements.length > 0) {
			for (IConfigurationElement e : elements) {
				if ("importer".equals(e.getName())) {
					try {
						String name = e.getAttribute("name");
						Bundle plugin = PluginUtils.startPlugin(e.getContributor().getName());
						Class<?> clazz = plugin.loadClass(e.getAttribute("class"));
						cboType.addOption(name, (IDataBaseImporter)clazz.newInstance());
					} catch (Exception ex) {
						Activator.getInstance().warning("Extension "+EXTENSION_ID,ex);
					}
				}
			}
			cboType.refreshOptions();
		}
	}
	private void hookListeners() {
		btnOk.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IDataBaseImporter importer = cboType.getSelected();
				if (importer != null) {
					List<DataBase> databases = importer.parse(new File(cboSource.getText()), txtKey.getText());
					if (databases == null || databases.isEmpty()) {
						txtMessages.setText("The file has not connections");
					} else if (databases.get(0).getPassword() == null) {
						txtMessages.setText("The key \""+txtKey.getText()+"\" is invalid");
					} else {
						output = databases;
						shell.close();
						shell.dispose();
					}
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
    	txtKey.addModifyListener((ModifyEvent e)->{
    		txtKey.validate();
    		validate();
		});
		btnSource.addSelectionListener(widgetSelectedAdapter(e -> chooseFile(cboSource, new String[] {"*"})));
		btnSource.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File file = new File(cboSource.getText());
				if (!file.exists() || !file.isFile()) {
					cboSource.setText("");
				}
				validate();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
    	cboType.getCombo().addModifyListener((ModifyEvent e)->{
    		if (cboType.getSelected() == null) {
    			cboType.invalid();
    		} else {
    			cboType.valid();
    		}
    		validate();
		});
	}
	protected void chooseFile(Combo combo, String[] filters) {
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
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
    private boolean isValid() {
    	return txtKey.isValid() && cboType.isValid() && !"".equals(cboSource.getText());
    }
    private void validate() {
    	btnOk.setEnabled(isValid());
    }
	public List<DataBase> getDataBases() {
		return output;
	}
	@Override
    protected boolean isResizable() {
    	return true;
    }
}
