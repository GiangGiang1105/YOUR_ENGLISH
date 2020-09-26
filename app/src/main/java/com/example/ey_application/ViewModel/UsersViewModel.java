package com.example.ey_application.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ey_application.Model.Users;
import com.example.ey_application.database.Repository.UserRepository;

import java.util.List;

public class UsersViewModel extends ViewModel {
    private MutableLiveData<List<Users>> mutableLiveData;
    private UserRepository userRepository;
    public void init(){
        if (mutableLiveData != null){
            return;
        }
        try{
            userRepository= UserRepository.getInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<Users>> getListUser() {
        mutableLiveData = userRepository.getListUser();
        return mutableLiveData;
    }
    public void createUser(String email, String phone, String pass, String username, Application application){
        userRepository.createUser( email, phone, pass, username,application );
    }
    public void getUserLogin(String username, String email, String password, final Application application){
        userRepository.getUserLogin(username, email, password, application);
    }
}
