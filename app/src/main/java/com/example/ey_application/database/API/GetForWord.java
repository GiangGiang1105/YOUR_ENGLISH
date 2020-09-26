package com.example.ey_application.database.API;

import com.example.ey_application.Model.MessageFromServer;
import com.example.ey_application.Model.Word.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetForWord {
    @FormUrlEncoded
    @POST("insertword.php")
    Call<MessageFromServer> insertWord(@Field("id_user") int id_user,@Field("word") String word);
    @GET("listword.php")
    Call<List<Word>> listWord(@Query("id_user") int id_user);
    @FormUrlEncoded
    @POST("delete.php")
    Call<MessageFromServer> deleteWord(@Field("id") int id);
    @FormUrlEncoded
    @POST("markword.php")
    Call<MessageFromServer> markWord(@Field("id") int id, @Field("star") int boolStar);
}
