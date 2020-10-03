package com.example.ey_application.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ey_application.R;
import com.example.ey_application.Volumn;
import com.example.ey_application.myinterface.WordItemClick;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;

import java.util.List;
import java.util.UUID;

public class ListItemDetail extends RecyclerView.Adapter<ListItemDetail.ViewHolderItem> {
    private List<String> list;
    private Context context;
    private boolean bool;

    public ListItemDetail(List<String> list, Context context, boolean bool) {
        this.list = list;
        this.context = context;
        this.bool = bool;
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_basic, viewGroup, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.txtText.setText(list.get(position));
        holder.setOnClickItem(new WordItemClick() {
            @Override
            public void onClick(View v, int position) {
                showTranslate(list.get(position));
            }

            @Override
            public void onClickItem(View v, String word) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void showTranslate(final String text){
        final TranslateAPI translateAPI = new TranslateAPI(Language.ENGLISH, Language.VIETNAMESE, text);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String s) {
                createDialog(text, s);
            }

            @Override
            public void onFailure(String s) {
                Log.i("Error translate ", s);
            }
        });
    }
    public void createDialog(final String text, String s){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_dialog);
        ImageButton volumn = (ImageButton) dialog.findViewById(R.id.volumn);
        final TextView definition  = (TextView) dialog.findViewById(R.id.definition);
        definition.setText(text);
        TextView translate = (TextView) dialog.findViewById(R.id.translate);
        translate.setText(s);
        Volumn volumn1 = new Volumn(context);
        final TextToSpeech textToSpeech = volumn1.setTextToSpeechEnglish();
        volumn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                String utteranceId = UUID.randomUUID().toString();
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            }
        });
        dialog.show();
    }


    class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtText;
        private WordItemClick wordItemClick;
        public void setOnClickItem(WordItemClick wordItemClick){
            this.wordItemClick = wordItemClick;
        }
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            txtText = (TextView) itemView.findViewById(R.id.definition);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            wordItemClick.onClick(v, getAdapterPosition());
        }
    }
}
