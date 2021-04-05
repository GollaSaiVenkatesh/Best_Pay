package com.example.Best_Pay

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val mi_button= findViewById<ImageButton>(R.id.mi_logo)
        val oppo_button = findViewById<ImageButton>(R.id.oppo_logo)
        val samsung_button = findViewById<ImageButton>(R.id.samsung_logo)
        mi_button.setOnClickListener {
            val intent= Intent(this, AnotherActivity::class.java)
            intent.putExtra(AnotherActivity.BRAND, "redmi")
            startActivity(intent)
        }
        oppo_button.setOnClickListener {
            val intent= Intent(this, AnotherActivity::class.java)
            intent.putExtra(AnotherActivity.BRAND, "oppo")
            startActivity(intent)
        }
        samsung_button.setOnClickListener {
            val intent= Intent(this, AnotherActivity::class.java)
            intent.putExtra(AnotherActivity.BRAND, "samsung")
            startActivity(intent)
        }












    }
}