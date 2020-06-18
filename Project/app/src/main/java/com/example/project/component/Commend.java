package com.example.project.component;

public class Commend {
    public int ImageId;
    public String username;
    public String date;
    public String content;

    public Commend(int ImageId, String username, String date, String content) {
        this.ImageId = ImageId;
        this.username = username;
        this.date = date;
        this.content = content;
    }
}
