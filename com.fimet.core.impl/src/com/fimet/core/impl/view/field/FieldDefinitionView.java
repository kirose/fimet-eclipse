package com.fimet.core.impl.view.field;


import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.fimet.commons.Images;
import com.fimet.core.impl.swt.field.FieldDefinitionPanel;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 *
 */
public class FieldDefinitionView extends ViewPart {

	public static final String ID = "com.fimet.preferences.FieldDefinitionPage";
	private FieldDefinitionPanel panel;
	
	public FieldDefinitionView() {

    }
	@Override
	public void createPartControl(Composite container) {
    	GridLayout layout = new GridLayout(1,false);
    	layout.marginHeight = 0;
    	layout.marginWidth = 0;
    	panel = new FieldDefinitionPanel(container, FieldDefinitionPanel.SHOW_FIELD_DETAILS);
    	panel.setLayout(layout);
    	createToolbar();
    	
	}
	private void createToolbar() {
        Action saveAction = new Action("Save") {
        	@Override
        	public void run() {
        		onSave();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_CONNECT_ICON;
        	}
        };
        Action cancelAction = new Action("Cancel") {
        	@Override
        	public void run() {
        		onCancel();
        	}
        	@Override
        	public ImageDescriptor getImageDescriptor() {
        		return Images.IAP_DISCONNECT_ICON;
        	}
        };
        IActionBars aBars = getViewSite().getActionBars();
        IMenuManager menu = aBars.getMenuManager();
        IToolBarManager toolBar = aBars.getToolBarManager();
        
        menu.add(saveAction);
        toolBar.add(saveAction);
        menu.add(cancelAction);
        toolBar.add(cancelAction);
	}
	private void onSave() {
		panel.commit();
	}
	private void onCancel() {
		panel.cancel();
	}
	@Override
	public void setFocus() {
		panel.setFocus();
	}
	@Override
	public void dispose() {
		panel.dispose();
	}

}