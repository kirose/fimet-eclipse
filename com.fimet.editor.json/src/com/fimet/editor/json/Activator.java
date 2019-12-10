package com.fimet.editor.json;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

import com.fimet.commons.AbstractActivator;
import com.fimet.commons.utils.PluginUtils;
import com.fimet.editor.json.core.JsonColorProvider;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractActivator {

    /**
     * The plug-in ID
     */
    public static final String PLUGIN_ID = PluginUtils.JSON_EDITOR_PLUGIN_ID; //$NON-NLS-1$

    /**
     * The shared instance
     */
    private static Activator plugin;
    public static final String FONT_ID = "com.fimet.usecase.fonts.textfont";
    private static final JsonColorProvider colorProvider = new JsonColorProvider();
    /**
     * The constructor
     */
    public Activator() {
        if (PlatformUI.isWorkbenchRunning() || Display.getCurrent() != null) {
            getImageRegistry();
        }
        
		/*MessageConsole myConsole = new MessageConsole("Console Editor", null); // declare console
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{myConsole});

		PrintStream myS = new PrintStream(myConsole.newMessageStream());
		System.setOut(myS); // link standard output stream to the console
		System.setErr(myS); // link error output stream to the console*/
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
    	colorProvider.purge();
        plugin = null;
        super.stop(context);
    }
    /**
     * @return the color manager/provider
     */
    public static JsonColorProvider getColorProvider() {
        return colorProvider;
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
