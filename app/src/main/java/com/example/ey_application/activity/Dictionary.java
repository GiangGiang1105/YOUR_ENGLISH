package com.example.ey_application.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.Model.Word.WordDictionary;
import com.example.ey_application.R;
import com.example.ey_application.adapter.ListDataWordAdapter;
import com.example.ey_application.myinterface.WordItemClick;
import com.example.ey_application.database.Repository.DatabaseAccess;

import java.io.Serializable;
import java.util.List;

public class Dictionary extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseAccess databaseAccess;
    public static List<WordDictionary> data;
    private ListDataWordAdapter listDataWordAdapter;
    private LinearLayoutManager llm;
    private Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        databaseAccess = ViewModelProviders.of(Dictionary.this).get(DatabaseAccess.class);
        databaseAccess.getQuotes();
        databaseAccess.listDictionary.observe(this, new Observer<List<WordDictionary>>() {
            @Override
            public void onChanged(List<WordDictionary> wordDictionaries) {
                Log.i("change", "change");
                data = wordDictionaries;
                listDataWordAdapter.setData(data);
            }
        });
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        listDataWordAdapter = new ListDataWordAdapter(getApplication());
        recyclerView.setAdapter(listDataWordAdapter);

        listDataWordAdapter.setOnClickListener(new WordItemClick() {
            @Override
            public void onClick(View v, int position) {

            }

            @Override
            public void onClickItem(View v, String w) {
                Intent intent = new Intent(Dictionary.this, WordDetail.class);
                Bundle bundle = new Bundle();
                Word word = new Word();
                word.setWord(w);
                bundle.putSerializable("Word", (Serializable) word);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_dictionary, menu);
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listDataWordAdapter.getFilter(data).filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listDataWordAdapter.getFilter(data).filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}