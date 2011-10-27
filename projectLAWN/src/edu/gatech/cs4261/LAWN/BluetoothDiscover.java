package edu.gatech.cs4261.LAWN;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BluetoothDiscover extends DeviceDiscover {
	
	private static final String TAG = "BluetoothDiscover";
	private static final String protocolType = "Bluetooth";
	private BluetoothAdapter mBluetoothAdapter;
	
	 // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

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
			    //startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE);
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
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
	}
	
	@Override
    public void onStart() {
        super.onStart();
        if(Common.DEBUG) Log.e(TAG, "ON START");

        // If Bluetooth is not on, request that it be enabled.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the ability to scan
        } else {
            //TODO Make this do something usefull
        }
    }
	
	@Override
    public synchronized void onResume() {
        super.onResume();
        if(Common.DEBUG) Log.e(TAG, "ON RESUME");

        // Perform check in onResume() that covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        //TODO put some code to handle the restarting of bluetooth
    }
	
	@Override
    public synchronized void onPause() {
        super.onPause();
        if(Common.DEBUG) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Common.DEBUG) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        //TODO Stop the Bluetooth chat services
        if(Common.DEBUG) Log.e(TAG, "--- ON DESTROY ---");
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

	@Override
	public void setUp() {
		// TODO Auto-generated method stub
		
	}

}
