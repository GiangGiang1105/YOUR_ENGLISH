package com.example.ey_application.adapter;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.TranslateViewModel;
import com.example.ey_application.Volumn;
import com.example.ey_application.myinterface.ResultRecognize;
import com.example.ey_application.myinterface.ResultTranslate;


import java.util.ArrayList;
import java.util.List;

public class ViewPagerItemMeanReview extends PagerAdapter implements ResultTranslate {

    public interface TranslateText{
        void sendToTranslateActivity(String wordTest, String word , String type);
    }
    private List<Word> wordList;
    private Context context;
    LayoutInflater inflater;
    private String wordTest;
    private TranslateText translateText;
    private MutableLiveData<Integer> integerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> resultMutableLiveData = new MutableLiveData<>();
    public ViewPagerItemMeanReview(Context context) {
        this.wordList = new ArrayList<>();
        this.context = context;
        this.translateText = (TranslateText) context;
    }
    public void setData(List<Word> list){
        this.wordList.clear();
        this.wordList.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == (ConstraintLayout) object;
    }

  @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager_review_mean_item,container,false);
        TextView txtWord = (TextView) view.findViewById(R.id.word);
        final EditText editMean = (EditText) view.findViewById(R.id.edit_text_word);
        Button btnResult = (Button) view.findViewById(R.id.btn_test);
        final Button btnGuess = (Button) view.findViewById(R.id.btn_guess);
        final TextView txtResult = (TextView) view.findViewById(R.id.textmean);
        txtWord.setText(wordList.get(position).getWord());

        btnGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordTest = String.valueOf(editMean.getText()).trim();
                translateText.sendToTranslateActivity(wordTest, wordList.get(position).getWord(), "test");
                txtResult.setText(wordTest);
                editMean.setText("");
            }
        });
      btnResult.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              translateText.sendToTranslateActivity(wordTest, wordList.get(position).getWord(), "show");
          }
      });

        integerMutableLiveData.observe((LifecycleOwner) context, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                txtResult.setTextColor(integer);
            }
        });
        resultMutableLiveData.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtResult.setText(s);
            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
    @Override
    public void resultTranslate(int color ) {
       integerMutableLiveData.postValue(color);
    }

    @Override
    public void resultTranslate(String word) {
        resultMutableLiveData.postValue(word);
    }


}
