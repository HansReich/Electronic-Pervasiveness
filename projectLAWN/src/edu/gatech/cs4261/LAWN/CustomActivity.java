package edu.gatech.cs4261.LAWN;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class CustomActivity extends Activity {
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
}
