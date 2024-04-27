package com.example.module.shortvideo.Entity;

/**
 * @ClassName Vedio
 * @Description TODO
 * @Author JK_Wei
 * @Date 2024-03-31
 * @Version 1.0
 */

public class Video {
    public String id;
    public String name;
    public String url;
    public String intro;
    public boolean isLike;
    public boolean isCollect;

    public Video() {
    }

    public Video(String id, String name, String url, String intro, boolean isLike, boolean isCollect) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.intro = intro;
        this.isLike = isLike;
        this.isCollect = isCollect;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", intro='" + intro + '\'' +
                ", isLike=" + isLike +
                ", isCollect=" + isCollect +
                '}';
    }
}