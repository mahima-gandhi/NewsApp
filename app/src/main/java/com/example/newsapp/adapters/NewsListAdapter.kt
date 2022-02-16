package com.example.newsapp.adapters

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.activities.NewsDetailActivity
import com.example.newsapp.interfaces.ItemClickInterface
import com.example.newsapp.interfaces.PaginationRequestInterface
import com.example.newsapp.model.ArticlesItem
import com.letmelisten.app.utils.DESCRIPTION
import com.letmelisten.app.utils.IMAGE
import com.letmelisten.app.utils.TITLE
import com.letmelisten.app.utils.URL

class NewsListAdapter(
    var activity: FragmentActivity?,
    var mNewsList: ArrayList<ArticlesItem?>?,
    var paginationRequestInterface: PaginationRequestInterface,
   var mItemClickInterface: ItemClickInterface?
) :
    RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_news_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val mModel: ArticlesItem? = mNewsList!!.get(position)
        if (mModel != null) {
            holder.descriptionTV.text = mModel.description


        }
        if ( activity!= null) {
            Glide.with(activity!!)
                .load(mModel?.urlToImage)
                .into(holder.imgIV)
        }
        if (position >= mNewsList!!.size - 1) {
            paginationRequestInterface.mPaginationRequestInterface(true)
        }

        holder.itemView.setOnClickListener {
            val mIntent = Intent(activity, NewsDetailActivity::class.java)
            mIntent.putExtra(IMAGE, mModel?.urlToImage)
            mIntent.putExtra(DESCRIPTION, mModel?.description)
            mIntent.putExtra(TITLE, mModel?.title)
            mIntent.putExtra(URL, mModel?.url)
            activity?.startActivity(mIntent)

        }
        holder.bookMarkIV.setOnClickListener {
            holder.bookMarkIV.setImageResource(R.drawable.ic_bookmark_s)
            mItemClickInterface?.onItemClick(mModel)
        }
    }

    override fun getItemCount(): Int {
        return mNewsList?.size!!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var descriptionTV: TextView
        var imgIV: ImageView
        var bookMarkIV: ImageView

        init {
            descriptionTV = itemView.findViewById<View>(R.id.descriptionTV) as TextView
            imgIV = itemView.findViewById<View>(R.id.imgIV) as ImageView
            bookMarkIV = itemView.findViewById<View>(R.id.bookMarkIV) as ImageView

        }
    }

}