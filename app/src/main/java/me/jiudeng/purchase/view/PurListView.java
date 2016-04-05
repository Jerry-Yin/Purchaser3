package me.jiudeng.purchase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Yin on 2016/3/31.
 */
public class PurListView extends android.widget.ListView {

    private SideBar mSideBar;

    public PurListView(Context context) {
        super(context);
    }

    public PurListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSideBar(SideBar sideBar){
        this.mSideBar = sideBar;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN | MotionEvent.ACTION_MOVE:
                if (mSideBar != null){
                    mSideBar.setVisibility(VISIBLE);
                }
                break;

            default:
                if (mSideBar != null){
                    mSideBar.setVisibility(INVISIBLE);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
