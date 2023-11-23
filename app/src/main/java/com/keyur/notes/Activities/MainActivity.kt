package com.keyur.notes.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.keyur.notes.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeSC::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}