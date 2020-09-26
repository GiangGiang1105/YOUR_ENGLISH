package com.example.ey_application.database.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.ey_application.Model.Word.Detail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Translate {
    public Translate(){
    }

    public MutableLiveData<String> translate(String text){
        final MutableLiveData<String> translate = new MutableLiveData<>();
        final TranslateAPI translateAPI = new TranslateAPI(Language.ENGLISH, Language.VIETNAMESE, text);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String s) {
                translate.postValue(s);
            }

            @Override
            public void onFailure(String s) {
                Log.i("Error translate ", s);
            }
        });
        return translate;

    }

}

