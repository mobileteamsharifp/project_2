package com.example.project_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Post> {
    public ListViewAdapter(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
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
