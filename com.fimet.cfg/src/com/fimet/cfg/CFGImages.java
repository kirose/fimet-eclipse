package com.fimet.cfg;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


public final class CFGImages {
	
	public static final ImageDescriptor IAP_CONNECT_ICON = getImageDescriptor("run.png");
	public static final ImageDescriptor FILTER_ICON = getImageDescriptor("zoom_16x16.png");
	public static final ImageDescriptor TYPES_IMG = getImageDescriptor("types.png");
	public static final ImageDescriptor REFRESH_ICON = getImageDescriptor("refresh.png");
	public static final ImageDescriptor CANCEL_ICON = getImageDescriptor("rem_co.png");
	public static final ImageDescriptor IAP_DISCONNECT_ICON = getImageDescriptor("terminate_co.png");
	public static final ImageDescriptor REMOVE_ICON = getImageDescriptor("remove_correction.png");
	public static final ImageDescriptor RUN_IMG = getImageDescriptor("run.png");

	
	private static ImageDescriptor getImageDescriptor(String file) {
	    Bundle bundle = FrameworkUtil.getBundle(CFGImages.class);
	    URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
	    return ImageDescriptor.createFromURL(url);
	}
}
