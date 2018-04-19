package com.hyjoy.livedatahttp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyjoy.livedatahttp.databinding.FragmentSinglepageBinding
import com.hyjoy.livedatahttp.entity.Gank

/**
 * Created by hyjoy on 2018/4/19.
 */
class SinglePageFragment : BaseListFragment<Gank, PageViewModel>() {
    lateinit var mBinding: FragmentSinglepageBinding


    override fun initView(view: View?) {
        super.initView(view)
        mViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java)
        mBinding.vm = mViewModel
        mBinding!!.vm!!.dataLoad.data.observe(this, Observer { data ->
            mDatas = data
        })
        mBinding!!.vm!!.dataLoad.status.observe(this, Observer { status ->
            if (status!!.isLoading) {

            }
        })

        mBinding!!.vm!!.dataLoad.pageStatus.observe(this, Observer { status ->
            if (status!!.isLoading) {

            }
        })

        mBinding!!.vm!!.dataLoad.hasNext.observe(this, Observer { next ->
            if (next != null) {
                mBinding.refreshLayout.setEnableLoadmore(next)
            }
        })
    }

    override fun initData() {
        super.initData()
        mBinding.refreshLayout.autoRefresh()
    }

    override fun createHolder(parent: ViewGroup?, viewType: Int): RVViewHolder {
        var view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_singlepage, parent, false)

        return RVViewHolder(view)
    }


}