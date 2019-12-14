package com.example.joinus.joinusapp.activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LongDef
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Toast
import com.example.joinus.joinusapp.MyApplication
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.ResponseModel
import com.example.joinus.joinusapp.models.SignupReqModel
import com.example.joinus.joinusapp.utils.AppUtils
import com.example.joinus.joinusapp.utils.Const
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var transAnimation : TranslateAnimation = TranslateAnimation(0.0f , 400.0f , 0.0f , 0.0f)
        transAnimation.duration = 5000
        transAnimation.repeatCount = 1300
        transAnimation.repeatMode = 2
//        iv_logo.animation = transAnimation

        tv_signup.setOnClickListener{
            val intent = Intent(this@LoginActivity , SignupActivity::class.java)
            startActivity(intent)
        }

        bt_login.setOnClickListener{
            makeLoginReq()
        }

    }

    private fun makeLoginReq(){
        try {
            var loginReq = SignupReqModel(et_login_username.text.toString())

            if (AppUtils.isNetworkConnected(this@LoginActivity)) {
//                progressDialog = ProgressDialog(this@LoginActivity)
//                progressDialog.setMessage("Loading....")
//                progressDialog.show()
                AppUtils.showLoader(this@LoginActivity)

                val call = MyApplication.networkService.login(loginReq)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
                        AppUtils.removeLoader(this@LoginActivity)
//                        progressDialog.dismiss()
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            AppUtils.getSharedPrefEditor(this@LoginActivity).putString(Const.SHARED_PREF_USERNAME,et_login_username.text.toString()).apply()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        AppUtils.removeLoader(this@LoginActivity)
//                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(this@LoginActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@LoginActivity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}
