package com.fimet.editor.usecase.wizard.export;


import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.File;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.pde.internal.ui.*;
import org.eclipse.pde.internal.ui.util.SWTUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;

public class ExportDestinationTab extends AbstractExportTab {

	protected static final String S_EXPORT_TYPE = "exportType"; //$NON-NLS-1$
	protected static final String S_DESTINATION = "destination"; //$NON-NLS-1$
	protected static final String S_ZIP_FILENAME = "zipFileName"; //$NON-NLS-1$

	protected static final int TYPE_DIR = 1;
	protected static final int TYPE_ARCHIVE = 2;

	protected static String SIM_QUEUE_EXTENSION = ".sim_queue"; //$NON-NLS-1$

	protected Button fArchiveFileButton;
	protected Combo fArchiveCombo;
	protected Button fBrowseFile;
	protected Button fDirectoryButton;
	protected Combo fDirectoryCombo;
	protected Button fBrowseDirectory;

	public ExportDestinationTab(AbstractExportWizardPage page) {
		super(page);
	}

	@Override
	public Control createControl(Composite parent) {
		Composite composite = SWTFactory.createComposite(parent, 2, 1, GridData.FILL_HORIZONTAL);

		fDirectoryButton = SWTFactory.createRadioButton(composite, PDEUIMessages.ExportWizard_directory, 2);

		fDirectoryCombo = SWTFactory.createCombo(composite, SWT.BORDER, 1, null);
		((GridData) fDirectoryCombo.getLayoutData()).horizontalIndent = 15;

		fBrowseDirectory = SWTFactory.createPushButton(composite, PDEUIMessages.ExportWizard_browse, null);
		SWTUtil.setButtonDimensionHint(fBrowseDirectory);

		fArchiveFileButton = SWTFactory.createRadioButton(composite, PDEUIMessages.ExportWizard_archive, 2);

		fArchiveCombo = SWTFactory.createCombo(composite, SWT.BORDER, 1, null);
		((GridData) fArchiveCombo.getLayoutData()).horizontalIndent = 15;

		fBrowseFile = SWTFactory.createPushButton(composite, PDEUIMessages.ExportWizard_browse, null);
		SWTUtil.setButtonDimensionHint(fBrowseFile);

		return composite;
	}

	@Override
	protected void initialize(IDialogSettings settings) {
		String exportType = settings.get(S_EXPORT_TYPE);
		int exportTypeCode = 1;
		if (exportType != null && exportType.length() > 0) {
			try {
				exportTypeCode = Integer.parseInt(exportType);
			} catch (NumberFormatException e) {
			}
		}
		fDirectoryButton.setSelection(exportTypeCode == TYPE_DIR);
		fArchiveFileButton.setSelection(exportTypeCode == TYPE_ARCHIVE);
		updateExportType();
		initializeCombo(settings, S_DESTINATION, fDirectoryCombo);
		initializeCombo(settings, S_ZIP_FILENAME, fArchiveCombo);
		// Always add a default repo location to the install combo
		String defaultRepo = PDEPlugin.getWorkspace().getRoot().getLocation()+"";
		if (fDirectoryCombo.indexOf(defaultRepo) == -1) {
			fDirectoryCombo.add(defaultRepo);
		}
		hookListeners();
	}

	@Override
	protected void initializeCombo(IDialogSettings settings, String key, Combo combo) {
		super.initializeCombo(settings, key, combo);
		if (!isValidLocation(combo.getText().trim())) // If default value is invalid, make it blank
			combo.setText(""); //$NON-NLS-1$
	}

	protected void updateExportType() {
		fArchiveCombo.setEnabled(fArchiveFileButton.getSelection());
		fBrowseFile.setEnabled(fArchiveFileButton.getSelection());
		fDirectoryCombo.setEnabled(fDirectoryButton.getSelection());
		fBrowseDirectory.setEnabled(fDirectoryButton.getSelection());
	}

	protected void hookListeners() {
		fArchiveFileButton.addSelectionListener(widgetSelectedAdapter(e -> {
			updateExportType();
			fPage.pageChanged();
		}));
		fBrowseFile.addSelectionListener(widgetSelectedAdapter(e -> chooseFile(fArchiveCombo, new String[] {"*" + SIM_QUEUE_EXTENSION})));
		fArchiveCombo.addModifyListener(e -> fPage.pageChanged());

		fDirectoryCombo.addModifyListener(e -> fPage.pageChanged());

		fBrowseDirectory.addSelectionListener(widgetSelectedAdapter(e -> chooseDestination(fDirectoryCombo)));
	}

	private void chooseDestination(Combo combo) {
		DirectoryDialog dialog = new DirectoryDialog(fPage.getShell(), SWT.SAVE);
		String path = combo.getText();
		if (path.trim().length() == 0)
			path = PDEPlugin.getWorkspace().getRoot().getLocation().toString();
		dialog.setFilterPath(path);
		dialog.setText(PDEUIMessages.ExportWizard_dialog_title);
		dialog.setMessage(PDEUIMessages.ExportWizard_dialog_message);
		String res = dialog.open();
		if (res != null) {
			if (combo.indexOf(res) == -1)
				combo.add(res, 0);
			combo.setText(res);
		}
	}

	@Override
	protected void saveSettings(IDialogSettings settings) {
		int type = fDirectoryButton.getSelection() ? 1 : 3;
		if (fArchiveFileButton.getSelection()) {
			type = 2;
		}
		settings.put(S_EXPORT_TYPE, type);
		saveCombo(settings, S_DESTINATION, fDirectoryCombo);
		saveCombo(settings, S_ZIP_FILENAME, fArchiveCombo);
	}

	protected String validate() {
		if (fArchiveFileButton.getSelection()) {
			if (fArchiveCombo.getText().trim().length() == 0)
				return PDEUIMessages.ExportWizard_status_nofile;
			else if (!isValidLocation(fArchiveCombo.getText().trim()))
				return PDEUIMessages.ExportWizard_status_invaliddirectory;
		}
		if (fDirectoryButton.getSelection()) {
			if (fDirectoryCombo.getText().trim().length() == 0)
				return PDEUIMessages.ExportWizard_status_nodirectory;
			else if (!isValidLocation(fDirectoryCombo.getText().trim()))
				return PDEUIMessages.ExportWizard_status_invaliddirectory;
		}

		return null;
	}

	protected String getFileName() {
		if (fArchiveFileButton.getSelection()) {
			String path = fArchiveCombo.getText();
			if (path != null && path.length() > 0) {
				String fileName = new Path(path).lastSegment();
				if (!fileName.endsWith(SIM_QUEUE_EXTENSION)) {
					fileName += SIM_QUEUE_EXTENSION;
				}
				return fileName;
			}
		}
		return null;
	}

	protected String getDestination() {
		if (fArchiveFileButton.getSelection()) {
			String path = fArchiveCombo.getText();
			if (path.length() > 0) {
				path = new Path(path).removeLastSegments(1).toOSString();
				return new File(path).getAbsolutePath();
			}
			return ""; //$NON-NLS-1$
		}

		File dir = new File(fDirectoryCombo.getText().trim());
		return dir.getAbsolutePath();
	}

	protected boolean doExportToDirectory() {
		return fDirectoryButton.getSelection();
	}

}
