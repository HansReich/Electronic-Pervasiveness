package edu.gatech.cs4261.LAWN;

import java.util.ArrayList;

public abstract class DeviceDiscover {
	private String protocolType;
	private boolean serviceAvailable;
	
	public abstract boolean isServiceAvailable();
	
	public abstract void setServiceAvailable();
	
	public abstract ArrayList<Device> scan();
	
	public abstract String getProtocolType();
	
	public abstract void setProtocolType(String protocolType);
	
}
