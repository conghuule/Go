package com.company.go.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.company.go.Models.Review;
import com.company.go.R;
import com.company.go.Utils.PicassoTrustAll;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Review> {
    Context context;
    List<Review> reviews;

    public ReviewAdapter(@NonNull Context context, int resource, List<Review> reviewList) {
        super(context, resource, reviewList);

        this.context = context;
        this.reviews = reviewList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            return convertView;
        }

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.adapter_review, null);

        TextView userName = view.findViewById(R.id.name);
        ImageView userAvatar = view.findViewById(R.id.avatar);
        TextView review = view.findViewById(R.id.review);

        Review reviewData = reviews.get(position);
        userName.setText(reviewData.getUserName());
        review.setText(reviewData.getReview());
        PicassoTrustAll.getInstance(getContext()).load(reviewData.getUserAvatar()).fit().into(userAvatar);

        return (view);
    }
}