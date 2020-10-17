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

    public boolean getMark() {
        return mark == 1? true : false;
    }

    public void setMark(boolean mark) {
        this.mark = mark ? 1 : 0;
    }
}
