package com.example.customlistdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView selectedItem;
    ListView listView;

    String[] names = {"Nguyễn Minh Trí", "Lê Công Hửu", "Lê Tấn Kiệt", "Ngô Thanh Lực", "Nguyễn Long Vũ"};
    String[] phones = {"0911271505", "0817433090", "0817433090", "0793652374", "0356436545"};
    Integer[] avatars = {R.drawable.ic_avatar_1, R.drawable.ic_avatar_2, R.drawable.ic_avatar_3, R.drawable.ic_avatar_4, R.drawable.ic_avatar_5};
    People[] peoples = {
            new People(avatars[0], names[0], phones[0]),
            new People(avatars[1], names[1], phones[1]),
            new People(avatars[2], names[2], phones[2]),
            new People(avatars[3], names[3], phones[3]),
            new People(avatars[4], names[4], phones[4]),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        selectedItem = findViewById(R.id.text);
        listView = findViewById(R.id.list);

        CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_row_icon_label, peoples);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            String choose_msg = "You choose: " + peoples[i].getName();
            selectedItem.setText(choose_msg);
        });
    }
}