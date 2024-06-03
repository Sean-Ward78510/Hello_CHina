package com.example.tool.Entity;

public class Fruit {
    private int image;
    private String name;
    private String url;


    public Fruit(String name, int image, String url) {
        this.image = image;
        this.name = name;
        this.url = url;
    }

    public int getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
