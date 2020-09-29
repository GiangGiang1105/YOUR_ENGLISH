package com.example.ey_application.database.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.ey_application.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private DatabaseHelper openHelper;
    private static DatabaseAccess instance;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context, "eng_dictionary.db", 1);
       try {
           openHelper.createDatabase();
       }
       catch (Exception e){
           e.printStackTrace();
       }
       try{
           openHelper.openDatabase();

       }
       catch (Exception e){
           e.printStackTrace();
       }
    }


    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    public List<String> getQuotes() {
        List<String> list = openHelper.getQuotes();
        openHelper.closeDataBase();
        return list;
    }
}
