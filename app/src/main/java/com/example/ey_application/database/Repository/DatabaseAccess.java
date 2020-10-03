package com.example.ey_application.database.Repository;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ey_application.Model.Word.WordDictionary;
import com.example.ey_application.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess extends AndroidViewModel {
    private DatabaseHelper openHelper;
    public MutableLiveData<List<WordDictionary>> listDictionary = new MutableLiveData<>();
    List<WordDictionary> list = new ArrayList<>();
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
        list = openHelper.getQuotes();
        listDictionary.postValue(list);
        openHelper.closeDataBase();
    }
    public boolean markWord(int id, int mark){
        boolean bool = openHelper.markWord(id, mark);
        getQuotes();
        return bool;
    }
}
