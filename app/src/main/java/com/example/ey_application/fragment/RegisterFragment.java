package com.example.ey_application.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ey_application.R;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    public interface Register{
        void dataRegister(String username, String phone, String email, String Password);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText mUserName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mBtnRegister;

    private Register register;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
       setUpUI(view);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkDataEntered()){
                   sendDataToAcitivity();
                }
            }
        });
        changeTextError();

        return view;
    }
    private void sendDataToAcitivity(){
        String username = String.valueOf(mUserName.getText()).trim();
        String phone = String.valueOf(mPhone.getText()).trim();
        String email = String.valueOf(mEmail.getText()).trim();
        String password = String.valueOf(mPassword.getText()).trim();
        register.dataRegister(username, phone, email, password);
    }
    private void setUpUI(View view){
        mUserName = view.findViewById(R.id.editUserName);
        mPhone = view.findViewById(R.id.editPhone);
        mEmail = view.findViewById(R.id.editEmail);
        mPassword = view.findViewById(R.id.editPassword);
        mPasswordAgain = view.findViewById(R.id.editPasswordAgain);
        mBtnRegister = view.findViewById(R.id.btn_register);
    }

    private boolean checkDataEntered(){
        if (FormatEdittext.isEmpty(mUserName) ||
                (FormatEdittext.isPhone(mPhone) == false) ||
                (FormatEdittext.isEmail(mEmail) == false) ||
                (FormatEdittext.isPassword(mPassword) == false)||
                (FormatEdittext.isEqualPassword(mPassword, mPasswordAgain))  ){
            return false;
        }

        return true;
    }
    private void changeTextError(){

        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isEmpty(mUserName);
            }
        });
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isPhone(mPhone);
            }
        });
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isEmail(mEmail);
            }
        });
        mPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isEqualPassword(mPassword, mPasswordAgain);
            }
        });
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                FormatEdittext.isPassword(mPassword);
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragment.Register){
            register = (RegisterFragment.Register) context;
        }
        else{
            throw new RuntimeException(context.toString()+
                    "must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        register = null;
    }
}