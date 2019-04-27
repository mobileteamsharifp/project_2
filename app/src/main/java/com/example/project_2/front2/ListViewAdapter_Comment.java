package com.example.project_2.front2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project_2.R;
import com.example.project_2.back.Comment;
import com.example.project_2.back.Post;

import java.util.List;

public class ListViewAdapter_Comment extends ArrayAdapter<Comment> {
    public ListViewAdapter_Comment(Context context, int resource, List<Comment> objects) {
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

        Comment comment = getItem(position);
        TextView title = view.findViewById(R.id.textViewTitle);
        TextView body = view.findViewById(R.id.textViewBody);

        assert comment != null;
        title.setText(comment.getName());
        body.setText(comment.getBody());

        return view;
    }
}
