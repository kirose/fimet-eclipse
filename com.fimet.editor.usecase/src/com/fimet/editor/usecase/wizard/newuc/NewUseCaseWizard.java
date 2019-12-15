package com.fimet.editor.usecase.wizard.newuc;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

import com.fimet.commons.messages.Messages;
import com.fimet.core.usecase.UseCase;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * A file wizard for Transaction files.
 */
public class NewUseCaseWizard extends BasicNewResourceWizard
{
	
	public NewUseCaseWizard() {
		super();
		useCase = new UseCase();
	}
    private NewUseCaseWizardPage mainPage;
    private NewUseCaseWizardMsgPage secondPage;
    private UseCase useCase;
    public NewUseCaseWizardPage getMainPage() {
		return mainPage;
	}
	public NewUseCaseWizardMsgPage getSecondPage() {
		return secondPage;
	}
	@Override
    public void addPages()
    {
        super.addPages();
        mainPage = new NewUseCaseWizardPage("newTransactionPage1", this);
        mainPage.setTitle(Messages.NewTransactionWizard_CreateTransactionFile);
        mainPage.setFileName(Messages.NewTransactionWizard_FileName);
        secondPage = new NewUseCaseWizardMsgPage("newTransactionPage2", this);
        secondPage.setTitle(Messages.NewTransactionWizard_CreateTransactionFile);
        addPage(mainPage);
        addPage(secondPage);
        
    }
    @Override
    public void init(IWorkbench workbench, IStructuredSelection currentSelection)
    {
        super.init(workbench, currentSelection);
        setWindowTitle(Messages.NewTransactionWizard_NewTransactionFile);
        setNeedsProgressMonitor(false);
    }

    @Override
    public boolean performFinish()
    {
        IFile file = mainPage.createNewFile();
        if (file == null) {
            return false;
        }

        selectAndReveal(file);

        final IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();

        try {
            if (dw != null) {
                IWorkbenchPage page = dw.getActivePage();

                if (page != null) {
                    IDE.openEditor(page, file, true);
                }
            }
        }
        catch (PartInitException e) {
            StatusManager.getManager().handle(e.getStatus());
        }

        return true;
    }
    public UseCase getUseCase() {
    	return useCase;
    }
}
