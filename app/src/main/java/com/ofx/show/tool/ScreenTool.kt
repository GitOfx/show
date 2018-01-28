package com.ofx.show.tool

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.ofx.show.base.App
import android.app.Activity
import java.lang.reflect.Field


/**
 * Created by Ofx on 2017/11/28.
 */
object ScreenTool {
    fun getScreenWidth(): Int {
        return getDisplayMetrics().widthPixels
    }

    fun getScreenHeight(): Int {
        return getDisplayMetrics().heightPixels
    }

    fun dp2px(dp: Int): Int {
        return (getDisplayMetrics().density * dp).toInt()
    }

    fun getDisplayMetrics(): DisplayMetrics {
        val context = App.appContext as Context
        val metrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(metrics)
        return metrics
    }

    fun getStatusBarHeight(): Int {
        val context = App.appContext as Context
        var c: Class<*>? = null
        var obj: Any? = null
        var field: Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }

        return statusBarHeight
    }
}