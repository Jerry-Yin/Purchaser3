package me.jiudeng.purchase.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yin on 2016/4/5.
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 读取文件
     * @return
     */
    public static String readFile(Context context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date(System.currentTimeMillis()));
        String fileName = "table" + date + ".txt";
//        String fileName = "table2016-0.-08.txt";

        FileInputStream fileInputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            fileInputStream = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            Log.d(TAG, "文件读取完毕！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(TAG, "文件未找到！！！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

}
