package com.fimet.core.net.listeners;

import com.fimet.core.net.IMessenger;

public interface IMessengerConnecting extends IMessengerListener {
	void onMessangerConnecting(IMessenger conn);
}
