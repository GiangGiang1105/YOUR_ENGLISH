package com.example.ey_application.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ey_application.Model.Word.WordDictionary;
import com.example.ey_application.myinterface.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    String dbName;
    String dbPath;
    SQLiteDatabase database;
    Result result;
    public DatabaseHelper( Context context,  String name, int version) {
        super(context, name, null, version);
        this.dbName = name;
        this.context = context;
        this.dbPath = context.getApplicationInfo().dataDir+"/databases/";
    }

    public MutableLiveData<List<WordDictionary>> getQuotes() {
        MutableLiveData<List<WordDictionary>> listMutableLiveData = new MutableLiveData<>();
        List<WordDictionary> list = new ArrayList<>();
        openDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM words", null);
        cursor.moveToFirst();
        if (cursor != null){
            while (!cursor.isAfterLast()) {
                list.add(new WordDictionary(cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));
                cursor.moveToNext();
            }

        }
        cursor.close();
        listMutableLiveData.postValue(list);
        return listMutableLiveData;
    }
    public void markWord(int id, int mark){
        ContentValues values = new ContentValues();
        values.put("mark", mark);
        openDatabase();
        int rs = database.update("words", values, "id_ = ?", new String[]{String.valueOf(id)});

        if (rs == 0){
            database.close();
            Log.i("update", "unsuccesss");
        }
        else{
            database.close();
            Log.i("update", "successs" + mark);
        }
    }
    public void createDatabase()
    {

        boolean dbExist = checkDataBase();

        if(dbExist)
        {
            Log.v("DB Exists", "db exists");
        }

        boolean dbExist1 = checkDataBase();
        if(!dbExist1)
        {
            this.getReadableDatabase();
            try
            {
                this.close();
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error("Error copying database");
            }
        }

    }
    //Check database already exist or not
    private boolean checkDataBase()
    {
        boolean checkDB = false;
        try
        {
            String myPath = dbPath + dbName;
            File dbfile = new File(myPath);
            checkDB = dbfile.exists();
        }
        catch(SQLiteException e)
        {
            e.printStackTrace();
        }

        return checkDB;
    }
    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() throws IOException
    {

        InputStream mInput= context.getAssets().open("databases/"+dbName);
        Log.i("pathName", dbPath + dbName);
        OutputStream mOutput = new FileOutputStream(dbPath + dbName);
        byte[] mBuffer = new byte[2024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    //delete database
    public void db_delete()
    {
        File file = new File(dbPath+ dbName);
        if(file.exists())
        {
            file.delete();
            System.out.println("delete database file.");
        }
    }
    //Open database
    public void openDatabase() throws SQLException
    {
        String myPath =dbPath+ dbName;
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void closeDataBase()throws SQLException
    {
        if(database != null)
            database.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
        {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }

    }

}
