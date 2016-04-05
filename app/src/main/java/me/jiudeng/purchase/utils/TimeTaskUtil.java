package me.jiudeng.purchase.utils;

import android.os.Handler;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import me.jiudeng.purchase.module.PurchaseData;

/**
 * Created by Yin on 2016/3/30.
 */
public class TimeTaskUtil {

    private static final String TAG = "TimeTaskUtil";

    public static List<PurchaseData> mDataList = new ArrayList<>();

    private static Handler mHandler = new Handler();
    private static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //TODO 定时任务
            if (mDataList != null){

                Log.d(TAG, "定时上传 当前数据 data =" +mDataList.toString());

                HttpUtil.postDataToServer(HttpUtil.addressData, mDataList.toString(), new OnResponseListener() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        Log.d(TAG, "定时上传完毕："+System.currentTimeMillis()+"data = "+mDataList.toString());
                    }

                    @Override
                    public void onError(Exception error) {
                        Log.d(TAG, "定时上传失败："+System.currentTimeMillis()+"data = "+mDataList.toString());
                    }
                });
            }
            mHandler.postDelayed(this, 10000);
        }
    };

    public static void startTimeTask(){
        mHandler.postDelayed(runnable, 10000);
    }

    public static void stopTimeTask(){
        mHandler.removeCallbacks(runnable);
    }
}
