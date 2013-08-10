package com.jasonfu19860310.db;

import com.jasonfu19860310.db.DBContract.ProjectEntry;
import com.jasonfu19860310.db.DBContract.RecordEntry;

import android.content.Context;

public class RecordDBHelper extends DBHelper {

	public RecordDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		SQL_CREATE_ENTRIES = "CREATE TABLE " + RecordEntry.TABLE_NAME + " (" +
				RecordEntry._ID + " INTEGER PRIMARY KEY," +
				RecordEntry.COLUMN_NAME_PROJECT_ID + INT_TYPE + COMMA_SEP +
			    RecordEntry.COLUMN_NAME_RECORD_DATE + BIGINT_TYPE + COMMA_SEP +
			    RecordEntry.COLUMN_NAME_RECORD_TIME_CONSUMING + INT_TYPE + COMMA_SEP +
			    "FOREIGN KEY (" + RecordEntry.COLUMN_NAME_PROJECT_ID + ") REFERENCES "+ 
			    ProjectEntry.TABLE_NAME + "(" + ProjectEntry.COLUMN_NAME_ID + ")" + ")";
	}

}
