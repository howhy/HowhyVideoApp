package com.howhy.video.model;

import java.io.Serializable;

public class VideoDetail  implements Serializable {
    private Long vod_id;
    private String vod_name;
    private String vod_director;
    private String vod_actor;
    private String type_name;
    private String vod_area;
    private String vod_lang;
    private String vod_year;
    private String vod_pic;
    private String vod_content;
    private String vod_play_url;
    private String vod_time;
    public Long getVod_id() {
        return vod_id;
    }

    public void setVod_id(Long vod_id) {
        this.vod_id = vod_id;
    }
    public String getVod_name() {
        return vod_name;
    }

    public void setVod_name(String vod_name) {
        this.vod_name = vod_name;
    }

    public String getVod_director() {
        return vod_director;
    }

    public void setVod_director(String vod_director) {
        this.vod_director = vod_director;
    }

    public String getVod_actor() {
        return vod_actor;
    }

    public void setVod_actor(String vod_actor) {
        this.vod_actor = vod_actor;
    }

    public String getVod_time() {
        return vod_time;
    }

    public void setVod_time(String vod_time) {
        this.vod_time = vod_time;
    }

    public String getVod_area() {
        return vod_area;
    }

    public void setVod_area(String vod_area) {
        this.vod_area = vod_area;
    }

    public String getVod_lang() {
        return vod_lang;
    }

    public void setVod_lang(String vod_lang) {
        this.vod_lang = vod_lang;
    }

    public String getVod_year() {
        return vod_year;
    }

    public void setVod_year(String vod_year) {
        this.vod_year = vod_year;
    }

    public String getVod_pic() {
        return vod_pic;
    }

    public void setVod_pic(String vod_pic) {
        this.vod_pic = vod_pic;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getVod_content() {
        return vod_content;
    }

    public void setVod_content(String vod_content) {
        this.vod_content = vod_content;
    }

    public String getVod_play_url() {
        return vod_play_url;
    }

    public void setVod_play_url(String vod_play_url) {
        this.vod_play_url = vod_play_url;
    }

    @Override
    public String toString() {
        return "VideoDetail{" +
                "vod_name='" + vod_name + '\'' +
                ", vod_director='" + vod_director + '\'' +
                ", vod_actor='" + vod_actor + '\'' +
                ", vod_class='" + type_name + '\'' +
                ", vod_area='" + vod_area + '\'' +
                ", vod_lang='" + vod_lang + '\'' +
                ", vod_year='" + vod_year + '\'' +
                ", vod_content='" + vod_content + '\'' +
                ", vod_play_url='" + vod_play_url + '\'' +
                '}';
    }
}
