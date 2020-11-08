package com.example.ey_application.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ey_application.Model.Reviews.Score;
import com.example.ey_application.Model.Reviews.WordReviews;
import com.example.ey_application.Model.Translate.HistoryTranslate;
import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.database.Repository.WordRepository;
import com.example.ey_application.myinterface.ResultCallBackWord;
import com.example.ey_application.myinterface.ResultCallback;

import java.util.List;

public class WordViewModel extends ViewModel implements ResultCallback {
    private ResultCallBackWord resultCallBackWord;
    private WordRepository wordReponsitory;
    public MutableLiveData<List<Word>> listWord = new MutableLiveData<>();
    public MutableLiveData<List<Score>> listScore = new MutableLiveData<List<Score>>();
    public MutableLiveData<List<WordReviews>> listWordReviews = new MutableLiveData<>();
    public MutableLiveData<List<HistoryTranslate>> listHistoryTranslate = new MutableLiveData<>();
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
    /*public void deleteWord(int id, int id_user){
        wordReponsitory.deleteWord(id, id_user);
    }
    public void markWord(int id, int boolStar, int id_user){
        wordReponsitory.markWord(id, boolStar, id_user);
    }
   *//* public void insertWordReviews(int id_user, String word) {
        wordReponsitory.insertWordReviews(id_user, word);
    }*//*
    public void deleteWordReviews(int id_user, String word) {
        wordReponsitory.deleteWordReviews( word, id_user);
    }*/
    public void deleteListWordReviews(int id_user) {
        wordReponsitory.deleteListWordReviews(id_user);
        wordReponsitory.unMarkWord(id_user);
    }
   /* public void markAllWord(int idUser, List<Integer> listId){
        wordReponsitory.markAllWord(idUser, listId);
    }*/
    public void getListWordReviews(int id_user){
        wordReponsitory.getListWordReviews(id_user);
    }
    public void insertAllWord(int idUser, List<String> listWord, List<Integer> listId){
        wordReponsitory.insertAllWord(idUser, listWord, listId);
    }
    public void deleteAllWord(int idUser, List<Integer> listId){
        wordReponsitory.deleteAllWord(idUser, listId);
    }
    public void updateScore(final int IdUser, final int  kingofreview, int truth, int fail){
        wordReponsitory.updateScore( IdUser,  kingofreview, truth, fail);
    }
    public void showScore(int idUser,int kingofreview){
        wordReponsitory. showScore(idUser, kingofreview);
    }
    public void createHistoryTranslate(int idUser, String text, String mean){
        wordReponsitory.createHistoryTranslate(idUser, text, mean);
    }
    public void showHistoryTranslate(int idUser){
        wordReponsitory.showHistoryTranslate(idUser);
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
    @Override
    public void onInsertReviewsResult(boolean success, int userId) {
        if (success) {
            getListWord(userId);
            resultCallBackWord.onInsertReviewsResult(true);
        }
    }

    @Override
    public void onDeleteReviewsResult(boolean success, int userId) {
        if (success) {
            getListWord(userId);
            resultCallBackWord.onDeleteReviewsResult(true);
        }
    }

    @Override
    public void onDeleteListReviewsResult(boolean success, int userId) {
        if (success){
            getListWordReviews(userId);
            resultCallBackWord.onDeleteListReviewsResult(true);
        }
    }

    @Override
    public void onGetWordsReviews(List<WordReviews> list) {
        listWordReviews.postValue(list);
    }

    @Override
    public void onInsertAllReviewsResult(boolean bool, int id) {
        if (bool){
            getListWord(id);
            resultCallBackWord.onInsertAllReviewsResult(true);
        }
    }

    @Override
    public void onDeleteAllWord(boolean bool, int userId) {
        if (bool){
            getListWord(userId);
            resultCallBackWord.onDeleteAllWord(true);
        }
    }

    @Override
    public void onShowCore(List<Score> scores) {
        if (scores != null){
            listScore.postValue(scores);
        }
    }

    @Override
    public void onUpdateScore(boolean bool,  int idUser, int kingofreview) {
        if (bool){
            showScore(idUser,kingofreview);
        }
    }

    @Override
    public void onResultCreateHistoryTranslate(boolean bool, int idUser) {
        if (bool){
            showHistoryTranslate(idUser);
        }
    }

    @Override
    public void onShowHistoryTranslate(List<HistoryTranslate> historyTranslates) {
        listHistoryTranslate.postValue(historyTranslates);
    }
}