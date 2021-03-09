package com.example.Best_Pay

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class Adapter internal constructor(
    context: Context?,
    product_titles: List<String>,
    ratings: List<String>,
    review_counts: List<String>,
    product_imgs: List<String>,
    product_prices: List<String>
) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {
    private val product_titles: List<String>
    private val ratings: List<String>
    private val review_counts: List<String>
    private val product_imgs: List<String>
    private val product_prices: List<String>
    private val inflater: LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.custom_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title = product_titles[position]
        val rating = ratings[position]
        val review_count = review_counts[position]
        val product_img = product_imgs[position]
        val product_price = product_prices[position]
        Picasso.get().load(product_imgs[position]).into(holder.thumbnail)
        holder.productTitle.text = title
        holder.rating.text = rating
        holder.reviewCount.text = review_count
        holder.productPrice.text = product_price
    }

    override fun getItemCount(): Int {
        return product_titles.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var productTitle: TextView
        var rating: TextView
        var reviewCount: TextView
        var thumbnail: ImageView
        var productPrice: TextView

        init {
            productTitle = itemView.findViewById(R.id.product_title)
            rating = itemView.findViewById(R.id.ratings)
            reviewCount = itemView.findViewById(R.id.review_count)
            productPrice =itemView.findViewById(R.id.product_price)
            thumbnail = itemView.findViewById(R.id.product_Img)
        }
    }

    init {
        Log.d("data", "titles -> $product_titles")
        this.product_titles = product_titles
        this.ratings = ratings
        this.review_counts = review_counts
        this.product_imgs = product_imgs
        this.product_prices = product_prices
        inflater = LayoutInflater.from(context)
    }
}