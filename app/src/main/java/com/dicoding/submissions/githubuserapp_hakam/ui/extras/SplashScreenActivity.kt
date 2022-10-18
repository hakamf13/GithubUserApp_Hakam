package com.dicoding.submissions.githubuserapp_hakam.ui.extras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.submissions.githubuserapp_hakam.R
import com.dicoding.submissions.githubuserapp_hakam.data.token.ConstantToken.Companion.WAKTU_LOADING
import com.dicoding.submissions.githubuserapp_hakam.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, WAKTU_LOADING.toLong())
    }
}