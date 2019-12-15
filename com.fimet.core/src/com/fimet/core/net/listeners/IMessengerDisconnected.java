package com.fimet.core.net.listeners;

import com.fimet.core.net.IMessenger;

public interface IMessengerDisconnected extends IMessengerListener {
	void onMessangerDisconnected(IMessenger conn);
}
