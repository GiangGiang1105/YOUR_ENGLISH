package com.example.ey_application.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ey_application.Model.Word.DataViewPager;
import com.example.ey_application.Model.Word.DataWord;
import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.WordDetailViewModel;
import com.example.ey_application.ViewModel.WordViewModel;
import com.example.ey_application.adapter.ViewPagerItemDetail;
import com.example.ey_application.adapter.ViewPagerItemReview;
import com.example.ey_application.session.SessionUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reviews extends AppCompatActivity implements ViewPagerItemDetail.ChangerPager {
    private ViewPager viewPager;
    private ViewPager viewPagerDetail;
    private Button mSearch;
    private Switch mSwitch;
    private List<Word> wordList;
    private List<DataViewPager> dataViewPagerList =  new ArrayList<>();
    WordViewModel wordViewModel;
    int id_user;
    SessionUser sessionUser;
    ViewPagerItemReview viewPagerItemReview;
    WordDetailViewModel wordDetailViewModel;
    ViewPagerItemDetail viewPagerItemDetail;
    private int mCureentPage = 0;
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        getView();
        wordList = new ArrayList<>();
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordDetailViewModel = ViewModelProviders.of(this).get(WordDetailViewModel.class);
        wordDetailViewModel.init();
        sessionUser = new SessionUser(getApplicationContext());
        sessionUser.getPreferences();
        id_user = sessionUser.getLoggedID();
        wordViewModel.getListWord(id_user);
        wordViewModel.listWord.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordList = words;
                viewPagerItemReview.setData(wordList);
                position.postValue(mCureentPage);
                if (wordList.size() == 0){
                    setDialog();
                }
            }
        });

        viewPagerItemReview = new ViewPagerItemReview( this);
        viewPager.setAdapter(viewPagerItemReview);
        viewPager.beginFakeDrag();
        viewPagerItemDetail = new ViewPagerItemDetail(this);
        viewPagerDetail.setAdapter(viewPagerItemDetail);
        viewPagerDetail.setVisibility(View.INVISIBLE);
        viewPagerDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == (wordList.size()+1)){
                    viewPagerDetail.setCurrentItem(0);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        position.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                setDataViewPagerItem(integer);
            }
        });
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewPagerDetail.setVisibility(View.VISIBLE);
                }
                else{
                    viewPagerDetail.setVisibility(View.INVISIBLE);
                }
            }
        });


    }
    private void setDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
                alertDialogBuilder.setPositiveButton("Add word",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                intentAddWord();
                            }
                        });

        alertDialogBuilder.setNegativeButton("Exit",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intentExit();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void intentAddWord(){
        Intent intent = new Intent(this, AddWord.class);
        startActivity(intent);
    }
    private void intentExit(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void setDataViewPagerItem(Integer integer){
        if (wordList.size() != 0){
            if (wordList.get(integer).getWord() != null ){
                Log.i("word", wordList.get(integer).getWord());
                wordDetailViewModel.getWord(wordList.get(integer).getWord()).observe(this, new Observer<DataWord>() {
                    @Override
                    public void onChanged(DataWord dataWord) {
                        dataViewPagerList.removeAll(dataViewPagerList);
                        if (dataWord.getPhonetics() != null){
                            dataViewPagerList.add(new DataViewPager("Spelling", dataWord.getPhonetics().getText()));
                        }
                        if (dataWord.getDefinition().size() != 0){
                            dataViewPagerList.add(new DataViewPager("English meaning", dataWord.getDefinition().get(0).getList().get(0)));
                        }
                        if (dataWord.getExample().size() != 0){
                            dataViewPagerList.add( new DataViewPager("Example", dataWord.getExample().get(0).getList().get(0)));
                        }
                        if (dataWord.getSynonyms().size() != 0){
                            dataViewPagerList.add( new DataViewPager("Synonyms", dataWord.getSynonyms().get(0).getList().get(0)));
                        }
                        viewPagerItemDetail.setItem(dataViewPagerList);
                    }
                });
            }
        }
    }
    private void getView(){
        viewPager = findViewById(R.id.viewPager);
        mSearch = findViewById(R.id.search);
        mSwitch = findViewById(R.id.switch_view);
        viewPagerDetail = findViewById(R.id.viewdetail);
    }

    @Override
    public void onResultChangerPager(boolean bool) {
       if (bool){
           mSwitch.setChecked(false);
           mCureentPage = mCureentPage + 1;
           position.postValue(mCureentPage);
           viewPager.setCurrentItem(mCureentPage);
           viewPagerDetail.setVisibility(View.INVISIBLE);
       }
    }
}