package com.coolweather.android.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Account extends DataSupport {
    private String username;
    private String password;

    public String getUserName(){
        return username;
    }
    public void setUserName(String name){
        this.username=name;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }
}
