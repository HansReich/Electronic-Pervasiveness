package edu.gatech.cs4261.LAWN;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import  edu.gatech.cs4261.LAWN.Log;


/**
 * The Class BluetoothDiscover for discovering bluetooth devices
 */
public class BluetoothDiscover extends DeviceDiscover {

	// Class variables
	private static final String TAG = "BluetoothDiscover";	
	/** The protocol type. */
	private static String protocolType = "Bluetooth";	
	/** The current bluetooth adapter. */
	private BluetoothAdapter curBluetoothAdapter;
	/** In Meters **/
	private int accuracy = 10;	
	private double lattitude;
	private double longitude; 
	private int weight = 1;
	
	
	 // Intent request codes (from the Internet)
 	public static final int REQUEST_CONNECT_DEVICE_SECURE = 1;  
    public static final int REQUEST_CONNECT_DEVICE_INSECURE = 2; 
    public static final int REQUEST_ENABLE_BT = 3;
    
    public BluetoothDiscover(){
    	if(Common.DEBUG) Log.v(TAG, "ON CREATE");
		
		curBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if(checkForBluetoothAdapter()){
			if(Common.DEBUG) Log.v(TAG, "device address" +curBluetoothAdapter.getAddress());
		}
		else{
			if(Common.DEBUG) Log.v(TAG, "device does not have bluetooth");
		}
    }
    
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
	            //TODO insert into database
            	
            	// Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.v(TAG,"Device address: " + device.getAddress());
                Log.v(TAG, "My current location is: " + 
	    				"Latitude = " + getLattitude() +
	    				"Longitud = " + getLongitude());
                Log.v(TAG, "Accuracy: " + getAccuracy());
                
	        }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.v(TAG,"Entered the Finished ");
                curBluetoothAdapter.startDiscovery();
            }
		}
         
    };


	@Override
	public boolean isServiceAvailable() {	
		if(Common.DEBUG) Log.v(TAG, "isServiceAvailable");
		
		if (checkForBluetoothAdapter()) {
		    // Device does not support Bluetooth
			return false;
		}
		else{
			if (!curBluetoothAdapter.isEnabled()) {
				//there is bluetooth but it is not enabled
			    return false;
			} else {
				//there is bluetooth and it is enabled
				return true;
			}			
		}
	}
	

	/**
	 * Check for bluetooth adapter.
	 *
	 * @return true, if successful
	 */
	private boolean checkForBluetoothAdapter() {
		if(Common.DEBUG) Log.v(TAG, "checkForBluetoothAdapter");
		
		// If the adapter is null, then Bluetooth is not supported
        if (curBluetoothAdapter == null) {            
            if(Common.DEBUG) {
            	Log.w(TAG, "Bluetooth is not available");
            }
            //not sure why this would need a finish but in google's example
            //finish(); // commented out because of the return 
            return false;
        } else {
        	return true;
        }
	}
	
	@Override
	public boolean scan(double lat, double lon) {
		if(Common.DEBUG) Log.v(TAG, "scan called");
		setLattitude(lat);
		setLongitude(lon);
		boolean worked = false;
		if(checkForBluetoothAdapter()){
			//Since we dont know who might have called the bluetooth on the phone we
			//must stop all current actions using the adapter
			curBluetoothAdapter.cancelDiscovery();
			//This is an asyncronous call and will return instantly  
			worked = curBluetoothAdapter.startDiscovery();
			Log.v(TAG, "found bluetooth device");
		}	
		
		return worked;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getAccuracy() {
		return accuracy;
	}    
    
	@Override
	public String getProtocolType() {
		return protocolType;
	}
	
	@Override
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;		
	}


	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}


	public double getLattitude() {
		return lattitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public int getWeight() {
		return weight;
	}
}
