package com.jasonfu19860310.habit.db;

import com.jasonfu19860310.habit.db.DBContract.RecordEntry;

import android.content.Context;

public class RecordDBHelper extends DBHelper {

	public RecordDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		SQL_CREATE_ENTRIES = "CREATE TABLE " + RecordEntry.TABLE_NAME + " (" +
				RecordEntry._ID + " INTEGER PRIMARY KEY," +
				RecordEntry.COLUMN_NAME_PROJECT_ID + BIGINT_TYPE + COMMA_SEP +
			    RecordEntry.COLUMN_NAME_RECORD_DATE + BIGINT_TYPE + COMMA_SEP +
			    RecordEntry.COLUMN_NAME_RECORD_TIME_CONSUMING + BIGINT_TYPE + ")";
	}

}
