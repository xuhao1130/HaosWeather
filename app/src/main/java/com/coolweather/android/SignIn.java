package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignIn extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @BindView(R.id.signin_account_edt)EditText account;
    @BindView(R.id.signin_password_edt)EditText password;
    @BindView(R.id.back_button)Button back;
    @BindView(R.id.remember_pass_cb)CheckBox remember;

    @BindView(R.id.signin_warning)LinearLayout warn;
    @BindView(R.id.signin_warning_tv)TextView warnTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtity_sign_in);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        ButterKnife.bind(this);


        boolean isRemember=pref.getBoolean("remember_password",false);
        if (isRemember){
            String acc=pref.getString("account","");
            String pass=pref.getString("password","");
            account.setText(acc);
            password.setText(pass);
            remember.setChecked(true);
        }

    }


    @OnClick(R.id.create_account_ly)
    void createClick(){
        startActivity(new Intent(SignIn.this,CreateAccount.class));
    }
    @OnClick(R.id.reset_password_ly)
    void resetClick(){
        startActivity(new Intent(SignIn.this,ResetPassword.class));
    }
    @OnClick(R.id.signin_btn)
    void signinClick(){
        String acc=account.getText().toString();
        String pass=password.getText().toString();

        editor=pref.edit();
        if (remember.isChecked()){
            editor.putBoolean("remember_password",true);
            editor.putString("account",acc);
            editor.putString("password",pass);
        }else {
            editor.clear();
        }
        editor.apply();
        startActivity(new Intent(SignIn.this,MainActivity.class));
    }


}
