package edu.gatech.cs4261.LAWN;

import java.util.ArrayList;

public class BluetoothDiscover extends DeviceDiscover {
	private static String protocolType = "Bluetooth";
	
	@Override
	public String getProtocolType() {
		return null;
	}

	@Override
	public boolean isServiceAvailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Device> scan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProtocolType(String protocolType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServiceAvailable() {
		// TODO Auto-generated method stub
		
	}

}
