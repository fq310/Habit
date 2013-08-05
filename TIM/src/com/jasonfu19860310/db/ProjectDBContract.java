package com.jasonfu19860310.db;

import android.provider.BaseColumns;

public class ProjectDBContract {
	public ProjectDBContract() {}
	
	public static abstract class ProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "project";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_START_DATE = "startDate";
        public static final String COLUMN_NAME_END_DATE = "endDate";
        public static final String COLUMN_NAME_DAYS = "days";
    }
}
