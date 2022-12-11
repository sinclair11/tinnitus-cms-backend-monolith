package com.tinnitussounds.cms.category;

import java.util.List;

public class CategoryPack {
    private List<Category> albumCategories;
    private List<Category> presetCategories;
    private List<Category> sampleCategories;


    public CategoryPack(List<Category> albumCategories, List<Category> presetCategories, List<Category> sampleCategories) {
        this.albumCategories = albumCategories;
        this.presetCategories = presetCategories;
        this.sampleCategories = sampleCategories;
    }

    public List<Category> getAlbumCategories() {
        return this.albumCategories;
    }

    public void setAlbumCategories(List<Category> albumCategories) {
        this.albumCategories = albumCategories;
    }

    public List<Category> getPresetCategories() {
        return this.presetCategories;
    }

    public void setPresetCategories(List<Category> presetCategories) {
        this.presetCategories = presetCategories;
    }

    public List<Category> getSampleCategories() {
        return this.sampleCategories;
    }

    public void setSampleCategories(List<Category> sampleCategories) {
        this.sampleCategories = sampleCategories;
    }
    
}
