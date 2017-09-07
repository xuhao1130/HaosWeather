package com.coolweather.android;

import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coolweather.android.gson.Forecast;
import com.coolweather.android.gson.HourlyForecast;
import com.coolweather.android.gson.Weather;
import com.coolweather.android.service.AutoUpdateService;
import com.coolweather.android.util.HttpUtil;
import com.coolweather.android.util.Utility;

import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    public com.tuesda.walker.circlerefresh.CircleRefreshLayout circleRefreshLayout;

    private ScrollView weatherLayout;

    private Button navButton;

    private ImageView refreshIv;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private LinearLayout hourlyForecastLayout;

    private TextView uvText;

    private TextView visText;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;

    private String mWeatherId;

    private static String mUv;


    private String str;
    private int length;


    private String wet;
    private String wet1;

    private TextView uvBtn;
    private TextView visBtn;
    private TextView helpTitle;
    private TextView helpText;
    private LinearLayout uvShow;
    private boolean clickTimes1;
    private boolean clickTimes2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        // 初始化各控件
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        hourlyForecastLayout=(LinearLayout) findViewById(R.id.hourly_forecast_layout);
        uvBtn=(TextView) findViewById(R.id.help_uv_btn);
        uvBtn.setText(" more ");
        visBtn=(TextView) findViewById(R.id.help_vis_btn);
        visBtn.setText(" more ");
        uvShow=(LinearLayout) findViewById(R.id.uv_help);
        helpTitle=(TextView) findViewById(R.id.help_title);
        helpText=(TextView) findViewById(R.id.help_text);
        refreshIv=(ImageView) findViewById(R.id.refresh_iv);

        uvText = (TextView) findViewById(R.id.uv_text);
        visText = (TextView) findViewById(R.id.vis_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        circleRefreshLayout =(com.tuesda.walker.circlerefresh.CircleRefreshLayout) findViewById(R.id.refresh_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button) findViewById(R.id.nav_button);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            // 有缓存时直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            // 无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }




        /**
         * 显示uv和vis具体按钮监听
         */
        clickTimes1=false;
        clickTimes2=false;
        uvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTimes1=!clickTimes1;
                if (clickTimes1) {
                    if(!clickTimes2){
                        uvBtn.setText(" fold ");
                        helpTitle.setText(R.string.help_uv_title);
                        helpText.setText(R.string.help_uv_text);
                        uvShow.setVisibility(uvShow.VISIBLE);
                    }else {
                        visBtn.setText(" more ");
                        clickTimes2=!clickTimes2;
                        uvBtn.setText(" fold ");
                        helpTitle.setText(R.string.help_uv_title);
                        helpText.setText(R.string.help_uv_text);
                        uvShow.setVisibility(uvShow.VISIBLE);
                    }
                }else {
                    uvBtn.setText(" more ");
                    uvShow.setVisibility(uvShow.GONE);
                }
            }
        });

        visBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTimes2=!clickTimes2;
                if (clickTimes2) {
                    if (!clickTimes1){
                        visBtn.setText(" fold ");
                        helpTitle.setText(R.string.help_vis_title);
                        helpText.setText(R.string.help_vis_text);
                        uvShow.setVisibility(uvShow.VISIBLE);
                    }else {
                        uvBtn.setText(" more ");
                        clickTimes1=!clickTimes1;
                        visBtn.setText(" fold ");
                        helpTitle.setText(R.string.help_vis_title);
                        helpText.setText(R.string.help_vis_text);
                        uvShow.setVisibility(uvShow.VISIBLE);
                    }
                }else {
                    visBtn.setText(" more ");
                    uvShow.setVisibility(uvShow.GONE);
                }
            }
        });




        refreshIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWeather(mWeatherId);
                loadBingPic();
            }
        });


        /**
         * CircleRefresh控件监听
         */
