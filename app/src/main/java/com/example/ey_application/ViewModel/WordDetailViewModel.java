package com.example.ey_application.ViewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.ey_application.Model.Word.DataViewPager;
import com.example.ey_application.Model.Word.DataWord;
import com.example.ey_application.Model.Word.Detail;
import com.example.ey_application.Model.Word.InforWord;
import com.example.ey_application.Model.Word.Phonetics;
import com.example.ey_application.database.Repository.WordDetailReponsitory;

import java.util.HashMap;
import java.util.List;

public class WordDetailViewModel extends ViewModel implements WordDetailReponsitory.WordDetailResult {
    private WordDetailReponsitory wordReponsitory;
    public MutableLiveData<DataWord> dataWordMutableLiveData = new MutableLiveData<>();
    public void init(){
        wordReponsitory = WordDetailReponsitory.getInstance((WordDetailReponsitory.WordDetailResult) this);

    }
    public MutableLiveData<DataWord>  getWord(String word){
        dataWordMutableLiveData = wordReponsitory.getWord(word);
        return dataWordMutableLiveData;
    }
    @Override
    public void onResult(DataWord dataWord) {
         dataWordMutableLiveData.postValue(dataWord);
    }
}
