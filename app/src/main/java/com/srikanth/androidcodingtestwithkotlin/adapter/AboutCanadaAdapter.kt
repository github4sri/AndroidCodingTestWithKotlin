package com.srikanth.androidcodingtestwithkotlin.adapter

import Rows
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srikanth.androidcodingtestwithkotlin.R
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Adapter class for displaying Recyclerview About AboutCanada
 */
class AboutCanadaAdapter(
    private val context: Context,
    private val mRows: MutableList<Rows>
) : RecyclerView.Adapter<AboutCanadaAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mRows.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = mRows[position].title
        holder.description.text = mRows[position].description
        Glide.with(context)
            .load(mRows[position].imageHref)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.image);
    }

    //Custom view holder will assign the values from the model to corresponding views
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.iv_image
        var title : TextView = itemView.tv_title
        var description : TextView = itemView.tv_description
    }
}
