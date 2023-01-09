package com.company.go.Fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.company.go.Activities.LoginActivity;
import com.company.go.Activities.MainActivity;
import com.company.go.R;
import com.company.go.Utils.PicassoTrustAll;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfile extends Fragment {
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    FirebaseStorage storage;

    public static final int PICK_IMAGE = 1;

    ImageView btnCamera;

    public static com.company.go.Fragments.Account newInstance() {
        com.company.go.Fragments.Account fragment = new com.company.go.Fragments.Account();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storage = FirebaseStorage.getInstance();

        ImageView btnBack = view.findViewById(R.id.btnBack);
        TextView btnLogout = view.findViewById(R.id.btnLogout);
        TextView username = view.findViewById(R.id.username);
        btnCamera = view.findViewById(R.id.btnCamera);
        EditText editFullName = view.findViewById(R.id.edit_fullname);
        EditText editPhone = view.findViewById(R.id.edit_phone);
        EditText editEmail = view.findViewById(R.id.edit_email);
        EditText editAddress = view.findViewById(R.id.edit_address);
        ImageView btnCamera1 = view.findViewById(R.id.btnCamera1);
        ImageView btnCamera2 = view.findViewById(R.id.btnCamera2);
        ImageView btnCamera3 = view.findViewById(R.id.btnCamera3);
        ImageView btnCamera4 = view.findViewById(R.id.btnCamera4);
        TextView btnSave = view.findViewById(R.id.btnSave);

        PicassoTrustAll.getInstance(getActivity())
                .load(auth.getCurrentUser().getPhotoUrl())
                .fit().into(btnCamera);

        editFullName.setText(auth.getCurrentUser().getDisplayName());
        editPhone.setText(auth.getCurrentUser().getPhoneNumber());
        editEmail.setText(auth.getCurrentUser().getEmail());
        username.setText(auth.getCurrentUser().getDisplayName());
        editAddress.setText("");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).switchFragment(new Account(), false);
//                ((MainActivity) getActivity()).contentFragment = new Account();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent myIntent_Profile2Login = new Intent(getActivity(), LoginActivity.class);
                startActivity(myIntent_Profile2Login);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        btnCamera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        btnCamera2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        btnCamera3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        btnCamera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(editFullName.getText().toString())
                        .build();

                auth.getCurrentUser().updateEmail(editEmail.getText().toString());
            }
        });
    }

    void pickImage() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
            PicassoTrustAll.getInstance(getActivity())
                    .load(data.getData())
                    .fit().into(btnCamera);

//            try {

//                InputStream stream = new FileInputStream(new File(String.valueOf(data.getData())));
            Uri file = Uri.fromFile(new File(String.valueOf(data.getData())));
            StorageReference ref = storage.getReference();
            UploadTask uploadTask = ref.putFile(file);

            uploadTask.addOnFailureListener(exception -> {
                Log.d("hihi", exception.toString());
            }).addOnSuccessListener(taskSnapshot -> {
            });
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Log.d("hihi", e.toString());
//            }

        }
    }
}
