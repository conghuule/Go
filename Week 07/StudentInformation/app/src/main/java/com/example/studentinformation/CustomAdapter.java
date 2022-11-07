package com.example.studentinformation;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class CustomAdapter extends ArrayAdapter<People> {
    private final Context context;
    private final People[] peoples;

    public CustomAdapter(Context context, int resource, People[] peoples) {
        super(context, resource, peoples);
        this.context = context;
        this.peoples = peoples;
    }

    @Override
    public int getCount() {
        return peoples.length;
    }

    @Nullable
    @Override
    public People getItem(int position) {
        return peoples[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_row_icon_label, null);
        TextView txtStudentID = (TextView) view.findViewById(R.id.studentID);
        ImageView avatar = (ImageView) view.findViewById(R.id.avatar);

        txtStudentID.setText(peoples[position].getPeopleID());
        avatar.setImageResource(peoples[position].getThumbnailID());
        return view;
    }
}