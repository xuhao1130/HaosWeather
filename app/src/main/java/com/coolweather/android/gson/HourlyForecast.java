package com.coolweather.android.gson;

/**
 * Created by Administrator on 2017/8/15.
 */
import com.google.gson.annotations.SerializedName;

public class HourlyForecast {
    @SerializedName("date")
    public String time;
//时刻

    @SerializedName("cond")
    public More more;


    public class More{
        @SerializedName("txt")
        public String wet;
    }
//天气

    @SerializedName("tmp")
    public String temp;
//温度

    @SerializedName("wind")
    public Wind wind;
    public class Wind{
        @SerializedName("dir")
        public String dir;
    }
//风向
}
