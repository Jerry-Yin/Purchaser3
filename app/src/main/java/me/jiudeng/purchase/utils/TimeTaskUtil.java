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
            final String fileContent = FileUtil.readFile(mContext);
            Log.d(TAG, "定时上传 当前数据 fileContent =" + fileContent);
            if (!TextUtils.isEmpty(fileContent)) {
                final String data = "PurchaseInfo="+createPushData(fileContent);
                HttpUtil.postDataToServer(HttpUtil.addressPushData, data, new OnResponseListener() {
                    @Override
                    public void onResponse(String response) throws JSONException {
                        Log.d(TAG, "定时上传完毕：" + System.currentTimeMillis() + "data = " + data);
                    }

                    @Override
                    public void onError(Exception error) {
                        Log.d(TAG, "定时上传失败：" + System.currentTimeMillis() + "data = " + data);
                    }
                });
            }
            mHandler.postDelayed(this, 10000);
        }
    };

    private static String createPushData(String fileContent) {
        JSONArray array1 = null;
        JSONArray array2 = null;
        try {
            array1 = new JSONArray(fileContent);
            array2 = new JSONArray();
            for (int i = 0; i < array1.length(); i++) {
                JSONObject object = (JSONObject) array1.get(i);
                int PurchasedId = object.getInt("InfoId");

                double Price = (double) object.get("BuyPrice");
                float Price1 = (float) Price*1000;
//                float Price = (float) object.get("BuyPrice");

                double Number = (double) object.get("NeedNumber");
//                float Number = (float) object.getDouble("NeedNumber");
                float Number1 = (float) Number;

                String Operator = (String) object.get("Operator");

                JSONObject o = new JSONObject();
                o.put("Id", PurchasedId);
                o.put("Price", Price1);
                o.put("Number", Number1);
                o.put("Operator", Operator);
                array2.put(o);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array2.toString();
    }

    public static void startTimeTask() {
        mHandler.postDelayed(runnable, 10000);
    }

    public static void stopTimeTask() {
        mHandler.removeCallbacks(runnable);
    }
}
