package com.example.ey_application.Model.Word;

import androidx.annotation.StringDef;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Word implements Serializable {
    @SerializedName("ID")
    @Expose
    int id;
    @SerializedName("id_user")
    @Expose
    int id_user ;
    @SerializedName("word")
    @Expose
    String word;
    @SerializedName("time")
    @Expose
    Date time;
    @SerializedName("star")
    @Expose
    int star;
    public Word() {

    }

    public Word(int id_user, String word) {
        this.id_user = id_user;
        this.word = word;
    }


    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }
}
