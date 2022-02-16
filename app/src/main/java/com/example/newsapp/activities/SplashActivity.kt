package com.example.newsapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.newsapp.R

class SplashActivity : BaseActivity() {

    val SPLASH_TIME_OUT = 1500L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setStatusBar(mActivity)
        setUpSplash()
    }

    private fun setUpSplash() {
        Handler().postDelayed({
            val mIntent = Intent(mActivity, LogInActivity::class.java)
            startActivity(mIntent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}