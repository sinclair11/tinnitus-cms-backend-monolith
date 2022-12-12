package com.tinnitussounds.cms.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("admins")
public class Admin {
    @Id
    private String id;

    private String user;
    private String password;
    private String storagePreauth;

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoragePreauth() {
        return this.storagePreauth;
    }

    public void setStoragePreauth(String storagePreauth) {
        this.storagePreauth = storagePreauth;
    }

}
