package com.example.studentinformation;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<People> {
    Context context;
    int layoutToBeInflated;
    People[] peoples;

    public CustomAdapter(Context context, int layoutToBeInflated, People[] peoples) {
        super(context, layoutToBeInflated, peoples);
        this.context = context;
        this.layoutToBeInflated = layoutToBeInflated;
        this.peoples = peoples;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) return convertView;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(layoutToBeInflated, null);

        ImageView avatar = row.findViewById(R.id.avatar);
        TextView studentID = row.findViewById(R.id.studentID);

        People people = peoples[position];
        avatar.setImageResource(people.getAvatar());
        studentID.setText(people.getId());

        return (row);
    }

}