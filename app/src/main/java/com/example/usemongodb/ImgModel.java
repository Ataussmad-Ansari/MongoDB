package com.example.usemongodb;

public class ImgModel {
    private String title;
    private String url;

    public ImgModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
