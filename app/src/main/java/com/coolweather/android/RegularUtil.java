package com.coolweather.android;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 正则的检查
 */

public class RegularUtil {

    /**
     * 检查字符串是否符合某个正则
     *
     * @param s
     * @param rex
     * @return
     */
    public static boolean matchRex(String s, String rex) {
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(s);
        return m.matches();
    }


    /**
     * 是否是手机号
     *
     * @param txt
     * @return
     */
    public static boolean isMobilePhoneNumber(String txt) {
        return matchRex(txt, "^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
    }

    /**
     * 检测是否有中文字符
     *
     * @param txt
     * @return
     */
    public static boolean isContainChinese(String txt) {
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(txt);
        return m.find();
    }

    /**
     * 检查邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean checkMailFormat(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 检查密码格式6~21字母和数字的组合
     */
    public static boolean checkPassword(String pass){
        Pattern p=Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
        Matcher m=p.matcher(pass);
        return m.matches();
    }

    /**
     * 用户名：以中文或字母开头的汉字、字母和数字的组合
     */
    public static boolean checkUserName(String pass){
        Pattern p=Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z][\\u4E00-\\u9FA5A-Za-z0-9]+$");
        Matcher m=p.matcher(pass);
        return m.matches();
    }
}
