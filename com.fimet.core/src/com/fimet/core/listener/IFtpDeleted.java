package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Ftp;

public interface IFtpDeleted extends IFtpListener {
	public void onFtpDeleted(Ftp e);
}
