package com.example.project_2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewStub viewStubGrid;
    private ViewStub viewStubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<Post> posts;
    int currentMode = VIEW_MODE_LIST_VIEW;
    MenuItem grid_list_item;


    static final int VIEW_MODE_LIST_VIEW = 0;
    static final int VIEW_MODE_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, posts);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, posts);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    private void getPostList() {
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post(1, 1, "n1", "1ksdghfjdsdjghdfg"));
        posts.add(new Post(1, 2, "n2", "2kfjglksfhglfkshgrlgkjshg"));
        posts.add(new Post(1, 3, "n3", "3swkurhglsjfxhgliurekgsh;rkugjhr"));
        posts.add(new Post(1, 4, "n4", "4keshgkjhlkjrhglksuh"));
        posts.add(new Post(1, 5, "n5", "5kwjshkhgr"));
        posts.add(new Post(1, 6, "n6", "6kesrhgkjhseglrukhgjfklshglkjfhg"));
        posts.add(new Post(1, 7, "n7", "7skjehgkjslrgrklsgjh"));

        this.posts = posts;
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
//TODO
        }
    };

}
