package com.example.project_2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    MenuItem playMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.start_menu, menu);
        playMenu = menu.findItem(R.id.grid);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_people:
                new DeveloperDialog().show(getSupportFragmentManager(), "");
                return true;
            case R.id.grid:
                changeIcon();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean flag = true;

    private void changeIcon() {
        if (playMenu != null){
            if (flag)
                playMenu.setIcon(R.drawable.ic_reorder_black_24dp);
            else
                playMenu.setIcon(R.drawable.ic_grid_on_black_24dp);
            flag = !flag;
        }
    }


}
