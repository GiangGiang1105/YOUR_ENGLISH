package com.example.ey_application.Model.Reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Score {
    @SerializedName("ID")
    @Expose
    int id;
    @SerializedName("IdUser")
    @Expose
    int IdUser;
    @SerializedName("kingofreview")
    @Expose
    int kingOfReview;
    @SerializedName("truth")
    @Expose
   int truth;
    @SerializedName("fail")
    @Expose
    int fail;
        @SerializedName("time")
    @Expose
    Date date;
    public Score(int idUser, int kingOfReview, int truth, int fail) {
        this.IdUser = idUser;
        this.kingOfReview = kingOfReview;
        this.truth = truth;
        this.fail = fail;
    }

    public int getIdUser() {
        return IdUser;
    }

    public void setIdUser(int idUser) {
        IdUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKingOfReview() {
        return kingOfReview;
    }

    public void setKingOfReview(int kingOfReview) {
        this.kingOfReview = kingOfReview;
    }

    public int getTruth() {
        return truth;
    }

    public void setTruth(int truth) {
        this.truth = truth;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
