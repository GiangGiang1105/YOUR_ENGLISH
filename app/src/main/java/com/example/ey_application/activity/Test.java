package com.example.ey_application.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ey_application.Model.Reviews.Score;
import com.example.ey_application.Model.Word.DataViewPager;
import com.example.ey_application.Model.Word.DataWord;
import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.TranslateViewModel;
import com.example.ey_application.ViewModel.WordDetailViewModel;
import com.example.ey_application.ViewModel.WordViewModel;
import com.example.ey_application.adapter.ViewPagerItemDetail;
import com.example.ey_application.adapter.ViewPagerItemMeanReview;
import com.example.ey_application.adapter.ViewPagerItemReview;
import com.example.ey_application.myinterface.ResultRecognize;
import com.example.ey_application.myinterface.ResultTranslate;
import com.example.ey_application.session.SessionUser;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test extends AppCompatActivity implements ViewPagerItemDetail.ChangerPager, ViewPagerItemReview.CallRecognize, ViewPagerItemMeanReview.TranslateText {

    private static final int RECOGNIZE_RESULT = 1;
    private ViewPager viewPager;
    private ViewPager viewPagerDetail;
    private Button mSearch;
    private ProgressBar progressTest;
    private Switch mSwitch;
    private TextView scoreView;
    private List<Word> wordList;
    private List<DataViewPager> dataViewPagerList =  new ArrayList<>();
    WordViewModel wordViewModel;
    int id_user;
    SessionUser sessionUser;
    ViewPagerItemReview viewPagerItemReview;
    WordDetailViewModel wordDetailViewModel;
    ViewPagerItemDetail viewPagerItemDetail;
    ViewPagerItemMeanReview viewPagerItemMeanReview;
    private int mCureentPage = 0;
    private MutableLiveData<Integer> position = new MutableLiveData<>();
    private ResultRecognize resultRecognize;
    private Intent intent;
    private int typeTest;
    private TranslateViewModel translateViewModel;
    private ResultTranslate resultTranslate;
    private int scoreTest = 0;
    private Score score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        getView();

        intent = getIntent();
        typeTest = intent.getIntExtra("testtype", 2);
        score = new Score(id_user, typeTest,  0, 0);

        wordList = new ArrayList<>();
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordDetailViewModel = ViewModelProviders.of(this).get(WordDetailViewModel.class);
        wordDetailViewModel.init();
        translateViewModel = ViewModelProviders.of(Test.this).get(TranslateViewModel.class);
        translateViewModel.init();
        sessionUser = new SessionUser(getApplicationContext());
        sessionUser.getPreferences();
        id_user = sessionUser.getLoggedID();
        wordViewModel.getListWord(id_user);
        wordViewModel.listWord.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                wordList = words;
                viewPagerItemReview.setData(wordList);
                viewPagerItemMeanReview.setData(wordList);
                position.postValue(mCureentPage);
                if (wordList.size() == 0){
                    setDialog();
                }
            }
        });
        viewPagerItemMeanReview = new ViewPagerItemMeanReview(this);
        viewPagerItemReview = new ViewPagerItemReview( this);
        if (typeTest == 0){
            resultTranslate = (ResultTranslate) viewPagerItemMeanReview;
            viewPager.setAdapter(viewPagerItemMeanReview);
        }
        else if (typeTest == 1){
            resultRecognize = (ResultRecognize) viewPagerItemReview;
            viewPager.setAdapter(viewPagerItemReview);
        }
        else{
            Toast.makeText(Test.this, "Error", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Test.this, MainActivity.class);
            startActivity(intent);
        }
        viewPager.beginFakeDrag();

        viewPagerItemDetail = new ViewPagerItemDetail(this);
        viewPagerDetail.setAdapter(viewPagerItemDetail);
        viewPagerDetail.setVisibility(View.INVISIBLE);
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
        if (typeTest == 0){
            wordViewModel.showScore(id_user, typeTest);
        }
        else if (typeTest == 1){
            wordViewModel.showScore(id_user, typeTest);
        }
        wordViewModel.listScore.observe(this, new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                if (scores.size() != 0){
                    score = scores.get(0);
                    scoreTest += scores.get(0).getTruth() * 100;
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
        scoreView = findViewById(R.id.score);
        scoreView.setText(String.valueOf(scoreTest));
    }

    @Override
    public void onResultChangerPager(boolean bool) {
       if (bool){
           mSwitch.setChecked(false);
           mCureentPage = mCureentPage + 1;
           if (mCureentPage == wordList.size() ){
               Intent intent = new Intent(this, Score.class);
               startActivity(intent);
               return;
           }
           position.postValue(mCureentPage);
           viewPager.setCurrentItem(mCureentPage);
           viewPagerDetail.setVisibility(View.INVISIBLE);
           if (typeTest == 0){
               if (viewPagerItemMeanReview.boolTest){
                   score.setTruth(score.getTruth() +1);
               }
               else{
                   score.setFail(score.getFail() +1);
               }
               wordViewModel.updateScore( score.getIdUser(),score.getKingOfReview(), score.getTruth(), score.getFail());
           }
           else if (typeTest == 1){
               //Score score = new Score(id_user, codeTest, typeTest,  viewPagerItemReview.countTruth, viewPagerItemReview.countFail);
              // wordViewModel.updateScore(score);
           }


       }
    }

    @Override
    public void callRecognize(boolean bool) {
        if (bool){
            Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speach to text");
            startActivityForResult(speachIntent, RECOGNIZE_RESULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RECOGNIZE_RESULT && resultCode == RESULT_OK){
            ArrayList<String> recognizeWord = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            resultRecognize.resultRecognize(recognizeWord.get(0).toString());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void sendToTranslateActivity(String text) {
        if (text!= null){
            translateViewModel.translate(text).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                   resultTranslate.resultTranslate(s);
                }
            });
        }
    }
}
