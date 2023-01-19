package com.tinnitussounds.cms.album;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("albums")
public class Album {
    @Id
    private String id;

    private String name;
    private String category;
    private String description;
    private String length;
    private String[] tags;
    private ArrayList<Song> songs;
    private int totalSongs;
    private String uploadDate;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLength() {
        return this.length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public int getTotalSongs() {
        return this.totalSongs;
    }

    public void setTotalSongs(int totalSongs) {
        this.totalSongs = totalSongs;
    }

    public String getUploadDate() {
        return this.uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public ArrayList<Song> getSongs() {
        return this.songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

}
