package com.github.kongpf8848.rxhttp.sample.bean;

public class TopStory {

    private int id;
    private int type;
    private String title;
    private String image;
    private String ga_prefix;

    private String date;



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGaPrefix() {
        return ga_prefix;
    }

    public void setGaPrefix(String gaPrefix) {
        this.ga_prefix = gaPrefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
