package com.fimet.commons.utils;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.wizards.IWizardDescriptor;


public class ViewUtils {

	public static Map<String, Object> instances = new HashMap<>();
	public static final String DATA_BASE_CONNECTIONS_ID = "com.fimet.view.DataBaseView";
	public static final String IAP_CONNECTIONS_ID = "com.fimet.view.SocketView";
	public static final String TRANSACTION_LOG_ID = "com.fimet.view.TransactionLogView";
	public static final String FTP_CONNECTIONS_ID = "com.fimet.view.FTPView";
	public static final String SIM_QUEUE_ID = "com.fimet.view.SimQueueView";
	public static final String MESSAGE_ISO_ID = "com.fimet.view.MessageIsoView";
	public static void registerInstance(String id, Object instance) {
		instances.put(id, instance);
	}
	public static Object getInstance(String id) {
		return instances.get(id);
	}
	public static boolean existsInstance(String id) {
		return instances.containsKey(id);
	}
	public static ISelectionService getSelectionService() {
		IWorkbenchWindow workbench = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbench != null) {
			ISelectionService selectionService = workbench.getSelectionService();
			return selectionService;
		} else {
			return null;
		}
	}
	public static IViewPart getViewById(String id){
		IWorkbenchWindow workbench = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (workbench != null) {
			IWorkbenchPage page = workbench.getActivePage();
			return page.findView(id);
		} else {
			return null;
		}
	}
	public static IViewPart getViewAndShowById(String id){
    	try {
    		IWorkbenchWindow workbench = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    		if (workbench != null) {
    			IWorkbenchPage page = workbench.getActivePage();
    			return page.showView(id);
    		} else {
    			return null;
    		}
		} catch (PartInitException e) {}
    	return null;
	}
	public static ISelection getSelection() {
		IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		ISelectionService selectionService = workbenchWindow.getSelectionService();
		return selectionService.getSelection();
	}
	public static void openWizard(String id, IStructuredSelection selection) {
		// First see if this is a "new wizard".
		IWizardDescriptor descriptor = PlatformUI.getWorkbench()
				.getNewWizardRegistry().findWizard(id);
		// If not check if it is an "import wizard".
		if  (descriptor == null) {
			descriptor = PlatformUI.getWorkbench().getImportWizardRegistry()
					.findWizard(id);
		}
		// Or maybe an export wizard
		if  (descriptor == null) {
			descriptor = PlatformUI.getWorkbench().getExportWizardRegistry()
					.findWizard(id);
		}
		try  {
			// Then if we have a wizard, open it.
			if  (descriptor != null) {
				IWizard wizard = descriptor.createWizard();
				if (wizard instanceof IWorkbenchWizard) {
					((IWorkbenchWizard)wizard).init(PlatformUI.getWorkbench(), selection);
				}
				WizardDialog wd = new  WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
				wd.setTitle(wizard.getWindowTitle());
				wd.open();
			}
		} catch  (CoreException e) {}
	}
    public static void selectAndReveal(IResource resource, IWorkbenchWindow window) {
        // validate the input
        if (window == null || resource == null) {
			return;
		}
        IWorkbenchPage page = window.getActivePage();
        if (page == null) {
			return;
		}

        // get all the view and editor parts
		List<IWorkbenchPart> parts = new ArrayList<>();
		for (IWorkbenchPartReference ref : page.getViewReferences()) {
            IWorkbenchPart part = ref.getPart(false);
            if (part != null) {
				parts.add(part);
			}
        }
		for (IWorkbenchPartReference ref : page.getEditorReferences()) {
            if (ref.getPart(false) != null) {
				parts.add(ref.getPart(false));
			}
        }

        final ISelection selection = new StructuredSelection(resource);
		Iterator<?> itr = parts.iterator();
        while (itr.hasNext()) {
            IWorkbenchPart part = (IWorkbenchPart) itr.next();

            // get the part's ISetSelectionTarget implementation
			ISetSelectionTarget target = Adapters.adapt(part, ISetSelectionTarget.class);

            if (target != null) {
                // select and reveal resource
                final ISetSelectionTarget finalTarget = target;
                window.getShell().getDisplay().asyncExec(() -> finalTarget.selectReveal(selection));
            }
        }
    }
    public static void setToClipboard(String value) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(value);
        clipboard.setContents(selection, selection);
    }
    public static String getFromClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try {
			return (String)clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException|IOException e) {
			return null;
		}
    }
}
