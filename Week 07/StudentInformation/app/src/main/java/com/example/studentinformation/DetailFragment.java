package com.example.studentinformation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment implements FragmentCallbacks {
    MainActivity main;
    TextView txtStudentID, txtName, txtClass, txtScore;
    Button btnFirst, btnPrevious, btnNext, btnLast;
    private People[] peoples;
    private Class[] classes;
    int currentPosition;

    public static DetailFragment newInstance(String strArg1) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("arg1", strArg1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!(getActivity() instanceof MainCallbacks)) {
            throw new IllegalStateException("Activity must implement MainCallbacks");
        }
        main = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout_detail = (LinearLayout) inflater.inflate(R.layout.activity_detail_fragment, null);
        txtStudentID = (TextView) layout_detail.findViewById(R.id.detailID);
        txtName = (TextView) layout_detail.findViewById(R.id.detailName);
        txtClass = (TextView) layout_detail.findViewById(R.id.detailClass);
        txtScore = (TextView) layout_detail.findViewById(R.id.detailAvg);

        try {
            Bundle arguments = getArguments();
            txtStudentID.setText(arguments.getString("arg1",""));
        }
        catch (Exception e) {
            Log.e("DETAIL BUNDLE ERROR - ","" + e.getMessage());
        }

        peoples = main.peoples;
        classes = main.classes;

        btnFirst = (Button) layout_detail.findViewById(R.id.btnFirst);
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", 0);
            }
        });

        btnPrevious = (Button) layout_detail.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", currentPosition-1);
            }
        });

        btnNext = (Button) layout_detail.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", currentPosition+1);
            }
        });

        btnLast = (Button) layout_detail.findViewById(R.id.btnLast);
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", peoples.length - 1);
            }
        });

        return layout_detail;
    }

    @Override
    public void onMsgFromMainToFragment(int position) {
        currentPosition = position;
        txtStudentID.setText(peoples[position].getPeopleID());
        txtName.setText(peoples[position].getName());
        txtClass.setText(findClassNameById(peoples[position].getClassID()));
        txtScore.setText(Float.toString(peoples[position].getAvgScore()));
        updateButton(peoples, position);
    }

    public void updateButton(People[] students, int position) {
        if (position == 0)
            btnPrevious.setEnabled(false);
        else
            btnPrevious.setEnabled(true);

        if (position == (students.length - 1))
            btnNext.setEnabled(false);
        else
            btnNext.setEnabled(true);
    }

    private String findClassNameById(int classID) {
        for (Class c: classes) {
            if (c.getClassID() == classID)
                return c.getClassName();
        }
        return "No Class Found";
    }
}