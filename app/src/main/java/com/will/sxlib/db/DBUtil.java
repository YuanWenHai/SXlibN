package com.will.sxlib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.will.sxlib.db.SQLiteHelper.SearchHistoryEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by will on 2017/2/18.
 */

public class DBUtil {
    private static DBUtil instance;
    private static SQLiteDatabase db;

    private DBUtil(Context context){
        db = new SQLiteHelper(context).getWritableDatabase();
    }

    public static DBUtil getInstance(Context context){
        if(instance == null){
            synchronized(DBUtil.class){
                if(instance == null){
                    instance = new DBUtil(context);
                }
            }
        }
        return instance;
    }
    public List<String> getSearchHistoryFromDB(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + SearchHistoryEntry.TABLE_NAME + " order by " + SearchHistoryEntry._ID + " asc",null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex(SearchHistoryEntry.COLUMN_TITLE)));
        }
        cursor.close();
        return list;
    }

    /**
     * 因为只需要五条历史数据，所以此操作会删除之前的数据
     * @param list list
     */
    public void insertSearchHistoryToDB(List<String> list){
        deleteTable(SearchHistoryEntry.TABLE_NAME);
        ContentValues values;
        for(String item : list){
            values = new ContentValues();
            values.put(SearchHistoryEntry.COLUMN_TITLE,item);
            db.insert(SearchHistoryEntry.TABLE_NAME,null,values);
        }
    }

    public void deleteTable(String tableName){
        db.delete(tableName,null,null);
    }

    public static void onDestroy(){
        if(instance != null){
            if(db != null){
                db.close();
            }
            instance = null;
        }
    }
}
