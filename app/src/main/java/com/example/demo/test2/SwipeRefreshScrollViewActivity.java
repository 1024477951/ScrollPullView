package com.example.demo.test2;

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

public class SwipeRefreshScrollViewActivity extends BaseActivity {

    @BindView(R.id.scrollView)
    MyNestedScroll scrollView;
    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.layout)
    SwipeRefreshLayout layout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private boolean hasMeasured;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test3;
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(SwipeRefreshScrollViewActivity.this));
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
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new  ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                boolean b = scrollView.getScrollY()==0;
                layout.setEnabled(b);
            }
        });
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
            return new ContentViewHolder(LayoutInflater.from(SwipeRefreshScrollViewActivity.this).inflate(R.layout.item_test, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ContentViewHolder viewHolder = (ContentViewHolder) holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SwipeRefreshScrollViewActivity.this, ""+position, Toast.LENGTH_SHORT).show();
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
