package com.example.indopedia.PhrasesMenu;

/**
 * Created by Velcone on 12/13/2017.
 */

public class Levels {

    private String levels;
    private int imageID;


    public Levels(String levels, int imageID) {
        this.levels = levels;
        this.imageID = imageID;
    }

    public String getLevels() {
        return this.levels;
    }

    public void setLevels(String levels) {
        this.levels = levels;
    }

    public int getImageID() {
        return this.imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
