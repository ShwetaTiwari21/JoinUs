package com.example.joinus.joinusapp.network

import android.content.Context
import com.example.joinus.joinusapp.utils.AppUtils
import com.example.joinus.joinusapp.utils.Const
import java.util.HashMap

/**
 * Created by shwetatiwari on 14/12/19.
 */

class HeaderGenerator {
    companion object {
        fun createHeaderMap(context: Context): Map<String, String>? {
            try {
                val map = HashMap<String, String>()
                val username = AppUtils.getSharedPrefs(context).getString(Const.SHARED_PREF_USERNAME, "")

                map.put(Const.USERNAME, username)

                return map
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

        }
    }

}