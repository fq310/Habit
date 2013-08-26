package com.jasonfu19860310.habit.controller;

import java.util.Calendar;

import com.jasonfu19860310.habit.db.DBHelper;
import com.jasonfu19860310.habit.db.RecordDBHelper;
import com.jasonfu19860310.habit.db.DBContract.RecordEntry;

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
		database.close();
	}

	private ContentValues getUpdateValues(long projectID, long seconds) {
		ContentValues values = new ContentValues();
		values.put(RecordEntry.COLUMN_NAME_PROJECT_ID, projectID);
		values.put(RecordEntry.COLUMN_NAME_RECORD_DATE, Calendar.getInstance().getTimeInMillis());
		values.put(RecordEntry.COLUMN_NAME_RECORD_TIME_CONSUMING, seconds);
		return values;
	}
}
