package com.example.project_2.back;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageController {
    private static MessageController messageController;
    public ArrayList<Post> posts = new ArrayList<>();
    private long lastUpdateTime = 0;
    public ArrayList<Comment> comments = new ArrayList<>();
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

    public ArrayList<Comment> getComments(final int i, final ConnectivityManager connectivityManager, final SQLiteDatabase mydatabase) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        Cursor resultSet = mydatabase.rawQuery("select * from Comment where postId = " + i + ";", null);
        boolean a = networkInfo.isConnected();
        Long aaa = System.currentTimeMillis() - lastUpdateTime;
        int aaaaa = resultSet.getCount();
        if(networkInfo != null && networkInfo.isConnected() && (System.currentTimeMillis() - lastUpdateTime > 300_000 || resultSet.getCount() <= 0)){
            cloudExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    comments.clear();
                    comments.addAll(ConnectionManager.getConnectionManager().loadComments(i));

                    StorageManager.getStorageManager().saveComments(i, comments, mydatabase);
                    lastUpdateTime = System.currentTimeMillis();
                    NotificationCenter.getNotificationCenter().comments_loaded();
                }
            });
        } else {
            storageExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                comments.clear();
                comments.addAll(StorageManager.getStorageManager().loadComments(mydatabase));

                NotificationCenter.getNotificationCenter().comments_loaded();
                }
            });
        }

        return comments;
    }



}

