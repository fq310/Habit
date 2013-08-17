package com.jasonfu19860310.project;

import java.util.Calendar;

import com.jasonfu19860310.db.DBContract.RecordEntry;
import com.jasonfu19860310.db.DBHelper;
import com.jasonfu19860310.db.RecordDBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class RecordManager {
	private DBHelper databaseHelper;
	public RecordManager(Context context) {
		databaseHelper = new RecordDBHelper(context);
	}
	public void addNewRecord(long id, long totalSeconds) {
		databaseHelper.getWritableDatabase();
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		ContentValues values = getUpdateValues(id, totalSeconds);
		database.insert(RecordEntry.TABLE_NAME, null, values);
	}

	private ContentValues getUpdateValues(long projectID, long seconds) {
		ContentValues values = new ContentValues();
		values.put(RecordEntry.COLUMN_NAME_PROJECT_ID, projectID);
		values.put(RecordEntry.COLUMN_NAME_RECORD_DATE, Calendar.getInstance().getTimeInMillis());
		values.put(RecordEntry.COLUMN_NAME_RECORD_TIME_CONSUMING, seconds);
		return values;
	}
}
