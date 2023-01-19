package com.tinnitussounds.cms.activity;

import org.joda.time.DateTime;

public class Reviews {
    private String userId;
    private String userEmail;
    private String header;
    private String feedback;
    private DateTime date;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    public DateTime getDate() {
        return date;
    }
    public void setDate(DateTime date) {
        this.date = date;
    }
}
