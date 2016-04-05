package me.jiudeng.purchase.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.Button;

import me.jiudeng.purchase.R;

/**
 * Created by Yin on 2016/3/31.
 * 提示框
 * 重写dialog，定时关闭
 */
public class TipsDialog extends Dialog{

    private Context mContext;
//    private LayoutInflater mInflater;
//    private Button mBtnPotv, mBtnNgtv;
//    private Dialog mDialog;

    private int FLAG_DISMISS = 1;
    private boolean flag = true;


    public TipsDialog(Context context) {
        super(context);
        getWindow().setContentView(R.layout.layout_tips_dialog);
    }

    @Override
    public void show() {
        super.show();
//        mThread.run();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 2000);
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        flag = false;
    }

//    private Thread mThread = new Thread(new Runnable() {
//        @Override
//        public void run() {
//            while (flag){
//                try {
//                    mHandler.postDelayed()
//                    Message message = new Message();
//                    message.what = FLAG_DISMISS;
//                    mHandler.sendMessage(message);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    });

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_DISMISS){
                dismiss();
            }
        }
    };
}
