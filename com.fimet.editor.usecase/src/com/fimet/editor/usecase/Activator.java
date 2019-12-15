package com.fimet.editor.usecase;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

import com.fimet.commons.AbstractActivator;
import com.fimet.commons.utils.PluginUtils;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivator {

    /**
     * The plug-in ID
     */
    public static final String PLUGIN_ID = PluginUtils.USE_CASE_EDITOR_PLUGIN_ID; //$NON-NLS-1$

    /**
     * The shared instance
     */
    private static Activator plugin;
    public static final String FONT_ID = "com.fimet.usecase.fonts.textfont";
    /**
     * The constructor
     */
    public Activator() {
        if (PlatformUI.isWorkbenchRunning() || Display.getCurrent() != null) {
            getImageRegistry();
        }
    }
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }
    /**
     * Returns the shared instance
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }
	public static Activator getInstance() {
		return plugin;
	}

	@Override
	public String getPluginId() {
		return PLUGIN_ID;
	}
}
