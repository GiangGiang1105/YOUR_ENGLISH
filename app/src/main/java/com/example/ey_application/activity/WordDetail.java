package com.example.ey_application.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ey_application.Model.Word.DataWord;
import com.example.ey_application.Model.Word.Detail;
import com.example.ey_application.Model.Word.InforWord;
import com.example.ey_application.Model.Word.Meaning;
import com.example.ey_application.Model.Word.Phonetics;
import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.TranslateViewModel;
import com.example.ey_application.database.Repository.Translate;
import com.example.ey_application.ViewModel.WordDetailViewModel;
import com.example.ey_application.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class WordDetail extends AppCompatActivity {
    public interface WordDetailInterface {
        void sendDataWord(String word, String meanVietnamese);
        void sendPhonetics(Phonetics phonetics);
        void sendDefinition(List<Detail> detailsDefinition);
        void sendSynonyms(List<Detail> synonyms);
        void sendExample(List<Detail> example);
    }
    private Word word;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private WordDetailViewModel wordDetailViewModel;
    private TranslateViewModel translateViewModel;
    private List<Detail> detailDefinition;
    private List<Detail> detailExample;
    private List<Detail> detailSynonyms;
    private Phonetics mPhonetics;
    private List<WordDetailInterface> listWordDetailInterfaces;
    private  String wordTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);
        getView();
        getWord();
        setUp();
        setViewPager();
        listWordDetailInterfaces = new ArrayList<>();
        wordDetailViewModel.getWord(word.getWord()).observe(this, new Observer<DataWord>() {
            @Override
            public void onChanged(DataWord dataWord) {
                mPhonetics = dataWord.getPhonetics();
                detailDefinition = dataWord.getDefinition();
                detailExample = dataWord.getExample();
                detailSynonyms = dataWord.getSynonyms();
                onSendData();
            }
        });
        wordDetailViewModel.getWord(word.getWord()).observe(this, new Observer<DataWord>() {
            @Override
            public void onChanged(DataWord dataWord) {
                detailDefinition = dataWord.getDefinition();
                onSendData();
            }
        });
        translateViewModel.translate(word.getWord()).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                wordTranslate = s;
                onSendData();
            }
        });

    }

    private void setUp(){
        wordDetailViewModel =  ViewModelProviders.of(WordDetail.this).get(WordDetailViewModel.class);
        wordDetailViewModel.init();
        translateViewModel = ViewModelProviders.of(WordDetail.this).get(TranslateViewModel.class);
        translateViewModel.init();

    }
    private void getView(){
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }
    private void getWord(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        word = (Word) bundle.getSerializable("Word");
    }
    private void setViewPager(){
        viewPagerAdapter  = new ViewPagerAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                onSendData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void onSendData(){
        for (WordDetailInterface wordDetailInterface : listWordDetailInterfaces){
            wordDetailInterface.sendDataWord(word.getWord(), wordTranslate);
            wordDetailInterface.sendPhonetics(mPhonetics);
            wordDetailInterface.sendDefinition(detailDefinition);
            wordDetailInterface.sendSynonyms(detailSynonyms);
            wordDetailInterface.sendExample(detailExample);
        }

    }
    public synchronized void createDataUpdateListener(final WordDetailInterface listener) {
       listWordDetailInterfaces.add(listener);
    }

    public synchronized void unDataUpdateListener(final WordDetailInterface listener) {
       listWordDetailInterfaces.remove(listener);
    }


}



