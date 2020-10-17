package com.example.ey_application.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ey_application.Model.Reviews.WordReviews;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.WordViewModel;
import com.example.ey_application.adapter.ListWordReviewsAdapter;
import com.example.ey_application.myinterface.ResultCallBackWord;
import com.example.ey_application.session.SessionUser;

import java.util.List;

public class TestActivity extends AppCompatActivity implements ResultCallBackWord {
    private Toolbar toolbar;
    private RecyclerView recyclerListReviews;
    private ListWordReviewsAdapter listWordReviewsAdapter;
    private List<WordReviews> wordReviewsList;
    private LinearLayoutManager llm;
    private WordViewModel wordViewModel;
    SessionUser sessionUser;
    int id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reviews);
        getView();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Reviews");
        setSupportActionBar(toolbar);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        listWordReviewsAdapter = new ListWordReviewsAdapter(this);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerListReviews.setLayoutManager(llm);
        recyclerListReviews.setHasFixedSize(true);
        recyclerListReviews.setAdapter(listWordReviewsAdapter);
        sessionUser = new SessionUser(getApplicationContext());
        sessionUser.getPreferences();
        id_user = sessionUser.getLoggedID();
        wordViewModel.getListWordReviews(id_user);
        wordViewModel.setResultCallBackWord(this);
        wordViewModel.listWordReviews.observe(this, new Observer<List<WordReviews>>() {
            @Override
            public void onChanged(List<WordReviews> wordReviews) {
                listWordReviewsAdapter.setData(wordReviews);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.testMeaning:
                Intent intent = new Intent(TestActivity.this, Test.class);
                intent.putExtra("testtype", 0);
                startActivity(intent);
                break;
            case R.id.testPronounce:
                Intent intent1 = new Intent(TestActivity.this, Test.class);
                intent1.putExtra("testtype", 1);
                startActivity(intent1);
                break;
            case R.id.deleteList:
                deleteList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteList() {
        wordViewModel.deleteListWordReviews(id_user);
    }

    private void getView(){
        recyclerListReviews = (RecyclerView) findViewById(R.id.recyclerViewListReview);

    }

    @Override
    public void onDeleteResultFail(boolean bool) {

    }

    @Override
    public void onMarkResultFail(boolean bool) {

    }

    @Override
    public void onInsertResultFail(boolean bool) {

    }

    @Override
    public void onInsertReviewsResult(boolean bool) {

    }

    @Override
    public void onDeleteReviewsResult(boolean bool) {

    }

    @Override
    public void onDeleteListReviewsResult(boolean bool) {
        if (bool){
            Toast.makeText(this, "You deleted all word reviews!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Server Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onInsertAllReviewsResult(boolean bool) {

    }

    @Override
    public void onDeleteAllWord(boolean bool) {

    }
}