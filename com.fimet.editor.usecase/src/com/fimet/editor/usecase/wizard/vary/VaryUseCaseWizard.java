package com.fimet.editor.usecase.wizard.vary;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class VaryUseCaseWizard extends BasicNewResourceWizard {
	private static Pattern REGEXP_KEY_VALUE = Pattern.compile("\"([^\"]+)\"[ ]*:[ ]*\"([^\"]+)\"");
	public static final String ID = "com.fimet.wizard.varusecase.VarUseCaseWizard";
	private IResource useCaseResource;
	public VaryUseCaseWizard() {
		super();
	}
    private VaryUseCaseWizardFilePage mainPage;
    public VaryUseCaseWizardFilePage getMainPage() {
		return mainPage;
	}
	@Override
    public void addPages()
    {
        super.addPages();
        mainPage = new VaryUseCaseWizardFilePage("copyUseCasePage1", this);
        mainPage.setTitle(Messages.CopyUseCaseWizard_VaryUseCaseFile);
        mainPage.setFileName(Messages.NewTransactionWizard_FileName);
        addPage(mainPage);
    }
    @Override
    public void init(IWorkbench workbench, IStructuredSelection currentSelection)
    {
        super.init(workbench, currentSelection);
        setWindowTitle(Messages.NewTransactionWizard_NewTransactionFile);
        setNeedsProgressMonitor(false);

		if (selection != null && selection instanceof IStructuredSelection) {
			useCaseResource = (IResource)((IStructuredSelection)selection).getFirstElement();
		}
    }
	private boolean askOverride(String name) {
		//PlatformUI.getPreferenceStore().setValue(IPreference.COPY_USECASE_OVERRIDE_FILE, false);
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
    	String params = null;
    	for (String resource : resources) {
    		i = resource.indexOf(":{");
    		if (i > 0) {
    			params = resource.substring(i+1);
    			resource = resource.substring(0,i);
    			if (params.endsWith("."+Messages.NewTransactionWizard_UseCaseFileExtension)) {
    				params = params.substring(0,params.length()-Messages.NewTransactionWizard_UseCaseFileExtension.length()-1);
    			}
    		}
    		if (!resource.endsWith(Messages.NewTransactionWizard_UseCaseFileExtension)) {
    			resource += "."+Messages.NewTransactionWizard_UseCaseFileExtension;
    		}
	        IFile file = mainPage.createNewFile(useCaseResource, resource, createParams(params));
	        if (file != null) {
	        	selectAndReveal(file);
	        }
    	}
        return true;
    }
	private List<Parameter> createParams(String sparams) {
		List<Parameter> params = new ArrayList<>();
		if (sparams != null && sparams.length() > 0) {
			Matcher m = REGEXP_KEY_VALUE.matcher(sparams);
			while (m.find()) {
				params.add(new Parameter(m.group(1), m.group(2)));
			}
		}
		return params;
	}

    public IResource getUseCaseResource() {
    	return useCaseResource;
    }
}
