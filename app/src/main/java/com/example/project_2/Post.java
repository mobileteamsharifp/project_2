package com.example.project_2;

import java.util.ArrayList;

public class Post {

    private int userID;
    private int id;
    private ArrayList<Comment> comments;
    private String title;
    private String body;


    public void addComment(Comment comment){
        comments.add(comment);
    }

    public String getBody() {
        return body;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int getUserID() {
        return userID;
    }

    public String getTitle() {
        return title;
    }

    public Post(int userID, int id, String title, String body){
        comments = new ArrayList<>();
        this.body = body;
        this.id = id;
        this.title = title;
        this.userID = userID;
    }

}

