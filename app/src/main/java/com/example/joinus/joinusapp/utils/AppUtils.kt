package com.example.joinus.joinusapp.utils

import android.content.Context
import android.content.SharedPreferences

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

    }

}