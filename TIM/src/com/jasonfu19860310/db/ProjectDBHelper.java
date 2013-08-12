package com.jasonfu19860310.db;


import com.jasonfu19860310.db.DBContract.ProjectEntry;

import android.content.Context;

public class ProjectDBHelper extends DBHelper {
	public ProjectDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		SQL_CREATE_ENTRIES = "CREATE TABLE " + ProjectEntry.TABLE_NAME + " (" +
				    ProjectEntry._ID + " INTEGER PRIMARY KEY," +
				    ProjectEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_START_DATE + BIGINT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_END_DATE + BIGINT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_DAYS + INT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_HOURS + INT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_WORKDAYS + TEXT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_MINITUES + INT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_TIMER_STARTED + TEXT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_TIMER_PAUSED + TEXT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_TIMER_SECONDS + BIGINT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_TOTAL_MINITUES + BIGINT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_TOTAL_FINISHED_MINITUES + BIGINT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_TIMER_START_DATE + BIGINT_TYPE + COMMA_SEP +
				    ProjectEntry.COLUMN_NAME_TOTAL_PASSED_DAYS + INT_TYPE + ")";
	}
	

}
