package com.ofx.show.fragment

import android.os.Bundle
import android.widget.ImageView
import com.ofx.show.R
import com.ofx.show.base.AppNavi
import com.ofx.show.base.BaseActivity
import com.ofx.show.base.BaseFragment
import kotlinx.android.synthetic.main.fg_splash.*

/**
 * Created by Ofx on 2017/11/28.
 */
class SplashFragment() :BaseFragment() {

    override fun initView() {
        img_splash?.setImageResource(R.mipmap.splash)
        img_splash?.setOnClickListener {
            var bundle = Bundle();
            bundle.putBoolean(AppNavi.hasTitleKey,false)
            bundle.putBoolean(AppNavi.isTransparentStatus,false)
            AppNavi.startActivityForFragment(PhotoFragment::class,bundle, activity as BaseActivity?,true)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fg_splash
    }

}