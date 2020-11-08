package com.example.ey_application.myinterface;

import com.example.ey_application.Model.Reviews.Score;
import com.example.ey_application.Model.Reviews.WordReviews;
import com.example.ey_application.Model.Translate.HistoryTranslate;
import com.example.ey_application.Model.Word.Word;

import java.util.List;

public interface  ResultCallback {
    void onGetWordsResult(List<Word> data);
    void onInsertResult(boolean success, int userId);
    void onDeleteResult(boolean success, int userId);
    void onMarkResult(boolean success, int userId);
    void onInsertReviewsResult(boolean success, int userId);
    void onDeleteReviewsResult(boolean success, int userId);
    void onDeleteListReviewsResult(boolean success, int userId);
    void onGetWordsReviews(List<WordReviews> list);
    void onInsertAllReviewsResult(boolean bool, int userId);
    void onDeleteAllWord(boolean bool, int userId);
    void onShowCore(List<Score> scores);
    void onUpdateScore(boolean bool, int idUser, int kingofreview);
    void onResultCreateHistoryTranslate(boolean bool, int idUser);
    void onShowHistoryTranslate(List<HistoryTranslate> historyTranslates);

}
