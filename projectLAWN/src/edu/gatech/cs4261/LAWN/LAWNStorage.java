package edu.gatech.cs4261.LAWN;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class LAWNStorage extends ContentProvider {
	/* constants*/
	private static final String TAG = "Project LAWN Storage";
	private static final String DATABASE_NAME = "LAWNStorage.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DEVICES_TABLE_NAME = "devices";
    private static final String DETECTIONS_TABLE_NAME = "detections";

    /* TODO: this class helps open, create, and upgrade the database file*/
    private static class DatabaseHelper extends SQLiteOpenHelper {
    	/* constructor*/
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// execute the SQL to create the devices table
			db.execSQL("CREATE TABLE " + DEVICES_TABLE_NAME + " ("
                    + "uid INTEGER PRIMARY KEY,"
                    + "mac_addr TEXT,"
                    + "protocol_found TEXT"
                    + ");");
			
			// execute the SQL to create the detections table
			db.execSQL("CREATE TABLE " + DETECTIONS_TABLE_NAME + " ("
                    + "uid INTEGER FOREIGN KEY,"
                    + "accuracy INTEGER,"
                    + "latitude DOUBLE,"
                    + "longitude DOUBLE,"
                    + "time_logged DATETIME,"
                    + "weight INTEGER"
                    + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
