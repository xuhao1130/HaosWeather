package com.coolweather.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolweather.android.db.Account;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccount extends AppCompatActivity {

    @BindView(R.id.title_text)
    TextView title;
    @BindView(R.id.create_account_edt)
    EditText usernameET;
    @BindView(R.id.create_password_edt)
    EditText passwordET;
    @BindView(R.id.create_repassword_edt)
    EditText comfirmpassET;
    @BindView(R.id.warning_name_tv)
    TextView namewarnTextview;
    @BindView(R.id.create_name_warning)
    LinearLayout namewarn;
    @BindView(R.id.warning_pass_tv)
    TextView passwarnTextview;
    @BindView(R.id.create_pass_warning)
    LinearLayout passwarn;
    @BindView(R.id.warning_repass_tv)
    TextView repasswarnTextview;
    @BindView(R.id.create_repass_warning)
    LinearLayout repasswarn;
    @BindView(R.id.create_account_btn)
    Button create;


    private String username;
    private String password;
    private String comfirmpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        namewarnTextview.setText(R.string.wrong_name);
        title.setText(R.string.create);


        /**
         *获取实时输入的内容并判断是否符合正则规范
         *
         */
        /**
         * 用户名
         */
        usernameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                username = s.toString();
                if (!(RegularUtil.checkUserName(username))) {
                    namewarn.setVisibility(View.VISIBLE);
                } else {
                    namewarn.setVisibility(View.GONE);
                }
            }
        });

        /**
         *密码
         */
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
                if (!(RegularUtil.checkPassword(password))) {
                    passwarn.setVisibility(View.VISIBLE);
                    passwarnTextview.setText(R.string.wrong_pass);
                } else {
                    passwarn.setVisibility(View.GONE);
                }
            }
        });
        /**
         * 重复密码
         */
        comfirmpassET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                comfirmpass = s.toString();
                if (!(comfirmpass.equals(password))) {
                    repasswarn.setVisibility(View.VISIBLE);
                    repasswarnTextview.setText(R.string.repass_not_match);
                } else {
                    repasswarn.setVisibility(View.GONE);
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((RegularUtil.checkUserName(username)) && (RegularUtil.checkPassword(password))
                        && (comfirmpass.equals(password))) {

                    //存入数据库
                    Account account = new Account();
                    account.setUserName(username);
                    account.setPassword(password);
                    account.save();

                    startActivity(new Intent(CreateAccount.this, SignIn.class));
                }

            }
        });

    }

    /**
     *
     * 监听按钮
     */
    @OnClick(R.id.back_button)
    void backClick(){
        startActivity(new Intent(CreateAccount.this, SignIn.class));
    }
    @OnClick(R.id.signin_ly)
    void signinClick(){
        startActivity(new Intent(CreateAccount.this, SignIn.class));
    }
    @OnClick(R.id.reset_password_ly)
    void resetClick(){
        startActivity(new Intent(CreateAccount.this, ResetPassword.class));
    }
}
