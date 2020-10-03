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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Word.WordDictionary;
import com.example.ey_application.R;
import com.example.ey_application.activity.Dictionary;
import com.example.ey_application.database.Repository.DatabaseAccess;
import com.example.ey_application.myinterface.Result;
import com.example.ey_application.myinterface.WordItemClick;

import java.util.ArrayList;
import java.util.List;

public class ListDataWordAdapter extends  RecyclerView.Adapter<ListDataWordAdapter.RecyclerViewHolder>  {
    public  List<WordDictionary> data;
    private List<WordDictionary> dataFilter;
    public Context context;
    private WordItemClick wordItemClick;
    private DatabaseAccess databaseAccess;
    private Result result;
    public ListDataWordAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
        databaseAccess = new DatabaseAccess((Application) context);
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
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        String word = data.get(position).getWord().toLowerCase();
        holder.textWord.setText(word);
        if (data.get(position).getMark() == 1){
            holder.checkBox.setChecked(true);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    databaseAccess.markWord(data.get(position).getId(), 1);
                }
                else{
                    databaseAccess.markWord(data.get(position).getId(), 0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class  RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textWord;
        ImageButton imageButton;
        CheckBox checkBox;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = (TextView) itemView.findViewById(R.id.text_word);
            imageButton = (ImageButton) itemView.findViewById(R.id.remove);
            checkBox = (CheckBox) itemView.findViewById(R.id.mark);
            imageButton.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        wordItemClick.onClickItem(v, data.get(position).getWord());
                    }
                }
            });


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
