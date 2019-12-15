package com.fimet.editor.usecase.wizard.export;

import java.util.*;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.pde.core.IModel;
import org.eclipse.pde.internal.core.FeatureModelManager;
import org.eclipse.pde.internal.core.ifeature.IFeatureModel;
import org.eclipse.pde.internal.ui.PDEUIMessages;
import org.eclipse.pde.internal.ui.parts.WizardCheckboxTreePart;
import org.eclipse.pde.internal.ui.shared.CachedCheckboxTreeViewer;
import org.eclipse.pde.internal.ui.wizards.ListUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.dialogs.IWorkingSetSelectionDialog;

import com.fimet.commons.messages.Messages;


public abstract class BaseExportWizardPage extends AbstractExportWizardPage {
	
	protected ExportPart fExportPart;
	private IStructuredSelection fSelection;
	protected ExportDestinationTab fDestinationTab;
	protected ExportOptionsTab fOptionsTab;
	protected TabFolder fTabFolder;

	private class ExportListProvider implements ITreeContentProvider {
		@Override
		public Object[] getElements(Object parent) {
			return getListElements();
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}
	}

	class ExportPart extends WizardCheckboxTreePart {
		public ExportPart(String label, String[] buttonLabels) {
			super(label, buttonLabels);
		}

		@Override
		public void updateCounterLabel() {
			super.updateCounterLabel();
			pageChanged();
		}

		@Override
		protected void buttonSelected(Button button, int index) {
			switch (index) {
				case 0 :
					handleSelectAll(false);
					handleSelectAll(true);
					break;
				case 1 :
					handleSelectAll(false);
					break;
				case 2 :
					handleWorkingSets();
			}
		}
	}

