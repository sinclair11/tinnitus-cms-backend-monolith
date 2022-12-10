package com.tinnitussounds.cms.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {
    @Id
    private String id;

    private String email;
    private String password;
    private Object storagePreauth;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Object getStoragePreauth() {
        return this.storagePreauth;
    }

    public void setStoragePreauth(Object storagePreauth) {
        this.storagePreauth = storagePreauth;
    }


}
