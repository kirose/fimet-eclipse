package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Ftp;

public interface IFtpConnecting extends IFtpListener {
	public void onFtpConnecting(Ftp e);
}
