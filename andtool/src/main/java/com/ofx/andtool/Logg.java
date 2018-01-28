package com.ofx.andtool;

import android.app.ActivityManager;
import android.util.Log;

/**
 * Created by Ofx on 2018/1/6.
 */

public class Logg{
    private final static String default_tag = "show_tag";
     public static boolean isDebug = true;

     public static void e(String msg){
         if (isDebug){
             Log.e(default_tag,msg);
         }
     }

    public static void i(String msg){
        if (isDebug){
            Log.i(default_tag,msg);
        }
    }

}
