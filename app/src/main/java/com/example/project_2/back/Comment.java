package com.example.project_2.back;

public class Comment {
    private int postId;
    private int id;
    private String name;
    private String mail;
    private String body;

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public int getPostId() {
        return postId;
    }

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public Comment(int postId, int id, String name, String mail, String body){
        this.body = body;
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.postId = postId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;
        Comment a = (Comment) obj;
        return a.id == id && a.postId == postId;
    }
}
