package com.example.ey_application.fragment;

import android.companion.WifiDeviceFilter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ey_application.R;
import com.example.ey_application.activity.Login_Register;
import com.example.ey_application.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    public interface UserLogin{
        void userInfo(String name, String email, String password);
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editTextUserName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    private UserLogin userLogin;
    private String name;
    private String email;
    private String password;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
       getView(view);
       checkDataEditText();
       buttonLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (checkData()){
                   getTextView();
                   userLogin.userInfo(name, email, password);
               }
           }
       });

        return view;
    }
    private void getView(View view){
        editTextUserName = (EditText) view.findViewById(R.id.editUserName);
        editTextEmail = (EditText) view.findViewById(R.id.editEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editPassword);
        buttonLogin = (Button) view.findViewById(R.id.button_login);
    }
    private void getTextView(){
        name = String.valueOf(editTextUserName.getText()).trim();
        email = String.valueOf(editTextEmail.getText()).trim();
        password = String.valueOf(editTextPassword.getText()).trim();
    }
    private void IntentMain(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
    private boolean checkData(){
        if (FormatEdittext.isEmpty(editTextUserName) ||
                (FormatEdittext.isEmail(editTextEmail) == false) ||
                (FormatEdittext.isPassword(editTextPassword) == false)){
            return false;
        }
        return true;
    }
    private void checkDataEditText(){
        editTextUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isEmpty(editTextUserName);
            }
        });
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isEmail(editTextEmail);
            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isPassword(editTextPassword);
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragment.Register){
            userLogin = (LoginFragment.UserLogin) context;
        }
        else{
            throw new RuntimeException(context.toString()+
                    "must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        userLogin = null;
    }

}