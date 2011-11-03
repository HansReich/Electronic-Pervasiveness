package edu.gatech.cs4261.LAWN;


import android.app.IntentService;

public abstract class DeviceDiscover {

	private String protocolType;
	
	public abstract boolean isServiceAvailable();
	
	public abstract boolean scan(double lat, double lon);
	
	public abstract String getProtocolType();
	
}
