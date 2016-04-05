package me.jiudeng.purchase.utils;

import android.util.Log;

/**
 * Created by Yin on 2016/3/30.
 * 获取汉字的首字母
 */
public class GetFirstAlp {

//    private static String TAG = "GetFirstAlp";
//    private static String TAG = "MainActivity";

    public static String getFirstAlpha(String name){

        String firstStr = (String) name.subSequence(0, 1);    //获取name的第一个字符(第一个汉字)
//        Log.d(TAG, "firstStr = " + firstStr);

        CharacterParser characterParser = CharacterParser.getInstance();
        String alp = characterParser.convert(firstStr);  //汉字转换成拼音
//        Log.d(TAG, "alp = " + alp);
        String firtAlp = (String) alp.subSequence(0, 1);   //拼音首字母

//        Log.d(TAG, "firtAlp = " + firtAlp);

        return firtAlp;
    }
}
