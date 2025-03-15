package com.example.expensesplitterapp.Ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.expensesplitterapp.R
import com.example.expensesplitterapp.SharedPrefManager

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPrefManager = SharedPrefManager.getInstance(this)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()  // Hide ActionBar if exists

        Handler(Looper.getMainLooper()).postDelayed({
            val userType = sharedPrefManager.getUserType()
            if(userType == null){
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 1500)
    }
}