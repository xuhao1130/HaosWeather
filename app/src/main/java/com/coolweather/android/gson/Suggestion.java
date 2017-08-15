package com.coolweather.android.gson;

import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("uv")
    public Uv uV;

    @SerializedName("flu")
    public Flu flu;

    public class Comfort {

        @SerializedName("txt")
        public String info;

    }

    public class Uv {

        @SerializedName("txt")
        public String info;

    }

    public class Flu {

        @SerializedName("txt")
        public String info;

    }

}
