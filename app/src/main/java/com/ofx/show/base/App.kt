package com.ofx.show.base

import android.app.Application
import android.content.Context

/**
 * Created by Ofx on 2017/11/28.
 */
class App :Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

        Thread.setDefaultUncaughtExceptionHandler { t, e -> e.printStackTrace() }
    }

    companion object {
        var appContext: Context? = null
    }
}