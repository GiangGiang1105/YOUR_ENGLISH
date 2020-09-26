package com.example.ey_application.ViewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.database.Repository.WordRepository;

import java.util.List;

public class WordViewModel extends ViewModel implements WordRepository.ResultCallback {
    public interface ResultCallBackWord{
        void onDeleteResultFail(boolean bool);
        void onMarkResultFail(boolean bool);
        void onInsertResultFail(boolean bool);
    }
    private ResultCallBackWord resultCallBackWord;
    private WordRepository wordReponsitory;
    public MutableLiveData<List<Word>> listWord = new MutableLiveData<>();

    public WordViewModel() {
        wordReponsitory = WordRepository.getInstance();
        wordReponsitory.setCallback(this);
    }
    public void setResultCallBackWord(ResultCallBackWord resultCallBackWord){
        this.resultCallBackWord = resultCallBackWord;
    }

    public void insertWord(int id_user, String word) {
        wordReponsitory.insertWord(id_user, word);
    }

    public void getListWord(int id_user) {
        wordReponsitory.getListWordFromApi(id_user);
    }
    public void deleteWord(int id, int id_user){
        wordReponsitory.deleteWord(id, id_user);
    }
    public void markWord(int id, int boolStar, int id_user){
        wordReponsitory.markWord(id, boolStar, id_user);
    }
    @Override
    public void onGetWordsResult(List<Word> data) {
        listWord.postValue(data);
    }

    @Override
    public void onInsertResult(boolean success, int userId) {
        if (success) getListWord(userId);
        else resultCallBackWord.onInsertResultFail(true);
    }

    @Override
    public void onDeleteResult(boolean success, int userId) {
        if (success) getListWord(userId);
        else resultCallBackWord.onDeleteResultFail(true);
    }

    @Override
    public void onMarkResult(boolean success, int userId) {
        if (success) getListWord(userId);
        else resultCallBackWord.onMarkResultFail(true);
    }
}