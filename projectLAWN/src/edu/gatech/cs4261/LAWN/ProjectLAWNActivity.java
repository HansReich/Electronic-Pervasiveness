package edu.gatech.cs4261.LAWN;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import  edu.gatech.cs4261.LAWN.Log;


public class ProjectLAWNActivity extends CustomActivity {
	/** constants needed*/
	private static final String TAG = "Project LAWN Main";
	private Context ctx = this;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* set up the button listeners*/
        /* enable scanning*/
		Button btnScan = (Button)findViewById(R.id.btnScan);
		btnScan.setOnClickListener(btnScanListener);
		
		/* pull up new screen with data history*/
		Button btnHistory = (Button)findViewById(R.id.btnHistory);
		btnHistory.setOnClickListener(btnHistoryListener);
		
		/* pull up new screen with preferences on it*/
		Button btnPref = (Button)findViewById(R.id.btnPref);
		btnPref.setOnClickListener(btnPrefListener);
		
		setupLocation();
    }
    
    /** what to do when Scan is clicked */
    private OnClickListener btnScanListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d(TAG, "Scan clicked");
			getDiscover().scan();
		}
    };
    
    /** what to do when History is clicked */
    private OnClickListener btnHistoryListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d(TAG, "History clicked");
			
			/* Send to History screen*/
			//make the intent to call the new screen
			Intent i = new Intent(ProjectLAWNActivity.this, History.class);
			
			Log.d(TAG, "made the intent");
			
			//start the new activity
			ctx.startActivity(i);
			
			Log.d(TAG, "started the activity");
		}
    };
    
    /** what to do when Preferences is clicked */
    private OnClickListener btnPrefListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d(TAG, "Preferences clicked");
			
			/* Send to Preferences screen*/
			//make the intent to call the new screen
			Intent i = new Intent(ProjectLAWNActivity.this, Preferences.class);
			
			//start the new activity
			ctx.startActivity(i);
		}
    };
    public void setupLocation(){
		/* Use the LocationManager class to obtain GPS locations */
		LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0,
															0, mlocListener);
	}
    
    public class MyLocationListener implements LocationListener {
	
	    @Override
	    public void onProviderDisabled(String provider) {
	    	Log.v(TAG, "Gps Disabled"); 
	    }
	    
	    @Override
	    public void onProviderEnabled(String provider) {
	    	Log.v(TAG,"Gps Enabled"); 
	    }
	    
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }

		@Override
		public void onLocationChanged(Location location) {
			location.getLatitude();	
			location.getLongitude();	
		    String Text = "My current location is: " + 
		    				"Latitude = " + location.getLatitude() +
		    				"Longitud = " + location.getLongitude();
		    
		    Log.v(TAG, Text);			
		}

    }/* End of Class MyLocationListener */
}