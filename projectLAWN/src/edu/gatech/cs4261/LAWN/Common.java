package edu.gatech.cs4261.LAWN;

import android.os.Environment;

public class Common {
	public static final String ROOT_DIRECTORY = 
		Environment.getExternalStorageDirectory().getAbsolutePath() + "/LAWNFolder";
	
	public static final String MAIN_PREFERENCES = "PREFERENCES";
	
	public static final boolean DEBUG = true;
	
}
