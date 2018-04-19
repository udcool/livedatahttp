package com.hyjoy.livedatahttp;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laidian360.tuodian.base.BaseListViewModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment 列表基类
 * Created by hyjoy on 2018/3/16.
 */
public abstract class BaseListFragment<T, VM extends BaseListViewModel<T>> extends BaseFragment {

    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter<RVViewHolder> mAdapter;

    protected List<T> mDatas = new ArrayList();
    protected VM mViewModel;

    protected DividerItemDecoration mItemDecoration;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_baselist;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        initRefreshLayout();
        initRecyclerView();
        initAdapter();
        observeDataStatus();
    }

    /**
     * SmartRefreshLayout
     */
    protected void initRefreshLayout() {
        mRefreshLayout.setEnableLoadmore(true);
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                BaseListFragment.this.onLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                BaseListFragment.this.onRefresh();
            }
        });

    }

    /**
     * RecyclerView
     */
    protected void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mItemDecoration);
    }

    /**
     * 需要保证调用顺序
     */
    private void initAdapter() {
        mAdapter = new RecyclerView.Adapter<RVViewHolder>() {
            @NonNull
            @Override
            public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return createHolder(parent, viewType);
            }

            @Override
            public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
                bindHolder(holder, position);
            }

            @Override
            public int getItemCount() {
                return BaseListFragment.this.getItemCount();
            }

            @Override
            public int getItemViewType(int position) {
                return BaseListFragment.this.getItemViewType(position);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }


    protected void observeDataStatus() {
        if (mViewModel == null) return;
        // 对话框
        mViewModel.getDataLoad().getStatus().observe(this, status -> {
            // TODO: 对话框
            if (status.isLoading()) {
            } else {
            }
        });
        // 下一页
        mViewModel.getDataLoad().getPageStatus().observe(this, status -> {

            if (status.isSuccess()) {
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            } else if (status.isError()) {
                Toast.makeText(mContext, status.getMessage(), Toast.LENGTH_SHORT).show();
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadmore();
            }
        });
        // 数据改变时
        mViewModel.getDataLoad().getData().observe(this, data -> {
            mDatas = data;
            mAdapter.notifyDataSetChanged();
        });
        // 是否有下一页数据
        mViewModel.getDataLoad().getHasNext().observe(this, has -> {
            mRefreshLayout.setEnableLoadmore(has);
        });
    }

    /**
     * 刷新数据
     */
    protected void onRefresh() {
        mViewModel.getDataLoad().loadData();
    }

    /**
     * 加载更多
     */
    protected void onLoadmore() {
        mViewModel.getDataLoad().loadNextPage();
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract RVViewHolder createHolder(ViewGroup parent, int viewType);

    /**
     * @param holder
     * @param position
     */
    protected void bindHolder(RVViewHolder holder, int position) {

    }

    protected int getItemCount() {
        return mDatas.size();
    }

    protected int getItemViewType(int position) {
        return 0;
    }
}


