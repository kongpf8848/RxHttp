
package com.github.kongpf8848.rxhttp.sample.bean;


import java.util.List;


public class Story {

    private int id;
    private int type;
    private String title;
    private List<String> images;
    private String ga_prefix;

    private String date;
    private boolean multipic;


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }
}
