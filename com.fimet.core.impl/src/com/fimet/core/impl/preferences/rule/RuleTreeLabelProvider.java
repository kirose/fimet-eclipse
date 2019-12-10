package com.fimet.core.impl.preferences.rule;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.swt.graphics.Image;

import com.fimet.commons.Images;

class RuleTreeLabelProvider extends LabelProvider implements IStyledLabelProvider {

    private ImageDescriptor fieldParentImage;
    private ImageDescriptor fieldLeafImage;
    private ResourceManager resourceManager;

    public RuleTreeLabelProvider() {
        this.fieldLeafImage = Images.FIELD_LEAF_ICON;
        this.fieldParentImage = Images.FIELD_PARENT_ICON;
    }
    @Override
    public StyledString getStyledText(Object element) {
    	return ((RuleNode)element).getStyledText();
    }
    @Override
    public Image getImage(Object element) {
        if(element instanceof RuleNode) {
            return getResourceManager().createImage(((RuleNode)element).hasChildren() ? fieldParentImage : fieldLeafImage);
        }

        return super.getImage(element);
    }
    @Override
    public void dispose() {
        // garbage collect system resources
        if(resourceManager != null) {
            resourceManager.dispose();
            resourceManager = null;
        }
    }
    protected ResourceManager getResourceManager() {
        if(resourceManager == null) {
            resourceManager = new LocalResourceManager(JFaceResources.getResources());
        }
        return resourceManager;
    }
}