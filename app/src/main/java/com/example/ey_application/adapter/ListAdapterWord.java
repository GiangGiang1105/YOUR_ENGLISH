package com.example.ey_application.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Word.Word;
import com.example.ey_application.R;
import com.example.ey_application.myinterface.WordItemClick;

import java.util.ArrayList;
import java.util.List;


public class ListAdapterWord extends RecyclerView.Adapter<ListAdapterWord.ViewHolder>{

    private List<Word> listData;
    private Context context;
    WordItemClick wordItemClick;
    public List<Integer> listPosition = new ArrayList<>();
    public List<Integer> listPositionDelete = new ArrayList<>();
    public ListAdapterWord(Context context) {
        this.listData = new ArrayList<>();
        this.context = context;
    }

    public void setData(List<Word> listData){
        this.listData.clear();
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }
    public void markAll(){
        for (int i = 0 ; i < listData.size(); i++){
            if (listData.get(i).getStar() == 0){
                listPosition.add(i);
                listPositionDelete.add(i);
                listData.get(i).setStar(1);
            }
        }
        notifyDataSetChanged();
    }
    public void cancel(){
        for (int i = 0 ; i < listData.size(); i++){
            listPosition.clear();
            listPositionDelete.clear();
            listData.get(i).setStar(0);
        }
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
            listPositionDelete.add(position);
        }else{
            holder.checkMark.setChecked(false);
        }
        holder.checkMark.setOnCheckedChangeListener(null);
        holder.checkMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listData.get(position).getStar() == 1){
                    listData.get(position).setStar(0);
                    listPosition.remove(new Integer(position));
                    listPositionDelete.remove(new Integer(position));
                }
                else{
                    listData.get(position).setStar(1);
                    listPosition.add(position);
                    listPositionDelete.add(position);
                }
                notifyDataSetChanged();
                Log.i("position", String.valueOf(listPosition.size()));
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = (TextView) itemView.findViewById(R.id.text_word);
            checkMark = (CheckBox) itemView.findViewById(R.id.mark);
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
}