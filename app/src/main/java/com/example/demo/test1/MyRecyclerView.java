package com.example.demo.test1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by LiuZhen on 2017/6/8.
 */

public class MyRecyclerView extends RecyclerView {

    float y1 = 0;
    float y2 = 0;
    CallBack callBack;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                y2 = event.getY();
                if (y1 - y2 > 50) {
                    //向上滑
//                    MyScrollView.STATE = 2;
//                    if (!MyScrollView.isFlowHead){
//                        callBack.top();
//                    }
//                    Log.e("isTop", "向上滑"+MyScrollView.isFlowHead);
                } else if (y2 - y1 > 50) {
                    //向下滑
//                    MyScrollView.STATE = 1;
//                    Log.e("isTop", "向下滑");
                    if (computeVerticalScrollOffset() == 0) {
                        callBack.top();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void top();
    }

}
