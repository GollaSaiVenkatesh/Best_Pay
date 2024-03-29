package com.example.Best_Pay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
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
    var productName: MutableList<String>? = null
    var ratings: MutableList<String>? = null
    var reviewCount: MutableList<String>? = null
    var productPrice: MutableList<String>? = null
    lateinit var amazonPrice: MutableList<String>
    lateinit var flipkartPrice: MutableList<String>
    lateinit var amazonUrl: MutableList<String>
    lateinit var flipkartUrl: MutableList<String>
    var productImg: MutableList<String>? = null
    var recyclerView: RecyclerView? = null
    var adapter: Adapter? = null
    var progressBar: ProgressBar? = null
    var wait: TextView? = null
    var flipkartProducts: MutableMap<String,String>? = null
    var amazonProducts: MutableMap<String,String>? = null
    var flipkartProductUrls: MutableMap<String,String>? = null
    var amazonProductUrls: MutableMap<String,String>? = null
    companion object {
        const val BRAND = "brand"
    }
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)
        val company_name = intent?.extras?.getString(BRAND).toString()
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        wait = findViewById(R.id.wait)
        val URL1 = "https://github.com/GollaSaiVenkatesh/Best_Pay/blob/master/data/flipkart.xls?raw=true"
        val URL2 = "https://github.com/GollaSaiVenkatesh/Best_Pay/blob/master/data/amazon.xls?raw=true"
        productTitle = ArrayList()
        productName = ArrayList()
        ratings = ArrayList()
        reviewCount = ArrayList()
        productPrice = ArrayList()
        amazonPrice = ArrayList()
        flipkartPrice = ArrayList()
        productImg = ArrayList()
        productPrice = ArrayList()
        amazonUrl = ArrayList()
        flipkartUrl = ArrayList()
        flipkartProducts = mutableMapOf()
        amazonProducts = mutableMapOf()
        flipkartProductUrls = mutableMapOf()
        amazonProductUrls = mutableMapOf()
        asyncHttpClient = AsyncHttpClient()
        asyncHttpClient!![URL1, object : FileAsyncHttpResponseHandler(this) {
            override fun onFailure(statusCode: Int, headers: Array<Header>, throwable: Throwable, file: File) {

                val toast = Toast.makeText(this@AnotherActivity, "Error in Downloading Excel File", Toast.LENGTH_SHORT)
                toast.show()
                wait?.setVisibility(View.GONE)
                progressBar?.setVisibility(View.GONE)
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, file: File) {
                val ws = WorkbookSettings()
                ws.gcDisabled = true
                if (file != null) {
                    wait?.setVisibility(View.GONE)
                    progressBar?.setVisibility(View.GONE)
                    try {
                        workbook = Workbook.getWorkbook(file)
                        val sheet = workbook?.getSheet(0)
                        if (sheet != null) {

                            for (i in 1 until sheet.rows) {
                                val row = sheet.getRow(i)
                                if(company_name=="lowest ever"){
                                    if(!Python.isStarted())
                                        Python.start( AndroidPlatform(this@AnotherActivity))
                                    var py4 : Python = Python.getInstance()
                                    var pyo4 : PyObject = py4.getModule("least_price")
                                    var obj4 : PyObject = pyo4.callAttr("main",row[0].contents)
                                    var least:Float = obj4.toFloat()
                                    if(row.last().contents !="unavailable" && row.last().contents != "Not Found") {
                                        if (row.last().contents.replace(",","").toFloat() <= least) {
                                            if (row[0].contents.toLowerCase().replace(" ", "") !in productName as ArrayList<String>) {
                                                productTitle?.add(row[0].contents)
                                                productName?.add(row[0].contents.toLowerCase().replace(" ", ""))
                                                ratings?.add(row[1].contents)
                                                reviewCount?.add(row[2].contents)
                                                productImg?.add(row[3].contents)
                                                productPrice?.add(row.last().contents)

                                            }
                                            flipkartProducts!![row[0].contents.toLowerCase().replace(" ", "")] = row.last().contents
                                            flipkartProductUrls!![row[0].contents.toLowerCase().replace(" ", "")] = row[4].contents
                                        }
                                    }

                                }
                                else {
                                    if (company_name.toLowerCase().replace(" ", "") in row[0].contents.toLowerCase().replace(" ", "")) {
                                        if (row[0].contents.toLowerCase().replace(" ", "") !in productName as ArrayList<String>) {
                                            productTitle?.add(row[0].contents)
                                            productName?.add(row[0].contents.toLowerCase().replace(" ", ""))
                                            ratings?.add(row[1].contents)
                                            reviewCount?.add(row[2].contents)
                                            productImg?.add(row[3].contents)
                                            productPrice?.add(row.last().contents)

                                        }
                                        flipkartProducts!![row[0].contents.toLowerCase().replace(" ", "")] = row.last().contents
                                        flipkartProductUrls!![row[0].contents.toLowerCase().replace(" ", "")] = row[4].contents


                                    }
                                }
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
        asyncHttpClient!![URL2, object : FileAsyncHttpResponseHandler(this) {
            override fun onFailure(statusCode: Int, headers: Array<Header>, throwable: Throwable, file: File) {

                val toast = Toast.makeText(this@AnotherActivity, "Error in Downloading Excel File", Toast.LENGTH_SHORT)
                toast.show()
                wait?.setVisibility(View.GONE)
                progressBar?.setVisibility(View.GONE)
            }

            override fun onSuccess(statusCode: Int, headers: Array<Header>, file: File) {
                Log.d("amazon","1")
                val ws = WorkbookSettings()
                ws.gcDisabled = true
                if (file != null) {
                    wait?.setVisibility(View.GONE)
                    progressBar?.setVisibility(View.GONE)
                    try {
                        workbook = Workbook.getWorkbook(file)
                        val sheet = workbook?.getSheet(0)
                        if (sheet != null) {

                            for (i in 1 until sheet.rows) {
                                val row = sheet.getRow(i)
                                if(company_name=="lowest ever"){
                                    if(!Python.isStarted())
                                        Python.start( AndroidPlatform(this@AnotherActivity))
                                    var py4 : Python = Python.getInstance()
                                    var pyo4 : PyObject = py4.getModule("least_price")
                                    var obj4 : PyObject = pyo4.callAttr("main",row[0].contents)
                                    var least:Float = obj4.toFloat()
                                    if(row.last().contents !="unavailable" && row.last().contents != "Not Found"){
                                        if(row.last().contents.replace(",","").toFloat() <= least) {
                                            if (row[0].contents.toLowerCase().replace(" ", "") !in productName as ArrayList<String>) {
                                                productTitle?.add(row[0].contents)
                                                productName?.add(row[0].contents.toLowerCase().replace(" ", ""))
                                                ratings?.add(row[1].contents)
                                                reviewCount?.add(row[2].contents)
                                                productImg?.add(row[3].contents)
                                                productPrice?.add(row.last().contents)

                                            }
                                            amazonProducts!![row[0].contents.toLowerCase().replace(" ", "")] = row.last().contents
                                            amazonProductUrls!![row[0].contents.toLowerCase().replace(" ", "")] = row[4].contents

                                        }

                                    }


                                }
                                else {
                                    if (company_name in row[0].contents.toLowerCase()) {
                                        if (row[0].contents.toLowerCase().replace(" ", "") !in productName as ArrayList<String>) {
                                            productTitle?.add(row[0].contents)
                                            productName?.add(row[0].contents.toLowerCase().replace(" ", ""))
                                            ratings?.add(row[1].contents)
                                            reviewCount?.add(row[2].contents)
                                            productImg?.add(row[3].contents)
                                            productPrice?.add(row.last().contents)

                                        }
                                        amazonProducts!![row[0].contents.toLowerCase().replace(" ", "")] = row.last().contents
                                        amazonProductUrls!![row[0].contents.toLowerCase().replace(" ", "")] = row[4].contents

                                    }
                                }
                            }
                        }


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
        for(i in productTitle!!) {
            if (amazonProducts?.contains(i.toLowerCase().replace(" ", "")) == true) {
                amazonProducts!![i.toLowerCase().replace(" ", "")]?.let { amazonPrice.add(it) }
                amazonProductUrls!![i.toLowerCase().replace(" ", "")]?.let { amazonUrl.add(it) }


            } else {
                amazonUrl.add("Not Found")
                amazonPrice.add("Not Found")
            }
            if (flipkartProducts?.contains(i.toLowerCase().replace(" ", "")) == true) {
                flipkartProducts!![i.toLowerCase().replace(" ", "")]?.let { flipkartPrice.add(it) }
                flipkartProductUrls!![i.toLowerCase().replace(" ", "")]?.let { flipkartUrl.add(it) }
            } else {
                flipkartUrl.add("Not Found")
                flipkartPrice.add("Not Found")
            }

        }
        if(amazonPrice.size== 0){
            if(flipkartPrice.size== 0){
                val toast = Toast.makeText(this@AnotherActivity, "No results found", Toast.LENGTH_SHORT)
                toast.show()
            }

        }
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        adapter = productTitle?.let { ratings?.let { it1 -> reviewCount?.let { it2 -> productImg?.let { it3 -> amazonPrice?.let { it4 -> flipkartPrice?.let { it5 -> amazonUrl?.let { it6 -> flipkartUrl?.let { it7 -> intent?.extras?.getString(BRAND).toString()?.let { it8 ->  Adapter(this, it, it1, it2, it3, it4,it5,it6,it7,it8) } } } } } } } } }
        recyclerView!!.adapter = adapter

    }
}