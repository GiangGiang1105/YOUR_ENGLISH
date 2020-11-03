package com.example.ey_application;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class Volumn {
    private TextToSpeech textToSpeechUK;
    private TextToSpeech textToSpeechUS;
    private TextToSpeech textToSpeechENGLISH;
    private TextToSpeech textToSpeechVN;
    private Context context;
    public Volumn(Context context) {
        this.context = context;
    }
    public TextToSpeech textToSpeechUK(){
            textToSpeechUK = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if(status != TextToSpeech.ERROR) {
                        textToSpeechUK.setLanguage(Locale.UK);
                    }
                }
            });
            return textToSpeechUK;
    }
    public TextToSpeech setTextToSpeechEnglish(){
        textToSpeechENGLISH = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeechENGLISH.setLanguage(Locale.ENGLISH);
                }
            }
        });
       return textToSpeechENGLISH;

    }
    public TextToSpeech textToSpeechUS(){
        textToSpeechUS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeechUS.setLanguage(Locale.US);
                }
            }
        });

        return textToSpeechUS;
    }
    public TextToSpeech textToSpeechVN(){
        textToSpeechUS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeechUS.setLanguage(Locale.ROOT);
                }
            }
        });

        return textToSpeechUS;
    }
}
