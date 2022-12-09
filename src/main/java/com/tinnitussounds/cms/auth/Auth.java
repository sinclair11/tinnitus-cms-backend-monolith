package com.tinnitussounds.cms.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("admins")
public class Auth {
    @Id
    private String id;

    private String user;
    private String password;

    public Auth(String id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

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
}
