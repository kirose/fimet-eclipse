package com.fimet.core.net.listeners;

public interface IMessengerListener {
	int ON_SIMULATE_ACQ_REQUEST = 1;
	int ON_WRITE_ACQ_REQUEST = 2;
	int ON_READ_ISS_REQUEST = 3;
	int ON_PARSE_ISS_REQUEST = 4;
	int ON_SIMULATE_ISS_RESPONSE = 5;
	int ON_WRITE_ISS_RESPONSE = 6;
	int ON_READ_ACQ_RESPONSE = 7;
	int ON_PARSE_ACQ_RESPONSE = 8;
	int ON_COMPLETE = 9;
	int ON_CONNECTED = 10;
	int ON_CONNECTING = 11;
	int ON_DISCONNECTED = 12;
}
