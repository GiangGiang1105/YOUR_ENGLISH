package com.example.ey_application.Model.Translate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryTranslate {
    @SerializedName("ID")
    @Expose
    int ID;
    @SerializedName("iduser")
    @Expose
    int idUser;
    @SerializedName("text")
    @Expose
    String text;
    @SerializedName("mean")
    @Expose
    String mean;
    @SerializedName("star")
    @Expose
    boolean star;

    public HistoryTranslate(int ID, int idUser, String text, String mean, boolean star) {
        this.ID = ID;
        this.idUser = idUser;
        this.text = text;
        this.mean = mean;
        this.star = star;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }
}
