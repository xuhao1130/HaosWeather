package com.coolweather.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolweather.android.R;

public class LoginLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_backlayout);

        Button loginbtn=(Button) findViewById(R.id.login_btn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginLayout.this,MainActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout abhaos=(LinearLayout) findViewById(R.id.haos_tv);
        abhaos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginLayout.this,AboutHaos.class));
            }
        });

    }
}
