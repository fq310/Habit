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
        public static final String COLUMN_NAME_WORKDAYS = "workdays";
        public static final String COLUMN_NAME_DAYS = "days";
        public static final String COLUMN_NAME_HOURS = "hours";
        public static final String COLUMN_NAME_MINITUES = "minitues";
        public static final String COLUMN_NAME_TOTAL_MINITUES = "total_minitues";
        public static final String COLUMN_NAME_TOTAL_FINISHED_MINITUES = "total_finished_minitues";
        public static final String COLUMN_NAME_TOTAL_PASSED_DAYS = "total_passed_days";
    }
}
