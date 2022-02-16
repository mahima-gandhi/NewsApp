package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.ArticlesItem

class BookMarkListAdapter(
    var activity: FragmentActivity?,
   var emp: ArrayList<ArticlesItem>
) :
    RecyclerView.Adapter<BookMarkListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_news_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val mModel: ArticlesItem? = emp!!.get(position)
        if (mModel != null) {
            holder.descriptionTV.text = mModel.description
        }
        if ( activity!= null) {
            Glide.with(activity!!)
                .load(mModel?.urlToImage)
                .placeholder(R.drawable.ic_dummy)
                .into(holder.imgIV)
        }


    }

    override fun getItemCount(): Int {
        return emp?.size!!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var descriptionTV: TextView
        var imgIV: ImageView

        init {
            descriptionTV = itemView.findViewById<View>(R.id.descriptionTV) as TextView
            imgIV = itemView.findViewById<View>(R.id.imgIV) as ImageView

        }
    }

}