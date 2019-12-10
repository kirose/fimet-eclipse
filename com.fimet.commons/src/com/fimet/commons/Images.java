package com.fimet.commons;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;



public final class Images {
	
	public static final ImageDescriptor USE_CASE_ICON = getImageDescriptor("usecase.png");
	public static final ImageDescriptor REFRESH_ICON = getImageDescriptor("refresh.png");
	public static final ImageDescriptor FILTER_ICON = getImageDescriptor("zoom_16x16.png");
	public static final ImageDescriptor DETAILS_ICON = getImageDescriptor("details_view.png");
	public static final ImageDescriptor TRANSACTION_LOG_ICON = getImageDescriptor("transaction_log.png");
	public static final ImageDescriptor FIELD_FORMAT_ICON = getImageDescriptor("field_format.png");
	public static final ImageDescriptor FIELD_PARENT_ICON = getImageDescriptor("field_parent.png");
	public static final ImageDescriptor FIELD_LEAF_ICON = getImageDescriptor("field_leaf.png");
	public static final ImageDescriptor CONNECTED_ICON = getImageDescriptor("operation_success.png");
	public static final ImageDescriptor CONNECTING_ICON = getImageDescriptor("warning_obj.png");
	public static final ImageDescriptor DISCONNECTED_ICON = getImageDescriptor("error_obj.png");
	public static final ImageDescriptor IAP_CONNECT_ICON = getImageDescriptor("run.png");
	public static final ImageDescriptor IAP_DISCONNECT_ICON = getImageDescriptor("terminate_co.png");
	public static final ImageDescriptor IAP_DISCONNECTED_ALL_ICON = getImageDescriptor("terminate_all_co.png");
	public static final ImageDescriptor IAP_MACHINE_ICON = getImageDescriptor("console_view.png");
	public static final ImageDescriptor INCOMMING_ICON = getImageDescriptor("incomming.png");
	public static final ImageDescriptor OUTGOING_ICON = getImageDescriptor("outgoing.png");
	public static final ImageDescriptor TOKEN_ICON = getImageDescriptor("token.png");
	public static final ImageDescriptor EGLOBAL_IMG = getImageDescriptor("EGlobal.png");
	public static final ImageDescriptor FTP_IMG = getImageDescriptor("ftp.png");
	public static final ImageDescriptor TYPES_IMG = getImageDescriptor("types.png");
	public static final ImageDescriptor RUN_IMG = getImageDescriptor("run.png");
	public static final ImageDescriptor SIM_QUEUE_ICON = getImageDescriptor("uc.png");
	public static final ImageDescriptor SORTABLE_ICON = getImageDescriptor("sotable.png");
	public static final ImageDescriptor REMOVE_ICON = getImageDescriptor("remove_correction.png");
	public static final ImageDescriptor CANCEL_ICON = getImageDescriptor("rem_co.png");
	public static final ImageDescriptor RUN_USECASE = getImageDescriptor("run_usecase.png");
	public static final ImageDescriptor VALIDATION_ICON = getImageDescriptor("validation.png");
	public static final ImageDescriptor DATABASE_ICON = getImageDescriptor("database_view.gif");	
	
	private static ImageDescriptor getImageDescriptor(String file) {
	    Bundle bundle = FrameworkUtil.getBundle(Images.class);
	    URL url = FileLocator.find(bundle, new Path("icons/" + file), null);
	    return ImageDescriptor.createFromURL(url);
	}
}
