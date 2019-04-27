package com.example.project_2.back;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;


public class StorageManager {
    private static StorageManager storageManager;
    private String filename = "myfile1.txt";
    FileInputStream fileInputStream;

    public static StorageManager getStorageManager() {
        if (storageManager == null)
            storageManager = new StorageManager();
        return storageManager;
    }

    public ArrayList<Post> loadPosts(SQLiteDatabase mydatabase){
        @SuppressLint("Recycle") Cursor resultSet = mydatabase.rawQuery("select * from Post;", null);

        ArrayList<Post> posts = new ArrayList<>();

        resultSet.moveToFirst();

        for (int i = 0; i < resultSet.getCount(); i++) {
            Post post = new Post(Integer.valueOf(resultSet.getString(0)), Integer.valueOf(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3));

            posts.add(post);

            resultSet.moveToNext();
        }

        return posts;
    }


    public void savePost(ArrayList<Post> posts, SQLiteDatabase mydatabase)
    {
        for (int i = 0; i < posts.size(); i++) {
            String userID = Integer.toString(posts.get(i).getUserID());
            String id = Integer.toString(posts.get(i).getId());
            String title = posts.get(i).getTitle();
            String body = posts.get(i).getBody();
            mydatabase.execSQL("delete from Post where id = " + id);
            String q = "INSERT INTO Post VALUES(" + userID + "," + id + ",\"" + title + "\",\"" + body + "\");";
            mydatabase.execSQL(q);
        }
    }

    private StorageManager(){

    }

    public ArrayList<Comment> loadComments(SQLiteDatabase mydatabase) {
        @SuppressLint("Recycle") Cursor resultSet = mydatabase.rawQuery("select * from Comment;", null);

        ArrayList<Comment> comments = new ArrayList<>();

        resultSet.moveToFirst();

        for (int i = 0; i < resultSet.getCount(); i++) {
            Comment comment = new Comment(Integer.valueOf(resultSet.getString(0)), Integer.valueOf(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));

            comments.add(comment);

            resultSet.moveToNext();
        }

        return comments;
    }

    public void saveComments(int pid, ArrayList<Comment> comments, SQLiteDatabase mydatabase) {
        for (int i = 0; i < comments.size(); i++) {
            String body = comments.get(i).getBody();
            String id = Integer.toString(comments.get(i).getId());
            String mail = comments.get(i).getMail();
            String name = comments.get(i).getName();
            mydatabase.execSQL("delete from Comment where (id = " + id + " and " + " postId = " + pid + ");");
            String q = "INSERT INTO Comment VALUES(" + pid + "," + id + ",\"" + name + "\",\"" + mail + "\",\"" + body + "\");";
            mydatabase.execSQL(q);
        }
    }
}
