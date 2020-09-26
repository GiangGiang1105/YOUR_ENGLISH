package com.example.ey_application.Model.Word;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phonetics {
    @SerializedName("Id")
    @Expose
    int id;
    @SerializedName("text")
    @Expose
    String text;
    @SerializedName("audio")
    @Expose
    String audio;
    @SerializedName("wkey")
    @Expose
    String wkey;

    public Phonetics() {
    }

    public Phonetics(String text, String audio, String wkey) {
        this.text = text;
        this.audio = audio;
        this.wkey = wkey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getWkey() {
        return wkey;
    }

    public void setWkey(String wkey) {
        this.wkey = wkey;
    }
}
