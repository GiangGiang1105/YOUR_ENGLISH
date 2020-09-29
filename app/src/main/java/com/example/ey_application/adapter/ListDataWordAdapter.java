package com.example.ey_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.R;

import java.util.List;

public class ListDataWordAdapter extends  RecyclerView.Adapter<ListDataWordAdapter.RecyclerViewHolder> {
    public  List<String> data;
    public Context context;
    public ListDataWordAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_word, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String word = data.get(position).toLowerCase();
        holder.textWord.setText(word);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class  RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textWord;
        ImageButton imageButton;
        private WordItemClick wordItemClick;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = (TextView) itemView.findViewById(R.id.text_word);
            imageButton = (ImageButton) itemView.findViewById(R.id.remove);
            imageButton.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wordItemClick.onClick(v, getAdapterPosition());
                }
            });


        }
    }
}
