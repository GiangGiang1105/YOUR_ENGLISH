package com.example.ey_application.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
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


public class Review extends AppCompatActivity implements ViewPagerItemDetail.ChangerPager, ViewPagerItemReview.CallRecognize, ViewPagerItemMeanReview.TranslateText {

    private static final int RECOGNIZE_RESULT = 1;
    private ViewPager viewPager;
    private ViewPager viewPagerDetail;
    private Button mSearch;
    private ProgressBar progressTest;
    private Switch mSwitch;
    private TextView scoreView;
    private ImageButton btnExit;
    private Button btnDetail;
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
    private String wordTest;
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
        translateViewModel = ViewModelProviders.of(Review.this).get(TranslateViewModel.class);
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
            Toast.makeText(Review.this, "Error", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Review.this, MainActivity.class);
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
        wordViewModel.showScore(id_user, typeTest);
        wordViewModel.listScore.observe(this, new Observer<List<Score>>() {
            @Override
            public void onChanged(List<Score> scores) {
                if (scores.size() != 0){
                    score = scores.get(0);
                    scoreTest = scores.get(0).getTruth() * 100;
                    scoreView.setText(""+scoreTest);
                }
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogExitProcess();
            }
        });

    }
    private void createDialogExitProcess(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.exit_process));
        alertDialogBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                       Intent intent = new Intent(Review.this, ReviewActivity.class);
                       startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton(getString(R.string.no),new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.question_add_word));
                alertDialogBuilder.setPositiveButton(getString(R.string.choose_add_word),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                intentAddWord();
                            }
                        });

        alertDialogBuilder.setNegativeButton(getString(R.string.choose_exit),new DialogInterface.OnClickListener() {
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
        mSwitch.setEnabled(false);
        viewPagerDetail = findViewById(R.id.viewdetail);
        scoreView = findViewById(R.id.score);
        scoreView.setText(String.valueOf(scoreTest));
        progressTest = findViewById(R.id.progressTest);
        btnExit = findViewById(R.id.exit);
        btnDetail = findViewById(R.id.search);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwitch.setEnabled(true);
            }
        });
    }

    @Override
    public void onResultChangerPager(boolean bool) {
       if (bool){
           mSwitch.setChecked(false);
           mCureentPage = mCureentPage + 1;
           if (mCureentPage == wordList.size() ){
               Intent intent = new Intent(this, ScoreActivity.class);
               startActivity(intent);
               return;
           }
           position.postValue(mCureentPage);
           viewPager.setCurrentItem(mCureentPage);
           viewPagerDetail.setVisibility(View.INVISIBLE);
           wordViewModel.updateScore( score.getIdUser(),score.getKingOfReview(), score.getTruth(), score.getFail());
           wordTest = "";
           progressTest.setProgress(progressTest.getProgress() + (int)100/wordList.size());
           mSwitch.setEnabled(false);
       }
    }

    @Override
    public void callRecognize(String word ) {
        mSwitch.setEnabled(true);
        mSwitch.setChecked(true);
        wordTest = word;
        Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speach to text");
        startActivityForResult(speachIntent, RECOGNIZE_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RECOGNIZE_RESULT && resultCode == RESULT_OK){
            ArrayList<String> recognizeWord = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (recognizeWord.get(0).toLowerCase().trim().equals(wordTest.toLowerCase().trim())){
                resultRecognize.resultRecognize(recognizeWord.get(0), 0xFF0BF415);
                score.setTruth(score.getTruth() +1);
            }
            else{
                resultRecognize.resultRecognize(recognizeWord.get(0), 0xFFFF1D0D);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void sendToTranslateActivity(final String wordTest, String word, final String type ) {
        mSwitch.setEnabled(true);
        mSwitch.setChecked(true);
        translateViewModel.translate(word).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (type.equals("test")){
                    if (wordTest.toLowerCase().trim().equals(s.toLowerCase().trim())){
                        resultTranslate.resultTranslate(0xFF0BF415);
                        score.setTruth(score.getTruth() +1);
                    }
                    else{
                        score.setFail(score.getFail() + 1);
                        resultTranslate.resultTranslate(0xFFFF1D0D);
                    }
                }else{
                    resultTranslate.resultTranslate(s);
                }

            }
        });
    }
}