/*


        circleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void refreshing() {
                requestWeather(mWeatherId);
                loadBingPic();
            }

            @Override
            public void completeRefresh() {
                Toast.makeText(WeatherActivity.this,"刷新成功！",Toast.LENGTH_SHORT).show();
            }

        });
*/


        /**
         * 选择地区按钮监听
         */
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
    }


    /**
     * 根据天气id请求城市天气信息。
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=9f4cabaa1bbc41c69583621b67236562";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }

                        /**
                         * 设置一个两秒的延迟让下拉刷新动画完成
                         */
                        /*
                        new Handler(new Handler.Callback() {

                            @Override
                            public boolean handleMessage(Message arg0) {
                                circleRefreshLayout.finishRefreshing();
                                return false;
                            }
                        }).sendEmptyMessageDelayed(0, 2000);
                        */

                    }
                });
            }


            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();

                        /**
                         * 设置一个两秒的延迟让下拉刷新动画完成
                         */
            /*
                        new Handler(new Handler.Callback() {

                            @Override
                            public boolean handleMessage(Message arg0) {
                                circleRefreshLayout.finishRefreshing();
                                return false;
                            }
                        }).sendEmptyMessageDelayed(0, 2000);
            */


                    }
                });
            }

        });
        loadBingPic();
    }


    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);

                        //circleRefreshLayout.finishRefreshing();
                                       }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(WeatherActivity.this,"加载背景失败...",Toast.LENGTH_SHORT).show();

                //circleRefreshLayout.finishRefreshing();
            }
        });
    }

    /**
     * 处理并展示Weather实体类中的数据。
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        visText.setText(weather.now.vis);
        titleCity.setText(cityName);
        titleUpdateTime.setText("更新时间:"+updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            ImageView wetpic=(ImageView) view.findViewById(R.id.weather_pic1);

            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
            String uv=forecast.uv;
            mUv=uv;

            wet1=forecast.more.info;
            if (wet1.contains("雷")){
                wetpic.setImageResource(R.drawable.storm);
            }else if (wet1.contains("雨")){
                wetpic.setImageResource(R.drawable.rain);
            }else if (wet1.contains("云")){
                if (wet1.contains("晴")){
                    wetpic.setImageResource(R.drawable.cloudy);
                }else {
                    wetpic.setImageResource(R.drawable.overcast);
                }
            }else if (wet1.contains("晴")){
                wetpic.setImageResource(R.drawable.sunny);
            }else if (wet1.contains("雪")){
                wetpic.setImageResource(R.drawable.snow);
            }else if (wet1.contains("雾")){
                wetpic.setImageResource(R.drawable.mist);
            }else if (wet1.contains("阴")){
                wetpic.setImageResource(R.drawable.overcast);
            }


        }
            uvText.setText(mUv);

        hourlyForecastLayout.removeAllViews();
        for (HourlyForecast hourlyForecast : weather.hourlyForecastList){
            View view = LayoutInflater.from(this).inflate(R.layout.hourly_forecast_item,hourlyForecastLayout,false);
            TextView timeText=(TextView) view.findViewById(R.id.time_text);
            TextView weatherText=(TextView) view.findViewById(R.id.weather_text);
            TextView tempText = (TextView) view.findViewById(R.id.temp_text);
            TextView dirText=(TextView) view.findViewById(R.id.dir_text);
            ImageView wetIm=(ImageView) view.findViewById(R.id.weather_pic);

            str=hourlyForecast.time;
            length=str.length();
            timeText.setText(str.substring(length-5));
            weatherText.setText(hourlyForecast.more.wet);
            tempText.setText(hourlyForecast.temp + "℃");
            dirText.setText(hourlyForecast.wind.dir);
            hourlyForecastLayout.addView(view);

            wet=hourlyForecast.more.wet;

            if (wet.contains("雷")){
                wetIm.setImageResource(R.drawable.storm);
            }else if (wet.contains("雨")){
                wetIm.setImageResource(R.drawable.rain);
            }else if (wet.contains("云")){
                if (wet.contains("晴")){
                    wetIm.setImageResource(R.drawable.cloudy);
                }else {
                    wetIm.setImageResource(R.drawable.overcast);
                }
            }else if (wet.contains("晴")){
                wetIm.setImageResource(R.drawable.sunny);
            }else if (wet.contains("雪")){
                wetIm.setImageResource(R.drawable.snow);
            }else if (wet.contains("雾")){
                wetIm.setImageResource(R.drawable.mist);
            }else if (wet.contains("阴")){
                wetIm.setImageResource(R.drawable.overcast);
            }

        }

        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "Uv指数：" + weather.suggestion.uV.info;
        String sport = "健康建议：" + weather.suggestion.flu.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);



        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

}
