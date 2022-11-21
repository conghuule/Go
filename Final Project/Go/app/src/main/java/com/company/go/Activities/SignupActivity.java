package com.company.go.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.widget.Toast;

import com.company.go.Fragments.InputLabel;
import com.company.go.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {
    private InputLabel nameInput, emailInput, passwordInput, confirmPasswordInput;
    private Button signupBtn;
    private FirebaseAuth auth;

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
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        nameInput = (InputLabel) getSupportFragmentManager().findFragmentById(R.id.name_input);
        emailInput = (InputLabel) getSupportFragmentManager().findFragmentById(R.id.email_input);
        passwordInput = (InputLabel) getSupportFragmentManager().findFragmentById(R.id.password_input);
        confirmPasswordInput = (InputLabel) getSupportFragmentManager().findFragmentById(R.id.confirm_password_input);
        signupBtn = (Button) findViewById(R.id.signup_btn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = nameInput.getInputValue();
                String email = emailInput.getInputValue();
                String password = passwordInput.getInputValue();
                String confirmPassword = confirmPasswordInput.getInputValue();

                if (isValidForm(fullName, email, password, confirmPassword)) {
                    Log.d("Success", "Signup successfully");
                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(fullName)
                                                .build();

                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "User profile updated.");
                                                        }
                                                    }
                                                });
                                        startActivity(new Intent(SignupActivity.this, CompletedActivity.class));
                                        finish();
                                    } else {
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private boolean isValidForm(String fullName, String email, String password, String confirmPassword) {
        nameInput.setError(null);
        emailInput.setError(null);
        passwordInput.setError(null);
        confirmPasswordInput.setError(null);

        if (TextUtils.isEmpty(fullName)) {
            nameInput.setError("Name is required!");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("Email is required!");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("Password is required!");
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordInput.setError("Confirm password is required!");
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
        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Password is not match!");
            return false;
        }

        return true;
    }
}