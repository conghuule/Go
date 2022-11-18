package com.company.go.Fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.company.go.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class InputLabel extends Fragment {
    private TextView label;
    private TextInputLayout editText;
    private String labelText;
    private String placeholder;
    private String type = "";

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InputLabel);

        labelText = a.getString(R.styleable.InputLabel_label);
        placeholder = a.getString(R.styleable.InputLabel_placeholder);
        if (a.getString(R.styleable.InputLabel_type) != null) {
            type = a.getString(R.styleable.InputLabel_type);
        } else type = "text";

        a.recycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_input_label, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        label = (TextView) getView().findViewById(R.id.label);
        editText = (TextInputLayout) getView().findViewById(R.id.input);

        label.setText(labelText);
        editText.setHint(placeholder);
        if (type.equals("password")) {
            editText.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            editText.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }


    public void setError(String errorMsg) {
        editText.setError(errorMsg);
    }

    public String getInputValue() {
        return editText.getEditText().getText().toString();
    }
}