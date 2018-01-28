package com.ofx.show.image

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Ofx on 2017/11/30.
 */
object ImageTool {
    fun display(url: String, imageView: ImageView?,context: Context?): Unit {
        if (TextUtils.isEmpty(url)) {
            return
        }

        if (imageView == null || context == null) {
            return
        }
        Glide.with(context)
                .load(url)
                .into(imageView)

    }
    fun display(url: String, imageView: ImageView?,context: Context?,width:Int,height:Int): Unit {
       Glide.with(context)
                .asBitmap()
               .load(url)
               .submit(width,height)

//        imageView?.setImageBitmap(futureTarget.get())

    }
}