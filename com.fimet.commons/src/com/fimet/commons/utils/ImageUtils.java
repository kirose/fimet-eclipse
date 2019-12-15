package com.fimet.commons.utils;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


public final class ImageUtils {

	private ImageUtils() {
	}
	public static ImageDescriptor getImageDescriptor(Class<?> ref, String file) {
	    Bundle bundle = FrameworkUtil.getBundle(ref);
	    URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
	    return ImageDescriptor.createFromURL(url);
	}
}
