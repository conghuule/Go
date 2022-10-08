package com.example.studentinformation;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
    int currentPosition;

    public CustomAdapter(Context context, int layoutToBeInflated, People[] peoples) {
        super(context, layoutToBeInflated, peoples);
        this.context = context;
        this.layoutToBeInflated = layoutToBeInflated;
        this.peoples = peoples;
        setCurrentPosition(0);
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        ((MainActivity) this.context).onMsgFromFragToMain("LIST", currentPosition);
        notifyDataSetChanged();
    }

    private void setBackground(int position, View view) {
        if (currentPosition == position) view.setBackgroundColor(Color.parseColor("#FCDADA"));
        else view.setBackgroundColor(Color.WHITE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            setBackground(position, convertView);
            return convertView;
        }

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(layoutToBeInflated, null);

        ImageView avatar = row.findViewById(R.id.avatar);
        TextView studentID = row.findViewById(R.id.studentID);

        People people = peoples[position];
        avatar.setImageResource(people.getAvatar());
        studentID.setText(people.getId());

        setBackground(position, row);
        return (row);
    }

}