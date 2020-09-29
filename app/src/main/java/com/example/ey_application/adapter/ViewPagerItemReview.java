package com.example.ey_application.adapter;
import android.content.Context;
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
import androidx.viewpager.widget.PagerAdapter;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.Volumn;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerItemReview extends PagerAdapter {
    private List<Word> wordList;
    private Context context;
    LayoutInflater inflater;
    private Volumn volumn;
    TextToSpeech textToSpeech;

    public ViewPagerItemReview(Context context) {
        this.wordList = new ArrayList<>();
        this.context = context;
        this.volumn = new Volumn(context);
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
        View view = inflater.inflate(R.layout.viewpager_review_item,container,false);
        TextView txtWord = (TextView) view.findViewById(R.id.word);
        EditText editMean = (EditText) view.findViewById(R.id.edit_text_word);
        Button btnTest = (Button) view.findViewById(R.id.btn_test);
        ImageButton btnVolumn = (ImageButton) view.findViewById(R.id.volumn_word);
        ImageButton btnMicrophone = (ImageButton) view.findViewById(R.id.microphone_word);
        txtWord.setText(wordList.get(position).getWord());
        btnVolumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech = volumn.textToSpeechUS();
                textToSpeech.speak(wordList.get(position).getWord(), TextToSpeech.QUEUE_FLUSH, null);

            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }

}
