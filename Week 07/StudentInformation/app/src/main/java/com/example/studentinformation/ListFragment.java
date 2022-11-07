package com.example.studentinformation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListFragment extends Fragment implements FragmentCallbacks {
    MainActivity main;
    Context context = null;
    ListView listView;
    TextView txtViewList;

    private People[] _peoples = new People[5];
    private Class[] _classes = new Class[1];

    public People[] get_students() {
        return _peoples;
    }
    public Class[] get_classes() {
        return _classes;
    }

    public void getDataFromDatabase() {
        SQLiteDatabase database = main.getDatabase();

        Cursor cursor = database.rawQuery("select * from HOCSINH", null);
        cursor.moveToPosition(-1);
        int i = 0;
        while (cursor.moveToNext()) {
            _peoples[i] = new People(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getFloat(3));
            i++;
        }

        _peoples[0].setThumbnailID(R.drawable.ic_avatar_1);
        _peoples[1].setThumbnailID(R.drawable.ic_avatar_2);
        _peoples[2].setThumbnailID(R.drawable.ic_avatar_3);
        _peoples[3].setThumbnailID(R.drawable.ic_avatar_4);
        _peoples[4].setThumbnailID(R.drawable.ic_avatar_5);

        cursor = database.rawQuery("select * from LOPHOC", null);
        cursor.moveToPosition(-1);
        i = 0;
        while (cursor.moveToNext()) {
            _classes[i] = new Class(cursor.getInt(0), cursor.getString(1));
            i++;
        }
        main.setPeoples(_peoples);
        main.setClasses(_classes);
    }


    public static ListFragment newInstance(String strArg) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString("strArg1", strArg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = getActivity();
            main = (MainActivity) getActivity();
            getDataFromDatabase();
        }
        catch (IllegalStateException e) {
            throw new IllegalStateException("MainActivity must implement callbacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout_list = (LinearLayout) inflater.inflate(R.layout.activity_list_fragment, null);

        txtViewList = layout_list.findViewById(R.id.currentID);
        listView = layout_list.findViewById(R.id.list);

        CustomAdapter adapter = new CustomAdapter(context, R.layout.custom_row_icon_label, _peoples);
        listView.setAdapter(adapter);

        listView.setSelection(0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                txtViewList.setText(_peoples[position].getPeopleID());
                for (int index = 0; index < _peoples.length; index++) {
                    if (position != index)
                        listView.getChildAt(index).setBackgroundColor(Color.WHITE);
                }
                listView.getChildAt(position).setBackgroundColor(Color.parseColor("#FCDADA"));
                main.onMsgFromFragToMain("LIST", position);
            }
        });

        return layout_list;
    }

    @Override
    public void onMsgFromMainToFragment(int position) {
        for (int i = 0; i < _peoples.length; i++) {
            if (i!= position)
                listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        listView.getChildAt(position).setBackgroundColor(Color.parseColor("#FCDADA"));
        txtViewList.setText(_peoples[position].getPeopleID());
    }
}