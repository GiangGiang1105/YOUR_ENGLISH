package com.example.ey_application.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Word.Detail;
import com.example.ey_application.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDetail extends RecyclerView.Adapter<RecyclerViewDetail.RecyclerViewHolder> {
    private List<Detail> listDefinition;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private boolean aBoolean = false;

   public RecyclerViewDetail(Context context) {
        listDefinition = new ArrayList<>();
        this.context = context;
    }
    public void setData(List<Detail> list){
        listDefinition = list;
        notifyDataSetChanged();
    }
    public void setCategory(boolean b){
       aBoolean = b;
    }
    private Context context;
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.definition_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {

        if ((listDefinition.get(position).getType() != null) && (listDefinition.get(position).getList()!= null)){
            holder.txtTypeWord.setText(listDefinition.get(position).getType());
        }
        if (listDefinition.get(position).getList() != null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    holder.itemRecyclerview.getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            layoutManager.setInitialPrefetchItemCount(listDefinition.get(position).getList().size());
            ListItemDetail listItemDefinition = new ListItemDetail(listDefinition.get(position).getList(), context, aBoolean);
            holder.itemRecyclerview.setLayoutManager(layoutManager);
            holder.itemRecyclerview.setAdapter(listItemDefinition);
            holder.itemRecyclerview.setRecycledViewPool(viewPool);

        }

    }

    @Override
    public int getItemCount() {
        return listDefinition.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView txtTypeWord;
        RecyclerView itemRecyclerview;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTypeWord = (TextView) itemView.findViewById(R.id.type);
            itemRecyclerview = (RecyclerView) itemView.findViewById(R.id.listview);
        }
    }
}
