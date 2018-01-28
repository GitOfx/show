package com.ofx.show.base

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import com.ofx.show.R
import com.ofx.show.fragment.SplashFragment
import com.ofx.show.tool.Logg
import com.ofx.show.tool.ScreenTool
import kotlinx.android.synthetic.main.activity_base.*

/**
 * Created by Ofx on 2017/11/27.
 */
open class BaseActivity :AppCompatActivity(){

    var isFullScreen:Boolean = false;
    var hasTitle:Boolean = true
    var isTransparentStatus:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var intent = getIntent()
        var bundle = intent.extras
        var fragmentName = bundle?.getString(AppNavi.fragmentName)

        //if the name is null this must be come from splashFragment
        if (TextUtils.isEmpty(fragmentName)) {
            hasTitle = false
            isFullScreen = true;
            isTransparentStatus = false
        }

        if (bundle != null) {
            hasTitle = bundle.getBoolean(AppNavi.hasTitleKey,hasTitle)
            isFullScreen = bundle.getBoolean(AppNavi.isFullScreenKey,isFullScreen)
            isTransparentStatus = bundle.getBoolean(AppNavi.isFullScreenKey,isTransparentStatus)
        }

        Logg.e("fragmentName $fragmentName  hasTitle ${hasTitle}  isFullScreen ${isFullScreen} isTransparentStatus ${isTransparentStatus} activity ${this}")
        /*set it to be no title*/
        if (!hasTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
        /*set it to be full screen */
        if (isFullScreen){
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        /*http://www.jianshu.com/p/2a884e211a62*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val localLayoutParams = window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }
        setContentView(R.layout.activity_base)
        if (!hasTitle) {
            if (supportActionBar != null) {
                supportActionBar!!.hide()
            }
        }

        if (isTransparentStatus) {
            cl_contain.setPadding(0,ScreenTool.getStatusBarHeight(),0,0)
        }
        if (TextUtils.isEmpty(fragmentName)) {
            fragmentName  = SplashFragment::class.java.name
        }

        setFragment(fragmentName,bundle);
    }

    fun setFragment(fragmentName :String?,bundle:Bundle?){
        try {
            val fragmentClass = Class.forName(fragmentName)
            val fragmentInstance = fragmentClass.newInstance() as BaseFragment
            if (bundle != null) {
                fragmentInstance.arguments = bundle
            }

            var currentFrgment: BaseFragment? = null
            try {
                currentFrgment = fragmentManager.findFragmentByTag("fragment_tag") as BaseFragment
            } catch (e: Exception) {
            }
            if (currentFrgment != null
                    && currentFrgment.getShareElement() != null
                    && !TextUtils.isEmpty(currentFrgment.getShareName())){
                fragmentManager.beginTransaction()
                        .addSharedElement(fragmentInstance.getShareElement(),fragmentInstance.getShareName())
                        .replace(R.id.rl_content_fragment, fragmentInstance,"fragment_tag")
                        .commit()
            }else{
                fragmentManager.beginTransaction()
                        .replace(R.id.rl_content_fragment, fragmentInstance)
                        .commit()
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}