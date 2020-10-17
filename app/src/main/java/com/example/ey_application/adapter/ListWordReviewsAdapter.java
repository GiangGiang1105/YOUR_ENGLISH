package com.example.ey_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Reviews.WordReviews;
import com.example.ey_application.Model.Word.WordDictionary;
import com.example.ey_application.R;

import java.util.ArrayList;
import java.util.List;

public class ListWordReviewsAdapter extends RecyclerView.Adapter<ListWordReviewsAdapter.ViewHolderReviews> {
    private List<WordReviews> wordReviewsList = new ArrayList<>();
    private Context context;

    public ListWordReviewsAdapter(Context context) {
        this.context = context;
    }
    public void setData( List<WordReviews> data){
        wordReviewsList.clear();
       wordReviewsList.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderReviews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_word_reviews, parent, false);
        return  new ViewHolderReviews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderReviews holder, int position) {
        final WordReviews wordDictionary = wordReviewsList.get(position);
        String word = wordReviewsList.get(position).getWordReviews();
        holder.textWord.setText(word);
    }

    @Override
    public int getItemCount() {
        return wordReviewsList.size();
    }

    class ViewHolderReviews extends RecyclerView.ViewHolder{
        TextView textWord;
        public ViewHolderReviews(@NonNull View itemView) {
            super(itemView);
            textWord = (TextView) itemView.findViewById(R.id.text_word);

        }
    }

}
