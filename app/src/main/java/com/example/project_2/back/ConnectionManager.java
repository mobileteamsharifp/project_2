package com.example.project_2.back;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ConnectionManager {
    private static ConnectionManager connectionManager;

    public static ConnectionManager getConnectionManager() {
        if (connectionManager == null)
            connectionManager = new ConnectionManager();
        return connectionManager;
    }

    public synchronized ArrayList<Post> loadPosts(){
        ArrayList<Post> posts = new ArrayList<>();
        String siteaddr = "https://jsonplaceholder.typicode.com/posts";

        String text = getUrlText(siteaddr);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Post post;
        try {
            assert jsonArray != null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = (JSONObject) jsonArray.get(i);
                post = new Post((Integer) o.get("userId"), (Integer) o.get("id"), (String) o.get("title"), (String) o.get("body"));
                posts.add(post);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posts;
    }


    private String getUrlText(String siteaddr){
        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            url = new URL(siteaddr);

            urlConnection = (HttpURLConnection) url
                    .openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                stringBuilder.append(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return String.valueOf(stringBuilder);
    }


    private ConnectionManager(){}

    public ArrayList<Comment> loadComments(int numOfComment) {

        ArrayList<Comment> comments = new ArrayList<>();
        String siteaddr = "https://jsonplaceholder.typicode.com/comments?postId=" + (numOfComment + 1);


        String text = getUrlText(siteaddr);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Comment comment = null;
        try {
            assert jsonArray != null;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject o = (JSONObject) jsonArray.get(i);
                comment = new Comment((Integer) o.get("postId"), (Integer) o.get("id"), (String) o.get("name"), (String) o.get("email"), (String) o.get("body"));
                comments.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comments;
    }
}
