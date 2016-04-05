package me.jiudeng.purchase.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.jiudeng.purchase.activity.LoginActivity;

/**
 * Created by Yin on 2016/4/1.
 */
public class SaveFileUtil {

    private static final String TAG = "SaveFileUtil";

    private Context mContext;
    private String mData;

    public SaveFileUtil( Context mContext, String mData) {
        this.mData = mData;
        this.mContext = mContext;
    }

    public void saveDataToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream outputStream = null;
                BufferedWriter writer = null;

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(new Date(System.currentTimeMillis()));

                String fileName = "table" + date + ".txt";

                try {
                    outputStream = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
                    writer = new BufferedWriter(new OutputStreamWriter(outputStream));
                    writer.write(mData);

                    Log.d(TAG, "数据已经存储到本地文件！"+fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d(TAG, "数据存储有误！");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "数据存储有误！");
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }


}
