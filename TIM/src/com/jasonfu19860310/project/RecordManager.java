package com.jasonfu19860310.project;

import com.jasonfu19860310.db.DBHelper;
import com.jasonfu19860310.db.RecordDBHelper;

import android.content.Context;


public class RecordManager {
	private DBHelper databaseHelper;
	public RecordManager(Context context) {
		databaseHelper = new RecordDBHelper(context);
	}
	public boolean addNewRecord(int timerTotalMinutes) {
		databaseHelper.getWritableDatabase();
		return false;
	}

}
