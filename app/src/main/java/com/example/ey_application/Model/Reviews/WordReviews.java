package com.example.ey_application.Model.Reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class WordReviews {
    @SerializedName("ID")
    @Expose
    int id;
    @SerializedName("id_user")
    @Expose
    int idUser;
    @SerializedName("word_reviews")
    @Expose
    String wordReviews;
    @SerializedName("time")
    @Expose
    Date date;
    @SerializedName("bool")
    @Expose
    boolean bool;
    public WordReviews(int idUser, String wordReviews) {
        this.idUser = idUser;
        this.wordReviews = wordReviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getWordReviews() {
        return wordReviews;
    }

    public void setWordReviews(String wordReviews) {
        this.wordReviews = wordReviews;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
