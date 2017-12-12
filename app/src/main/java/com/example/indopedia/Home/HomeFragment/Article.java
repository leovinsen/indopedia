package com.example.indopedia.Home.HomeFragment;

/**
 * Created by asus on 12/11/2017.
 */

public class Article {

    private String title;
    private String content;
    private int photoId;

    public Article(String title, String content, int photoId){
        this.title = title;
        this.content =content;
        this.photoId = photoId;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent(){
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getPhotoId(){
        return this.photoId;
    }
    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
