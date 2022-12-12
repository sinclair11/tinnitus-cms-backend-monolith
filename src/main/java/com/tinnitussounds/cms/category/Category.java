package com.tinnitussounds.cms.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("categories")
public class Category {
    @Id
    private String id;

    private String name;
    private String description;
    private String color;
    private String type;


    public Category(String id, String name, String description, String color, String type) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.type = type;
    }

    public Category() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
