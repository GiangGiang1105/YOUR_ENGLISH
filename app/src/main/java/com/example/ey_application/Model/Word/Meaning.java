package com.example.ey_application.Model.Word;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meaning {
    @SerializedName("noun")
    @Expose
    List<InforDetail> noun;
    @SerializedName("verb")
    @Expose
    List<InforDetail> verb;
    @SerializedName("transitive verb")
    @Expose
    List<InforDetail> transitive_verb;
    @SerializedName("adjective")
    @Expose
    List<InforDetail> adjective;
    @SerializedName("adverb")
    @Expose
    List<InforDetail> adverb;
    @SerializedName("phrase")
    @Expose
    List<InforDetail> phrase;
    @SerializedName("exclamation")
    @Expose
    List<InforDetail> exclamation;

    public Meaning(List<InforDetail> noun, List<InforDetail> verb, List<InforDetail> transitive_verb, List<InforDetail> adjective, List<InforDetail> adverb, List<InforDetail> phrase) {
        this.noun = noun;
        this.verb = verb;
        this.transitive_verb = transitive_verb;
        this.adjective = adjective;
        this.adverb = adverb;
        this.phrase = phrase;
    }

    public List<InforDetail> getNoun() {
        return noun;
    }

    public void setNoun(List<InforDetail> noun) {
        this.noun = noun;
    }

    public List<InforDetail> getVerb() {
        return verb;
    }

    public void setVerb(List<InforDetail> verb) {
        this.verb = verb;
    }

    public List<InforDetail> getTransitive_verb() {
        return transitive_verb;
    }

    public void setTransitive_verb(List<InforDetail> transitive_verb) {
        this.transitive_verb = transitive_verb;
    }

    public List<InforDetail> getAdjective() {
        return adjective;
    }

    public void setAdjective(List<InforDetail> adjective) {
        this.adjective = adjective;
    }

    public List<InforDetail> getAdverb() {
        return adverb;
    }

    public void setAdverb(List<InforDetail> adverb) {
        this.adverb = adverb;
    }

    public List<InforDetail> getPhrase() {
        return phrase;
    }

    public void setPhrase(List<InforDetail> phrase) {
        this.phrase = phrase;
    }
    public List<InforDetail> getExclamation() {
        return exclamation;
    }

    public void setExclamation(List<InforDetail> exclamation) {
        this.exclamation = exclamation;
    }

}
