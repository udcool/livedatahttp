package com.hyjoy.livedatahttp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hyjoy on 2018/3/9.
 */
public abstract class BaseFragment extends Fragment {
    protected final String TAG = getClass().getSimpleName();

    protected Context mContext;
    protected View mVRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        initArguments(bundle == null ? new Bundle() : bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mVRoot != null) {
            ViewGroup parent = (ViewGroup) mVRoot.getParent();
            if (parent != null)
                parent.removeView(mVRoot);
        } else {
            mVRoot = inflater.inflate(getLayoutId(), container, false);
//            ButterKnife.bind(this, mVRoot);

            initView(mVRoot);
            initData();
        }
        return mVRoot;
    }

    protected abstract int getLayoutId();

    protected void initArguments(Bundle bundle) {

    }

    protected void initView(View view) {
    }

    protected void initData() {

    }
}
