package com.example.productdisplay.ViewModel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.productdisplay.R
import com.example.productdisplay.model.Product

class productAdapter(private val productList: MutableList<Product>): RecyclerView.Adapter<productAdapter.viewHolder>() {

    class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView =  LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)
        return  viewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.textViewTitle.text = productList[position].title
        val trimmedText = if (productList[position].description.length > 30) {
            productList[position].description.substring(0, 20) + "..."
        } else {
            productList[position].description
        }
        holder.textViewDescription.text = trimmedText
        Glide.with(holder.itemView)
            .load(productList[position].thumbnail)
            .fitCenter()
            .into(holder.imageViewThumbnail)

    }
    fun addData(newData: MutableList<Product>) {
        val currentSize = productList.size
        productList.addAll(newData)
        notifyItemRangeInserted(currentSize, newData.size)
    }
}