package com.example.ey_application.database.API;

import com.example.ey_application.Model.Word.InforWord;
import com.example.ey_application.Model.Word.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetInforWordAPI {
    @GET("en/{word}")
    Call<List<InforWord>> getWord(@Path("word") String word);
}
