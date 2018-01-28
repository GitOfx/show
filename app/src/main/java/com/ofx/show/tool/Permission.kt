package com.ofx.show.tool

/**
 * Created by Ofx on 2017/11/29.
 */
object Permission {
    val request_code_READ_EXTERNAL_STORAGE = 100;

    interface PermissionCallBack{
        fun onPermissionsGrant(requestCode: Int)
        fun onPermissionsDeny(requestCode: Int)
    }
}