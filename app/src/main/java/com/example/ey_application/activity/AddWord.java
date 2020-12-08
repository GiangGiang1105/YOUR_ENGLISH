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
import android.widget.ImageButton;
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
    private ImageButton btnAddWord;
    private Button btnRemoveTo;
    private Button btnDeleteAll;
    private Button btnMarkAll;
    private LinearLayoutManager llm;
    SessionUser sessionUser;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        getView();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_add_word));
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
        btnAddWord = (ImageButton) findViewById(R.id.btn_note);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        btnRemoveTo = (Button) findViewById(R.id.removeTo);
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
                if (btnMarkAll.getText().equals(getString(R.string.mark_all))){
                    listAdapterWord.markAll();
                    btnMarkAll.setText(getString(R.string.cancel));
                }
                else{
                    listAdapterWord.cancel();
                    btnMarkAll.setText(getString(R.string.mark_all));
                }


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
            Toast.makeText(this, getString(R.string.message_save_word), Toast.LENGTH_LONG).show();
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
        alBuilder.setMessage(getString(R.string.question_add_all));
        alBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Integer> listId = new ArrayList<>();
                List<String> listStringWord = new ArrayList<>();
                for (int i : listAdapterWord.listPosition){
                    listId.add(listWord.get(i).getId());
                    listStringWord.add(listWord.get(i).getWord());
                }
                wordViewModel.insertAllWord(id_user, listStringWord, listId);
            }
        });
        alBuilder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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
        alBuilder.setMessage(getString(R.string.question_delete_all));
        alBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Integer> listId = new ArrayList<>();
                for (int i : listAdapterWord.listPositionDelete){
                    listId.add(listWord.get(i).getId());
                    Log.i("ListId", String.valueOf(listWord.get(i).getId()));
                }

                wordViewModel.deleteAllWord(id_user, listId);
            }
        });
        alBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
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
            Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMarkResultFail(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(),  getString(R.string.server_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInsertResultFail(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(),  getString(R.string.server_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInsertReviewsResult(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), getString(R.string.message_add_review), Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteReviewsResult(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), getString(R.string.message_remove_review), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteListReviewsResult(boolean bool) {

    }

    @Override
    public void onInsertAllReviewsResult(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), getString(R.string.message_add_all_word_review), Toast.LENGTH_LONG).show();
            listAdapterWord.listPosition.clear();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteAllWord(boolean bool) {
        if (bool){
            Toast.makeText(getApplicationContext(), getString(R.string.message_delete_all_word), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
        }
    }
}