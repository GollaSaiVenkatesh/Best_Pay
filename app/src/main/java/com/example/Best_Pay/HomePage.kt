package com.example.Best_Pay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton


class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val mi_button=findViewById<ImageButton>(R.id.mi_logo)
        mi_button.setOnClickListener {
            val intent= Intent(this,AnotherActivity::class.java)
            startActivity(intent)
            finish()
        }












    }
}