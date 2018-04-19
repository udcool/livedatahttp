package com.hyjoy.livedatahttp

import android.arch.lifecycle.ViewModel

/**
 * Created by hyjoy on 2018/4/19.
 */

class MainViewModel : ViewModel() {
    val callEvent = SingleLiveEvent<Void>()
    val singleEvent = SingleLiveEvent<Void>()
    val singlePageEvent = SingleLiveEvent<Void>()

    fun callClick() {
        callEvent.call()
    }

    fun singleClick() {
        singleEvent.call()
    }

    fun singlePageClick() {
        singlePageEvent.call()
    }
}