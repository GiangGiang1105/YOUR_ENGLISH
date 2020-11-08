package com.example.ey_application.database.API;

import com.example.ey_application.Model.MessageFromServer;
import com.example.ey_application.Model.Reviews.Score;
import com.example.ey_application.Model.Reviews.WordReviews;
import com.example.ey_application.Model.Translate.HistoryTranslate;
import com.example.ey_application.Model.Word.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
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
    Call<MessageFromServer> deleteWord(@Field("id") int id,@Field("id_user") int id_user);
    @FormUrlEncoded
    @POST("markword.php")
    Call<MessageFromServer> markWord(@Field("id") int id, @Field("star") int boolStar);
    @GET("listwordreviews.php")
    Call<List<WordReviews>> listWordReviews(@Query("id_user") int id_user);
    @FormUrlEncoded
    @POST("insertwordreviews.php")
    Call<MessageFromServer> insertWordReviews(@Field("id_user") int id_user,@Field("word_reviews") String word,@Field("idWord") int idWord);
    @FormUrlEncoded
    @POST("deletewordreviews.php")
    Call<MessageFromServer> deleteWordReviews(@Field("word_reviews") String wordReviews, @Field("id_user") int id_user);
    @FormUrlEncoded
    @POST("deletelistreviews.php")
    Call<MessageFromServer> deleteListWordReviews(@Field("id_user") int id_user);
    @FormUrlEncoded
    @POST("unmarkword.php")
    Call<MessageFromServer> unMarkWord(@Field("id_user") int idUser);
    @FormUrlEncoded
    @POST("updatescore.php")
    Call<MessageFromServer> updateScore(@Field("IdUser") int IdUser,
                                         @Field("kingofreview") int  kingofreview, @Field("truth") int truth, @Field("fail") int fail);
    @GET("showscore.php")
    Call<List<Score>> showScore(@Query("IdUser") int idUser, @Query("kingofreview") int kingofreview);
    @FormUrlEncoded
    @POST("historytranslate.php")
    Call<MessageFromServer> createHistoryTranslate(@Field("Iduser") int idUser, @Field("text") String text, @Field("mean") String mean);
    @GET("showhistorytranslate.php")
    Call<List<HistoryTranslate>> showHistoryTranslate(@Query("Iduser") int idUser);
}
