package me.jiudeng.purchase.utils;

import me.jiudeng.purchase.listener.OnResponseListener;
import me.jiudeng.purchase.network.HttpUtil;

/**
 * Created by Yin on 2016/3/29.
 */
public class UserDataServiceHelper {

    public static String address = "http://10.0.0.158:9999/warehouseorder/loginnormal";    //账户验证地址
    private static String TAG = "UserDataServiceHelper";
    private static boolean RESULT_OK = false;

    public static boolean Login(String usr, String pwd) {
        /**TODO 验证用户名， 密码 --->> 正确则登录*/
        HttpUtil.sendHttpRequestPost(address, usr, pwd, new OnResponseListener() {
            @Override
            public void onResponse(String response) {
                RESULT_OK = true;
            }

            @Override
            public void onError(Exception error) {
                RESULT_OK = false;
            }
        });

        return RESULT_OK;
    }
}
