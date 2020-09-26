package com.example.ey_application.Model.Word;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Synonyms {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("synonyms")
    @Expose
    String synonyms;
    @SerializedName("case")
    @Expose
    String category;
    @SerializedName("wkey")
    @Expose
    String wkey;

    public Synonyms(String synonyms, String category, String wkey) {
        this.synonyms = synonyms;
        this.category = category;
        this.wkey = wkey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getWkey() {
        return wkey;
    }

    public void setWkey(String wkey) {
        this.wkey = wkey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
