package edu.gatech.cs4261.LAWN;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
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
	
	 // Intent request codes (from the Internet)
 	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;  
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2; 
    private static final int REQUEST_ENABLE_BT = 3;
    
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
	        }
            else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.v(TAG,"Entered the Finished ");
                curBluetoothAdapter.startDiscovery();
            }
		}
        
    };

	/* (non-Javadoc)
	 * @see edu.gatech.cs4261.LAWN.DeviceDiscover#getProtocolType()
	 */
	@Override
	public String getProtocolType() {
		return protocolType;
	}

	/* (non-Javadoc)
	 * @see edu.gatech.cs4261.LAWN.DeviceDiscover#isServiceAvailable()
	 */
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
	 * Request bluetooth enable.
	 */
	public void requestBluetoothEnable(){
		if(Common.DEBUG) Log.v(TAG, "requestBluetoothEnable");
		Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    // This should make a pop up asking the user to allow
	    // bluetooth to be enabled
	    startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BT);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Common.DEBUG) Log.v(TAG, "ON CREATE");
		curBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(checkForBluetoothAdapter()){
			//TODO set up bluetooth things
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
            } else {
            	Toast.makeText(this, "Bluetooth is not available", 
            					Toast.LENGTH_LONG).show();
            }            	
            //not sure why this would need a finish but in google's example
            //finish(); // commented out because of the return 
            return false;
        } else {
        	return true;
        }
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
    public void onStart() {
        super.onStart();
        if(Common.DEBUG) Log.e(TAG, "ON START");

        // If Bluetooth is not on, request that it be enabled.
        if (!curBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        // Otherwise, setup the ability to scan
        } else {
            //TODO Make this do something useful
        }
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
    public synchronized void onResume() {
        super.onResume();
        if(Common.DEBUG) Log.v(TAG, "ON RESUME");

        // Perform check in onResume() that covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        // TODO put some code to handle the restarting of bluetooth
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
    public synchronized void onPause() {
        super.onPause();
        if(Common.DEBUG) Log.v(TAG, "- ON PAUSE -");
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onStop()
     */
    @Override
    public void onStop() {
        super.onStop();
        if(Common.DEBUG) Log.v(TAG, "-- ON STOP --");
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        //TODO Stop the Bluetooth chat services
        if(Common.DEBUG) Log.v(TAG, "--- ON DESTROY ---");
    }
    
    //this method came from google not sure what it does 
    /**
     * Ensure discoverable.
     */
    private void ensureDiscoverable() {
        if(Common.DEBUG) Log.d(TAG, "ensureDiscoverable");
        if (curBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
	
	/* (non-Javadoc)
	 * @see edu.gatech.cs4261.LAWN.DeviceDiscover#scan()
	 */
	@Override
	public boolean scan() {
		if(Common.DEBUG) Log.v(TAG, "scan called");		
		
		//Since we dont know who might have called the bluetooth on the phone we
		//must stop all current actions using the adapter
		curBluetoothAdapter.cancelDiscovery();
		//This is an asyncronous call and will return instantly  
		boolean worked = curBluetoothAdapter.startDiscovery();	
		
		return worked;
	}
	

	/* (non-Javadoc)
	 * @see edu.gatech.cs4261.LAWN.DeviceDiscover#setProtocolType(java.lang.String)
	 */
	@Override
	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;		
	}

	

}
