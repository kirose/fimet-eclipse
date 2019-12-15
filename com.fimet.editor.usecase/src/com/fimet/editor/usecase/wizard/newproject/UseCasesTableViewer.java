
package com.fimet.editor.usecase.wizard.newproject;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Composite;

import com.fimet.core.impl.swt.CrudTable;
import com.fimet.core.usecase.UseCase;

public class UseCasesTableViewer extends CrudTable<UseCase> {
	
	private NewFimetProjectWizardPage2 page;
	public UseCasesTableViewer(NewFimetProjectWizardPage2 page, Composite parent, int style) {
		super(parent, CONTEXT_MENU | BUTTONS_RIGTH);
		this.page = page;
	}	
	protected void createContents() {

        newColumn(80, "Name", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof UseCase) {
            		return ((UseCase)element).getName();
            	}
                return super.getText(element);
            }
        });
        newColumn(50, "MTI", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof UseCase) {
            		return ((UseCase)element).getMsgAcqReq().getMti();
            	}
                return super.getText(element);
            }
        });
        newColumn(170, "Acquirer", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof UseCase) {
            		UseCase uc = (UseCase)element;
            		if (uc.getAcquirer() != null && uc.getAcquirer().getConnection() != null) {
            			return uc.getAcquirer().getConnection().getName() + " / " + uc.getAcquirer().getConnection().getPort() + " / " + uc.getAcquirer().getConnection().getProcess();
            		} else {
            			return "";
            		}
            	}
                return super.getText(element);
            }
        });
        newColumn(170, "Issuer", new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof UseCase) {
            		UseCase uc = (UseCase)element;
            		if (uc.getIssuer() != null && uc.getIssuer().getConnection() != null) {
            			return uc.getIssuer().getConnection().getName() + " / " + uc.getIssuer().getConnection().getPort();
            		} else {
            			return "";
            		}
            	}
                return super.getText(element);
            }
        });
	}
	@Override
	protected void onNew() {
		page.onNewUseCase();
	}
	@Override
	protected void onEdit() {
		page.onEditUseCase();
	}
	@Override
	protected void onDelete() {
		page.onDeleteUseCase();
	}
}