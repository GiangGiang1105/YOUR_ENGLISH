package com.example.ey_application.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.activity.WordDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ListAdapterWord extends RecyclerView.Adapter<ListAdapterWord.ViewHolder>{

    private List<Word> listData;
    private Context context;
    WordItemClick wordItemClick;
    private CallBackData callBackData;
    public ListAdapterWord(Context context) {
        this.listData = new ArrayList<>();
        this.context = context;
    }
    public void Callback(CallBackData callBackData){
        this.callBackData = callBackData;
    }
    public void setData(List<Word> listData){
        this.listData.clear();
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public void setOnClickListener(WordItemClick wordItemClick){
        this.wordItemClick = wordItemClick;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textWord.setText(listData.get(position).getWord());
        if (listData.get(position).getStar() == 1){
            holder.checkMark.setChecked(true);
        }
        holder.checkMark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                callBackData.markWord(listData.get(position));
            }
        });
        holder.mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackData.deleteWord(listData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView textWord;
        CheckBox checkMark;
        ImageButton mRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = (TextView) itemView.findViewById(R.id.text_word);
            checkMark = (CheckBox) itemView.findViewById(R.id.mark);
            mRemove = (ImageButton) itemView.findViewById(R.id.remove);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wordItemClick != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            wordItemClick.onClick(v, position);
                        }
                    }

                }
            });
        }

    }
    public interface CallBackData{
        void deleteWord(Word word);
        void markWord(Word word);
    }

}