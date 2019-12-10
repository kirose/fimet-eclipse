package com.fimet.editor.usecase.wizard.export;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.pde.core.IModel;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.ICoreConstants;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.WorkspaceModelManager;
import org.eclipse.pde.internal.ui.IHelpContextIds;
import org.eclipse.pde.internal.ui.PDEUIMessages;
import org.eclipse.pde.internal.ui.util.PersistablePluginObject;
import org.eclipse.pde.internal.ui.wizards.plugin.AbstractFieldData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class TransactionExportWizardPage extends BaseExportWizardPage {

	public TransactionExportWizardPage(IStructuredSelection selection) {
		super(selection, "pluginExport", //$NON-NLS-1$
				PDEUIMessages.ExportWizard_Plugin_pageBlock);
		setTitle(PDEUIMessages.ExportWizard_Plugin_pageTitle);
	}

	@Override
	protected Object getInput() {
		return PDECore.getDefault().getModelManager();
	}

	@Override
	public Object[] getListElements() {
		
		final Set<IFile> selected = new HashSet<>();
		final Set<IProject> selectedProjects = new HashSet<>();

		// collect from original selection proper models which can be used for export
		for (Object elem : getSelection().toArray()) {
			if (elem instanceof IFile) {
				IFile file = (IFile) elem;
				if (!selectedProjects.contains(file.getProject())) {
					selectedProjects.add(file.getProject());
				}
			} else if (elem instanceof IProject) {
				IProject project = (IProject) elem;
				if (!selectedProjects.contains(project)) {
					selectedProjects.add(project);
				}
			} else if (elem instanceof IJavaProject) {
				IProject project = ((IJavaProject) elem).getProject();
				if (!selectedProjects.contains(project)) {
					selectedProjects.add(project);
				}
			}
		}
		for (IProject iProject : selectedProjects) {
			getTransactionsFromContainer(iProject, selected);
		}
		return selected.toArray();
	}

	@Override
	protected void hookHelpContext(Control control) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, IHelpContextIds.PLUGIN_EXPORT_WIZARD);
	}

	private boolean hasBuildProperties(IPluginModelBase model) {
		File file = new File(model.getInstallLocation(), ICoreConstants.BUILD_FILENAME_DESCRIPTOR);
		return file.exists();
	}

	@Override
	protected boolean isValidModel(IModel model) {
		return model != null && model instanceof IPluginModelBase;
	}

	@Override
	protected IModel findModelFor(IAdaptable object) {
		if (object instanceof IJavaProject)
			object = ((IJavaProject) object).getProject();
		if (object instanceof IProject)
			return PluginRegistry.findModel((IProject) object);
		if (object instanceof PersistablePluginObject) {
			IPluginModelBase model = PluginRegistry.findModel(((PersistablePluginObject) object).getPluginID());
			if (model != null && model.getUnderlyingResource() != null) {
				return model;
			}
		}
		return null;
	}

	protected boolean isEnableJarButton() {
		return getSelectedItems().length <= 1;
	}

	@Override
	protected void adjustAdvancedTabsVisibility() {
		pageChanged();
	}
}
