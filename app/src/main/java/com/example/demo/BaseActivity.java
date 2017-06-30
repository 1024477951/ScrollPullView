package com.example.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by LiuZhen on 2017/6/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = BaseActivity.this;
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(BaseActivity.this);
        initView();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
