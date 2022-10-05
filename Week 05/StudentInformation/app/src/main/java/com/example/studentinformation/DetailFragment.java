package com.example.studentinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailFragment extends Fragment implements FragmentCallbacks {
    MainActivity main;
    TextView detailID;
    TextView detailName;
    TextView detailClass;
    TextView detailAvg;


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
            throw new IllegalStateException("loiloiloi");
        }
        main = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        LinearLayout detailLayout = (LinearLayout) inflater.inflate(R.layout.activity_detail_fragment, null);

        detailID = detailLayout.findViewById(R.id.detailID);
        detailName = detailLayout.findViewById(R.id.detailName);
        detailClass = detailLayout.findViewById(R.id.detailClass);
        detailAvg = detailLayout.findViewById(R.id.detailAvg);

        return detailLayout;
    }

    @Override
    public void onMsgFromMainToFragment(People people) {
        detailID.setText(people.getId());
        detailName.setText(people.getName());
        detailClass.setText(people.getClass_name());
        detailAvg.setText(people.getAvg_point().toString());

    }
}