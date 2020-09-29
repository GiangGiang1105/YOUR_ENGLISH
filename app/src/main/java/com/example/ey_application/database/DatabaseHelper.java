package com.example.ey_application.database;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public DatabaseHelper( Context context,  String name, int version) {
        super(context, name, null, version);
        this.dbName = name;
        this.context = context;
        this.dbPath = context.getApplicationInfo().dataDir+"/databases/";
        Log.i("path", dbPath);
    }
/*
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void CheckDb(){
        Log.i("CheckDb", "CheckDb");
        SQLiteDatabase checkDb =  null;
        String filePath = dbPath + dbName;
        try {

            checkDb = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (checkDb != null){
            Log.i("Database already exists", "Database already exists");
        }
        else{
            CopyDatabase();
            checkDb.close();
        }
    }
    public void CopyDatabase(){
        try {
            InputStream ios = context.getAssets().open("databases/"+dbName);
            Log.i("pathName", dbPath + dbName);
            OutputStream os = new FileOutputStream(dbPath + dbName);
            byte[] buffer = new byte[1024];
            int len;
            while((len = ios.read(buffer)) > 0){
                os.write(buffer, 0, len);
                Log.d("copyDb", " Database copied");
            }
            os.flush();
            ios.close();
            os.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void OpenDatabase(){
        Log.d("OpenDatabase", " OpenDatabase");
        String filePath = dbPath + dbName;
        database = SQLiteDatabase.openDatabase(filePath, null, SQLiteDatabase.OPEN_READWRITE);

    }*/
    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM words", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return list;
    }
    public void createDatabase() throws IOException
    {

        boolean dbExist = checkDataBase();

        if(dbExist)
        {
            Log.v("DB Exists", "db exists");
            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
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
