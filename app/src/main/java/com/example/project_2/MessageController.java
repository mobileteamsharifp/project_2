package com.example.project_2;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageController {
    private static MessageController messageController;
    ArrayList<Post> posts = new ArrayList<>();
    private long lastUpdateTime = 0;
    ArrayList<Comment> comments = new ArrayList<>();
    private static Context context;
    private ExecutorService cloudExecutorService =  Executors.newSingleThreadExecutor();
    private ExecutorService storageExecutorService = Executors.newSingleThreadExecutor();
    private Boolean isWorking = false;

    public static MessageController getMessageController(Context context) {
        MessageController.context = context;
        if (messageController == null)
            messageController = new MessageController();
        return messageController;
    }

    public static Context getContext() {
        return context;
    }

    private MessageController(){}

    public void stop() {
        this.cloudExecutorService.shutdown();
        this.storageExecutorService.shutdown();
    }

    public void getPosts(ConnectivityManager connectivityManager, final SQLiteDatabase mydatabase) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() && System.currentTimeMillis() - lastUpdateTime > 300_000){
            cloudExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    posts.clear();
                    posts.addAll(ConnectionManager.getConnectionManager().loadPosts());

                    StorageManager.getStorageManager().savePost(posts, mydatabase);
                    lastUpdateTime = System.currentTimeMillis();
                    NotificationCenter.getNotificationCenter().data_loaded();
                }
            });
        } else {
            storageExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    posts.clear();
                    posts.addAll(StorageManager.getStorageManager().loadPosts(mydatabase));

                    NotificationCenter.getNotificationCenter().data_loaded();
                }
            });
        }
    }

    public ArrayList<Comment> getComments(int i, ConnectivityManager ConnectionManager) {
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected() && System.currentTimeMillis() - lastUpdateTime > 300_000){

            lastUpdateTime = System.currentTimeMillis();
        } else {

        }

        return comments;
    }



}

