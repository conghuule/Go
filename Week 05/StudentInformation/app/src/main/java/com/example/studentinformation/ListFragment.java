package com.example.studentinformation;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    TextView currentID;
    ListView listView;
    CustomAdapter adapter;
    People[] peoples;

    public static ListFragment newInstance(String strArgs) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString("strArg1", strArgs);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout_list = (LinearLayout) inflater.inflate(R.layout.activity_list_fragment, null);
        currentID = layout_list.findViewById(R.id.currentID);

        peoples = main.peoples;

        listView = (ListView) layout_list.findViewById(R.id.list);
        adapter = new CustomAdapter(context, R.layout.custom_row_icon_label, peoples);
        listView.setAdapter(adapter);
        listView.setSelection(0);
        listView.smoothScrollToPosition(0);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            currentID.setText(peoples[i].getId());

            adapter.setCurrentPosition(i);
        });

        return layout_list;
    }

    @Override
    public void onMsgFromMainToFragment(int position) {
        adapter.setCurrentPosition(position);
        currentID.setText(peoples[position].getId());
    }
}