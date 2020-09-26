package com.example.ey_application.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ey_application.R;
import com.example.ey_application.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyAccount extends AppCompatActivity {
    private ImageButton imageButton;
    private TextView userName;
    private ListView listView;
    private List<String> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        getView();
        listData = getData();
        setAdapter();
    }
    private void getView(){
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        userName = (TextView) findViewById(R.id.username);
        listView = (ListView) findViewById(R.id.listview);
    }
    private void setAdapter(){
        listView.setAdapter(new ListAdapter(this, listData));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                String text = (String) o;
                switch (text){

                }
            }
        });
    }
    private List<String> getData(){
        List<String> data = new ArrayList<>();
        data.add("Edit Account");
        data.add("List Word");
       data.add("List Vocabularies");
        data.add("Review");
        return data;
    }
}