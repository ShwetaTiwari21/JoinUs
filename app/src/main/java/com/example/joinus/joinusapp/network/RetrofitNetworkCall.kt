//package com.example.joinus.joinusapp.network
//
//import android.content.Context
//import android.util.Log
//import com.example.joinus.joinusapp.utils.Const
//import org.greenrobot.eventbus.EventBus
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.lang.reflect.Type
//
///**
// * Created by shwetatiwari on 13/12/19.
// */
// class RetrofitNetworkCall {
//
//    companion object {
//        fun makeRetrofitCall(context: Context, call: Call, tag: String, transactionWorkerId: String,
//                             transactionAccessToken: String) {
//            try {
//                val eventBus = EventBus.getDefault()
//
//                for (i in 0 until call.request().headers().size()) {
//                    Log.d(Const.TAG, "hello im the header " + call.request().headers().name(i))
//                    Log.d(Const.TAG, "hello im the header " + call.request().headers().value(i))
//                }
//
//                Log.d(Const.TAG, "hitting it with api: " + call.request().toString())
//
//                call.enqueue(object : Callback {
//                    override fun onResponse(call: Call<*>, response: Response<*>) {
//
//                        if (response.isSuccessful) {
//                            val headers = response.headers()
//
//                            eventBus.post(RetrofitSuccessEvent(response, tag))
//
//                        }
//                    }
//
//                    override fun onFailure(call: Call<*>, t: Throwable) {
////                        val apiError = Error(-1, t.message)
////                        eventBus.post(RetrofitErrorEvent(apiError, tag))
//                    }
//                })
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//        }
//
//        fun handleSuccess(response: Response<*>, context: Context) {
//            val eventBus = EventBus.getDefault()
//            val headers = response.headers()
//
//
//        }
//
//        fun handleError(response: Response<*>, context: Context) {
//            val eventBus = EventBus.getDefault()
//            try {
//                val apiError = RetrofitErrorUtils.parseErrorResponse(response.errorBody().string())
//
//                if (apiError.getStatusCode() === 403 && apiError.getReason() != null &&
//                        apiError.getReason().equalsIgnoreCase(Const.APP_UPDATE_FORCED)) {
//
//                    val appUpdateObject = AppUpdate()
//                    appUpdateObject.setUpdate_type(Const.APP_UPDATE_FORCED)
//                    appUpdateObject.setMessage(apiError.message)
//                    appUpdateObject.setLink(apiError.getApp_link())
//                    appUpdateObject.setApp_link_side_load(apiError.getApp_link_side_load())
//                    appUpdateObject.setLatest_version_code(apiError.getLatest_version_code())
//                    appUpdateObject.setMin_supported_version_code(apiError.getMin_supported_version_code())
//
//                    val appSettingsEvent = AppSettingsEvent()
//                    appSettingsEvent.setShowOptionalUpdate(true)
//                    appSettingsEvent.setAppUpdate(appUpdateObject)
//
//                    EventBus.getDefault().postSticky(appSettingsEvent)
//
//                } else if (apiError.getStatusCode() === 403 && apiError.getReason() != null &&
//                        apiError.getReason().equalsIgnoreCase(Const.APP_WRONG_TIME)) {
//                    RiderUtils.handleWrongTime(apiError, context)
//
//                } else if (apiError.getStatusCode() === 401) {
//                    if (RiderUtils.isAppIsInBackground(context)) {
//                        RiderUtils.getSharedPrefEditor(context).putBoolean(Const.APP_ERROR_FORCED_LOGOUT, true).apply()
//                    } else {
//                        if (eventBus.hasSubscriberForEvent(LogoutEvent::class.java)) {
//                            val logoutEvent = LogoutEvent(true, context)
//                            eventBus.post(logoutEvent)
//                        }
//                    }
//
//                    RiderUtils.stopBackgroundServices(context)    //stop any running background services
//                }
//
//                pushErrorEvent(apiError)
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Crashlytics.logException(e)
//            }
//
//        }
//    }
//}