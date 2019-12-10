
package com.fimet.editor.usecase.wizard.newproject;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.fimet.core.usecase.UseCase;

public class UseCasesTableViewer extends TableViewer {
	
	private List<UseCase> useCases;
	public UseCasesTableViewer(Composite parent, int style) {
		super(parent, style);
		createTable();
	}	
	private void createTable() {


        Table table = this.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setToolTipText("Table Use Cases");
        
        //stable.
        this.setContentProvider(ArrayContentProvider.getInstance());
        
        TableViewerColumn col;
        
       
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(170);
        col.getColumn().setText("Acquirer");
        col.setLabelProvider(new ColumnLabelProvider() {
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
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(170);
        col.getColumn().setText("Issuer");
        col.setLabelProvider(new ColumnLabelProvider() {
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

        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(50);
        col.getColumn().setText("MTI");
        col.setLabelProvider(new ColumnLabelProvider() {
            @Override
            public String getText(Object element) {
            	if (element instanceof UseCase) {
            		return ((UseCase)element).getAcquirer().getRequest().getMessage().getMti();
            	}
                return super.getText(element);
            }
        });
        
        col = new TableViewerColumn(this, SWT.NONE);
        col.getColumn().setWidth(300);
        col.getColumn().setText("Message");
        col.setLabelProvider(new ColumnLabelProvider() {
        	@Override
        	public boolean useNativeToolTip(Object object) {
        		return true;
        	}
        	@Override
        	public String getToolTipText(Object element) {
            	if (element instanceof UseCase) {
            		return ((UseCase)element).getAcquirer().getRequest().getMessage().toJson();
            	}
            	return null;
        	}
            @Override
            public String getText(Object element) {
            	if (element instanceof UseCase) {
            		return "";//((UseCase)element).getAcquirer().getRequest().getMessage().getBitmap();
            	}
                return super.getText(element);
            }
        });
	}
	public void setUseCases(List<UseCase> useCases) {
		this.useCases = useCases;
		setInput(useCases);
	}
	public List<UseCase> getUseCases() {
		return useCases;
	}
	public void addUseCase(UseCase useCase) {
		if (useCases == null) {
			useCases = new ArrayList<>();
		}
		useCases.add(useCase);
		add(useCase);
	}
	public void removeUseCase(UseCase useCase) {
		if (useCases.contains(useCase)) {
			int i = useCases.indexOf(useCase);
			useCases.remove(i);
			remove(useCase);
		}
	}
	public void remove(UseCase useCase) {
		if (useCases.contains(useCase)) {
			getTable().remove(useCases.indexOf(useCase));
			this.refresh();
		}
	}
}