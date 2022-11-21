package com.company.go.Fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.go.R;
import com.google.android.material.textfield.TextInputLayout;

public class ActionBar extends Fragment {

    private TextView title, action;
    private ImageView backBtn;
    private String titleText;
    private String actionText;

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar);

        titleText = a.getString(R.styleable.ActionBar_title);
        actionText = a.getString(R.styleable.ActionBar_action);

        a.recycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_action_bar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (TextView) getView().findViewById(R.id.title);
        action = (TextView) getView().findViewById(R.id.action);
        backBtn = (ImageView) getView().findViewById(R.id.back_btn);

        title.setText(titleText);
        action.setText(actionText);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
}