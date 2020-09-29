package com.example.ey_application.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ey_application.R;
import com.example.ey_application.adapter.ListDataWordAdapter;
import com.example.ey_application.database.Repository.DatabaseAccess;

import java.util.List;

public class Dictionary extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseAccess databaseAccess;
    public static List<String> data;
    private ListDataWordAdapter listDataWordAdapter;
    private LinearLayoutManager llm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        databaseAccess = DatabaseAccess.getInstance(this);
        data = databaseAccess.getQuotes();
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        listDataWordAdapter = new ListDataWordAdapter(this, data);
        recyclerView.setAdapter(listDataWordAdapter);

    }
}