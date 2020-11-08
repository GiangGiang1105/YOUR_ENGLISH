package com.example.ey_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.Model.Translate.HistoryTranslate;
import com.example.ey_application.R;

import java.util.ArrayList;
import java.util.List;

public class ListHistoryTranslateAdapter extends RecyclerView.Adapter<ListHistoryTranslateAdapter.ViewHolder> {
    private List<HistoryTranslate> historyTranslateList = new ArrayList<>();
    private Context context;
    public ListHistoryTranslateAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<HistoryTranslate> list){
        historyTranslateList.clear();
        historyTranslateList.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history_translate, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryTranslate historyTranslate = historyTranslateList.get(position);
        holder.txtText.setText(historyTranslate.getText());
        holder.txtMean.setText(historyTranslate.getMean());
    }

    @Override
    public int getItemCount() {
        return historyTranslateList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtText;
        TextView txtMean;
        ImageButton btnStar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtText = (TextView) itemView.findViewById(R.id.text);
            txtMean = (TextView) itemView.findViewById(R.id.mean);
            btnStar = (ImageButton) itemView.findViewById(R.id.btn_star);
        }
    }
}
