package com.ofx.show.fragment

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.Transition
import com.bumptech.glide.Glide
import com.ofx.andtool.Logg
import com.ofx.show.R
import com.ofx.show.base.BaseFragment
import com.ofx.show.image.ImageTool
import kotlinx.android.synthetic.main.fg_img_display.*

/**
 * Created by Ofx on 2017/12/5.
 */
class ImageDisplayFragment:BaseFragment() {
    companion object {
        val img_url :String = "img_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fade = Fade();
        fade.duration = 1000
//        sharedElementEnterTransition =  fade
    }
    override fun initView() {

        var imgUrl :String = bundle?.get(img_url) as String
        ImageTool.display(imgUrl,iv_img,activity)
        Logg.i("scaletype "+iv_img.scaleType)
    }

    override fun getLayoutId(): Int {
        return R.layout.fg_img_display
    }


}