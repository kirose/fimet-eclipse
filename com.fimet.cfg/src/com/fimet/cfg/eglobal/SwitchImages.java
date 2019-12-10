package com.fimet.cfg.eglobal;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public final class SwitchImages {
	
	public static final ImageDescriptor CONNECTED_ICON = getImageDescriptor("operation_success.png");
	public static final ImageDescriptor CONNECTING_ICON = getImageDescriptor("warning_obj.png");
	public static final ImageDescriptor DISCONNECTED_ICON = getImageDescriptor("error_obj.png");
	
	private static ImageDescriptor getImageDescriptor(String file) {
	    Bundle bundle = FrameworkUtil.getBundle(SwitchImages.class);
	    URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
	    return ImageDescriptor.createFromURL(url);
	}
}
