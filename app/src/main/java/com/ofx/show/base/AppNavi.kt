package com.ofx.show.base

import android.app.ActivityOptions
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import kotlin.reflect.KClass

/**
 * Created by Ofx on 2017/11/28.
 */
object AppNavi {
    val hasTitleKey:String = "hasTitle"
    val isFullScreenKey:String = "hasTitle"
    val fragmentName:String = "fragmentName"
    val isTransparentStatus:String = "isTransparentStatus"

    fun <T:Fragment> startActivityForFragment(clazz: KClass<T>, bundl: Bundle? = null,fromActivity: BaseActivity? = null,finish:Boolean = false) {
        var fragment_name = clazz.java.name;
        var bundle = bundl
        val intent = Intent(App.appContext, BaseActivity::class.java)
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString(fragmentName, fragment_name)
        intent.putExtras(bundle)
        App.appContext!!.startActivity(intent)
        if (finish) fromActivity?.finish()
    }

    fun <T:Fragment> startActivityForFragmentWithTranstion(fromActivity: BaseActivity,clazz: KClass<T>, bundl: Bundle? = null,options: ActivityOptions? = null) {
        var fragment_name = clazz.java.name;
        var bundle = bundl
//        var trans = ActivityOptions.makeSceneTransitionAnimation()
        val intent = Intent(fromActivity, BaseActivity::class.java)
        if (bundle == null) {
            bundle = Bundle()
        }
        bundle.putString(fragmentName, fragment_name)
        intent.putExtras(bundle)
        fromActivity.startActivity(intent,options?.toBundle())
    }
}