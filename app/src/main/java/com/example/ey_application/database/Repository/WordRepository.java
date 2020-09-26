package com.example.ey_application.database.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.ey_application.Model.MessageFromServer;
import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.database.API.GetForWord;
import com.example.ey_application.database.RetrofitService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WordRepository {
    public static final String TAG = WordRepository.class.getSimpleName();
    private static WordRepository wordReponsitory;
    private GetForWord getForWord;
    private ResultCallback callback;

    private WordRepository() {
        getForWord = RetrofitService.createService(GetForWord.class);
    }

    public static WordRepository getInstance() {
        if (wordReponsitory == null) {
            wordReponsitory = new WordRepository();
        }
        return wordReponsitory;
    }

    public void setCallback(ResultCallback callback) {
        this.callback = callback;
    }

    public void insertWord(final int id_user, String word) {
        getForWord.insertWord(id_user, word).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(@NotNull Call<MessageFromServer> call, @NotNull Response<MessageFromServer> response) {
                if (response.isSuccessful()){
                    callback.onInsertResult(true, id_user);
                }

            }

            @Override
            public void onFailure(@NotNull Call<MessageFromServer> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: insertWord: " + t.getMessage());
            }
        });
    }

    public void getListWordFromApi(int id_user) {
        getForWord.listWord(id_user).enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(@NotNull Call<List<Word>> call, @NotNull Response<List<Word>> response) {
                if (response.isSuccessful()) {
                    callback.onGetWordsResult(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Word>> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: getListWordFromApi: " + t.getMessage());
            }
        });
    }
    public void deleteWord(int id, final int id_user){
        getForWord.deleteWord(id).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                if(response.isSuccessful()){
                    callback.onDeleteResult(true, id_user);
                }

            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Log.d(TAG, "onFailure:deleteWord : " + t.getMessage());
            }
        });
    }
    public void markWord(final int id, int bool, final  int id_user){
        getForWord.markWord(id,bool ).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                if (response.isSuccessful()){
                    callback.onMarkResult(true, id_user);
                }
            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Log.d(TAG, "onFailure:markWord" + t.getMessage());
            }
        });
    }

    public interface ResultCallback {
        void onGetWordsResult(List<Word> data);

        void onInsertResult(boolean success, int userId);
        void onDeleteResult(boolean success, int userId);
        void onMarkResult(boolean success, int userId);
    }
}