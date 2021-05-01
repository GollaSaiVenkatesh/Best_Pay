package com.example.Best_Pay

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
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
        val product_name = findViewById<TextView>(R.id.product_name)
        product_name.text = product_title
        var py1 :Python = Python.getInstance()
        var pyo1 : PyObject = py1.getModule("least_price")
        var obj1 :PyObject = pyo1.callAttr("main",product_title)
        var least:String = obj1.toString()
        val least_price = findViewById<TextView>(R.id.least_price)
        least_price.text = least
        var py2 :Python = Python.getInstance()
        var pyo2 : PyObject = py2.getModule("highest_price")
        var obj2 :PyObject = pyo2.callAttr("main",product_title)
        var highest:String = obj2.toString()
        val highest_price = findViewById<TextView>(R.id.highest_price)
        highest_price.text = highest

    }
}