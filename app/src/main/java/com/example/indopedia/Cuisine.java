package com.example.indopedia;

/**
 * Created by asus on 06/12/2017.
 */

public class Cuisine {

    private String name;
    private int photoId;

    public Cuisine(String name, int photoId){
        this.name = name;
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
