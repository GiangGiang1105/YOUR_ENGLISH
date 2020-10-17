package com.example.ey_application.database.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ey_application.Model.Word.WordDictionary;
import com.example.ey_application.database.DatabaseHelper;
import com.example.ey_application.myinterface.Result;


import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess extends AndroidViewModel{
    private DatabaseHelper openHelper;
    public MutableLiveData<List<WordDictionary>> listDictionary = new MutableLiveData<>();
    public DatabaseAccess(Application application) {
        super(application);
        this.openHelper = new DatabaseHelper(application, "eng_dictionary.db", 1);

        try {
            openHelper.createDatabase();
            openHelper.openDatabase();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getQuotes() {
        listDictionary = openHelper.getQuotes();
        openHelper.closeDataBase();
    }
    public void markWord(int id, int mark){
        openHelper.markWord(id, mark);
    }



}
