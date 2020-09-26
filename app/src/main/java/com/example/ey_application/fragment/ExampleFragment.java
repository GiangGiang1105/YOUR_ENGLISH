package com.example.ey_application.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ey_application.Model.Word.Detail;
import com.example.ey_application.Model.Word.Phonetics;
import com.example.ey_application.R;
import com.example.ey_application.Volumn;
import com.example.ey_application.activity.WordDetail;
import com.example.ey_application.adapter.RecyclerViewDetail;

import java.util.List;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExampleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExampleFragment extends Fragment implements WordDetail.WordDetailInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton btnVolumeUK;
    private ImageButton btnVolumeUS;
    private TextView txtWord;
    private TextView txtSpellWord;
    private RecyclerView listDefinition;
    private TextView txtMeanVietnamese;
    private RecyclerViewDetail recyclerViewDetail;
    private Volumn volumn;
    private TextToSpeech textToSpeechUK;
    private TextToSpeech textToSpeechUS;
    private String mWord;
    private TextToSpeech textToSpeechENGLISH;

    public ExampleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExampleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExampleFragment newInstance(String param1, String param2) {
        ExampleFragment fragment = new ExampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example, container, false);
        btnVolumeUK = (ImageButton) view.findViewById(R.id.imageVolumeUK);
        btnVolumeUS = (ImageButton) view.findViewById(R.id.imageVolumeUS);
        txtWord = (TextView) view.findViewById(R.id.word);
        txtSpellWord = (TextView) view.findViewById(R.id.spell_word);
        listDefinition = (RecyclerView) view.findViewById(R.id.list_definition11);
        txtMeanVietnamese = (TextView) view.findViewById(R.id.meanVietnamese);
        recyclerViewDetail = new RecyclerViewDetail(getContext());
        recyclerViewDetail.setCategory(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listDefinition.setLayoutManager(llm);
        listDefinition.setHasFixedSize(true);
        listDefinition.setAdapter(recyclerViewDetail);
        volumn = new Volumn(getContext());
        textToSpeechUK = volumn.textToSpeechUK();
        textToSpeechUS = volumn.textToSpeechUS();
        textToSpeechENGLISH = volumn.setTextToSpeechEnglish();
        btnVolumeUS.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (mWord.contains(" ")){
                    String utteranceId = UUID.randomUUID().toString();
                    textToSpeechENGLISH.speak(mWord, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                }else{
                    textToSpeechUS.speak(mWord, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        btnVolumeUK.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (mWord.contains(" ")){
                    String utteranceId = UUID.randomUUID().toString();
                    textToSpeechENGLISH.speak(mWord, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                }else{
                    textToSpeechUK.speak(mWord, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((WordDetail)context).createDataUpdateListener((WordDetail.WordDetailInterface) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((WordDetail) getActivity()).unDataUpdateListener((WordDetail.WordDetailInterface) this);
    }

    @Override
    public void sendExample(List<Detail> example) {

        if (example != null) {
            List<Detail> list =  example;
            recyclerViewDetail.setData(list);
        }
    }

    @Override
    public void sendDataWord(String word, String meanVietnamese) {
        if (word != null){
            txtWord.setText(word);
            mWord = word;
        }
        if (meanVietnamese != null){
            txtMeanVietnamese.setText(meanVietnamese);
        }
    }

    @Override
    public void sendPhonetics(Phonetics phonetics) {
        if (phonetics != null) {
            if (phonetics.getText() != null) {
                txtSpellWord.setText(phonetics.getText());
            }
        }
    }

    @Override
    public void sendDefinition(List<Detail> detailsDefinition) {

    }

    @Override
    public void sendSynonyms(List<Detail> synonyms) {

    }
}