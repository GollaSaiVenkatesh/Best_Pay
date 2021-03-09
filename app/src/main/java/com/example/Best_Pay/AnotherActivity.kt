package com.example.Best_Pay

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.FileAsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.read.biff.BiffException
import java.io.File
import java.io.IOException

class AnotherActivity : AppCompatActivity() {
    var workbook: Workbook? = null
    var asyncHttpClient: AsyncHttpClient? = null
    var productTitle: MutableList<String>? = null
    var ratings: MutableList<String>? = null
    var reviewCount: MutableList<String>? = null
    var productPrice: MutableList<String>? = null
    var productImg: MutableList<String>? = null
    var recyclerView: RecyclerView? = null
    var adapter: Adapter? = null
    var progressBar: ProgressBar? = null
    var wait: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        wait = findViewById(R.id.wait)
        val URL = "https://github.com/GollaSaiVenkatesh/Best_Pay/blob/master/data/amazon.xls?raw=true"
        productTitle = ArrayList()
        ratings = ArrayList()
        reviewCount = ArrayList()
        productPrice = ArrayList()
        productImg = ArrayList()
        productPrice = ArrayList()
        asyncHttpClient = AsyncHttpClient()
        asyncHttpClient!![URL, object : FileAsyncHttpResponseHandler(this) {
            override fun onFailure(statusCode: Int, headers: Array<Header>, throwable: Throwable, file: File) {

                val toast = Toast.makeText(this@AnotherActivity, "Error in Downloading Excel File", Toast.LENGTH_SHORT)
                wait?.setVisibility(View.GONE)
                progressBar?.setVisibility(View.GONE)
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, file: File) {
                val ws = WorkbookSettings()
                ws.gcDisabled = true
                if (file != null) {
                    //text.setText("Success, DO something with the file.");
                    wait?.setVisibility(View.GONE)
                    progressBar?.setVisibility(View.GONE)
                    try {
                        workbook = Workbook.getWorkbook(file)
                        val sheet = workbook?.getSheet(0)
                        //Cell[] row = sheet.getRow(1);
                        //text.setText(row[0].getContents());
                        if (sheet != null) {
                            for (i in 1 until sheet.rows) {
                                val row = sheet.getRow(i)
                                productTitle?.add(row[0].contents)
                                ratings?.add(row[1].contents)
                                reviewCount?.add(row[2].contents)
                                productImg?.add(row[3].contents)
                                productPrice?.add(row.last().contents)
                            }
                        }
                        showData()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: BiffException) {
                        e.printStackTrace()
                    }
                }
            }
        }]
    }

    private fun showData() {
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        adapter = productTitle?.let { ratings?.let { it1 -> reviewCount?.let { it2 -> productImg?.let { it3 -> productPrice?.let { it4 -> Adapter(this, it, it1, it2, it3, it4) } } } } }
        recyclerView!!.adapter = adapter

    }
}