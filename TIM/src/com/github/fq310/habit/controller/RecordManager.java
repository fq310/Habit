package com.github.fq310.habit.controller;

import java.util.Calendar;

import com.github.fq310.habit.db.DBHelper;
import com.github.fq310.habit.db.DBContract.RecordEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecordManager {
	private DBHelper databaseHelper;
	protected RecordManager(Context context) {
		databaseHelper = new DBHelper(context);
	}
	public void addNewRecord(long id, long totalSeconds) {
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
	
	public long getFinishedSecondsToday(long habitID) {
		SQLiteDatabase database = databaseHelper.getReadableDatabase();
		String selection = RecordEntry.COLUMN_NAME_PROJECT_ID + "=? AND " + RecordEntry.COLUMN_NAME_RECORD_DATE + ">=?";
		String id = String.valueOf(habitID);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		String[] selectionArgs = {id, String.valueOf(today.getTimeInMillis())};
		Cursor cursor = database.query(RecordEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
		long totalTime = 0;
		if (cursor.moveToFirst()) {
			totalTime += getTimeConsuming(cursor);
			while (cursor.moveToNext()) {
				totalTime += getTimeConsuming(cursor);
			}
		}
		database.close();
		return totalTime;
	}
	private long getTimeConsuming(Cursor cursor) {
		return cursor.getLong(
				cursor.getColumnIndexOrThrow(RecordEntry.COLUMN_NAME_RECORD_TIME_CONSUMING));
	}
	
	public void deleteRecords(long projectID) {
		SQLiteDatabase database = databaseHelper.getWritableDatabase();
		String selection = RecordEntry.COLUMN_NAME_PROJECT_ID + "=?";
		String id = String.valueOf(projectID);
		String[] selectionArgs = {id};
		database.delete(RecordEntry.TABLE_NAME, selection, selectionArgs);
		database.close();
	}
}
