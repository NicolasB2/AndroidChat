package com.example.chatandroid.model;

public class Message {

    public static final int TYPE_TEXT=0;
    public static final int TYPE_IMAGE=1;

    private int type;
    private String id;
    private String body;
    private String userId;
    private Long timeStamp;

    public Message(int type, String id, String body, String userId, Long timeStamp) {
        this.type = type;
        this.id = id;
        this.body = body;
        this.userId = userId;
        this.timeStamp = timeStamp;
    }

    public Message() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
