package com.example.project_2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewStub;
import android.widget.ListView;

import com.example.project_2.back.MessageController;
import com.example.project_2.back.NotificationCenter;
import com.example.project_2.back.Observer;
import com.example.project_2.front1.ListViewAdapter;
import com.example.project_2.front2.ListViewAdapter_Comment;

public class Main2Activity extends AppCompatActivity implements Observer {

    private ViewStub viewStubList;
    private ListView listView;
    private ListViewAdapter_Comment listViewAdapter;
    Integer numOfComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        NotificationCenter.getNotificationCenter().registerForComment(this);

        Intent intent = getIntent();
        numOfComment = Integer.valueOf(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

        setTitle("Post #" + (numOfComment+1) + " (loading...)");

        viewStubList = findViewById(R.id.listStub_2);

        viewStubList.inflate();

        listView = findViewById(R.id.myListView);

        setAdapters();

        getCommentsList();

    }

    private void getCommentsList() {
        ConnectivityManager ConnectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        MessageController.getMessageController(getBaseContext()).getComments(numOfComment, ConnectionManager, openOrCreateDatabase("myDataBase",MODE_PRIVATE,null));
    }


    private void setAdapters() {
        listViewAdapter = new ListViewAdapter_Comment(this, R.layout.list_item, MessageController.getMessageController(getBaseContext()).comments);
        listView.setAdapter(listViewAdapter);
    }


    @Override
    public void update() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MessageController m = MessageController.getMessageController(getBaseContext());
                setTitle("Post #" + (numOfComment+1) + ", " + MessageController.getMessageController(getBaseContext()).comments.size() + " Comments");
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
