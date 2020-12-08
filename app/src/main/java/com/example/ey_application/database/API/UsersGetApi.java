package com.example.ey_application.database.API;

import androidx.lifecycle.LiveData;

import com.example.ey_application.Model.MessageFromServer;
import com.example.ey_application.Model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsersGetApi  {
    @GET("users.php")
    Call<List<Users>> getListUser();
    @FormUrlEncoded
    @POST("newUser.php")
    Call<MessageFromServer> createUser( @Field("email") String email, @Field("phone") String phone, @Field("password") String pass, @Field("userName") String username);
    @GET("userlogin.php")
    Call<MessageFromServer> getUserLogin(@Query("userName") String username, @Query("email")String email, @Query("password") String password);
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<MessageFromServer> updateUser(@Field("email") String email, @Field("phone") String phone, @Field("password") String pass, @Field("username") String username, @Field("id") int idUser);
}
