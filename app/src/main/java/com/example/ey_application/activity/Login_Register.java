package com.example.ey_application.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ey_application.Model.Users;
import com.example.ey_application.R;
import com.example.ey_application.ViewModel.UsersViewModel;
import com.example.ey_application.fragment.LoginFragment;
import com.example.ey_application.fragment.RegisterFragment;
import com.example.ey_application.session.SessionUser;

import java.util.Base64;
import java.util.List;

import static com.example.ey_application.R.id.fragment;

public class Login_Register extends AppCompatActivity implements RegisterFragment.Register, LoginFragment.UserLogin  {


    private Button mButton;
    private FragmentTransaction ft_add;
    private String titleButtonLogin = "Register Now?";
    private String titleButtonRegister = "Login Now?";
    private UsersViewModel usersViewModel;
    private List<Users> usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        getView();
        setFragment();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment((String) mButton.getText());
            }
        });
        usersViewModel =  ViewModelProviders.of(Login_Register.this).get(UsersViewModel.class);
        usersViewModel.init();
        usersViewModel.getListUser().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                usersList = users;
            }
        });
    }
    public void getView(){
        mButton = (Button) findViewById(R.id.button);
    }
    private void setFragment(){
        ft_add = getSupportFragmentManager().beginTransaction();
        ft_add.add(fragment, new LoginFragment());
        ft_add.commit();
    }
    private void replaceFragment(String str){

        ft_add = getSupportFragmentManager().beginTransaction();

        if (str.trim().equals(titleButtonLogin)){
            ft_add.replace(fragment, new RegisterFragment(), LoginFragment.class.getName());
            ft_add.addToBackStack(null);
            ft_add.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft_add.commit();
            mButton.setText(titleButtonRegister);
        }
        else{
            ft_add.replace(fragment, new LoginFragment(), RegisterFragment.class.getName());
            ft_add.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft_add.commit();
            mButton.setText(titleButtonLogin);
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void dataRegister(String username, String phone, String email, String Password) {
      /*  boolean bool = true;*/
        for(Users users : usersList){
            if (username.trim().equals(users.getUserName())){
                /*bool = false;*/
                Toast.makeText(this,"The user name is already in use!", Toast.LENGTH_SHORT ).show();
                return;
            }
            if (phone.trim().equals(users.getPhone())){
                Toast.makeText(this,"The phone number is already in use!", Toast.LENGTH_SHORT ).show();
                return;
            }
            if (email.trim().equals(users.getEmail())){
                Toast.makeText(this,"The email is already in use!", Toast.LENGTH_SHORT ).show();
                return;
            }

        }
        String encode_password
                = Base64.getEncoder()
                .encodeToString(Password.getBytes());
        usersViewModel.createUser(email, phone, encode_password, username, getApplication());
        replaceFragment(titleButtonRegister);

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void userInfo(String name, String email, String password) {
        String encode_password
                = Base64.getEncoder()
                .encodeToString(password.getBytes());
        usersViewModel.getUserLogin(name,email, encode_password, getApplication());

    }
}