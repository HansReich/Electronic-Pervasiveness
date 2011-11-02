package edu.gatech.cs4261.LAWN;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CustomActivity extends Activity {
	private static String TAG = "CustomActivity";
	/** called when the activity is first made*/
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public DeviceDiscover getDiscover() {
		LAWNApp app = (LAWNApp)getApplication();
		return app.getDiscover();
	}
	
	public SharedPreferences getPreferences() {
		LAWNApp app = (LAWNApp)getApplication();
		return app.getPreferences();
	}
	
	public void askUser(){
    	if(Common.DEBUG) Log.v(TAG, "requestBluetoothEnable");
		Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	    // This should make a pop up asking the user to allow
	    // bluetooth to be enabled
	    startActivityForResult(enableBluetoothIntent, BluetoothDiscover.REQUEST_ENABLE_BT);
    }
}
