package com.fimet.core.net.listeners;

import com.fimet.core.net.IMessenger;

public interface IMessengerConnected extends IMessengerListener {
	void onMessangerConnected(IMessenger conn);
}
