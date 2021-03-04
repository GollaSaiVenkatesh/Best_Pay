package com.example.Best_Pay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)




        fun main() {
            csvReader().open("data/amazon.csv") {
                readAllAsSequence().forEach { row ->
                    //Do something
                    println(row) //[a, b, c]
                }
            }
        }








    }
}