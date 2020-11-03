package com.example.ey_application.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.ey_application.R;
import com.example.ey_application.ViewModel.TranslateViewModel;
import com.example.ey_application.Volumn;

import java.util.ArrayList;

public class TranslateActivity extends AppCompatActivity {
    private static final int RECOGNIZE_RESULT = 1;
    private EditText inputTextTranslate;
    private ImageButton btnMicro;
    private TextView txtMeanTranslate;
    private Button btnTslAnhViet;
    private Button btnTslVietAnh;
    private ImageButton btnVolumnText;
    private ImageButton btnVolumnMean;
    private ImageButton btnCopy;
    private Toolbar toolbar;
    private String textTranslate = "";
    private String meanTranslate ="";
    private Volumn mVolumn;
    private TextToSpeech textToSpeechENGLISH;
    private TextToSpeech textToSpeechVN;
    private TranslateViewModel translateViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        translateViewModel = ViewModelProviders.of(this).get(TranslateViewModel.class);
        translateViewModel.init();
        mVolumn = new Volumn(this);
        textToSpeechENGLISH = mVolumn.setTextToSpeechEnglish();
        textToSpeechVN = mVolumn.textToSpeechVN();
        getView();
        eventTranslate();
        coverTranslate();
        mvolumnMeanTranslate();
        mvolumnTextTranslate();
    }

    private void getView(){
        inputTextTranslate = (EditText) findViewById(R.id.textArea_information);
        btnMicro = (ImageButton) findViewById(R.id.btn_micro);
        txtMeanTranslate = (TextView) findViewById(R.id.mean_translate);
        btnVolumnText = (ImageButton) findViewById(R.id.volumn2);
        btnVolumnMean = (ImageButton) findViewById(R.id.volumn1);
        btnCopy = (ImageButton) findViewById(R.id.btn_copy);
        btnTslAnhViet = (Button) findViewById(R.id.btn_translate1);
        btnTslVietAnh = (Button) findViewById(R.id.btn_translate2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.keyboard_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Your code
                finish();
            }
        });
    }
    private void eventTranslate(){
        inputTextTranslate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnMicro.setImageDrawable(getDrawable(R.drawable.close));
                btnMicro.setTag("close");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnTslAnhViet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textTranslate = String.valueOf(inputTextTranslate.getText());
                translate(textTranslate);
            }
        });
    }
    private void coverTranslate(){
        btnMicro.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (btnMicro.getTag().equals("close")){
                    inputTextTranslate.setText("");
                    txtMeanTranslate.setText("");
                    meanTranslate = "";
                    textTranslate = "";
                    btnMicro.setImageDrawable(getDrawable(R.drawable.microphone));
                }else{
                    recognize();
                }
                Log.i("hihi", String.valueOf(btnMicro.getTag()));
            }
        });
    }
    private void recognize(){
        Intent speachIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speachIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speachIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speach to text");
        startActivityForResult(speachIntent, RECOGNIZE_RESULT);
    }
    private void translate(String text){
        translateViewModel.translate(text).observe(this, new Observer<String>() {

            @Override
            public void onChanged(String s) {
                meanTranslate = s;
                txtMeanTranslate.setText(s);
            }
        });
    }
    private void mvolumnTextTranslate(){
        btnVolumnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textTranslate.equals("")){
                    textToSpeechENGLISH.speak(textTranslate, TextToSpeech.QUEUE_FLUSH, null);
                }

            }
        });
    }
    private void mvolumnMeanTranslate(){
        btnVolumnMean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!meanTranslate.equals("")){
                    textToSpeechVN.speak(meanTranslate, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
    pri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RECOGNIZE_RESULT && resultCode == RESULT_OK){
            ArrayList<String> recognizeWord = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            textTranslate = recognizeWord.get(0);
            inputTextTranslate.setText(textTranslate);
            translate(textTranslate);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}