package com.example.joinus.joinusapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.joinus.joinusapp.R
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        bt_signup.setOnClickListener{
            sendUserDetail()
        }
    }

    private fun sendUserDetail() {

    }
}
