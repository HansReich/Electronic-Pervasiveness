package edu.gatech.cs4261.LAWN;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class LAWNStorage extends ContentProvider {
	/* constants*/
	private static final String TAG = "Project LAWN Storage";
	private static final String DATABASE_NAME = "LAWNStorage.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DEVICES_TABLE_NAME = "devices";
    private static final String DETECTIONS_TABLE_NAME = "detections";
    private static final UriMatcher um;
    private static final String AUTHORITY = "edu.gatech.cs4261.LAWN.LAWNStorage";
    
    //define the content uris
    public static final Uri CONTENT_URI = 
    		Uri.parse("content://edu.gatech.cs4261.LAWN.LAWNStorage");
    public static final Uri CONTENT_URI_DEVICES = 
    		Uri.parse("content://" + AUTHORITY + "/" + DEVICES_TABLE_NAME);
    public static final Uri CONTENT_URI_DETECTIONS = 
    		Uri.parse("content://" + AUTHORITY + "/" + DETECTIONS_TABLE_NAME);
    
    //set up the special constants
    static {
    	um = new UriMatcher(UriMatcher.NO_MATCH);
    	
    	//set up query matches
    	//um.addURI(AUTHORITY, "devices", DEVICES);
    }

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
                    + "uid INTEGER,"
                    + "accuracy INTEGER,"
                    + "latitude REAL,"
                    + "longitude REAL,"
                    + "time_logged TEXT,"
                    + "weight INTEGER, "
                    + "FOREIGN KEY(uid) REFERENCES " + DEVICES_TABLE_NAME + "(uid)"
                    + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DEVICES_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + DETECTIONS_TABLE_NAME);
            onCreate(db);
		}
    	
    }
    
    //a database helper to make things easier
    private DatabaseHelper dbHelper;
    
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
		//initialize the database helper
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//make the query builder
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		//set the table(s) to query on
		qb.setTables(DEVICES_TABLE_NAME + " JOIN " + DETECTIONS_TABLE_NAME + 
				"ON (" + DEVICES_TABLE_NAME + ".uid = " + DETECTIONS_TABLE_NAME + ".uid)");
		
		/* TODO: check for what query it is?
		if(um.match(uri) == SPECIFIC_MESSAGE) {
			
		}*/
		
		//TODO: make the query
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
