package com.example.Best_Pay

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    val SPLASH_SCREEN=5000
    private lateinit var topAnimation: Animation
    private lateinit var bottomAnimation: Animation
    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        val actionBar=supportActionBar
        actionBar!!.hide()
        




        topAnimation=AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation=AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        imageView1=findViewById(R.id.BPR)
        imageView2=findViewById(R.id.rectangles)

        imageView1.animation=topAnimation
        imageView2.animation=bottomAnimation
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            val intent= Intent(this,HomePage::class.java)
            startActivity(intent)
            finish()


        }, SPLASH_SCREEN.toLong())




    }
}