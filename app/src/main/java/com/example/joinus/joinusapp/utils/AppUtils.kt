package com.example.joinus.joinusapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager

/**
 * Created by shwetatiwari on 13/12/19.
 */

class AppUtils {

    companion object {

        fun getSharedPrefEditor(context: Context): SharedPreferences.Editor {
            return getSharedPrefs(context).edit()
        }

        fun getSharedPrefs(context: Context): SharedPreferences {
            return context.getSharedPreferences(Const.SHARED_PREF_CONST, Context.MODE_PRIVATE)
        }

        fun isNetworkConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
        }

    }

}