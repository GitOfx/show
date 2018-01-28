package com.ofx.show.tool

import android.util.Log

/**
 * Created by Ofx on 2017/11/28.
 */
object Logg {
    var default_tag = "show_tag"
    var isDebug:Boolean = true;
    fun e(msg:String) {
        if (isDebug) {
            Log.e(default_tag,msg)
        }
    }

    fun i(msg:String) {
        if (isDebug) {
            Log.i(default_tag,msg)
        }
    }
}