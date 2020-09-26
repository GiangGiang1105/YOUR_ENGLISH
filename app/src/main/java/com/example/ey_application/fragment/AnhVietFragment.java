package com.example.ey_application.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ey_application.Model.Word.Detail;
import com.example.ey_application.Model.Word.Phonetics;
import com.example.ey_application.R;
import com.example.ey_application.activity.WordDetail;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnhVietFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnhVietFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageButton btnVolume;
    private TextView txtWord;
    private TextView txtSpellWord;
    private ListView listDefinition;
    public AnhVietFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnhVietFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnhVietFragment newInstance(String param1, String param2) {
        AnhVietFragment fragment = new AnhVietFragment();
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
        View view = inflater.inflate(R.layout.fragment_anh_viet, container, false);
        btnVolume = (ImageButton) view.findViewById(R.id.imageVolume);
        txtWord = (TextView) view.findViewById(R.id.word);
        txtSpellWord = (TextView) view.findViewById(R.id.spell_word);
        listDefinition = (ListView) view.findViewById(R.id.list_definition);
        return view;
    }




}