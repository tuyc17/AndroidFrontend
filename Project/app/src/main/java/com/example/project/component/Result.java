package com.example.project.component;

public class Result {
    public int imageId;
    public String username;
    public String date;
    public String title;
    public String content;
    public int id;
    public int authorId;

    public Result(int imageId, String username, String date, String title, String content, int id, int authorId) {
        this.imageId = imageId;
        this.username = username;
        this.date = date;
        this.title = title;
        this.content = content;
        this.id = id;
        this.authorId = authorId;
    }

}
