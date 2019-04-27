package com.example.project_2.front1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project_2.R;
import com.example.project_2.back.Post;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Post> {
    private List<Post> data;
    public GridViewAdapter(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, null);
        }

        Post post = getItem(position);
        TextView title = view.findViewById(R.id.textViewTitle);
        TextView body = view.findViewById(R.id.textViewBody);

        assert post != null;
        title.setText(post.getTitle());
        body.setText(post.getBody());

        return view;
    }
}
