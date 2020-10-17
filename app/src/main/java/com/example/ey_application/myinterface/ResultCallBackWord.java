package com.example.ey_application.myinterface;

public interface ResultCallBackWord {
    void onDeleteResultFail(boolean bool);
    void onMarkResultFail(boolean bool);
    void onInsertResultFail(boolean bool);
    void onInsertReviewsResult(boolean bool);
    void onDeleteReviewsResult(boolean bool);
    void onDeleteListReviewsResult(boolean bool);
    void onInsertAllReviewsResult(boolean bool);
    void onDeleteAllWord(boolean bool);
}
