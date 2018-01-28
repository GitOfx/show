package com.ofx.show.tool

import android.text.format.DateFormat
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ofx on 2018/1/7.
 */
object DateTool {
    var formate_yymmdd:String = "yyyy-MM-dd"

    fun Str2DateFormat(dateString: String):String{
        var dateFormate = SimpleDateFormat(formate_yymmdd);

        var date: String =  dateFormate.format(Date(dateString.toLong()))
        return date
    }
}