package com.example.demo.test1;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.BaseActivity;
import com.example.demo.R;

import butterknife.BindView;

public class Test1Activity extends BaseActivity {

    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.layout)
    SwipeRefreshLayout layout;
    @BindView(R.id.recyclerView)
    MyRecyclerView recyclerView;
    private boolean hasMeasured;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test1;
    }

    @Override
    protected void initView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(Test1Activity.this));
        recyclerView.setAdapter(new ContentAdapter());
        ViewTreeObserver vto = tv_head.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    int height = tv_head.getMeasuredHeight();
                    scrollView.setHeadHeight(height);
                    hasMeasured = true;
                }
                return true;
            }
        });
        recyclerView.setCallBack(new MyRecyclerView.CallBack() {
            @Override
            public void top() {
                scrollView.setIntercept(true);
            }
        });
        layout.setEnabled(false);
        //设置刷新时动画的颜色，可以设置4个
        layout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //正在刷新
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //刷新完成
                        layout.setRefreshing(false);
                    }
                }, 500);
            }
        });
    }


    class ContentAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentViewHolder(LayoutInflater.from(Test1Activity.this).inflate(R.layout.item_test, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ContentViewHolder viewHolder = (ContentViewHolder) holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Test1Activity.this, "ok", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 15;
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        public ContentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
