package com.example.project_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


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
            mydatabase.execSQL("INSERT INTO Post VALUES(" + userID + id + title + body + ");");
        }
    }

    private StorageManager(){

    }
}
