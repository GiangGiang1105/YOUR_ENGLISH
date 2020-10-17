package com.example.ey_application.myinterface;

import com.example.ey_application.Model.Word.WordDictionary;

import java.util.List;

public interface Result {
    void onResult(boolean bool);
    void onResultData(boolean bool, List<WordDictionary> list);
}
