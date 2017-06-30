package com.example.demo.test2;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.BaseActivity;
import com.example.demo.R;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PtrClassicFrameScrollViewActivity extends BaseActivity {

    @BindView(R.id.scrollView)
    MyNestedScroll scrollView;
    @BindView(R.id.tv_head)
    TextView tv_head;
    @BindView(R.id.layout)
    PtrClassicFrameLayout layout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private boolean hasMeasured;
    int height,dy;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test2;
    }

    @Override
    protected void initView() {
        final LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new ContentAdapter());
        ViewTreeObserver vto = tv_head.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    height = tv_head.getMeasuredHeight();
                    scrollView.setHeadHeight(height);//设置头部的高度
                    hasMeasured = true;
                }
                return true;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

//                int visibleItemCount = manager.getChildCount();
//                int totalItemCount = manager.getItemCount();
                int pastVisiblesItems = manager.findFirstVisibleItemPosition();
                PtrClassicFrameScrollViewActivity.this.dy = dy;
                //小于0表示下拉滑动，下拉到顶端则禁止子控件刷新，让scrollview滑动，头部完全显示后才能下拉，这里只做下滑到顶部时禁止立刻刷新，让scrollview滑动
                if ( pastVisiblesItems == 0 && dy < 0) {
                    Log.e("onScrolled","dy "+dy);
                    layout.setEnabled(false);
                }
            }
        });
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new  ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                //滑动的距离超出头部，让列表控件自己滑动,dy大于0是上滑操作，这里只做滑动超出头部后交给子列表滑动
                boolean b = scrollView.getScrollY()==0;//上拉一段距离后下拉，结果直接下拉效果出现了，滑动失效
                //解决上拉滑动到底部后让上拉效果有用
                if (scrollView != null && scrollView.getMeasuredHeight() <= scrollView.getScrollY()+height && dy >= 0) {
                    b = true;
//                    Log.e("Changed","true ");
                }
                layout.setEnabled(b);
//                Log.e("Changed",""+b+" - scrollView.getScrollY "+scrollView.getScrollY());
            }
        });
        layout.setLastUpdateTimeRelateObject(this);
        layout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, recyclerView, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, recyclerView, header);
            }
        });

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // mPtrFrame.autoRefresh();
            }
        }, 100);


    }

    protected void updateData() {

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                layout.refreshComplete();
            }
        }, 1000);
    }

    class ContentAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ContentViewHolder(LayoutInflater.from(PtrClassicFrameScrollViewActivity.this).inflate(R.layout.item_test, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ContentViewHolder viewHolder = (ContentViewHolder) holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
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
