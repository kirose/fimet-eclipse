package com.fimet.editor.usecase.wizard.copy;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

import com.fimet.commons.messages.Messages;
import com.fimet.commons.preference.IPreference;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * A file wizard for Transaction files.
 */
public class CopyUseCaseWizard extends BasicNewResourceWizard
{
	public static final String ID = "com.fimet.wizard.copyusecase.CopyUseCaseWizard";
	private List<IResource> useCaseResources;
	public CopyUseCaseWizard() {
		super();
	}
    private CopyUseCaseWizardFilePage mainPage;
    private CopyUseCaseWizardFieldsPage secondPage;
    public CopyUseCaseWizardFilePage getMainPage() {
		return mainPage;
	}
	public CopyUseCaseWizardFieldsPage getSecondPage() {
		return secondPage;
	}
	@Override
    public void addPages()
    {
        super.addPages();
        mainPage = new CopyUseCaseWizardFilePage("copyUseCasePage1", this);
        mainPage.setTitle(Messages.CopyUseCaseWizard_CopyUseCaseFile);
        mainPage.setFileName(Messages.NewTransactionWizard_FileName);
        secondPage = new CopyUseCaseWizardFieldsPage("copyUseCasePage2", this);
        secondPage.setTitle(Messages.CopyUseCaseWizard_ReplaceFields);
        mainPage.setSecondPage(secondPage);
        addPage(mainPage);
        addPage(secondPage);
    }
    @Override
    public void init(IWorkbench workbench, IStructuredSelection currentSelection)
    {
        super.init(workbench, currentSelection);
        setWindowTitle(Messages.NewTransactionWizard_NewTransactionFile);
        setNeedsProgressMonitor(false);
        useCaseResources = new ArrayList<>();
		if (selection != null && selection instanceof org.eclipse.jface.viewers.ITreeSelection) {
			org.eclipse.jface.viewers.ITreeSelection sel = (org.eclipse.jface.viewers.ITreeSelection)selection;
			List<?> list = sel.toList();
			for (Object item : list) {
				if (item instanceof IResource) {
					useCaseResources.add((IResource)item);
				}
			}
		}
    }
	private boolean askOverride(String name) {
		//IDEWorkbenchPlugin.getDefault().getPreferenceStore().setValue(IPreference.COPY_USECASE_OVERRIDE_FILE, false);
		boolean overrideFile = PlatformUI.getPreferenceStore().getBoolean(IPreference.COPY_USECASE_OVERRIDE_FILE);
		if (overrideFile) {
			return true;
		}
		MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
			"Override File",
			"Already exist "+name+", do you want override it?",
			"Don't ask again.",
			false,
			PlatformUI.getPreferenceStore(),
			IPreference.COPY_USECASE_OVERRIDE_FILE
		);
		overrideFile = dialog.getReturnCode() == Window.OK;
		if (dialog.getToggleState() && overrideFile) {
			PlatformUI.getPreferenceStore().setValue(IPreference.COPY_USECASE_OVERRIDE_FILE, overrideFile);
		}
		return overrideFile;
	}
    private boolean validationsPreFinish() {
    	List<String> resources = mainPage.getResources();
    	if (resources == null) {
    		return false;
    	}
    	if(resources.size() != useCaseResources.size()) {
    		return false;
    	}
    	IWorkspace workspace = ResourcesPlugin.getWorkspace();
    	IPath containerPath = mainPage.getContainerPath();
    	for (String  nameFile : resources) {
    		IPath newFilePath = containerPath.append(nameFile);
    		if (workspace.getRoot().getFile(newFilePath).exists()) {
    			if (!askOverride(nameFile)) {
    				return false;
    			}
    		}    		
    	}
    	return true;
    }
    @Override
    public boolean performFinish()
    {
    	if (!validationsPreFinish()) {
    		return false;
    	}
    	List<String> resources = mainPage.getResources();
    	int i = 0;
    	for (IResource useCase : useCaseResources) {
	        IFile file = mainPage.createNewFile(useCase, resources.get(i++));
	        if (file != null) {
	        	selectAndReveal(file);
	        }
    	}
        return true;
    }
	public List<IResource> getUseCaseResources() {
		return useCaseResources;
	}
    
}
