package com.example.ey_application.database.Repository;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ey_application.Model.MessageFromServer;
import com.example.ey_application.Model.Users;
import com.example.ey_application.activity.MainActivity;
import com.example.ey_application.database.API.UsersGetApi;
import com.example.ey_application.database.RetrofitService;
import com.example.ey_application.session.SessionUser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class UserRepository {
   public interface resultUser{
        void resultUpdateUser(boolean bool);
    }
    private static UserRepository userRepository;
    private  resultUser resultUser;
    public static UserRepository getInstance(){
        if (userRepository == null){
            userRepository = new UserRepository();
        }
        return userRepository;
    }
    public void createResult(resultUser resultUser){
        this.resultUser = resultUser;
    }
    private UsersGetApi userApi;

    public UserRepository() {
        userApi = RetrofitService.createService(UsersGetApi.class);
    }
    public MutableLiveData<List<Users>> getListUser(){
        final MutableLiveData<List<Users>> userLogin = new MutableLiveData<>() ;
        try {
            userApi.getListUser().enqueue(new Callback<List<Users>>() {
                @Override
                public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                    if (response.isSuccessful()){
                        List<Users> list = response.body();
                        userLogin.setValue(list);
                    }
                }
                @Override
                public void onFailure(Call<List<Users>> call, Throwable t) {
                    t.printStackTrace();
                    userLogin.setValue(null);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return userLogin;
    }
    public MutableLiveData<MessageFromServer> getUserLogin(final String username, final  String email, String password, final Application application){
        final MutableLiveData<MessageFromServer> userLogin = new MutableLiveData<>() ;
        try {
            userApi.getUserLogin(username, email, password).enqueue(new Callback<MessageFromServer>() {
                @Override
                public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                    MessageFromServer messageFromServer = response.body();
                    if (messageFromServer.getStatus() == 1){
                        Toast.makeText(application, "" + messageFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(application, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK |FLAG_ACTIVITY_CLEAR_TASK);
                        application.startActivity(intent);
                        int id = messageFromServer.getID();
                        SessionUser sessionUser = new SessionUser(username, email,id, application);
                        sessionUser.getPreferences();
                        sessionUser.saveUser();
                    }
                    else {
                        Toast.makeText(application, "" + messageFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<MessageFromServer> call, Throwable t) {
                    t.printStackTrace();
                    userLogin.setValue(null);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return userLogin;
    }
    public void createUser( String email, String phone, String pass, String username,  final Application application){
        userApi.createUser( email, phone, pass, username).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                MessageFromServer messageFromServer = response.body();
                if (messageFromServer.getStatus() == 1){
                    Toast.makeText(application, "" + messageFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(application, "" + messageFromServer.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Toast.makeText(application, "Server error!", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void updateUser(String email, String phone, String pass, String username, int idUser){
            userApi.updateUser(email, phone, pass, username, idUser).enqueue(new Callback<MessageFromServer>() {
                @Override
                public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                    if (response.isSuccessful()){
                        resultUser.resultUpdateUser(true);
                    }
                    else{
                        resultUser.resultUpdateUser(false);
                    }
                }

                @Override
                public void onFailure(Call<MessageFromServer> call, Throwable t) {
                    resultUser.resultUpdateUser(false);
                }
            });
    }
}
