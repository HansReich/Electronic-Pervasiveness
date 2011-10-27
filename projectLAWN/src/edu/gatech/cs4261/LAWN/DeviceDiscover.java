package edu.gatech.cs4261.LAWN;

import java.util.ArrayList;

import android.app.Activity;

public abstract class DeviceDiscover extends Activity {
	private String protocolType;
	private boolean serviceAvailable;
	
	public abstract boolean isServiceAvailable();
	
	public abstract void setServiceAvailable();
	
	public abstract ArrayList<Device> scan();
	
	public abstract String getProtocolType();
	
	public abstract void setProtocolType(String protocolType);
	
	public abstract void setUp();
	
}
