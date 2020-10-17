package com.example.ey_application.adapter;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Word.WordDictionary;
import com.example.ey_application.R;
import com.example.ey_application.database.Repository.DatabaseAccess;
import com.example.ey_application.myinterface.CallBackDictionary;
import com.example.ey_application.myinterface.WordItemClick;

import java.util.ArrayList;
import java.util.List;

public class ListDataWordAdapter extends  RecyclerView.Adapter<ListDataWordAdapter.RecyclerViewHolder>  {
    public  List<WordDictionary> data;
    private List<WordDictionary> dataFilter;
    public Context context;
    private WordItemClick wordItemClick;
    List<Integer> listPossitionChecked = new ArrayList<>();
    public ListDataWordAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
    }
    public void setData( List<WordDictionary> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    public void setOnClickListener(WordItemClick wordItemClick){
        this.wordItemClick = wordItemClick;}

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_word, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        final WordDictionary wordDictionary = data.get(position);
        final String word = data.get(position).getWord().toLowerCase();
        holder.textWord.setText(word);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class  RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textWord;
        CheckBox checkBox;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = (TextView) itemView.findViewById(R.id.text_word);
            checkBox = (CheckBox) itemView.findViewById(R.id.mark);
            checkBox.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        wordItemClick.onClickItem(v, data.get(position).getWord());
                    }
                }
            });
            this.setIsRecyclable(false);
        }
    }
    public Filter getFilter(final List<WordDictionary> words){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<WordDictionary> filteredList = new ArrayList<>();
                for (WordDictionary row : words) {
                    if (row.getWord().toLowerCase().contains(charString.toLowerCase())){
                        filteredList.add(row);
                    }
                }
                dataFilter = filteredList;
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<WordDictionary>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
