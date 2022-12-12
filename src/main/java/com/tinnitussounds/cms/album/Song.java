package com.tinnitussounds.cms.album;

public class Song {
    private String name;
    private String category;
    private Integer favorites;
    private String length;
    private Integer likes;
    private Integer position;
    private Integer views;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getFavorites() {
        return this.favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Integer getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

}
