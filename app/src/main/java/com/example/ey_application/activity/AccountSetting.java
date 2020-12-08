package com.example.ey_application.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ey_application.Model.Users;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.UsersViewModel;
import com.example.ey_application.session.SessionUser;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class AccountSetting extends AppCompatActivity implements UsersViewModel.ResultUser {
    private Toolbar toolbar;
    private Button btnSave;
    private EditText editUserName;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editPassword;
    private UsersViewModel usersViewModel;
    private Users user;
    private String userName;
    private String mPhone;
    private String mEmail;
    private String mPassword;
    SessionUser sessionUser;
    private int id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.keyboard_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                intentBack();
                finish();
            }
        });
        getView();
        setDataForEditText();
        changePassword();
        changeInfoUser();
    }
    void changePassword(){
        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputChangePassDialog();
            }
        });
    }
    void changeInfoUser(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                getTextEditText();
                usersViewModel.updateUser(mEmail, mPhone, mPassword, userName, id_user);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    void getTextEditText(){
        userName = String.valueOf(editUserName.getText());
        mPhone = String.valueOf(editPhone.getText());
        mEmail = String.valueOf(editEmail.getText());
        mPassword = Base64.getEncoder()
                .encodeToString(String.valueOf(editPassword.getText()).getBytes());


    }
    void showInputChangePassDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(AccountSetting.this);
        View promptView = layoutInflater.inflate(R.layout.change_password, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountSetting.this);
        alertDialogBuilder.setTitle(R.string.change_password);
        alertDialogBuilder.setView(promptView);

        final EditText editPasswordOld = (EditText) promptView.findViewById(R.id.passwordold);
        final EditText editPasswordNew = (EditText) promptView.findViewById(R.id.password_new);
        final EditText editPasswordConfirm = (EditText) promptView.findViewById(R.id.password_confirm);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.btn_change, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    public void onClick(DialogInterface dialog, int id) {
                        String passwordOld = String.valueOf(editPasswordOld.getText());
                        String passwordNew = String.valueOf(editPasswordNew.getText());
                        String passwordConfirm = String.valueOf(editPasswordConfirm.getText());
                        checkPasswordNew(passwordOld, passwordNew, passwordConfirm);
                    }
                })
                .setNegativeButton(R.string.btn_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void checkPasswordNew(String passwordOld, String passwordNew, String passwordConfirm){
        String password = decodePassword(user.getPassword());
        if (!passwordOld.equals(password)){
            Toast.makeText(this, R.string.message_check_passwordold, Toast.LENGTH_LONG ).show();
            showInputChangePassDialog();
        }else if (passwordNew.length() < 8){
            Toast.makeText(this, R.string.message_checkpasswordnewlengh, Toast.LENGTH_LONG ).show();
            showInputChangePassDialog();
        }else if (!passwordConfirm.equals(passwordNew)){
            Toast.makeText(this, R.string.message_check_passwordconfirm, Toast.LENGTH_LONG).show();
            showInputChangePassDialog();
        }else{
            editPassword.setText(passwordNew);
        }
    }
    void getView(){
        editUserName = (EditText) findViewById(R.id.editUserName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPhone = (EditText) findViewById(R.id.editPhone);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnSave = (Button) findViewById(R.id.btnsave);
    }
    void setDataForEditText(){
        usersViewModel =  ViewModelProviders.of(AccountSetting.this).get(UsersViewModel.class);
        usersViewModel.init();
        usersViewModel.setResultUser(this);
        sessionUser = new SessionUser(getApplicationContext());
        sessionUser.getPreferences();
        id_user = sessionUser.getLoggedID();
        getUser(id_user).observe(this, new Observer<Users>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Users users) {
                user = users;
                editUserName.setText(user.getUserName());
                editPhone.setText(user.getPhone());
                editEmail.setText(user.getEmail());
                editPassword.setText(decodePassword(user.getPassword()));

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String decodePassword(String pass){
        byte[] decode_password = Base64.getDecoder().decode(user.getPassword());
        String password = new String(decode_password);
        return password;
    }
    public MutableLiveData<Users> getUser(final int id){
        final MutableLiveData<Users> mutableLiveDataUser = new MutableLiveData<>();
         usersViewModel.getListUser().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                mutableLiveDataUser.postValue(checkUser(users, id));
            }
        });
        return mutableLiveDataUser;
    }
    private Users checkUser(List<Users> users, int id){
        Users user = new Users();
        for (Users users1 : users){
            if (users1.getID() == id){
                user = users1;
                break;
            }
        }
        return user;
    }
    void intentBack(){
        Intent i = new Intent(AccountSetting.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intentBack();
    }


    @Override
    public void resultUpdateUser(boolean bool) {
        if (bool){
            Toast.makeText(getBaseContext(), "Update successfull!", Toast.LENGTH_LONG).show();
            sessionUser.updateData(userName, mEmail, id_user);
            sessionUser.saveUser();
        }
        else{
            Toast.makeText(getBaseContext(), "Update unsuccessfull!", Toast.LENGTH_LONG).show();
        }
    }
}