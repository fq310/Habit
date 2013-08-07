package com.jasonfu19860310.db;

import com.jasonfu19860310.db.ProjectDBContract.ProjectEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProjectDBHelper extends SQLiteOpenHelper {
	private static String DATABASE_NAME = "project.db";
	public static final int DATABASE_VERSION = 1;
	private static final String TEXT_TYPE = " TEXT";
	private static final String INT_TYPE = " INT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
		    "CREATE TABLE " + ProjectEntry.TABLE_NAME + " (" +
		    ProjectEntry._ID + " INTEGER PRIMARY KEY," +
		    ProjectEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_START_DATE + TEXT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_END_DATE + TEXT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_DAYS + INT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_HOURS + INT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_WORKDAYS + TEXT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_MINITUES + INT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_TOTAL_MINITUES + INT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_TOTAL_FINISHED_MINITUES + INT_TYPE + COMMA_SEP +
		    ProjectEntry.COLUMN_NAME_TOTAL_PASSED_DAYS + INT_TYPE + ")";
		    
	public ProjectDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
	}
	

}
