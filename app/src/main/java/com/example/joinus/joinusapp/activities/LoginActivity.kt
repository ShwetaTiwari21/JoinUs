package com.example.joinus.joinusapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import com.example.joinus.joinusapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var transAnimation : TranslateAnimation = TranslateAnimation(0.0f , 400.0f , 0.0f , 0.0f)
        transAnimation.duration = 5000
        transAnimation.repeatCount = 1300
        transAnimation.repeatMode = 2
        iv_logo.animation = transAnimation

        tv_signup.setOnClickListener{
            val intent = Intent(this@LoginActivity , SignupActivity::class.java)
            startActivity(intent)
        }

        bt_login.setOnClickListener{
            startActivity(Intent(this@LoginActivity , MainActivity::class.java))
        }

    }
}
