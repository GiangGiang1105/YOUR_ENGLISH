package com.example.ey_application.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ey_application.activity.Login_Register;


public class SessionUser {
    private SharedPreferences sharedPreferences;
    private final String MyPREFERENCES = "MyAccount" ;
    private  final String Name = "nameKey";
    private final String Email = "emailKey";
    private final String pathPicture = "pathPicture";
    private final String ID = "ID";
    private final String BOOL = "BOOL_STATUS";
    private String data_name;
    private String data_email;

    private int id;

    private Context context;

    public SessionUser(Context context) {
        this.context = context;
    }

    public SessionUser(String data_name, String email, int ID, Context context) {
        this.data_name = data_name;
        this.data_email = email;
        this.context = context;
        this.id = ID;
    }
    public SessionUser(String data_name, String email, int ID) {
        this.data_name = data_name;
        this.data_email = email;
        this.context = context;
        this.id = ID;
    }
    public void getPreferences(){
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    public void updateData(String data_name, String email, int ID){
        this.data_name = data_name;
        this.data_email = email;
        this.id = ID;
    }
    public void saveUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(Name, data_name);
        editor.putString(Email, data_email);
        editor.putInt(ID, id);
        editor.putBoolean(BOOL, true);
        editor.commit();

    }
    public void savePathPicture(String path_picture){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(pathPicture, path_picture);
        editor.commit();
    }
    public void logout(){
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, Login_Register.class);
        context.startActivity(intent);
    }
    public boolean getLoggedStatus() {
        return sharedPreferences.getBoolean(BOOL, false);
    }
    public int getLoggedID() {
        return sharedPreferences.getInt(ID, 0);
    }
    public String getLoggedName() {
        return sharedPreferences.getString(Name, "");
    }
    public String getLoggedEmail() {
        return sharedPreferences.getString(Email, "");
    }
    public String getPathPicture(){
        return sharedPreferences.getString(pathPicture, "");
    }
}
