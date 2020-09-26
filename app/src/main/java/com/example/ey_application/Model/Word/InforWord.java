package com.example.ey_application.Model.Word;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InforWord {
    @SerializedName("word")
    @Expose
    String word;
    @SerializedName("phonetics")
    @Expose
    List<Phonetics> phonetics;
    @SerializedName("meaning")
    @Expose
    Meaning meaning;
    @SerializedName("message")
    @Expose
   String message;
    public InforWord(String word, List<Phonetics> phonetics, Meaning meaning, String message) {
        this.word = word;
        this.phonetics = phonetics;
        this.meaning = meaning;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }

    public Meaning getMeaning() {
        return meaning;
    }

    public void setMeaning(Meaning meaning) {
        this.meaning = meaning;
    }
}
