package com.example.Best_Pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AnotherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)
        val actionBar= supportActionBar
        actionBar!!.title="Another Activity"


    }
}