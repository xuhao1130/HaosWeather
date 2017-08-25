package com.coolweather.android;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.romainpiel.titanic.library.Titanic;
import com.romainpiel.titanic.library.TitanicTextView;


public class FloatingText extends AppCompatActivity {

    private TextView tips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_text);

        tips=(TextView) findViewById(R.id.wait_tips);

        /**
         * 加载使用floatingText
         */
        final TitanicTextView titanicTextView=(TitanicTextView) findViewById(R.id.titanic_tv);
        Typeface tf=Typeface.createFromAsset(getAssets(),"Satisfy-Regular.ttf");
        titanicTextView.setTypeface(tf);
        final Titanic titanic=new Titanic();

        titanic.start(titanicTextView);
        tips.setVisibility(tips.VISIBLE);
        new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message arg0) {
                tips.setVisibility(tips.INVISIBLE);
                return false;
            }
        }).sendEmptyMessageDelayed(0, 1000);

        /**
         * 定时器，使floatingText动画完成后跳转
         */
        new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message arg0) {
                startActivity(new Intent(FloatingText.this,SignIn.class));
                return false;
            }
        }).sendEmptyMessageDelayed(0, 1000);

    }
}
