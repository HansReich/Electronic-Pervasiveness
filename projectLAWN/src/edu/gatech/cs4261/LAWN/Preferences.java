package edu.gatech.cs4261.LAWN;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import edu.gatech.cs4261.LAWN.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class Preferences extends CustomActivity {
	/* constants*/
	private static final String TAG = "Preferences";
	
	/* buttons*/
	RadioButton WifiRadio;
	RadioButton BTRadio;
	
	/*called when the class is first made*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "before set prefs layout");
        setContentView(R.layout.prefs);
        
        /* enable options*/
		WifiRadio = (RadioButton)findViewById(R.id.WifiRadio);
		BTRadio = (RadioButton)findViewById(R.id.BTRadio);
		
		//check what scan was set to previously
		String scanSetting = getPreferences().getString("ScanSetting", "Wifi");
		
		Log.d(TAG, "after preferences check");
		Log.d(TAG, "scan is set to " + scanSetting);
		
		//change the radiobuttons to match the saved state
		if(scanSetting.equals("Wifi")) {
			WifiRadio.setChecked(true);
		} else {
			BTRadio.setChecked(true);
		}
		
		//set up save button listener
		Button btnSave = (Button)findViewById(R.id.SavePrefs);
		btnSave.setOnClickListener(SaveListener);
		
		Log.d(TAG, "end of onCreate");
	}
	
	/* button listeners/actions */
	/** what to do when an option is clicked */
    private OnClickListener SaveListener = new OnClickListener() {
		public void onClick(View v) {
			Log.d(TAG, "save preferences clicked");
			
			//set up the preferences editor
			SharedPreferences.Editor editor = getPreferences().edit();
			
			//check which setting is checked
	        if (WifiRadio.isChecked()) {
	            editor.putString("ScanSetting", "Wifi");
	        } else {
	            editor.putString("ScanSetting", "Bluetooth");
	        }
	        
	        //commit the changes to preferences
	        editor.commit();
	        
	        Log.d(TAG, "Scan Setting is " + getPreferences().getString("ScanSetting", "Wifi"));
		}
    };
}
