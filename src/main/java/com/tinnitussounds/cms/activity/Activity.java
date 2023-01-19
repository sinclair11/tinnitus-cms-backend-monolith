package com.tinnitussounds.cms.activity;

import java.util.ArrayList;

public class Activity {
    protected String name;
    protected ArrayList<Likes> likes;
    protected ArrayList<Views> views;
    protected ArrayList<Reviews> reviews;
    protected long listened;

    public Activity() {
        likes = new ArrayList<>();
        views = new ArrayList<>();
        reviews = new ArrayList<>();
        listened = 0;
    }

    public ArrayList<Likes> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Likes> likes) {
        this.likes = likes;
    }

    public ArrayList<Views> getViews() {
        return views;
    }

    public void setViews(ArrayList<Views> views) {
        this.views = views;
    }

    public ArrayList<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Reviews> reviews) {
        this.reviews = reviews;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
