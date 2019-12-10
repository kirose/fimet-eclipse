package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Ftp;

public interface IFtpUpdated extends IFtpListener {
	public void onFtpUpdated(Ftp e);
}
