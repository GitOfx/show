package com.ofx.show.fragment.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ofx.show.databean.PhotoBean
import com.ofx.show.R
import com.ofx.show.View.ZImageView
import com.ofx.show.base.AppNavi
import com.ofx.show.base.BaseActivity
import com.ofx.show.fragment.ImageDisplayFragment
import com.ofx.show.fragment.PhotoFragment
import com.ofx.show.image.ImageTool
import com.ofx.show.tool.DateTool
import com.ofx.show.tool.Logg
import com.ofx.show.tool.ScreenTool
import java.io.File
import java.util.*

/**
 * Created by Ofx on 2017/11/29.
 */
class PhotoAdapter() : Adapter<RecyclerView.ViewHolder>() {
    var  dataList: List<PhotoBean> = emptyList()
    var  context :Context? = null
    var fg:PhotoFragment? = null

    constructor(list: List<PhotoBean>,ctx: Context,fragment: PhotoFragment) : this() {
        dataList = list
        context = ctx
        fg = fragment
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position].grid_title.isNullOrBlank()) 1 else 0
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder1: RecyclerView.ViewHolder?, position: Int) {
//        var width = (ScreenTool.getScreenWidth()-ScreenTool.dp2px(10))/3
//        var holder : RecyclerView.ViewHolder?=null
        if (getItemViewType(position) == 0){
            var holder  = holder1 as TitleHolder

            holder.tv_title?.text = dataList[position].grid_title
            return
        }
        var holder :PhtotoHolder = holder1 as PhtotoHolder
        ImageTool.display(dataList.get(position).DATA,holder!!.iv_photo,context!!)
        holder.iv_photo?.setOnClickListener {
            view ->
            var bundle = Bundle();
            bundle.putString(ImageDisplayFragment.img_url,dataList.get(position).DATA)
            bundle.putBoolean(AppNavi.hasTitleKey,false)
            bundle.putBoolean(AppNavi.isTransparentStatus,false)
            fg?.mShareElement = view
            fg?.mShareName = "share_image"
            var option = ActivityOptions.makeSceneTransitionAnimation(context as Activity,holder.iv_photo,"share_image")
            AppNavi.startActivityForFragmentWithTranstion(context as BaseActivity,ImageDisplayFragment::class,bundle,option)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
         if (viewType == 0){
             var itemView :View = LayoutInflater.from(context).inflate(R.layout.photo_item_title,parent,false)
             var holder:TitleHolder = TitleHolder(itemView)
             return holder
         }
         var itemView :View = LayoutInflater.from(context).inflate(R.layout.photo_item,parent,false)
//         Logg.i("onCreateViewHolder "+itemView)
         var holder:PhtotoHolder = PhtotoHolder(itemView)
         return holder
    }

    class PhtotoHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var iv_photo :ImageView? = null
        init {
            iv_photo = itemView.findViewById(R.id.iv_zphoto)
//            Logg.e("iv_photo = "+iv_photo+" R.id.iv_photo "+R.id.iv_zphoto+" itemView "+(itemView as ViewGroup).getChildAt(0).id+" "+(itemView as ViewGroup).getChildAt(0) )
            var width = (ScreenTool.getScreenWidth()-ScreenTool.dp2px(10))/3;
            iv_photo?.layoutParams = ConstraintLayout.LayoutParams(width,width)

        }
    }

    class TitleHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var tv_title :TextView? = null
        init {
            tv_title = itemView.findViewById(R.id.tv_title)
        }
    }
}