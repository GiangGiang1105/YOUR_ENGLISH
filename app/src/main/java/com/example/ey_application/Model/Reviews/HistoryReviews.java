package com.example.ey_application.Model.Reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class HistoryReviews {
    @SerializedName("ID")
    @Expose
    int id;
    @SerializedName("id_wordreview")
    @Expose
    int idWordReview;
    @SerializedName("kingofreview")
    @Expose
    String kingOfReview;
    @SerializedName("codereviews")
    @Expose
    String codeReviews;
    @SerializedName("time")
    @Expose
    Date mTime;
    @SerializedName("truthorfail")
    @Expose
    boolean truthOrFail;

    public HistoryReviews(int idWordReview, String kingOfReview, String codeReviews, boolean truthOrFail) {
        this.idWordReview = idWordReview;
        this.kingOfReview = kingOfReview;
        this.codeReviews = codeReviews;
        this.truthOrFail = truthOrFail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdWordReview() {
        return idWordReview;
    }

    public void setIdWordReview(int idWordReview) {
        this.idWordReview = idWordReview;
    }

    public String getKingOfReview() {
        return kingOfReview;
    }

    public void setKingOfReview(String kingOfReview) {
        this.kingOfReview = kingOfReview;
    }

    public String getCodeReviews() {
        return codeReviews;
    }

    public void setCodeReviews(String codeReviews) {
        this.codeReviews = codeReviews;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public boolean isTruthOrFail() {
        return truthOrFail;
    }

    public void setTruthOrFail(boolean truthOrFail) {
        this.truthOrFail = truthOrFail;
    }
}
