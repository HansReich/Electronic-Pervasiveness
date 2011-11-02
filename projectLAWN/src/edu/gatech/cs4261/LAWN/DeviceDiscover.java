package edu.gatech.cs4261.LAWN;

import android.app.Activity;

public abstract class DeviceDiscover extends Activity {
	private String protocolType;
	
	public abstract boolean isServiceAvailable();
	
	public abstract boolean scan();
	
	public abstract String getProtocolType();
	
	public abstract void setProtocolType(String protocolType);
	
}
