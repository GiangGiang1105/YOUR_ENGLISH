package com.example.ey_application.activity;

import androidx.annotation.RequiresApi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;


import com.example.ey_application.R;
import com.example.ey_application.utils.LocaleHelper;


public class GeneralSetting extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    private Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.keyboard_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intentBack();
                finish();
            }
        });
        setupSharedPreferences();
    }
    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.key_language))) {
            String mLanguageCode = sharedPreferences.getString(key, "en");
            Toast.makeText(this, ""+mLanguageCode , Toast.LENGTH_LONG).show();
            LocaleHelper.changeLang(mLanguageCode, this);
            restartApplication();
        }

    }
    void restartApplication() {
        Intent i = new Intent(GeneralSetting.this, GeneralSetting.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    void intentBack(){
        Intent i = new Intent(GeneralSetting.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intentBack();
    }
}