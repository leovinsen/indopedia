package com.example.indopedia.Home.ArticleActivity;

/**
 * Created by asus on 07/12/2017.
 */

public class TouristDestination {

    private int photoId;
    private String title;
    private String description;

    public TouristDestination(int photoId, String title, String description) {
        this.photoId = photoId;
        this.title = title;
        this.description = description;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
