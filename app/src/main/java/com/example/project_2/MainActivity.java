package com.example.project_2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements Observer {

    private ViewStub viewStubGrid;
    private ViewStub viewStubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    int currentMode = VIEW_MODE_LIST_VIEW;
    SQLiteDatabase mydatabase;
    MenuItem grid_list_item;


    static final int VIEW_MODE_LIST_VIEW = 0;
    static final int VIEW_MODE_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationCenter.getNotificationCenter().registerForData(this);

        mydatabase = openOrCreateDatabase("your database name",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Post(userID int, id int, title VARCHAR, body VARCHAR, PRIMARY KEY(id));");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Comment(postId int, id int, name VARCHAR, mail VARCHAR, body VARCHAR, foreign key(postId) REFERENCES Post(id), PRIMARY KEY(id, postId));");

        viewStubList = findViewById(R.id.listStub);
        viewStubGrid = findViewById(R.id.gridStub);

        viewStubGrid.inflate();
        viewStubList.inflate();

        listView = findViewById(R.id.myListView);
        gridView = findViewById(R.id.myGridView);

        getPostList();

        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentMode = sharedPreferences.getInt("CurrentViewMode", VIEW_MODE_LIST_VIEW);

        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();
    }

    private void switchView() {
        if (currentMode == VIEW_MODE_LIST_VIEW){
            viewStubList.setVisibility(View.VISIBLE);
            viewStubGrid.setVisibility(View.GONE);
        } else {
            viewStubList.setVisibility(View.GONE);
            viewStubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if (currentMode == VIEW_MODE_LIST_VIEW){
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, MessageController.getMessageController(getBaseContext()).posts);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, MessageController.getMessageController(getBaseContext()).posts);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    private void getPostList() {
//        posts.add(new Post(1, 2, "1", "2"));
        ConnectivityManager ConnectionManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        MessageController.getMessageController(getBaseContext()).getPosts(ConnectionManager, mydatabase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.start_menu, menu);
        grid_list_item = menu.findItem(R.id.grid);
        changeIcon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_people:
                new DeveloperDialog().show(getSupportFragmentManager(), "");
                return true;
            case R.id.grid:
                switchMode();
                changeIcon();
                switchView();
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("CurrentViewMode", currentMode);
                editor.apply();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchMode() {
        if (currentMode == VIEW_MODE_LIST_VIEW)
            currentMode = VIEW_MODE_GRID_VIEW;
        else
            currentMode = VIEW_MODE_LIST_VIEW;
    }

    private void changeIcon() {
        if (currentMode == VIEW_MODE_GRID_VIEW)
            grid_list_item.setIcon(R.drawable.ic_reorder_black_24dp);
        else
            grid_list_item.setIcon(R.drawable.ic_grid_on_black_24dp);
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

    @Override
    public void update() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (gridViewAdapter != null)
                    gridViewAdapter.notifyDataSetChanged();
                if (listViewAdapter != null)
                    listViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
