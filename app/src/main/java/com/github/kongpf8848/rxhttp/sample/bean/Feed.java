
package com.github.kongpf8848.rxhttp.sample.bean;


import java.util.List;


public class Feed {

    private String date;
    private List<Story> stories ;
    private List<TopStory> top_stories ;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<TopStory> getTopStories() {
        return top_stories;
    }

    public void setTopStories(List<TopStory> topStories) {
        this.top_stories = topStories;
    }


}
