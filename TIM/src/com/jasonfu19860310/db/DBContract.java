package com.jasonfu19860310.db;

import android.provider.BaseColumns;

public class DBContract {
	public DBContract() {}
	
	public static abstract class ProjectEntry implements BaseColumns {
        public static final String TABLE_NAME = "project";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_START_DATE = "startDate";
        public static final String COLUMN_NAME_END_DATE = "endDate";
        public static final String COLUMN_NAME_WORKDAYS = "workdays";
        public static final String COLUMN_NAME_DAYS = "days";
        public static final String COLUMN_NAME_HOURS = "hours";
        public static final String COLUMN_NAME_MINITUES = "minitues";
        public static final String COLUMN_NAME_TOTAL_SECONDS = "total_seconds";
        public static final String COLUMN_NAME_TOTAL_FINISHED_SECONDS = "total_finished_seconds";
        public static final String COLUMN_NAME_TOTAL_PASSED_DAYS = "total_passed_days";
        public static final String COLUMN_NAME_TIMER_STARTED = "timer_started";
        public static final String COLUMN_NAME_TIMER_PAUSED = "timer_paused";
        public static final String COLUMN_NAME_TIMER_SECONDS = "timer_seconds";
        public static final String COLUMN_NAME_TIMER_START_DATE = "timer_start_date";
    }
	
	public static abstract class RecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "record";
        public static final String COLUMN_NAME_PROJECT_ID = "project_id";
        public static final String COLUMN_NAME_RECORD_DATE = "date";
        public static final String COLUMN_NAME_RECORD_TIME_CONSUMING = "time_consuming";
    }
}