	public BaseExportWizardPage(IStructuredSelection selection, String name, String choiceLabel) {
		super(name);
		fSelection = selection;
		fExportPart = new ExportPart(choiceLabel, new String[] {PDEUIMessages.WizardCheckboxTablePart_selectAll, PDEUIMessages.WizardCheckboxTablePart_deselectAll});
		setDescription(Messages.ExportWizard_Plugin_description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		container.setLayout(layout);

		createViewer(container);

		fTabFolder = new TabFolder(container, SWT.NONE);
		fTabFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		createTabs(fTabFolder, getDialogSettings());

		initializeViewer();
		if (getErrorMessage() != null) {
			setMessage(getErrorMessage());
			setErrorMessage(null);
		}
		setControl(container);
		hookHelpContext(container);
		Dialog.applyDialogFont(container);
	}

	protected void createTabs(TabFolder folder, IDialogSettings settings) {
		createDestinationTab(folder);
		createOptionsTab(folder);

		fDestinationTab.initialize(settings);
		fOptionsTab.initialize(settings);
	}

	protected void createDestinationTab(TabFolder folder) {
		fDestinationTab = new ExportDestinationTab(this);
		TabItem item = new TabItem(folder, SWT.NONE);
		item.setControl(fDestinationTab.createControl(folder));
		item.setText(PDEUIMessages.ExportWizard_destination);
	}

	protected void createOptionsTab(TabFolder folder) {
		fOptionsTab = new ExportOptionsTab(this);
		TabItem item = new TabItem(folder, SWT.NONE);
		item.setControl(fOptionsTab.createControl(folder));
		item.setText(PDEUIMessages.ExportWizard_options);
	}

	protected void createViewer(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		fExportPart.createControl(composite, 4, true);
		GridData gd = (GridData) fExportPart.getControl().getLayoutData();
		gd.heightHint = 125;
		gd.widthHint = 150;
		gd.horizontalSpan = 2;

		CachedCheckboxTreeViewer viewer = fExportPart.getTreeViewer();
		viewer.setContentProvider(new ExportListProvider());
		viewer.setLabelProvider(TransactionLabelProvider.getInstance());//PDEPlugin.getDefault().getLabelProvider());
		viewer.setComparator(ListUtil.PLUGIN_COMPARATOR);
		viewer.addDoubleClickListener(event -> {
			TreeItem firstTI = fExportPart.getTreeViewer().getTree().getSelection()[0];
			fExportPart.getTreeViewer().setChecked(firstTI.getData(), !firstTI.getChecked());
			fExportPart.updateCounterLabel();
		});
		Object input = getInput();
		if (input instanceof FeatureModelManager) {
			FeatureModelManager fmm = (FeatureModelManager) input;
			IFeatureModel[] models = fmm.getWorkspaceModels();
			for (IFeatureModel iFeatureModel : models) {
				if (iFeatureModel.getFeature().getId() == null) {
					fmm.removeFromWorkspaceFeature(iFeatureModel);
				}
			}
		}
		fExportPart.getTreeViewer().setInput(getInput());
	}

	protected abstract Object getInput();

	protected void initializeViewer() {
		final Set<IFile> selected = new HashSet<>();

		// collect from original selection proper models which can be used for export
		for (Object elem : fSelection.toArray()) {
			if (elem instanceof IFile) {
				IFile file = (IFile) elem;
				selected.add(file);
			} else if (elem instanceof IProject) {
				IProject project = (IProject) elem;
				getTransactionsFromContainer(project, selected);
			} else if (elem instanceof IJavaProject) {
				IProject project = ((IJavaProject) elem).getProject();
				getTransactionsFromContainer(project, selected);
			}
		}

		//collected models from selection actually doesn't have to be valid for 
		//this export page (or subclass) - select those which fits
		//because otherwise we could try to select for export something which is not
		//in the viewer visible for user
		final List<IFile> checked = new ArrayList<>(selected.size());
		for (Object o : getListElements()) {
			if (selected.contains(o)) {
				checked.add((IFile) o);
			}
		}

		//preselect all models which were deduced and if possible
		//scroll-down viewer to first one
		fExportPart.setSelection(checked.toArray());
		if (!checked.isEmpty()) {
			fExportPart.getTreeViewer().reveal(checked.get(0));
		}
	}
	protected void getTransactionsFromContainer(IContainer container, final Set<IFile> files) {
		IResource[] members = null;
		try {
			members = container.members();
		} catch (CoreException e) {}
		if (members != null) {
			for (IResource member : members) {
				if (member instanceof IContainer) {
					getTransactionsFromContainer((IContainer)member, files);
				} else if (member instanceof IFile) {
					String fileExtension = ((IFile) member).getFileExtension();
					if (fileExtension != null && fileExtension.equals(Messages.NewTransactionWizard_UseCaseFileExtension)) {
						files.add((IFile) member);
					}
		      	}
	    	}
		}
	}
	private void handleWorkingSets() {
		IWorkingSetManager manager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkingSetSelectionDialog dialog = manager.createWorkingSetSelectionDialog(getShell(), true);
		if (dialog.open() == Window.OK) {
			//collect proper models from working set
			Set<IModel> selected = new HashSet<>();
			IWorkingSet[] workingSets = dialog.getSelection();
			for (IWorkingSet workingSet : workingSets) {
				IAdaptable[] elements = workingSet.getElements();
				for (IAdaptable element : elements) {
					IModel model = findModelFor(element);
					if (isValidModel(model)) {
						selected.add(model);
					}
				}
			}
			//select only those which are possible to use in viewer list for exort
			final List<IModel> checked = new ArrayList<>(selected.size());
			for (Object o : getListElements()) {
				if (selected.contains(o)) {
					checked.add((IModel) o);
				}
			}

			fExportPart.setSelection(checked.toArray());
		}
	}

	public IFile[] getSelectedItems() {
		Object[] objSel = fExportPart.getSelection();
		IFile[] fileSel = new IFile[objSel.length];
		for (int i = 0; i < objSel.length; i++) {
			fileSel[i] = (IFile) objSel[i];
		}
		return fileSel;
	}

	@Override
	protected void pageChanged() {
		if (getMessage() != null)
			setMessage(null);
		String error = fExportPart.getSelectionCount() > 0 ? null : PDEUIMessages.ExportWizard_status_noselection;
		if (error == null)
			error = validateTabs();
		setErrorMessage(error);
		setPageComplete(error == null);
	}

	protected String validateTabs() {
		String message = fDestinationTab.validate();
		if (message == null)
			message = fOptionsTab.validate();
		return message;
	}

	protected abstract void hookHelpContext(Control control);

	protected abstract boolean isValidModel(IModel model);

	public abstract Object[] getListElements();

	protected abstract IModel findModelFor(IAdaptable object);

	@Override
	protected void saveSettings(IDialogSettings settings) {
		fDestinationTab.saveSettings(settings);
		fOptionsTab.saveSettings(settings);
	}

	public boolean doExportToDirectory() {
		return fDestinationTab.doExportToDirectory();
	}

	public String getFileName() {
		return fDestinationTab.getFileName();
	}

	public String getDestination() {
		return fDestinationTab.getDestination();
	}

	public boolean doSingleFile() {
		return fOptionsTab.doSingleFile();
	}

	protected boolean doZip() {
		return fOptionsTab.doZip();
	}

	protected abstract void adjustAdvancedTabsVisibility();

	public IStructuredSelection getSelection() {
		return fSelection;
	}
	
}
