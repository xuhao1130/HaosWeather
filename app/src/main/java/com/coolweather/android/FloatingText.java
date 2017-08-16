package com.coolweather.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.romainpiel.titanic.library.Titanic;
import com.romainpiel.titanic.library.TitanicTextView;

public class FloatingText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_text);


        /**
         * 加载使用floatingText
         */
        final TitanicTextView titanicTextView=(TitanicTextView) findViewById(R.id.titanic_tv);
        Typeface tf=Typeface.createFromAsset(getAssets(),"Satisfy-Regular.ttf");
        titanicTextView.setTypeface(tf);
        final Titanic titanic=new Titanic();



        Button login=(Button) findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titanic.start(titanicTextView);

                /**
                 * 定时器，使floatingText动画完成后跳转
                 */
                new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message arg0) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        return false;
                    }
                }).sendEmptyMessageDelayed(0, 10000);
            }
        });


    }
}
