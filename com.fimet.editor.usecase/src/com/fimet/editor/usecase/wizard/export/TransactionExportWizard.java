package com.fimet.editor.usecase.wizard.export;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.pde.internal.core.PDECoreMessages;
import org.eclipse.pde.internal.ui.IPreferenceConstants;
import org.eclipse.pde.internal.ui.PDEPlugin;
import org.eclipse.pde.internal.ui.PDEPluginImages;
import org.eclipse.pde.internal.ui.PDEUIMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressConstants;

import com.fimet.commons.messages.Messages;
import com.fimet.editor.usecase.wizard.export.TransactionExportInfo.TransactionModel;
import com.ibm.icu.text.MessageFormat;


public class TransactionExportWizard extends BaseExportWizard {

	private static final String STORE_SECTION = "PluginExportWizard";

	public TransactionExportWizard() {
		super();
		setDefaultPageImageDescriptor(PDEPluginImages.DESC_PLUGIN_EXPORT_WIZ);
		info = new TransactionExportInfo();
		job = new TransactionExportJob(info, PDEUIMessages.PluginExportJob_name);
	}
	protected BaseExportWizardPage fPage;
	private TransactionExportInfo info;
	private TransactionExportJob job;
	private JobChangeAdapter jobChangeAdapter;
	@Override
	public void addPages() {
		fPage = createPage1();
		fPage.setTitle(Messages.UseCaseExportWizard_title);
		addPage(fPage);
	}

	@Override
	protected boolean performPreliminaryChecks() {
		// Check if we are going to overwrite an existing build.xml file
		info.toDirectory = fPage.doExportToDirectory();
		info.singleFile = fPage.doSingleFile();
		info.zip = fPage.doZip();
		info.destinationDirectory = fPage.getDestination();
		info.fileName = fPage.getFileName();
		info.setIFiles(fPage.getSelectedItems());
		job.doConfiguration();

		List<String> problemModels = new ArrayList<>();
		if (info.singleFile) {
			if (new java.io.File(info.destinationDirectory+File.separator+info.fileName).exists()) {
				problemModels.add(".../"+info.fileName);
			}
		} else {
			for (TransactionModel file : info.getFiles()) {
				if (file.getTgtFile().exists()) {
					problemModels.add(".../"+file.getTgtFile().getParentFile().getName() + "/" +file.getTgtFile().getName());
				}
			}
		}

		if (!problemModels.isEmpty()) {
			StringBuilder buf = new StringBuilder();
			int maxCount = 10;
			for (Object object : problemModels) {
				buf.append(object);
				buf.append('\n');
				maxCount--;
				if (maxCount <= 0) {
					buf.append(Dialog.ELLIPSIS);
					break;
				}
			}

			MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
				getShell(), 
				Messages.TransactionExportWizard_FileOverwrite_1,
				MessageFormat.format(Messages.TransactionExportWizard_FileOverwrite_2, buf.toString()),
				Messages.TransactionExportWizard_FileOverwrite_3,
				false,
				PDEPlugin.getDefault().getPreferenceStore(),
				IPreferenceConstants.OVERWRITE_BUILD_FILES_ON_EXPORT
			);
			if (dialog.getReturnCode() == Window.CANCEL) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean confirmDelete() {
		if (!fPage.doExportToDirectory()) {
			File zipFile = new File(fPage.getDestination(), fPage.getFileName());
			if (zipFile.exists()) {
				if (!MessageDialog.openQuestion(getContainer().getShell(), PDEUIMessages.BaseExportWizard_confirmReplace_title, NLS.bind(PDEUIMessages.BaseExportWizard_confirmReplace_desc, zipFile.getAbsolutePath())))
					return false;
				zipFile.delete();
			}
		}
		return true;
	}

	protected String getExportOperation() {
		return fPage.doExportToDirectory() ? "directory" : "zip";
	}

	protected class AntErrorDialog extends MessageDialog {
		private File fLogLocation;

		public AntErrorDialog(File logLocation) {
			super(PlatformUI.getWorkbench().getDisplay().getActiveShell(), PDECoreMessages.FeatureBasedExportOperation_ProblemDuringExport, null, null, MessageDialog.ERROR, new String[] {IDialogConstants.OK_LABEL}, 0);
			fLogLocation = logLocation;
		}

		@Override
		protected Control createMessageArea(Composite composite) {
			Link link = new Link(composite, SWT.WRAP);
			try {
				link.setText(NLS.bind(PDEUIMessages.PluginExportWizard_Ant_errors_during_export_logs_generated, "<a>" + fLogLocation.getCanonicalPath() + "</a>")); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (IOException e) {
				PDEPlugin.log(e);
			}
			GridData data = new GridData();
			data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			link.setLayoutData(data);
			link.addSelectionListener(widgetSelectedAdapter(e -> {
				try {
					Program.launch(fLogLocation.getCanonicalPath());
				} catch (IOException ex) {
					PDEPlugin.log(ex);
				}
			}));
			return link;
		}
	}

	protected BaseExportWizardPage createPage1() {
		return new TransactionExportWizardPage(getSelection());
	}

	@Override
	protected String getSettingsSectionName() {
		return STORE_SECTION;
	}

	@Override
	protected void scheduleExportJob() {

		job.setUser(true);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.setProperty(IProgressConstants.ICON_PROPERTY, PDEPluginImages.DESC_PLUGIN_OBJ);
		job.addJobChangeListener(getJobChangeadapter());
		job.schedule();

	}
	
	public JobChangeAdapter getJobChangeadapter() {
		if (jobChangeAdapter == null) {
			jobChangeAdapter = new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (job.hasErrors()) {
					// If there were errors when running the ant scripts, inform the user where the logs can be found.
					final File logLocation = new File(info.destinationDirectory, "logs.zip"); //$NON-NLS-1$
					if (logLocation.exists()) {
						PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
							AntErrorDialog dialog = new AntErrorDialog(logLocation);
							dialog.open();
						});
					}
				} else if (event.getResult().isOK()) {
					// Do something ...
				}
				info.setIFiles(null);
			}
		};
		}
		return jobChangeAdapter;
	}

}
