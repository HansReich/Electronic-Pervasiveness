package edu.gatech.cs4261.LAWN;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import  edu.gatech.cs4261.LAWN.Log;


public class ProjectLAWNActivity extends CustomActivity {
	/** constants needed*/
	private static final String TAG = "Project LAWN Main";
	private Context ctx = this;
	 
	private LocationManager mlocManager;
	private LocationListener mlocListener;
	
    /** Called when the activity is first created. */
    /* (non-Javadoc)
     * @see edu.gatech.cs4261.LAWN.CustomActivity#onCreate(android.os.Bundle)
     */
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
		
		/* update the stats on the screen*/
		Button btnStats = (Button)findViewById(R.id.btnStats);
		btnStats.setOnClickListener(btnStatsListener);
		
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
			
			//check what the scan setting is set to (wifi is default)
			String scanSet = getPreferences().getString("ScanSetting", "Wifi");
			
			//change the device discover to whatever the setting is
			if(scanSet.equals("Bluetooth")) {
				//if setting is set to bluetooth then change the discover to the bluetooth one
				setDiscover(new BluetoothDiscover());
			} else {
				//if it's not bluetooth use wifi by default
				setDiscover(new APIWifiDiscover());
			}
			
			//perform the scan
			if(loc != null) {
				Boolean success = getDiscover().scan(loc.getLatitude(),loc.getLongitude(),ctx);
				
				if(success) {
					Toast display = Toast.makeText(getBaseContext(), "Successful scan", Toast.LENGTH_SHORT);
					display.show();
				} else {
					Toast display = Toast.makeText(getBaseContext(), "Unsuccessful scan", Toast.LENGTH_SHORT);
					display.show();
				}
			} else {
				Log.d(TAG, "loc returned null which means the provider is " +
						"currently disabled");
				Boolean success = getDiscover().scan(0,0,ctx);
				
				if(success) {
					Toast display = Toast.makeText(getBaseContext(), "Successful scan but no location", Toast.LENGTH_SHORT);
					display.show();
				} else {
					Toast display = Toast.makeText(getBaseContext(), "Unsuccessful scan", Toast.LENGTH_SHORT);
					display.show();
				}
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
    
    /** what to do when update statistics is clicked */
    private OnClickListener btnStatsListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d(TAG, "Update Statistics clicked");
			/*TODO: update the stats on the screen*/
			
			//set up the query
			String[] projection = new String[] {
											"mac_addr",
											"protocol_found",
											"accuracy",
											"latitude",
											"longitude",
											"time_logged",
											"weight"
										};
			ContentResolver cr = getContentResolver();
			
			//query the database (no selection parameters or sort order given)
			Cursor q = cr.query(LAWNStorage.CONTENT_URI, projection, null, null, null);
			
			//find the column indices needed
			int timeCol = q.getColumnIndex("time_logged");
			int weightCol = q.getColumnIndex("weight");
			
			//set up the text fields to be edited
			TextView tAllTime = (TextView)findViewById(R.id.all_time_stat);
			TextView tToday = (TextView)findViewById(R.id.today_stat);
			TextView tCurrent = (TextView)findViewById(R.id.current_stat);
			
			//find the latest scan and post it
			q.moveToFirst();
			int currWeight = q.getInt(weightCol);
			Log.d(TAG, "current weight = " + currWeight);
			Log.d(TAG, "current timestamp = " + q.getString(timeCol));
			tCurrent.setText(currWeight + " impressions");
			
			//find today's scans, total them, and post it
			q.moveToFirst();
			//get the current date for reference
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			
			//set up the variables needed
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date date;
			int todayWeight = 0;
			
			try {
				do {
					//parse the timestamp for the current entry
					String timestamp = q.getString(timeCol);
					date = format.parse(timestamp);
					
					//add the weight to the running total
					todayWeight += q.getInt(weightCol);
				} while(q.moveToNext() || cal.after(date));
			} catch (ParseException e) {
				Log.e(TAG, e.toString());
			}
			
			//put today's scan count up on the screen
			tToday.setText(todayWeight + " impressions");
			
			//total all the scans in the database and post it
			q.moveToFirst();
			int totalWeight = 0;
			do {
				totalWeight += q.getInt(weightCol);
			} while(q.moveToNext());
			Log.d(TAG, "total weight = " + totalWeight);
			tAllTime.setText(totalWeight + " impressions");
			
			//close the cursor
			q.close();
			
			Log.i(TAG, "stats updated");
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