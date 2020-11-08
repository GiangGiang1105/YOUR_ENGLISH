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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.Volumn;
import com.example.ey_application.myinterface.ResultRecognize;


import java.util.ArrayList;
import java.util.List;

public class ViewPagerItemReview extends PagerAdapter implements ResultRecognize {

    public interface CallRecognize{
        void callRecognize( String word);
    }
    private List<Word> wordList;
    private Context context;
    LayoutInflater inflater;
    private Volumn volumn;
    TextToSpeech textToSpeech;
    private CallRecognize callRecognize;
    private MutableLiveData<String> liveDataWordRegconize = new MutableLiveData<>();
    private MutableLiveData<Integer> liveDataWordColor = new MutableLiveData<>();
    public ViewPagerItemReview(Context context) {
        this.wordList = new ArrayList<>();
        this.context = context;
        volumn = new Volumn(context);
        textToSpeech = volumn.textToSpeechUS();
        callRecognize = (CallRecognize) context;
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
        liveDataWordRegconize.postValue("");
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager_review_item,container,false);
        TextView txtWord = (TextView) view.findViewById(R.id.word);
        Button btnVolumn = (Button) view.findViewById(R.id.volumn_word);
        Button btnRecognize = (Button) view.findViewById(R.id.microphone_word);
        final TextView txtRecognize = (TextView) view.findViewById(R.id.textRegconize);
        txtWord.setText(wordList.get(position).getWord());
        liveDataWordRegconize.observe((LifecycleOwner) context , new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtRecognize.setText(s);
            }
        });
        liveDataWordColor.observe((LifecycleOwner) context, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                txtRecognize.setTextColor(integer);
            }
        });
        btnVolumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(wordList.get(position).getWord(), TextToSpeech.QUEUE_FLUSH, null);

            }
        });
        btnRecognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRecognize.callRecognize(wordList.get(position).getWord());
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
    public void resultRecognize(String wordRecognize, int color) {
        if (!wordRecognize.equals("") ){
            liveDataWordRegconize.postValue(wordRecognize);
            liveDataWordColor.postValue(color);
        }
    }


}
