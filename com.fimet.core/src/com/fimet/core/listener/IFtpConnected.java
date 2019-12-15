package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Ftp;

public interface IFtpConnected extends IFtpListener {
	public void onFtpConnected(Ftp e);
}
