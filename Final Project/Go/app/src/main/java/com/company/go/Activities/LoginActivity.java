package com.company.go.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.company.go.Fragments.InputLabel;
import com.company.go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private InputLabel emailInput, passwordInput;

    //unfocused input when click outside
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Log.d("User Info", auth.getCurrentUser().getDisplayName());
            Log.d("User Info", auth.getCurrentUser().getEmail());

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        emailInput = (InputLabel) getSupportFragmentManager().findFragmentById(R.id.email_input);
        passwordInput = (InputLabel) getSupportFragmentManager().findFragmentById(R.id.password_input);
        Button loginBtn = (Button) findViewById(R.id.login_btn);
        TextView signupBtn = (TextView) findViewById(R.id.signup_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailInput.getInputValue();
                String password = passwordInput.getInputValue();

                if (isValidForm(email, password)) {
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();

                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Wrong email or password.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private boolean isValidForm(String email, String password) {
        emailInput.setError(null);
        passwordInput.setError(null);

        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email is required!");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password is required!");
            return false;
        }
        if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            emailInput.setError("Email is invalid!");
            return false;
        }
        if (password.length() < 6) {
            passwordInput.setError("Password too short, enter minimum 6 characters!");
            return false;
        }

        return true;
    }
}