package edu.gatech.cs4261.LAWN;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class LAWNApp extends Application {
	
	/** The instance of this Application. */
	private static LAWNApp instance; 
	
	/** The SharedPreferences provider that the application will use. */
	SharedPreferences preferences;
	
	
	public static LAWNApp getInstance() {
		return instance;
	}
	
	public static void setInstance(LAWNApp instance) {
		LAWNApp.instance = instance;
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}
	
	/** Set up our singleton. */
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;

		preferences = getSharedPreferences(Common.MAIN_PREFERENCES,
				Context.MODE_PRIVATE);
	}
}