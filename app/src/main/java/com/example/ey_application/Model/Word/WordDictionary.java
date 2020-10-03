package com.example.ey_application.Model.Word;

public class WordDictionary {
    String word;
    int mark;
    int id;

    public WordDictionary(String word, int mark, int id) {
        this.word = word;
        this.mark = mark;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
