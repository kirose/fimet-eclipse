package com.fimet.core.listener;

import com.fimet.core.entity.sqlite.Ftp;

public interface IFtpInserted extends IFtpListener {
	public void onFtpInserted(Ftp e);
}
