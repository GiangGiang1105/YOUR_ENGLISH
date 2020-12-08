package com.example.ey_application.ViewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ey_application.Model.Users;
import com.example.ey_application.database.Repository.UserRepository;

import java.util.List;

public class UsersViewModel extends ViewModel implements UserRepository.resultUser {
    private MutableLiveData<List<Users>> mutableLiveData;
    private UserRepository userRepository;
    private ResultUser resultUser;
    public void init(){
        if (mutableLiveData != null){
            return;
        }
        try{
            userRepository= UserRepository.getInstance();
            userRepository.createResult((UserRepository.resultUser) this);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setResultUser(ResultUser resultUser){
        this.resultUser = resultUser;
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
    public void updateUser(String email, String phone, String pass, String username, int idUser){
        userRepository.updateUser(email, phone, pass, username, idUser);
    }
    public interface ResultUser{
        void resultUpdateUser(boolean bool);
    }
    @Override
    public void resultUpdateUser(boolean bool) {
        if (bool){
            resultUser.resultUpdateUser(bool);
        }
    }
}
