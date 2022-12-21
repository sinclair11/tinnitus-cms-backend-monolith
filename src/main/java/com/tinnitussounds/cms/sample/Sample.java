package com.tinnitussounds.cms.sample;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("samples")
public class Sample {
    @Id
    private String id;

    private String name;
    private String category;
    private String description;
    private String[] tags;
    private String length;
    private int likes;
    private int favorites;
    private int views;
    private int reviews;
    private String uploadDate;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String[] getTags() {
        return tags;
    }
    public void setTags(String[] tags) {
        this.tags = tags;
    }
    public String getLength() {
        return length;
    }
    public void setLength(String duration) {
        this.length = duration;
    }
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getFavorites() {
        return favorites;
    }
    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }
    public int getViews() {
        return views;
    }
    public void setViews(int views) {
        this.views = views;
    }
    public int getReviews() {
        return reviews;
    }
    public void setReviews(int reviews) {
        this.reviews = reviews;
    }
    public String getUploadDate() {
        return uploadDate;
    }
    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }
}
