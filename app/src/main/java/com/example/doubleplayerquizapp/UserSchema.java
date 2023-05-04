package com.example.doubleplayerquizapp;

import java.util.ArrayList;
import java.util.List;

public class UserSchema {
    private String email;
    private List<String> friends;
    private List<Notification> notifications;

    public UserSchema() {

    }

    public UserSchema(String email) {
        this.email = email;
        this.friends = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}

class Notification{
    public String type;
    public String message;

    public Notification() {

    }

    public Notification(String type, String message) {
        this.type = type;
        this.message = message;
    }
}