package com.will.sxlib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by will on 2017/2/18.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "database.db";
    private static final int VERSION = 1;
    private static final String HISTORY_TABLE_CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS " + SearchHistoryEntry.TABLE_NAME +
            " (" + SearchHistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SearchHistoryEntry.COLUMN_TITLE + " TEXT)";


    public SQLiteHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(HISTORY_TABLE_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public static class SearchHistoryEntry{
        public static final String TABLE_NAME = "history";
        public static final String COLUMN_TITLE = "history_title";
        public static final String _ID = "_id";
    }
}
