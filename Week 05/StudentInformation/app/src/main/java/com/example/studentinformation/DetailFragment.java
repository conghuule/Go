package com.example.studentinformation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment implements FragmentCallbacks {
    MainActivity main;
    ImageView detailAvatar;
    TextView detailID;
    TextView detailName;
    TextView detailClass;
    TextView detailAvg;
    Button btnFirst, btnPrevious, btnNext, btnLast;
    int currentPosition;
    private People[] peoples;

    public static DetailFragment newInstance(String strArgs) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("strArg1", strArgs);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!((getActivity()) instanceof MainCallbacks)) {
            throw new IllegalStateException("Error");
        }
        main = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        ScrollView detailLayout = (ScrollView) inflater.inflate(R.layout.activity_detail_fragment, null);

        detailAvatar = detailLayout.findViewById(R.id.detailAvatar);
        detailID = detailLayout.findViewById(R.id.detailID);
        detailName = detailLayout.findViewById(R.id.detailName);
        detailClass = detailLayout.findViewById(R.id.detailClass);
        detailAvg = detailLayout.findViewById(R.id.detailAvg);

        peoples = main.peoples;

        btnFirst = (Button) detailLayout.findViewById(R.id.btnFirst);
        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", 0);
            }
        });

        btnPrevious = (Button) detailLayout.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", currentPosition - 1);
            }
        });

        btnNext = (Button) detailLayout.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", currentPosition + 1);
            }
        });

        btnLast = (Button) detailLayout.findViewById(R.id.btnLast);
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.onMsgFromFragToMain("DETAIL", peoples.length - 1);
            }
        });

        return detailLayout;
    }

    @Override
    public void onMsgFromMainToFragment(int position) {
        currentPosition = position;

        System.out.println(peoples);

        detailAvatar.setImageResource(peoples[position].getAvatar());
        detailID.setText(peoples[position].getId());
        detailName.setText(peoples[position].getName());
        detailClass.setText(peoples[position].getClass_name());
        detailAvg.setText(peoples[position].getAvg_point().toString());
        updateButton(peoples, position);
    }

    public void updateButton(People[] peoples, int position) {
        if (position == 0) {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
        } else {
            btnFirst.setEnabled(true);
            btnPrevious.setEnabled(true);
        }

        if (position == (peoples.length - 1)) {
            btnLast.setEnabled(false);
            btnNext.setEnabled(false);
        } else {
            btnLast.setEnabled(true);
            btnNext.setEnabled(true);
        }
    }
}