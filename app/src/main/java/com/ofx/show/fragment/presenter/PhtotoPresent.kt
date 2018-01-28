package com.ofx.show.fragment.presenter

import android.content.ContentResolver
import android.content.Context
import android.provider.MediaStore
import com.ofx.show.databean.PhotoBean
import com.ofx.show.base.BasePresent
import com.ofx.show.tool.Logg
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Ofx on 2017/11/29.
 */
class PhtotoPresent :BasePresent{
    constructor(model: IPhotoModel,context: Context){
        mIPhotoModel = model
        this.mContext= context
    }

    var  mContext :Context? = null
    var mIPhotoModel:IPhotoModel? = null

    fun getPhoto():List<PhotoBean> {
        var contentResolver = mContext?.contentResolver as ContentResolver
        var uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var projection: Array<String> = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.DATE_MODIFIED,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT
                )
        var cursor = contentResolver.query(uri,projection,null,null,MediaStore.Images.Media.DEFAULT_SORT_ORDER)
        var coloum_path = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        var coloum_display_name = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
        var coloum_bucket = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        var coloum_title = cursor.getColumnIndex(MediaStore.Images.Media.TITLE)
        var coloum_date_add = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)
        var coloum_modify = cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)
        var coloum_size = cursor.getColumnIndex(MediaStore.Images.Media.SIZE)
        var coloum_mime_type = cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)
        var coloum_width = cursor.getColumnIndex(MediaStore.Images.Media.WIDTH)
        var coloum_height = cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)
        cursor.moveToFirst()
        var count = cursor.columnCount-1
        var photoList :List<PhotoBean> = emptyList<PhotoBean>()
        var time  = System.currentTimeMillis();
        Logg.i("start ---------------------")
        try {
            while (cursor != null && !cursor.isAfterLast()){
                var bean = PhotoBean()
                bean.DATA = cursor.getString(coloum_path)
                bean.DISPLAY_NAME = cursor.getString(coloum_display_name)
                bean.BUCKET_DISPLAY_NAME = cursor.getString(coloum_bucket)
                bean.DATE_ADDED = cursor.getLong(coloum_date_add)
                bean.TITLE = cursor.getString(coloum_title)
                bean.DATE_MODIFIED = cursor.getLong(coloum_modify)
                bean.SIZE = cursor.getString(coloum_size)
                bean.MIME_TYPE = cursor.getString(coloum_mime_type)
                bean.WIDTH = cursor.getLong(coloum_width)
                bean.HEIGHT = cursor.getLong(coloum_height)
                cursor.moveToNext()
                Logg.i(bean.toString())
                photoList += bean
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Logg.i("time ${(System.currentTimeMillis()-time)} size ${photoList.size}")
        Collections.sort(photoList)
        return photoList
    }

    interface IPhotoModel{
        fun loadPhotoResult(result :List<PhotoBean>)
    }

    fun loadPhotoList(){
        Observable.create(ObservableOnSubscribe<List<PhotoBean>> { e ->
            e.onNext(getPhoto())
            e.onComplete()
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> mIPhotoModel?.loadPhotoResult(result)  }

    }
}