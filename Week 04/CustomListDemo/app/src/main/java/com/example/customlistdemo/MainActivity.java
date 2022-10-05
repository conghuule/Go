package com.example.customlistdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends Activity {
    TextView selectedItem;
    ListView listView;

    String[] names = {"Nguyễn Văn A", "Lê Thị B", "Trần Văn C", "Phan Văn C", "Đinh Văn D"};
    String[] phones = {"0989897873", "0967995843", "0907955843", "0967855411", "0988885231"};
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

        selectedItem = (TextView) findViewById(R.id.text);
        listView = (ListView) findViewById(R.id.list);

        CustomAdapter adapter = new CustomAdapter(this, R.layout.custom_row_icon_label, peoples);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem.setText("You choose: " + peoples[i].getName());
            }
        });
    }
}