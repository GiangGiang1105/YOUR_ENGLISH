package com.example.ey_application.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import com.example.ey_application.R;

import java.util.regex.Pattern;

public class FormatEdittext {
    private static final Pattern PATTERNPASSWORD = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
    private static final Pattern PATTERNPHONE = Pattern.compile("(09|01[2|6|8|9])([0-9].{6,10})");
    static boolean isEmpty(EditText username){
        CharSequence charSequence = username.getText().toString();
        boolean bool = TextUtils.isEmpty(charSequence) && (charSequence.length() > 5 && charSequence.length() <20);
        if (TextUtils.isEmpty(charSequence) ){
            username.requestFocus();
            username.setError("You must enter user name to register!");
        }else if ((charSequence.length() < 5 || charSequence.length() >20)){
            username.requestFocus();
            username.setError("You must enter user name have length from 5 to 20 character!");
        }else{
            username.setError(null);
        }
        return TextUtils.isEmpty(charSequence) && (charSequence.length() > 5 && charSequence.length() <20);
    }
    static boolean isEmail(EditText email){
        CharSequence charSequence = email.getText().toString();
        if (TextUtils.isEmpty(charSequence)){
            email.requestFocus();
            email.setError("You must enter email to register!");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()){
           email.requestFocus();
            email.setError("You must enter email valid!");
        }else {
            email.setError(null);
        }
        return (!TextUtils.isEmpty(charSequence)) && Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }
    static boolean isPhone(EditText phone){
        CharSequence charSequence = phone.getText().toString();
        if (TextUtils.isEmpty(charSequence)){
            phone.requestFocus();
            phone.setError("You must enter phone to register!");

        }else if (!PATTERNPHONE.matcher(charSequence).matches()){
            phone.requestFocus();
            phone.setError("You must enter phone valid!");
        }else {
            phone.setError(null);
        }
        return (!TextUtils.isEmpty(charSequence)) && PATTERNPHONE.matcher(charSequence).matches();
    }
    static boolean isPassword(EditText password){
        CharSequence charSequence = password.getText().toString();
        if (TextUtils.isEmpty(charSequence)){
            password.requestFocus();
            password.setError("You must enter password to register!");
        }else if (!PATTERNPASSWORD.matcher(charSequence).matches()){
            password.requestFocus();
            password.setError("You must enter password includes characters A-Z, a-z, 0-9!");
        }else {
            password.setError(null);
        }
        return (!TextUtils.isEmpty(charSequence)) && PATTERNPASSWORD.matcher(charSequence).matches();
    }
    static boolean isEqualPassword(EditText mPassword, EditText mPasswordAgain){
        CharSequence password = mPassword.getText().toString();
        CharSequence passwordAgain = mPasswordAgain.getText().toString();
        if (TextUtils.isEmpty(passwordAgain)){
            mPasswordAgain.requestFocus();
            mPasswordAgain.setError("You must enter password again to register!");
        }else if (!passwordAgain.equals(password)){
            mPasswordAgain.requestFocus();
            mPasswordAgain.setError("You must enter password again same password!");
        }else {
            mPasswordAgain.setError(null);
        }
        return TextUtils.isEmpty(passwordAgain) && passwordAgain.equals(password);
    }
}
