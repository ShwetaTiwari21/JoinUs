package com.example.joinus.joinusapp.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Build
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.activities.MainActivity

/**
 * Created by shwetatiwari on 13/12/19.
 */

class AppUtils {

    companion object {

        var alertdialog: AlertDialog? = null
        var mProgressDialog : ProgressDialog? = null

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

        fun showLoader(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                setProgressDialog(context)
            } else {
                showSimpleProgressDialog(context)
            }
        }

        fun setProgressDialog(context: Context) {
            try {
                val llPadding = 30
                val ll = LinearLayout(context)
                ll.orientation = LinearLayout.HORIZONTAL
                ll.setPadding(llPadding, llPadding, llPadding, llPadding)
                ll.gravity = Gravity.CENTER
                var llParam = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                llParam.gravity = Gravity.CENTER
                ll.layoutParams = llParam

                val progressBar = ProgressBar(context)
                progressBar.isIndeterminate = true
                progressBar.setPadding(0, 0, llPadding, 0)
                progressBar.layoutParams = llParam

                llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                llParam.gravity = Gravity.CENTER
                val tvText = TextView(context)
                tvText.text = "Loading ..."
                tvText.setTextColor(Color.parseColor("#333333"))
                tvText.textSize = 15f
                tvText.layoutParams = llParam

                ll.addView(progressBar)
                ll.addView(tvText)

                val builder = AlertDialog.Builder(context)
                builder.setView(ll)
                if (alertdialog == null) {
                    alertdialog = builder.create()
                    alertdialog?.setOwnerActivity(context as Activity)
                    alertdialog?.setCancelable(false)
                    alertdialog?.show()
                    alertdialog?.setCanceledOnTouchOutside(false)
                    val window = alertdialog?.getWindow()
                    if (window != null) {
                        val layoutParams = WindowManager.LayoutParams()
                        layoutParams.copyFrom(alertdialog?.getWindow()!!.getAttributes())
                        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
                        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                        alertdialog?.getWindow()!!.setAttributes(layoutParams)
                        (context as Activity).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)


                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun showSimpleProgressDialog(context: Context) {
            try {
                removeSimpleProgressDialog()

                showSimpleProgressDialog(context, null, context.resources.getString(R.string.simple_progress_dialog_loading), false)
            } catch (e: Exception) {
                e.printStackTrace()
                //            Crashlytics.logException(e);
            }

        }

        fun showSimpleProgressDialog(context: Context, s: String?, s1: String, cancelable: Boolean) {
            try {
                if (mProgressDialog == null) {
                    mProgressDialog = ProgressDialog(context, R.style.ProgressDialogTheme)
                    mProgressDialog?.setOwnerActivity(context as Activity)
                    mProgressDialog?.setMessage(s1)
                    mProgressDialog?.show()
                    val window = mProgressDialog?.getWindow()
                    window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                    //mProgressDialog = ProgressDialog.show(context, s, s1);
                    mProgressDialog?.setCancelable(cancelable)

                }
                if (!mProgressDialog?.isShowing!!) {

                    mProgressDialog?.show()

                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }

        fun removeSimpleProgressDialog() {
            try {
                if (mProgressDialog != null && mProgressDialog?.isShowing!!) {
                    val activity = mProgressDialog?.getOwnerActivity()
                    if (activity != null) {
                        mProgressDialog?.dismiss()
                        mProgressDialog = null
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

        }

        fun removeLoader(context: Context?) {

            if (context != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    removeSimpleProgressBar(context)
                } else {
                    removeSimpleProgressDialog()
                }
            }
        }

        fun removeSimpleProgressBar(context: Context) {
            try {
                val activity = context as Activity
                if (alertdialog != null && alertdialog?.isShowing!!) {
                    if (alertdialog?.getWindow() != null) {
                        (context as Activity).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                    }
                    alertdialog?.dismiss()
                    alertdialog = null
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

        }

    }

}