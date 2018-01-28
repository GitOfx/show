package com.ofx.show.base

import android.app.Fragment
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.content.PermissionChecker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ofx.show.tool.Permission


/**
 *
 */
abstract class BaseFragment : Fragment() {

//    private var mParam1: String? = null
//    private var mParam2: String? = null
    open var mShareElement:View? = null;
    open var mShareName:String? = null;

    var permissCallback:Permission.PermissionCallBack? = null
    protected var mFragmentView:View? = null
    protected var bundle:Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mFragmentView = inflater!!.inflate(getLayoutId(), container, false);
        return mFragmentView;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bundle = arguments;
        initView()
    }
    abstract fun getLayoutId():Int
    abstract fun initView():Unit

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    protected fun reqeustOnePermission(permissionName:String,requestCode:Int,permissionCallBack: Permission.PermissionCallBack){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissCallback = permissionCallBack
            requestPermissions(arrayOf(permissionName),requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissCallback != null){
             if (permissions != null && !permissions.isEmpty()) {
               if (grantResults != null && !grantResults.isEmpty()){
                   if (grantResults.get(0) == PermissionChecker.PERMISSION_GRANTED) {
                       permissCallback?.onPermissionsGrant(requestCode)
                   }else{
                       permissCallback?.onPermissionsDeny(requestCode)
                   }
               }else{
                   permissCallback?.onPermissionsDeny(requestCode)

               }
         }
        }
    }

    fun getShareElement():View?{
        return mShareElement
    }
    fun getShareName():String?{
        return mShareName
    }
}
