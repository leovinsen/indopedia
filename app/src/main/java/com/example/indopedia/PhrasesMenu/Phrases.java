package com.example.indopedia.PhrasesMenu;

/**
 * Created by Velcone on 12/5/2017.
 */

public class Phrases {
    private String indoWords;
    private String engWords;
    private int audioID;


    public Phrases(String indoWords, String engWords, int audioID) {
        this.indoWords = indoWords;
        this.engWords = engWords;
        this.audioID = audioID;
    }

    public String getIndoWords() {
        return this.indoWords;
    }

    public void setIndoWords(String indoWords) {
        this.indoWords = indoWords;
    }

    public String getEngWords() {
        return this.engWords;
    }

    public void setEngWords(String engWords) {
        this.engWords = engWords;
    }

    public int getAudioID() {
        return this.audioID;
    }

    public void setAudioID(int audioID) {
        this.audioID = audioID;
    }
}
