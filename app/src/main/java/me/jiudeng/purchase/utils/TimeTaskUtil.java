package me.jiudeng.purchase.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.jiudeng.purchase.listener.OnResponseListener;
import me.jiudeng.purchase.network.HttpUtil;

/**
 * Created by Yin on 2016/3/30.
 */
public class TimeTaskUtil {

    private static final String TAG = "TimeTaskUtil";
    private static Context mContext;

    public TimeTaskUtil(Context mContext) {
        this.mContext = mContext;
    }

    private static Handler mHandler = new Handler();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //TODO 定时任务 读取本地数据文件，获取内容并且上传
            PushDataUtil.pushData(mContext);
            mHandler.postDelayed(this, 10000);
        }
    };



    public static void startTimeTask() {
        mHandler.postDelayed(runnable, 10000);
    }

    public static void stopTimeTask() {
        mHandler.removeCallbacks(runnable);
    }
}
