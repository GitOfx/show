package com.ofx.show.tool

import android.content.res.Resources
import com.ofx.show.base.App

/**
 * Created by Ofx on 2017/12/13.
 */
object ResTool {

    fun Res():Resources?{
        return App.appContext?.resources;
    }

    fun String(strId:Int) : String?{
        return Res()?.getString(strId)
    }
}