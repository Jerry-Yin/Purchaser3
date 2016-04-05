package me.jiudeng.purchase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.jiudeng.purchase.R;
import me.jiudeng.purchase.module.PurchaseData;
import me.jiudeng.purchase.utils.CharacterParser;
import me.jiudeng.purchase.utils.GetFirstAlp;
import me.jiudeng.purchase.utils.HttpUtil;
import me.jiudeng.purchase.utils.OnResponseListener;
import me.jiudeng.purchase.utils.PinyinComparator;
import me.jiudeng.purchase.utils.SaveFileUtil;
import me.jiudeng.purchase.utils.TimeTaskUtil;
import me.jiudeng.purchase.view.SideBar;

/**
 * Created by Yin on 2016/3/29.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private static final int REFRESH_DATA = 0;
    private static final int SEND_SUCCESS = 1;
    private static final int SEND_FAIL = 2;

    /**
     * Views
     */
    private ListView mListView;
    private Button mBtnLogOut, mBtnSend;
    private TextView mTvPaySum;     //采购金额合计
    private TextView mTvCurDialog;  //显示当前字母
    private SideBar mSideBar;

    /**
     * Values
     */
    private PurchaseListAdapter mListAdapter;
    private List<PurchaseData> mPurchaseList = new ArrayList<>();
    private CharacterParser mCharacterParser; //汉字转换成拼音的类
    private PinyinComparator mPinyinComparator; //根据拼音来排列ListView里面的数据类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        initViews();
        initData();
    }

    private void initViews() {
        mBtnLogOut = (Button) findViewById(R.id.btn_logout);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnLogOut.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
        mTvPaySum = (TextView) findViewById(R.id.tv_pay_sum);
        mTvCurDialog = (TextView) findViewById(R.id.tv_dialog);
        mSideBar = (SideBar) findViewById(R.id.side_bar);
        mSideBar.setTextView(mTvCurDialog);
        mListView = (ListView) findViewById(R.id.list_view);
        //设置右侧触摸监听
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mListAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });
    }

    private void initData() {
        // TODO 初始化数据， 网络请求，刷新界面; ---> 数据加载完毕后，调用定时任务定时上传，并且在关闭时结束任务
        HttpUtil.SendHttpRequest(HttpUtil.addressData, new OnResponseListener() {
            Message message = new Message();
            @Override
            public void onResponse(String response) throws JSONException {
                pareseJsonData(response);

                message.what = REFRESH_DATA;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(Exception error) {

            }
        });


        // 设置数据（测试数据）
        String[] arry = getResources().getStringArray(R.array.date);
//        Log.d(TAG, "arry = " + arry.toString());

//        for (int i = 0; i < 30; i++) {
//            PurchaseData data = new PurchaseData();
//            Log.d(TAG, "arry[" + i + "] = " + arry[i].toString());
//            data.setItemName(arry[i]);
//            mPurchaseList.add(data);
//        }

        Log.d(TAG, "排序前的 mPurchaseList = " + mPurchaseList.toString());

//        String name = mPurchaseList.get(0).getItemName();
//        Log.d(TAG, "排序前的 mPurchaseList[0] = " + name);
//        String firstApl = GetFirstAlp.getFirstAlpha(name);
//        Log.d(TAG, "mPurchaseList[0]的首字母 = " + firstApl.toString());
//
//        mCharacterParser = CharacterParser.getInstance();
//        mPinyinComparator = new PinyinComparator();
//        // 根据a-z进行排序源数据
//        Collections.sort(mPurchaseList, mPinyinComparator);
//        Log.d(TAG, "排序后的 mPurchaseList = " + mPurchaseList.toString());
//        mListAdapter = new PurchaseListAdapter(MainActivity.this, mPurchaseList);
//        mListView.setAdapter(mListAdapter);
    }


    private void pareseJsonData(String response) throws JSONException {
        Gson gson = new Gson();
        mPurchaseList = gson.fromJson(response, new TypeToken<List<PurchaseData>>() {
        }.getType());
        // TODO: 2016/4/1 设置参数

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_DATA:
                    Log.d(TAG, "排序前的 mPurchaseList = " + mPurchaseList.toString());
                    mCharacterParser = CharacterParser.getInstance();
                    mPinyinComparator = new PinyinComparator();
                    // 根据a-z进行排序源数据
                    Collections.sort(mPurchaseList, mPinyinComparator);
                    Log.d(TAG, "排序后的 mPurchaseList = " + mPurchaseList.toString());
                    mListAdapter = new PurchaseListAdapter(MainActivity.this, mPurchaseList);
                    mListView.setAdapter(mListAdapter);
                    mListAdapter.notifyDataSetChanged();
                    break;

                case SEND_SUCCESS:

                    Toast.makeText(MainActivity.this, "上传完毕！", Toast.LENGTH_SHORT).show();
                    break;

                case SEND_FAIL:

                    Toast.makeText(MainActivity.this, "上传失败，请重新上传！", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout:
                // TODO: 2016/3/30  退出当前账号，弹出弹框；需要删除本地保存的用户信息，并退出到登录界面
//                showDialogOld();
                showDialogIos();

                break;

            case R.id.btn_send:
                // TODO: 2016/3/30 上传数据
                postCurDataToServer();
                break;
            default:
                break;
        }
    }

    private void postCurDataToServer() {
        if (mPurchaseList != null) {
            String data = new Gson().toJson(mPurchaseList);
            final Message message = new Message();
            HttpUtil.postDataToServer(HttpUtil.addressData, data, new OnResponseListener() {
                @Override
                public void onResponse(String response) throws JSONException {
                    message.what = SEND_SUCCESS;
                    message.obj = response;
                    mHandler.sendMessage(message);

                }

                @Override
                public void onError(Exception error) {
                    message.what = SEND_FAIL;
                    message.obj = error;
                    mHandler.sendMessage(message);

                }
            });
        }
    }

    private void showDialogIos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final Dialog dialog = builder.create();
        LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_ios_dialog, null);
        dialog.show();
        dialog.getWindow().setContentView(layout);

        layout.findViewById(R.id.btn_positive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016/3/30 删除账号信息
                logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
            }
        });
        layout.findViewById(R.id.btn_negative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 删除账号信息,退出登录
     */
    private void logOut() {
        SharedPreferences.Editor editor = this.getSharedPreferences("uer_data", MODE_PRIVATE).edit();
        editor.remove("Account");
        editor.remove("Password");
        editor.commit();
    }

    private void showDialogOld() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("确定退出？");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: 2016/3/30 删除账号信息

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    class PurchaseListAdapter extends BaseAdapter implements SectionIndexer, TextWatcher {

        private Context mContext;
        private LayoutInflater inflater;
        private List<PurchaseData> mDataList;
        private int order = 0;

        private class ViewHolder {
            private TextView tvOrder;                  //序号
            private TextView tvProdName, tvProdMount;   //商品名称 & 规格
            private TextView tvPriceLine;       //线上价格
            private EditText etPricePur;        //采购价格
            private TextView tvMountDing;       //订购量
            private EditText etMountPur;        //采购量
            private TextView tvMoneyLine, tvMoneyPur;   //线上金额 & 采购金额
        }

        public PurchaseListAdapter(Context c, List<PurchaseData> mPurchaseList) {
            this.mContext = c;
            this.inflater = LayoutInflater.from(c);
            this.mDataList = mPurchaseList;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.layout_item, null);
                holder.tvOrder = (TextView) convertView.findViewById(R.id.tv_orde);
                holder.tvProdName = (TextView) convertView.findViewById(R.id.tv_pord_name);
                holder.tvProdMount = (TextView) convertView.findViewById(R.id.tv_prod_mount);
                holder.tvPriceLine = (TextView) convertView.findViewById(R.id.tv_price_line);
                holder.etPricePur = (EditText) convertView.findViewById(R.id.et_price_pur);
                holder.etPricePur.setInputType(EditorInfo.TYPE_CLASS_PHONE);    //数字键盘
                holder.etPricePur.addTextChangedListener(this);
                holder.tvMountDing = (TextView) convertView.findViewById(R.id.tv_mount_ding);
                holder.etMountPur = (EditText) convertView.findViewById(R.id.et_mount_pur);
                holder.etMountPur.setInputType(EditorInfo.TYPE_CLASS_PHONE);
                holder.etMountPur.addTextChangedListener(this);
                holder.tvMoneyLine = (TextView) convertView.findViewById(R.id.tv_money_line);
                holder.tvMoneyPur = (TextView) convertView.findViewById(R.id.tv_money_pur);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // TODO 从数据list中取出数据并对应的设置
            int order = position + 1;
            holder.tvOrder.setText(String.valueOf(order));
            holder.tvProdName.setText(mDataList.get(position).getItemName());
            holder.tvProdMount.setText(mDataList.get(position).getUnit());
            holder.tvPriceLine.setText(String.valueOf(mDataList.get(position).getSellPrice()));
            holder.etPricePur.setText(String.valueOf(mDataList.get(position).getBuyPrice()));
            holder.tvMountDing.setText(String.valueOf(mDataList.get(position).getNeedNumbre()));

            float moneyLine = mDataList.get(position).getSellPrice() * mDataList.get(position).getNeedNumbre();
            holder.tvMoneyLine.setText(String.valueOf(moneyLine));

            float mountPur = Float.valueOf(holder.etMountPur.getText().toString());
            float moneyPur = mDataList.get(position).getBuyPrice() * mountPur;

            holder.tvMoneyPur.setText(String.valueOf(moneyPur));

            return convertView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        /**
         * 变化之后
         *
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
            String s1 = String.valueOf(s);
        }

        @Override
        public Object[] getSections() {
            return new Object[0];
        }

        /**
         * 根据首字母的ascii值来获取在该ListView中第一次出现该首字母的位置
         *
         * @param sectionIndex
         * @return
         */
        @Override
        public int getPositionForSection(int sectionIndex) {
            for (int i = 0; i < getCount(); i++) {
                String name = mDataList.get(i).getItemName();
                String sortStr = GetFirstAlp.getFirstAlpha(name);
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == sectionIndex) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 根据ListView的position来获取该位置上面的name的首字母char的ascii值
         *
         * @param position
         * @return
         */
        @Override
        public int getSectionForPosition(int position) {
            String name = mDataList.get(position).getItemName();
            return GetFirstAlp.getFirstAlpha(name).charAt(0);
        }
    }

    public void saveDataToFile(){
        if (mPurchaseList != null) {
            Log.d(TAG, "存储的 mPurchaseList = "+ mPurchaseList.toString());

            String jsonObject = new Gson().toJson(mPurchaseList);

            Log.d(TAG, "存储的 mPurchaseList 转化后 = "+ jsonObject.toString());
            new SaveFileUtil(MainActivity.this, jsonObject).saveDataToFile();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataToFile();
    }

    @Override
    protected void onDestroy() {
        TimeTaskUtil.stopTimeTask();
        super.onDestroy();
    }
}
