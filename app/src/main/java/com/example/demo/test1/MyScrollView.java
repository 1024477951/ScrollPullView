package com.example.demo.test1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by LiuZhen on 2017/6/8.
 */

public class MyScrollView extends LinearLayout {

    public static boolean isFlowHead = false;
    private float mLastY;
    private boolean isIntercept = true;
    private int headHeight = 300;

    public MyScrollView(Context context) {
        super(context);init();
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);init();
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);init();
    }

    private void init(){
        isFlowHead = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentY = event.getY();
        float deltaY = 0;
        int dis = getScrollY();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_MOVE:
                deltaY = mLastY - currentY;
                isFlowHead = false;
                if (deltaY >= 0) {//上滑
//                    Log.e("","deltaY >= 0");
                    if (dis < headHeight && dis >= 0) {//不能超出屏幕
                        scrollBy(0, (int) deltaY);
                    } else if (dis >= headHeight) {
                        isFlowHead = true;
                        isIntercept = false;
                    } else if (dis >= 0) {
                        scrollBy(0, (int) deltaY);
                    } else {
                        scrollBy(0, 0);
                    }
                } else {//下滑
//                    Log.e("","deltaY < 0");
                    if (dis <= 0 && deltaY <= 0) {
                        scrollBy(0, 0);
                    } else if (dis >= 0) {
                        scrollBy(0, (int) deltaY);
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                if (dis <= 0) {
//                    isIntercept = false;
                    setScrollY(0);//假如滑动超出屏幕，回归0点
                }
                break;
        }
//        Log.e("deltaY", "deltaY " + deltaY + " dis " + dis);
        mLastY = currentY;
        return true;//防止事件向下传递导致move不触发
    }

    public void setHeadHeight(int headHeight) {
        this.headHeight = headHeight;
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }

}
