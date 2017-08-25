package com.coolweather.android;

import com.coolweather.android.db.Account;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ResetPassword extends AppCompatActivity {

    private EditText accountET;
    private EditText oldpassET;
    private EditText newpassET;
    private Button reset;
    private Button back;
    private TextView title;

    private LinearLayout warn;
    private TextView warnTextview;
    private LinearLayout passwarn;
    private TextView passwarnTextview;

    private String username;
    private String oldpass;
    private String newpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        accountET=(EditText) findViewById(R.id.reset_account_edt);
        oldpassET=(EditText) findViewById(R.id.reset_oldpass_edt);
        newpassET=(EditText) findViewById(R.id.reset_newpass_edt);
        back=(Button) findViewById(R.id.back_button);
        reset=(Button) findViewById(R.id.reset_password_btn);

        //弹出警告的初始化
        warn=(LinearLayout) findViewById(R.id.reset_warning);
        warnTextview=(TextView) findViewById(R.id.warning_tv);
        passwarn=(LinearLayout) findViewById(R.id.reset_password_warning);
        passwarnTextview=(TextView) findViewById(R.id.warning_password_tv);

        title=(TextView) findViewById(R.id.title_text);
        title.setText(R.string.reset);


        /**
         * 监听Edittext
         *
         */

        accountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                username=s.toString();
            }
        });

        oldpassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                oldpass=s.toString();
            }
        });


        newpassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                newpass=s.toString();
                if (!RegularUtil.checkPassword(newpass)){
                    passwarn.setVisibility(View.VISIBLE);
                    passwarnTextview.setText(R.string.reset_pass_wrong);
                }else {
                    passwarn.setVisibility(View.GONE);
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this,SignIn.class));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Account> nameExist= DataSupport.select("username").where("username=?",username)
                        .find(Account.class);

                if (nameExist==null) {
                    warn.setVisibility(View.VISIBLE);
                    warnTextview.setText(R.string.name_not_exist);
                }else {
                    warn.setVisibility(View.GONE);
                    List<Account> passMatch=DataSupport.select("password").where("username",username)
                            .find(Account.class);
                    if (passMatch.get(0).equals(oldpass)){
                        Account update=new Account();
                        update.setPassword(newpass);
                        update.updateAll("username=?",username);
                    }else {
                        warn.setVisibility(View.VISIBLE);
                        warnTextview.setText(R.string.pass_not_match);
                    }
                }
                Toast.makeText(ResetPassword.this,"修改成功！",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ResetPassword.this,SignIn.class));







            }
        });

    }
}
