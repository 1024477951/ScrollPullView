package com.example.demo.test2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by LiuZhen on 2017/6/8.
 */

public class MyNestedScroll extends ScrollView {

    private int headHeight = 300;//默认高度，一般动态设置
    private int downY;

    public MyNestedScroll(Context context) {
        super(context);
    }

    public MyNestedScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        int dis = getScrollY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                /**判断是向下滑动**/
                if(downY-moveY>0){
                    if (dis < headHeight && dis >= 0){
                        return true;//滑动距离小于头部并且没有超出头部拦截事件，让本身去滑动
                    }else{
                        return false;//不拦截事件
                    }
                }else{
                }
        }

        return super.onInterceptTouchEvent(e);
    }

    public void setHeadHeight(int headHeight) {
        this.headHeight = headHeight;
    }

}
