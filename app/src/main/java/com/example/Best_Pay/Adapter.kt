package com.example.Best_Pay

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.squareup.picasso.Picasso


class Adapter internal constructor(
    context: Context?,
    product_titles: List<String>,
    ratings: List<String>,
    review_counts: List<String>,
    product_imgs: List<String>,
    amazon_prices: List<String>,
    flipkart_prices: List<String>,
    amazon_urls: List<String>,
    flipkart_urls: List<String>
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val product_titles: List<String>
    private val ratings: List<String>
    private val review_counts: List<String>
    private val product_imgs: List<String>
    private val amazon_prices: List<String>
    private val flipkart_prices: List<String>
    private val amazon_urls: List<String>
    private val flipkart_urls: List<String>
    private val inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.custom_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = product_titles[position]
        val rating = ratings[position]
        val review_count = review_counts[position]
        val amazon_price = amazon_prices[position]
        val flipkart_price = flipkart_prices[position]
        val amazon_url = amazon_urls[position]
        val flipkart_url = flipkart_urls[position]
        Picasso.get().load(product_imgs[position]).into(holder.thumbnail)

        if(!Python.isStarted())
            Python.start( AndroidPlatform(holder.productTitle.context))
        var py3 : Python = Python.getInstance()
        var pyo3 : PyObject = py3.getModule("least_price")
        var obj3 : PyObject = pyo3.callAttr("main",title)
        var least:Float = obj3.toFloat()
        if(amazon_price!= "unavailable" && flipkart_price!="unavailable" && amazon_price!="Not Found" && flipkart_price!="Not Found"){
            if(amazon_price.toFloat() <= least || flipkart_price.toFloat() <= least){
                holder.lowest_or_not.text="Lowest ever"
            }

        }


        holder.productTitle.text = title
        holder.rating.text = rating
        holder.reviewCount.text = review_count
        holder.amazonPrice.text = amazon_price
        holder.flipkartPrice.text = flipkart_price
        holder.productTitle.setOnClickListener{
            val context = holder.productTitle.context
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra(ProductActivity.title, title)
            context.startActivity(intent)

        }
        holder.amazonPrice.setOnClickListener{
            val context = holder.amazonPrice.context
            if(amazon_url != "Not Found"){
                val queryUrl: Uri = Uri.parse(amazon_url)
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                context.startActivity(intent)
            }
            else{
                val toast = Toast.makeText(context, "Not found", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
        holder.flipkartPrice.setOnClickListener{
            val context = holder.flipkartPrice.context
            if(flipkart_url != "Not Found"){
                val queryUrl: Uri = Uri.parse(flipkart_url)
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                context.startActivity(intent)
            }
            else{
                val toast = Toast.makeText(context, "Not found", Toast.LENGTH_SHORT)
                toast.show()
            }

        }

    }

    override fun getItemCount(): Int {
        return product_titles.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var lowest_or_not : TextView
        var productTitle: TextView
        var rating: TextView
        var reviewCount: TextView
        var thumbnail: ImageView
        var amazonPrice: TextView
        var flipkartPrice: TextView

        init {
            lowest_or_not = itemView.findViewById(R.id.lowest_or_not)
            productTitle = itemView.findViewById(R.id.product_title)
            rating = itemView.findViewById(R.id.ratings)
            reviewCount = itemView.findViewById(R.id.review_count)
            amazonPrice = itemView.findViewById(R.id.amazon_price)
            flipkartPrice = itemView.findViewById(R.id.flipkart_price)
            thumbnail = itemView.findViewById(R.id.product_Img)
        }
    }

    init {
        Log.d("data", "titles -> $product_titles")
        this.product_titles = product_titles
        this.ratings = ratings
        this.review_counts = review_counts
        this.product_imgs = product_imgs
        this.amazon_prices = amazon_prices
        this.flipkart_prices = flipkart_prices
        this.amazon_urls = amazon_urls
        this.flipkart_urls = flipkart_urls
        inflater = LayoutInflater.from(context)
    }
}