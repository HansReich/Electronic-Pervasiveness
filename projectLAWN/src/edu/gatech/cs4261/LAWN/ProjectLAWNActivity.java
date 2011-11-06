package edu.gatech.cs4261.LAWN;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import  edu.gatech.cs4261.LAWN.Log;


public class ProjectLAWNActivity extends CustomActivity {
	/** constants needed*/
	private static final String TAG = "Project LAWN Main";
	private Context ctx = this;
	 
	private LocationManager mlocManager;
	private LocationListener mlocListener;
	
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
		/*this.registerReceiver(this.myWifiReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));*/
    }
    
    /*private BroadcastReceiver myWifiReceiver = new BroadcastReceiver(){
       @Override
       public void onReceive(Context arg0, Intent arg1) {
           // TODO Auto-generated method stub
           NetworkInfo networkInfo = (NetworkInfo) arg1.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
           if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
               DisplayWifiState();
           }
       }
    };*/
    
    /** what to do when Scan is clicked */
    private OnClickListener btnScanListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d(TAG, "Scan clicked");
			
			//get the current location of the device
			Location loc = getLocation();
			
			
			if(loc != null) {
				getDiscover().scan(loc.getLatitude(),loc.getLongitude(),ctx);
			} else {
				Log.d(TAG, "loc returned null which means the provider is " +
						"currently disabled");
				getDiscover().scan(0,0,ctx);
			}
		}

		//finds the last known location of the device
		private Location getLocation() {
			Location loc = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if(loc == null) {
				loc = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			return loc;
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
    	Log.v(TAG, "Setting up Location Services");
		/* Use the LocationManager class to obtain GPS locations */
    	mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListener();
		mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0,
															0, mlocListener);
	}
    
    public class MyLocationListener implements LocationListener {
    	private static final String TAG = "MyLocationListener";
    	
	    
	    public void onProviderDisabled(String provider) {
	    	Log.v(TAG, "Gps Disabled"); 
	    }
	    
	    
	    public void onProviderEnabled(String provider) {
	    	Log.v(TAG,"Gps Enabled"); 
	    }
	    
	    
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	    }

		
		public void onLocationChanged(Location location) {
			Log.v(TAG, "LocationChanged");
			location.getLatitude();	
			location.getLongitude();	
		    String Text = "My current location is: " + 
		    				"Latitude = " + location.getLatitude() +
		    				"Longitud = " + location.getLongitude();
		    
		    Log.v(TAG, Text);			
		}

    }/* End of Class MyLocationListener */
}