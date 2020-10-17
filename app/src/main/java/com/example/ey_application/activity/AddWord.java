package com.example.ey_application.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.WordViewModel;
import com.example.ey_application.adapter.ListAdapterWord;
import com.example.ey_application.myinterface.ResultCallBackWord;
import com.example.ey_application.myinterface.WordItemClick;
import com.example.ey_application.session.SessionUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddWord extends AppCompatActivity  implements  ResultCallBackWord {
    public static final String TAG = AddWord.class.getSimpleName();
    private Toolbar toolbar;
    private EditText editWord;
    private WordViewModel wordViewModel;
    private int id_user;
    private List<Word> listWord;
    private ListAdapterWord listAdapterWord;
    private RecyclerView mRecyclerView ;
    private  Button btnAddWord;
    private Button btnRemoveTo;
    private Button btnDeleteAll;
    private Button btnMarkAll;
    private Button btnCancel;
    private LinearLayoutManager llm;
    SessionUser sessionUser;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        getView();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your new word");
        toolbar.setNavigationIcon(R.drawable.keyboard_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        wordViewModel = ViewModelProviders.of(AddWord.this).get(WordViewModel.class);
        wordViewModel.setResultCallBackWord(this);
        sessionUser = new SessionUser(getApplicationContext());
        sessionUser.getPreferences();
        id_user = sessionUser.getLoggedID();
        wordViewModel.getListWord(id_user);
        wordViewModel.listWord.observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
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
        btnRemoveTo = (Button) findViewById(R.id.removeTo);
        btnCancel = (Button) findViewById(R.id.cancelAll);
        btnDeleteAll = (Button) findViewById(R.id.deleteAll);
        btnMarkAll = (Button) findViewById(R.id.markAll);

        btnRemoveTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogAddAllToReviewsList();
            }
        });

        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogDeleteAll();
            }
        });
        btnMarkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapterWord.markAll();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAdapterWord.cancel();
            }
        });

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
        mRecyclerView.setNestedScrollingEnabled(false);
        listAdapterWord = new ListAdapterWord(getApplicationContext());
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
    private void createDialogAddAllToReviewsList(){
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("Do you want to add all the selected words to the checklist?");
        alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Integer> listId = new ArrayList<>();
                List<String> listStringWord = new ArrayList<>();
                for (int i : listAdapterWord.listPosition){
                    listId.add(listWord.get(i).getId());
                    listStringWord.add(listWord.get(i).getWord());
                }
                wordViewModel.markAllWord(id_user, listId);
                wordViewModel.insertAllWord(id_user, listStringWord);
            }
        });
        alBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                wordViewModel.getListWord(id_user);

            }
        });
        AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();
    }
    private void createDialogDeleteAll(){
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setMessage("Do you want to delete all selected words?");
        alBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Integer> listId = new ArrayList<>();
                for (int i : listAdapterWord.listPosition){
                    listId.add(listWord.get(i).getId());
                }
                wordViewModel.deleteAllWord(id_user, listId);
            }
        });
        alBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                wordViewModel.getListWord(id_user);

            }
        });
        AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();
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

    @Override
    public void onInsertReviewsResult(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), "You added a word to you review list!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteReviewsResult(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), "You was remove a word from your review list!  ", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteListReviewsResult(boolean bool) {

    }

    @Override
    public void onInsertAllReviewsResult(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), "You was add all words to list reviews word", Toast.LENGTH_LONG).show();
            listAdapterWord.listPosition.clear();
        }
        else{
            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteAllWord(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), "You was delete all words", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Server Error!", Toast.LENGTH_LONG).show();
        }
    }
}