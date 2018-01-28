package com.ofx.show.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.support.v4.content.PermissionChecker
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup
import android.view.View
import com.ofx.show.databean.PhotoBean
import com.ofx.show.R
import com.ofx.show.base.BaseFragment
import com.ofx.show.fragment.adapter.PhotoAdapter
import com.ofx.show.fragment.presenter.PhtotoPresent
import com.ofx.show.tool.Logg
import com.ofx.show.tool.Permission
import com.ofx.show.tool.Permission.PermissionCallBack
import kotlinx.android.synthetic.main.fg_photo.*

/**
 * Created by Ofx on 2017/11/28.
 */
class PhotoFragment :BaseFragment(),PhtotoPresent.IPhotoModel{

    override fun initView() {
        presenter = PhtotoPresent(this,activity)

        if (PermissionChecker.checkSelfPermission(activity,Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                reqeustOnePermission(Manifest.permission.READ_EXTERNAL_STORAGE,Permission.request_code_READ_EXTERNAL_STORAGE,
                        object :PermissionCallBack{
                            override fun onPermissionsDeny(requestCode: Int) {

                            }

                            override fun onPermissionsGrant(requestCode: Int) {
                                presenter?.loadPhotoList();
                            }
                        })
            }
        }else{
            presenter?.loadPhotoList();
        }
    }

    var presenter:PhtotoPresent? = null
    var dataList:List<PhotoBean> = emptyList()

    override fun getLayoutId(): Int {
        return R.layout.fg_photo
    }

    override fun loadPhotoResult(result :List<PhotoBean>) {
        dataList = handleTitle(result)
        var layoutManager = GridLayoutManager(activity,3)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                var size = if (dataList[position].grid_title.isNullOrBlank()) 1 else layoutManager.spanCount
                Logg.i("size "+size+" POS "+position)
                return  size
            }
        }
        rv_photo.layoutManager = layoutManager

        rv_photo.adapter = PhotoAdapter(dataList,activity,this)
    }

    fun handleTitle(result :List<PhotoBean>):List<PhotoBean>{
       var list: MutableList<PhotoBean> = mutableListOf()
        var title:String = ""
        for (photoBean in result) {
            if (!title .equals(photoBean.getDate())){
                var titleBean = PhotoBean();
                titleBean.grid_title = photoBean.getDate()
                list.add(titleBean)
                title =  photoBean.getDate()
            }
            list.add(photoBean)
        }
        return list
    }

}