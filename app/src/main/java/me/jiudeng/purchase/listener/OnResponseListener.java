package me.jiudeng.purchase.listener;

import org.json.JSONException;

/**
 * Created by Yin on 2016/3/29.
 */
public interface OnResponseListener {

    void onResponse(String response) throws JSONException;
    void onError(Exception error);
}
