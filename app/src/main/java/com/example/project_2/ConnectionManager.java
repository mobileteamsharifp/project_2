package com.example.project_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;// TODO هیچی درست نیست

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

        ArrayList<String> stringArray = new ArrayList<String>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(text);
            for (int i = 0; i < jsonArray.length(); i++) {
                stringArray.add(jsonArray.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Post post = null;
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
        posts.add(post);

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
}
