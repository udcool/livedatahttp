package com.hyjoy.livedatahttp

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Toast
import com.hyjoy.livedatahttp.databinding.ActivityMainBinding
import com.tbruyelle.rxpermissions2.RxPermissions

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = LayoutInflater.from(this).inflate(R.layout.activity_main, null)
        setContentView(view)
        mBinding = DataBindingUtil.bind(view)!!

        RxPermissions(this).request(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe({ aBoolean ->
                    mBinding.vm = ViewModelProviders.of(this).get(MainViewModel::class.java)
                    mBinding.vm!!.callEvent.observe(this, Observer<Void> {
                        Toast.makeText(this, "Call", Toast.LENGTH_SHORT).show()
                        BackActivity.startFragment(this, CallFragment::class.java)
                    })
                    mBinding.vm!!.singleEvent.observe(this, Observer<Void> {
                        Toast.makeText(this, "Single", Toast.LENGTH_SHORT).show()
                        BackActivity.startFragment(this, SingleFragment::class.java)
                    })
                    mBinding.vm!!.singlePageEvent.observe(this, Observer<Void> {
                        Toast.makeText(this, "列表", Toast.LENGTH_SHORT).show()
                        BackActivity.startFragment(this, SinglePageFragment::class.java)
                    })

                })
    }
}
