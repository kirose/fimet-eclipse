package com.fimet.editor.usecase.wizard.export;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;

public abstract class AbstractExportWizardPage extends WizardPage {

	protected AbstractExportWizardPage(String pageName) {
		super(pageName);
	}

	protected abstract void pageChanged();

	protected abstract void saveSettings(IDialogSettings settings);

}
