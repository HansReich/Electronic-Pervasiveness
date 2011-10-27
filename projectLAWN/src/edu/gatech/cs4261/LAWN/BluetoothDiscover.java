package edu.gatech.cs4261.LAWN;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BluetoothDiscover extends DeviceDiscover {
	private static final String TAG = "BluetoothDiscover";
	private static String protocolType = "Bluetooth";

	@Override
	public String getProtocolType() {
		return null;
	}

	@Override
	public boolean isServiceAvailable() {
		
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			return false;
		}
		else{
			if (!mBluetoothAdapter.isEnabled()) {
			    //Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			    //startActivityForResult(enableBluetoothIntent, BluetoothAdapter.ACTION_REQUEST_ENABLE);
			}
			
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Common.DEBUG) Log.v(TAG, "ON CREATE");
		
		checkForBluetoothAdapter();
	}

	private void checkForBluetoothAdapter() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		// If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            
            if(Common.DEBUG) {
            	Log.w(TAG, "Bluetooth is not available");
            } else {
            	Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            }
            	
            finish(); //not sure why this would need a finish but in google's example
            return;
        }
	};

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

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

}
