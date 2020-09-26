package com.example.ey_application.Model.Word;

import java.util.List;

public class DataWord {
    List<Detail> definition;
    List<Detail> example;
    Phonetics phonetics;

    public DataWord(List<Detail> definition, List<Detail> example, Phonetics phonetics, List<Detail> synonyms) {
        this.definition = definition;
        this.example = example;
        this.phonetics = phonetics;
        this.synonyms = synonyms;
    }

    public List<Detail> getDefinition() {
        return definition;
    }

    public void setDefinition(List<Detail> definition) {
        this.definition = definition;
    }

    public List<Detail> getExample() {
        return example;
    }

    public void setExample(List<Detail> example) {
        this.example = example;
    }

    public Phonetics getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(Phonetics phonetics) {
        this.phonetics = phonetics;
    }

    public List<Detail> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<Detail> synonyms) {
        this.synonyms = synonyms;
    }

    List<Detail> synonyms;
}
