package com.example.ey_application.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.WordViewModel;
import com.example.ey_application.adapter.ListAdapterWord;
import com.example.ey_application.myinterface.WordItemClick;
import com.example.ey_application.session.SessionUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddWord extends AppCompatActivity  implements ListAdapterWord.CallBackData, WordViewModel.ResultCallBackWord {
    public static final String TAG = AddWord.class.getSimpleName();
    private EditText editWord;
    private WordViewModel wordViewModel;
    private int id_user;
    private List<Word> listWord;
    private ListAdapterWord listAdapterWord;
    private RecyclerView mRecyclerView ;
    private  Button btnAddWord;
    private LinearLayoutManager llm;
    Toolbar toolbar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        getView();
        wordViewModel = ViewModelProviders.of(AddWord.this).get(WordViewModel.class);
        wordViewModel.setResultCallBackWord(this);
        SessionUser sessionUser = new SessionUser(getApplicationContext());
        sessionUser.getPreferences();
        id_user = sessionUser.getLoggedID();
        wordViewModel.getListWord(id_user);
        wordViewModel.listWord.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                Log.d(TAG, "onChanged: " + words.size());
                listWord = words;
                listAdapterWord.setData(listWord);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getView() {

        editWord = (EditText) findViewById(R.id.edit_word);
        btnAddWord = (Button) findViewById(R.id.btn_note);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);

        btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertWord();
            }
        });
        listWord = new ArrayList<>();
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setHasFixedSize(true);
        listAdapterWord = new ListAdapterWord(getApplicationContext());
        listAdapterWord.Callback(this);
        mRecyclerView.setAdapter(listAdapterWord);
        listAdapterWord.setOnClickListener(new WordItemClick() {
            @Override
            public void onClick(View v, int position) {
                intentWordDetail(listWord.get(position));
            }

            @Override
            public void onClickItem(View v, String word) {

            }
        });
    }



    private void insertWord() {
        String aWord = editWord.getText().toString().trim();
        Log.d(TAG, "insertWord: " + aWord);
        if (TextUtils.isEmpty(aWord)) {
            Toast.makeText(this, "Please input word you need save!", Toast.LENGTH_LONG).show();
        } else {
            for (Word word : listWord) {
                if (aWord.equals(word.getWord())) {
                    editWord.setText("");
                    return;
                }
            }
            wordViewModel.insertWord(id_user, aWord);
            editWord.setText("");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
    private void intentWordDetail(Word wordItem){
        Intent intent = new Intent(this, WordDetail.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Word", (Serializable) wordItem);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteWord(Word word) {
        wordViewModel.deleteWord(word.getId(), id_user);
    }

    @Override
    public void markWord(Word word) {
        if (word.getStar() == 1){
            wordViewModel.markWord(word.getId(), 0, id_user);
        }
        else{
            wordViewModel.markWord(word.getId(), 1, id_user);
        }

    }

    @Override
    public void onDeleteResultFail(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMarkResultFail(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInsertResultFail(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_LONG).show();
        }
    }
}