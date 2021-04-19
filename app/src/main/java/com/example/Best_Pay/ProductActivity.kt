package com.example.Best_Pay

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.widget.ImageButton
import android.widget.ImageView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import java.util.*

class ProductActivity : AppCompatActivity() {
    companion object {
        const val title = "product_title"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        val product_title = intent?.extras?.getString(ProductActivity.title).toString()
        if(!Python.isStarted())
            Python.start( AndroidPlatform(this))
        var py :Python = Python.getInstance()
        var pyo : PyObject = py.getModule("myscript1")
        var obj :PyObject = pyo.callAttr("main",product_title)
        var str:String = obj.toString()
        var data: ByteArray = android.util.Base64.decode(str,android.util.Base64.DEFAULT)
        var bmp:Bitmap = BitmapFactory.decodeByteArray(data,0,data.size)
        val graph = findViewById<ImageView>(R.id.price_graph)
        graph.setImageBitmap(bmp)

    }
}