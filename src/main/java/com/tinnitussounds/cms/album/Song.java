package com.tinnitussounds.cms.album;

import org.bson.types.ObjectId;

public class Song {
    private ObjectId id;
    private String name;
    private String category;
    private String length;
    private Integer position;

    public Song() {
        id = new ObjectId();
    }
 
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

    public void setLength(String length) {
        this.length = length;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getLength() {
        return length;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

}
