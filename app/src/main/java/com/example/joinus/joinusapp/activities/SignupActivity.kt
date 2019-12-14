package com.example.joinus.joinusapp.activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.joinus.joinusapp.MyApplication
import com.example.joinus.joinusapp.R
import com.example.joinus.joinusapp.models.ResponseModel
import com.example.joinus.joinusapp.models.SignupReqModel
import com.example.joinus.joinusapp.utils.AppUtils
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        bt_signup.setOnClickListener{
            sendUserDetail()
        }
    }

    private fun sendUserDetail() {
        try {
            val signupReqModel = SignupReqModel(et_username.text.toString(), et_name.text.toString(),et_phone.text.toString())

            if (AppUtils.isNetworkConnected(this@SignupActivity)) {
//                progressDialog = ProgressDialog(this@SignupActivity)
//                progressDialog.setMessage("Loading....")
//                progressDialog.show()
                AppUtils.showLoader(this@SignupActivity)

                val call = MyApplication.networkService.signUp(signupReqModel)
                call.enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>?) {
                        AppUtils.removeLoader(this@SignupActivity)
                        if (response != null && response.body() != null && response.body().status.equals("OK")) {
                            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        AppUtils.removeLoader(this@SignupActivity)
//                        progressDialog.dismiss()
                        Log.e("errror", toString())
                        Toast.makeText(this@SignupActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@SignupActivity, "Network not connected!", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

    }
}
