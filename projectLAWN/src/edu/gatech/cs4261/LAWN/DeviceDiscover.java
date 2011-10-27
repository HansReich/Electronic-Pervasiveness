package edu.gatech.cs4261.LAWN;

import java.util.ArrayList;

public abstract class DeviceDiscover {
	private String protocolType;
	
	public abstract ArrayList<Device> scan();
	
	public abstract String getProtocolType();
	
	public abstract void setProtocolType(String protocolType);
	
}
