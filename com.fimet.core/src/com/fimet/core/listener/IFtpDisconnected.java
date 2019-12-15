package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Ftp;

public interface IFtpDisconnected extends IFtpListener {
	public void onFtpDisconnected(Ftp e);
}