package com.example.joinus.joinusapp.utils

/**
 * Created by shwetatiwari on 13/12/19.
 */

  class Const {

    companion object {
        var SHARED_PREF_CONST = "SHARED_PREF_CONST"
        const val RETROFIT_NETWORK_CALL_TIMEOUT: Long = 60


        // api

        const val GET_API = "/getApi"
        const val EVENT_ID = "EVENT_ID"
        const val SIGNUP_API = "/api/usermasters/signup"
        const val LOGIN_API = "/api/usermasters/login"


        const val TAG = "hello"
        var SHARED_PREF_USERNAME = "username"

        const val CATEGORY_ID = "CATEGORY_ID"
        const val NAV_HOME_ID = 0
        const val NAV_SPORTS_ID = 1
        const val NAV_MUNCHES_ID = 2
        const val NAV_TRIPS_ID = 3
        const val NAV_COUPONS_ID = 4
        const val NAV_MOVIES_ID = 5
        const val NAV_OTHERS_ID = 6


    }
}