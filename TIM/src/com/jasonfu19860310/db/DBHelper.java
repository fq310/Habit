package com.jasonfu19860310.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DBHelper extends SQLiteOpenHelper {

	protected static String DATABASE_NAME = "project.db";
	public static final int DATABASE_VERSION = 1;
	protected static final String TEXT_TYPE = " TEXT";
	protected static final String INT_TYPE = " INT";
	protected static final String BIGINT_TYPE = " BIGINT";
	protected static final String COMMA_SEP = ",";
	protected String SQL_CREATE_ENTRIES;

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public DBHelper(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
	}

}