package com.fimet.editor.usecase.wizard.export;


import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.fimet.commons.messages.Messages;



public class ExportOptionsTab extends AbstractExportTab {

	protected static final String S_SINGLE_FILE = "exportSingleFile";
	private static final String S_ZIP_FILE = "exportZip";

	protected Button fSingleFile;
	private Button fZipButton;

	public ExportOptionsTab(BaseExportWizardPage page) {
		super(page);
	}

	@Override
	protected Control createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout());
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		addSingleFileOption(container);
		addZipSection(container);
		addAdditionalOptions(container);

		return container;
	}


	protected void addSingleFileOption(Composite comp) {
		fSingleFile = new Button(comp, SWT.CHECK);
		fSingleFile.setText(Messages.BaseExportWizardPage_GenerateSingleFile);
	}

	protected void addZipSection(Composite comp) {
		fZipButton = new Button(comp, SWT.CHECK);
		fZipButton.setText(com.fimet.commons.messages.Messages.BaseExportWizardPage_GenerateZip);
	}

	/**
	 * Provides an opportunity for subclasses to add additional options
	 * to the composite.
	 * @param comp
	 */
	protected void addAdditionalOptions(Composite comp) {
	}


	@Override
	protected void initialize(IDialogSettings settings) {
		fSingleFile.setSelection(getInitialSingleFileButtonSelection(settings));
		fZipButton.setSelection(getInitialZipSelection(settings));
		hookListeners();
	}

	@Override
	protected void saveSettings(IDialogSettings settings) {
		settings.put(S_SINGLE_FILE, fSingleFile.getSelection());
		settings.put(S_ZIP_FILE, fZipButton.getSelection());
	}

	protected boolean getInitialSingleFileButtonSelection(IDialogSettings settings) {
		String selected = settings.get(S_SINGLE_FILE);
		return selected == null ? false : Boolean.valueOf(selected).booleanValue();
	}

	protected boolean getInitialZipSelection(IDialogSettings settings) {
		String selected = settings.get(S_ZIP_FILE);
		return selected == null ? false : Boolean.valueOf(selected).booleanValue();
	}

	protected void hookListeners() {
		fSingleFile.addSelectionListener(widgetSelectedAdapter(e -> ((BaseExportWizardPage) fPage).adjustAdvancedTabsVisibility()));
	}

	protected boolean doZip() {
		return fZipButton.getSelection();
	}

	protected boolean doSingleFile() {
		return fSingleFile.getSelection();
	}

	protected String validate() {
		return null;
	}
}
