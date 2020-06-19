package com.example.project.component;

public class Message {
    public String content;

    //0代表收到，1代表发出
    public int type;
    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

}
