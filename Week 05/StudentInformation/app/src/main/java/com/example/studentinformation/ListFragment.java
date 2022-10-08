package com.example.studentinformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    TextView currentID;
    ListView listView;

    String[] ids = {"20120219", "20120294", "20120312", "20120325", "20120405"};
    String[] names = {"Nguyễn Minh Trí", "Lê Công Hửu", "Lê Tấn Kiệt", "Ngô Thanh Lực", "Nguyễn Long Vũ"};
    String[] class_names = {"20CTT2", "20CTT2", "20CTT2", "20CTT2", "20CTT3"};
    Float[] avg_points = {10F, 10F, 10F, 10F, 9.5F};

    Integer[] avatars = {R.drawable.ic_avatar_1, R.drawable.ic_avatar_2, R.drawable.ic_avatar_3, R.drawable.ic_avatar_4, R.drawable.ic_avatar_5,R.drawable.ic_avatar_1, R.drawable.ic_avatar_2, R.drawable.ic_avatar_3, R.drawable.ic_avatar_4, R.drawable.ic_avatar_5};
    People[] peoples = {
            new People(avatars[0], ids[0], names[0], class_names[0], avg_points[0]),
            new People(avatars[1], ids[1], names[1], class_names[1], avg_points[2]),
            new People(avatars[2], ids[2], names[2], class_names[2], avg_points[2]),
            new People(avatars[3], ids[3], names[3], class_names[3], avg_points[3]),
            new People(avatars[4], ids[4], names[4], class_names[4], avg_points[4]),
    };

    public People[] get_peoples() {
        return peoples;
    }

    public static ListFragment newInstance(String strArgs) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString("strArg1", strArgs);
        fragment.setArguments(args);

        return fragment;
    }

    void setActiveItemBg(int position) {
        position -= listView.getFirstVisiblePosition();
        System.out.println(position);
        for (int i = 0; i < listView.getChildCount(); i++) {
            if (i!= position)
                listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        main.setTheme(R.style.detailBtn);
        listView.getChildAt(position).setBackgroundColor(Color.parseColor("#FCDADA"));
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

        listView = (ListView) layout_list.findViewById(R.id.list);
        CustomAdapter adapter = new CustomAdapter(context, R.layout.custom_row_icon_label, peoples);

        currentID = layout_list.findViewById(R.id.currentID);

        listView.setAdapter(adapter);
        listView.setSelection(0);
        listView.smoothScrollToPosition(0);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            currentID.setText(peoples[i].getId());
            main.onMsgFromFragToMain("LIST", i);

            setActiveItemBg(i);
        });

        return layout_list;
    }

    @Override
    public void onMsgFromMainToFragment(int position) {
        setActiveItemBg(position);
        currentID.setText(peoples[position].getId());
    }
}