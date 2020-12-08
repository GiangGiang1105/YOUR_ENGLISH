package com.example.ey_application.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {
    @SerializedName("ID")
    @Expose
    int ID;
    @Expose
    @SerializedName("email")
    String Email;
    @Expose
   @SerializedName("phone")
    String Phone;
    @Expose
    @SerializedName("password")
    String Password;
    @Expose
    @SerializedName("userName")
    String UserName;
    public Users(){

    }
    public Users(int ID, String email, String phone, String password, String userName) {
        this.ID = ID;
        Email = email;
        Phone = phone;
        Password = password;
        UserName = userName;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }



}
