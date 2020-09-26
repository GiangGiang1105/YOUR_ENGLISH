package com.example.ey_application.Model.Word;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InforDetail {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("definition")
    @Expose
    String difinition;
    @SerializedName("example")
    @Expose
    String example;
    @SerializedName("synonyms")
    @Expose
    List<String> synonym;


    public InforDetail() {
    }

    public InforDetail(String difinition, String example, List<String>  synonym) {
        this.difinition = difinition;
        this.example = example;
        this.synonym = synonym;
    }

    public List<String> getSynonym() {
        return synonym;
    }

    public void setSynonym(List<String> synonym) {
        this.synonym = synonym;
    }

    public int getId() {
        return id;
    }

    public String getDifinition() {
        return difinition;
    }

    public void setDifinition(String difinition) {
        this.difinition = difinition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }


    public void setId(int id) {
        this.id = id;
    }
}
