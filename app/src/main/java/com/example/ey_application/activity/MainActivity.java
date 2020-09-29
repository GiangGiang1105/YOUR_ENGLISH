package com.example.ey_application.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.ey_application.R;
import com.example.ey_application.ViewModel.UsersViewModel;

public class MainActivity extends AppCompatActivity {
    private Button addWord;
    private Button btnReview;
    private Button btnDictionary;
    private ImageButton btn_account;
    private  UsersViewModel usersViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), AddWord.class);
                startActivity(intent);
            }
        });
       btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), Reviews.class);
                startActivity(intent);
            }
        });
        btnDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), Dictionary.class);
                startActivity(intent);
            }
        });
        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), MyAccount.class);
                startActivity(intent);
            }
        });

    }
    private void getView(){
        addWord = (Button) findViewById(R.id.add_word);
        btnReview = (Button) findViewById(R.id.review);
        btnDictionary = (Button) findViewById(R.id.dictionary);
        btn_account = (ImageButton) findViewById(R.id.fab);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return;
    }
}