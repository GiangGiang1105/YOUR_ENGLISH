package com.example.ey_application.database.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.ey_application.Model.MessageFromServer;
import com.example.ey_application.Model.Reviews.Score;
import com.example.ey_application.Model.Reviews.WordReviews;
import com.example.ey_application.Model.Translate.HistoryTranslate;
import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.database.API.GetForWord;
import com.example.ey_application.database.RetrofitService;
import com.example.ey_application.myinterface.ResultCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

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
        getForWord.deleteWord(id, id_user).enqueue(new Callback<MessageFromServer>() {
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
   /* public void insertWordReviews(final int id_user, String word) {
        getForWord.insertWordReviews(id_user, word.trim()).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(@NotNull Call<MessageFromServer> call, @NotNull Response<MessageFromServer> response) {
                if (response.isSuccessful()){
                    callback.onInsertReviewsResult(true, id_user);
                }

            }

            @Override
            public void onFailure(@NotNull Call<MessageFromServer> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: insertWord: " + t.getMessage());
            }
        });
    }*/
    public void deleteWordReviews(String wordReview, final int id_user){
        getForWord.deleteWordReviews(wordReview.trim(), id_user).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                if(response.body().getStatus() == 1){
                    callback.onDeleteReviewsResult(true, id_user);
                }
            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Log.d(TAG, "onFailure:deleteWord : " + t.getMessage());
            }
        });
    }
    public void getListWordReviews(int id_user) {
        getForWord.listWordReviews(id_user).enqueue(new Callback<List<WordReviews>>() {
            @Override
            public void onResponse(Call<List<WordReviews>> call, Response<List<WordReviews>> response) {
                if (response.isSuccessful()){
                    Log.i("getword", "repository");
                    callback.onGetWordsReviews(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<WordReviews>> call, Throwable t) {
                Log.d(TAG, "onFailure:listWordReviews " + t.getMessage());
            }
        });
    }
    public void deleteListWordReviews(final int id_user){
        getForWord.deleteListWordReviews(id_user).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                if (response.isSuccessful()){
                    callback.onDeleteListReviewsResult(true, id_user);
                }
            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Log.d(TAG, "onFailure:listWordReviews " + t.getMessage());
            }
        });
    }
    public void unMarkWord( final  int id_user){
        getForWord.unMarkWord(id_user).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                if (response.isSuccessful()){
                    Log.i("success", "successs");
                }
            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Log.d(TAG, "onFailure:markWord" + t.getMessage());
            }
        });
    }
    public void insertAllWord(final int idUser, List<String> list,  List<Integer> listId){
        final int[] i = {0};
        for (int j = 0; j < list.size(); j++){
            getForWord.insertWordReviews(idUser,list.get(j), listId.get(j) ).enqueue(new Callback<MessageFromServer>() {
                @Override
                public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                    if (response.isSuccessful()){
                        if (response.body().getStatus() == 0){
                            i[0] = 1;
                        }
                        Log.i("boooooo", String.valueOf(i[0]));

                    }
                }

                @Override
                public void onFailure(Call<MessageFromServer> call, Throwable t) {
                    Log.d(TAG, "onFailure:markWord" + t.getMessage());
                }
            });
           if (i[0] == 1){
               break;
           }
        }

        if (i[0] == 0){
            callback.onInsertAllReviewsResult(true, idUser);
        }
        else{
            callback.onInsertAllReviewsResult(false, idUser);
        }
    }
    /*public void markAllWord(int idUser, List<Integer> listId){
        for (int i : listId){
            getForWord.markWord(i, 1).enqueue(new Callback<MessageFromServer>() {
                @Override
                public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                    if (response.isSuccessful()){
                        if (response.body().getStatus() == 0){
                            Log.i("success", "successs");
                        }
                    }
                }

                @Override
                public void onFailure(Call<MessageFromServer> call, Throwable t) {
                    Log.d(TAG, "onFailure:markWord" + t.getMessage());
                }
            });
        }

    }
*/    public void deleteAllWord(final int idUser, List<Integer> listId){
        final int[] i = {0};
        for (int id : listId){
            getForWord.deleteWord(id, idUser).enqueue(new Callback<MessageFromServer>() {
                @Override
                public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                    if (response.isSuccessful()){
                        if (response.body().getStatus() == 0){
                            i[0] = 1;
                        }
                        Log.i("boooooo", String.valueOf(i[0]));

                    }

                }

                @Override
                public void onFailure(Call<MessageFromServer> call, Throwable t) {
                    Log.d(TAG, "onFailure:deleteWord : " + t.getMessage());
                }
            });
        }
        if (i[0] == 0){
            callback.onDeleteAllWord(true, idUser);
        }
        else{
            callback.onDeleteAllWord(false, idUser);
        }

    }
    public void updateScore(final int IdUser,  final int  kingofreview, int truth, int fail){
        getForWord.updateScore( IdUser, kingofreview, truth, fail).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                if (response.isSuccessful()){
                   callback.onUpdateScore(true, IdUser, kingofreview);
                }
                else{
                    callback.onUpdateScore(false,IdUser, kingofreview);
                }
            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Log.i("unsucesss", t.getMessage());
            }
        });
    }

    public void showScore(int idUser,int kingofreview) {
        getForWord.showScore(idUser, kingofreview).enqueue(new Callback<List<Score>>() {
            @Override
            public void onResponse(Call<List<Score>> call, Response<List<Score>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.onShowCore(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Score>> call, Throwable t) {
                Log.i("unsucesss", t.getMessage());
            }
        });
    }
    public void createHistoryTranslate(final int idUser, String text, String mean){
        getForWord.createHistoryTranslate(idUser, text, mean).enqueue(new Callback<MessageFromServer>() {
            @Override
            public void onResponse(Call<MessageFromServer> call, Response<MessageFromServer> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        callback.onResultCreateHistoryTranslate(true, idUser);
                    }
                }
            }

            @Override
            public void onFailure(Call<MessageFromServer> call, Throwable t) {
                Log.i("unsucesss", t.getMessage());
            }
        });
    }
    public void showHistoryTranslate(int idUser){
        getForWord.showHistoryTranslate(idUser).enqueue(new Callback<List<HistoryTranslate>>() {
            @Override
            public void onResponse(Call<List<HistoryTranslate>> call, Response<List<HistoryTranslate>> response) {
                if (response.isSuccessful()){
                    callback.onShowHistoryTranslate(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HistoryTranslate>> call, Throwable t) {
                Log.i("unsucesss", t.getMessage());
            }
        });
    }
}