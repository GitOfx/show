package com.ofx.show.databean

import com.ofx.show.tool.DateTool

/**
 * Created by Ofx on 2017/11/29.
 */
class PhotoBean() :Comparable<PhotoBean>{
    override fun compareTo(other: PhotoBean): Int {
        return (other!!.DATE_MODIFIED - DATE_MODIFIED).toInt()
    }


    var DATA = "_data"
    var DATE_ADDED = 0L
    var DATE_MODIFIED = 0L

    var DISPLAY_NAME = "_display_name"
    var HEIGHT = 0L
    var MIME_TYPE = "mime_type"
    var SIZE = "_size"
    var TITLE = ""
    var WIDTH = 0L
    var BUCKET_DISPLAY_NAME = "bucket_display_name"
    var grid_title = ""

    fun getDate():String{
        return DateTool.Str2DateFormat((DATE_MODIFIED*1000L).toString())
    }
    override fun toString(): String {
        return "PhotoBean(DATA='$DATA', DATE_ADDED='${DateTool.Str2DateFormat((DATE_ADDED*1000L).toString())}', DATE_MODIFIED='${DateTool.Str2DateFormat((DATE_MODIFIED*1000L).toString())}', DISPLAY_NAME='$DISPLAY_NAME', HEIGHT='$HEIGHT', MIME_TYPE='$MIME_TYPE', SIZE='$SIZE', TITLE='$TITLE', WIDTH='$WIDTH', BUCKET_DISPLAY_NAME='$BUCKET_DISPLAY_NAME')"
    }


}