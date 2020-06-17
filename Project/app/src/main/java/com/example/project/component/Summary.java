package com.example.project.component;

public class Summary {
    public int imageId;
    public String username;
    public String date;
    public String title;
    public String content;

    public Summary(int imageId, String username, String date, String title, String content)
    {
        this.imageId = imageId;
        this.username= username;
        this.date = date;
        this.title = title;
        this.content = content;
    }

}
